package de.Initium.Eco.Main;

import de.Initium.Eco.Util.Commands.*;
import de.Initium.Eco.Util.Listener.Bukkit_JoinListener;
import de.Initium.Eco.Util.VaultMain.EconomyImplementer;
import de.Initium.Eco.Util.VaultMain.VaultHook;
import net.milkbowl.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static Main plugin;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static final File configfile = new File("plugins//NovumEconomy//config.yml");
    private static YamlConfiguration configfileConfiguration = YamlConfiguration.loadConfiguration(configfile);
    private static final File saves = new File("plugins//NovumEconomy//saves.yml");
    private static YamlConfiguration safeFile = YamlConfiguration.loadConfiguration(saves);

    public static EconomyImplementer economyImplementer;

    public static final HashMap<UUID, Double> playerBank = new HashMap<>();

    public void onEnable() {

        if(getServer().getPluginManager().getPlugin("Vault").equals(null)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Vault konnte nicht gefunden werden! " + getName() + " wird deaktiviert!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;
        VaultHook.Hook();
        getCommand("money").setExecutor(new Money());
        getCommand("CNC").setExecutor(new CurrencyCreation());
        getCommand("give").setExecutor(new Give());
        getCommand("pay").setExecutor(new Pay());
        getCommand("take").setExecutor(new Take());
        getCommand("set").setExecutor(new Set());

        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(new Bukkit_JoinListener(), this);

        //loading Bank Values
        List<String> storage = getSaves().getStringList("players");
        for(String uuid : storage) {
            String[] words = uuid.split(":");
            playerBank.put(UUID.fromString(words[0]), Double.parseDouble(words[1]));
        }
        getSaves().set("players", null);
        saveSaves();
        if (!configfile.exists() || !configfileConfiguration.isSet("settings")) {
            configfileConfiguration.options().header("Placeholder\n" +
                    "%player% -> Ausführender Spieler-Name\n" +
                    "%target% -> Target-Spieler-Name\n" +
                    "%value% -> Betrag in einer Transaktion\n" +
                    "%currency% -> Der momentane Name der Währung\n" +
                    " \n" + "Ein Farbwechsel der Texte ist mit den ganz normalen ColorCodes möglich!\n" +
                    "ColorCode Übersicht -> Googel: Minecraft ColorCode (erstes Bild)\n"+
                    "Das & Zeichen muss jedoch mit einem § Zeichen ersetzt werden\n" +
                    "Beispiel: §c statt &c\n" + " ");
            configfileConfiguration.createSection("settings");
            configfileConfiguration.createSection("settings.Eco");
            configfileConfiguration.set("settings.Eco.currencyName", "Kronen");
            configfileConfiguration.set("settings.Eco.startMoney", 100);
            configfileConfiguration.createSection("settings.Messages");
            configfileConfiguration.set("settings.Messages.NoPerms", "Dazu hast du keine Berechtigungen!");
            configfileConfiguration.set("settings.Messages.WährungsNamenBestätigung", "Die Währung %currency% wurde Erstellt!");
            configfileConfiguration.set("settings.Messages.GleicherWährungsName", "Diese Währung hat bereits diesen Namen");
            configfileConfiguration.set("settings.Messages.NichtSelber", "Du kannst dich bei diesem Vorgang nicht selber auswählen!");
            configfileConfiguration.set("settings.Messages.NoMainPerms", "Dir fehlen die Rechte");
            configfileConfiguration.set("settings.Messages.UngültigerBetrag", "Der von dir angegebene Betrag ist ungültig!");
            configfileConfiguration.set("settings.Messages.KeinKonto", "Dieser Spieler hat noch kein Konto!");
            configfileConfiguration.set("settings.Messages.GeldBetragZuHoch", "Der von dir gewählte Betrag ist zu hoch!");
            configfileConfiguration.set("settings.Messages.GeldErfolgreichGeschickt", "Du hast dem Spieler %target% %value% %currency% gegeben!");
            configfileConfiguration.set("settings.Messages.GeldErfolgreichGezahlt", "Du hast dem Spieler %target% %value% %currency% gezahlt!");
            configfileConfiguration.set("settings.Messages.GeldErfolgreichErhalten", "Der Spieler %player% hat dir %value% %currency% gezahlt");
            configfileConfiguration.set("settings.Messages.GeldErfolgreichGesetzt", "Du hast den KontoStand von %target% erfolgreich auf %value% %currency% gesetzt!");
            configfileConfiguration.set("settings.Messages.GeldErfolgreichGenommen", "Du hast dem Spieler %target% %value% %currency% entfernt!");
            configfileConfiguration.set("settings.Messages.FehlerGeldNehmen", "Der Kontostand des Spielers ist zu niedrig!");
            configfileConfiguration.set("settings.Messages.KontoStand", "Dein Kontostand beträgt %value% %currency%");
            configfileConfiguration.set("settings.Messages.KontoStandFremd", "Der Kontostand von %target% beträgt %value% %currency%");
            configfileConfiguration.set("settings.Messages.PageNumberOver0", "Die eingegebene Seitenzahl muss eine Nummer über 0 sein");
            configfileConfiguration.set("settings.Messages.ZuWenigGeld", "Du hast zu wenig Geld!");


            saveConfiguration();
        }
        if(!saves.exists() || !safeFile.isSet("players")) {
            safeFile.createSection("players");
            saveSaves();
        }
    }

    @Override
    public void onDisable() {
        log.info(getDescription().getName() + " wurde beendet!");

        //saving Bank Values
        List<String> storage = getSaves().getStringList("players");
        for(UUID uuid : playerBank.keySet()) {
           OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
           storage.add(p.getUniqueId().toString() + ":" + Main.playerBank.get(p.getUniqueId()));
        }
        getSaves().set("players", storage);
        saveSaves();
        VaultHook.unHook();

    }

    public static Main getPlugin() {
        return plugin;
    }

    public static EconomyImplementer getImplementer() {
        economyImplementer = new EconomyImplementer();
        return economyImplementer;
    }

    public static YamlConfiguration config() {
        return configfileConfiguration;
    }

    public static YamlConfiguration getSaves() {
        return safeFile;
    }

    public static void saveConfiguration() {
        try {
            configfileConfiguration.save(configfile);
            configfileConfiguration = YamlConfiguration.loadConfiguration(configfile);
        } catch (IOException e) {
            getPlugin().getLogger().info("Fehler beim Speichern der config.yml: " + e);
        }
    }

    public static void saveSaves() {
        try {
            safeFile.save(saves);
            safeFile = YamlConfiguration.loadConfiguration(saves);
        } catch (IOException e) {
            getPlugin().getLogger().info("Fehler beim Speichern der saves.yml: " + e);
        }
    }
}
