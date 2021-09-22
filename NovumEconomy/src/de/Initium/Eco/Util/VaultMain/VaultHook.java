package de.Initium.Eco.Util.VaultMain;

import de.Initium.Eco.Main.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {


    private Economy provider;

    public static void Hook() {
        Bukkit.getServicesManager().register(Economy.class, Main.getImplementer(), Main.getPlugin(), ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked into " + ChatColor.AQUA +  Main.getPlugin().getName());
    }

    public static void unHook() {
        Bukkit.getServicesManager().unregister(Economy.class, Main.getImplementer());
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "VaultAPI unhooked from " + ChatColor.AQUA +  Main.getPlugin().getName());
    }
}
