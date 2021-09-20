package de.Initium.Eco.Util.Listener;

import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class Bukkit_JoinListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        if(!(Main.playerBank.containsKey(e.getPlayer().getUniqueId()))) {
            Main.playerBank.put(e.getPlayer().getUniqueId(), MainDis.StartMoney); //config
        }
    }
}
