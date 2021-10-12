package de.Initium.Eco.Util.Listener;


import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.MySqlConnector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Bukkit_JoinListener implements Listener {

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) throws SQLException {
        if(!e.getPlayer().hasPlayedBefore()) {
            PreparedStatement statement = MySqlConnector.connection.prepareStatement("INSERT INTO PlayerPurse(UUID, Purse) VALUES (?,?)");
            statement.setString(1, e.getPlayer().getUniqueId().toString());
            statement.setDouble(2, MainDis.StartMoney);
            statement.execute();
        }

    }
}