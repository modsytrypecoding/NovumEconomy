package de.Initium.Eco.Util.Commands;

import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.MySqlConnector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetPurse implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("NE.eco.SetPurse")) {
                PreparedStatement statement = null;
                try {
                   if (MySqlConnector.connection.isClosed()) {
                       MySqlConnector.connect();

                    }else {
                       statement = MySqlConnector.connection.prepareStatement("INSERT INTO PlayerPurse(UUID, Purse) VALUES (?,?)");
                       statement.setString(1, p.getUniqueId().toString());
                       statement.setDouble(2, MainDis.StartMoney);
                       statement.execute();
                   }

                } catch (SQLException | ClassNotFoundException e) {
                    p.sendMessage(MainDis.Prefix + "Dieser Spieler hat bereits ein Konto");
                    e.printStackTrace();
                }

            }else {
                p.sendMessage(MainDis.Prefix + MainDis.NoPerms);
            }
        }
        return false;
    }
}
