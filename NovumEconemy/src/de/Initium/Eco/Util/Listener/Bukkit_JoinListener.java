package de.Initium.Eco.Util.Listener;


import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.MySqlConnector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Bukkit_JoinListener implements Listener {
   public static PreparedStatement statement = null;
   public static PreparedStatement statement2 = null;

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) throws SQLException {
        if(!e.getPlayer().hasPlayedBefore()) {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("INSERT INTO PlayerInformationen(UUID, balance) VALUES (?,?) ON DUPLICATE KEY UPDATE balance = ?");
            statement.setString(1, e.getPlayer().getUniqueId().toString());
            statement.setDouble(2, MainDis.StartMoney);
            statement.setDouble(3, MainDis.StartMoney);
            statement.execute();
        }

    }
}