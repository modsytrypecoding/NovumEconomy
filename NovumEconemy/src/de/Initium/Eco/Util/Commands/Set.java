package de.Initium.Eco.Util.Commands;

import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.Main;
import de.Initium.Eco.Main.MySqlConnector;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Set implements CommandExecutor {

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            if (MySqlConnector.connection.isClosed()) {
                MySqlConnector.connect();

            }else {
                if(sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!(p.hasPermission("NE.eco.Main"))) {
                        p.sendMessage(MainDis.Prefix +MainDis.MissingMainPerms); //config
                        return true;
                    }
                    if(isDouble(args[1])) {
                        if(p.hasPermission("NE.eco.set")) {
                            OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                            if(!t.isOnline()) {
                                if(t.hasPlayedBefore()) {
                                    Main.getImplementer().withdrawPlayer(t, Main.getImplementer().getBalance(t));
                                    Main.getImplementer().depositPlayer(t, Double.parseDouble(args[1]));
                                    p.sendMessage(MainDis.Prefix +MainDis.MoneySetSuccess.replace("%target%", t.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                    p.sendMessage(MainDis.Prefix +MainDis.MoneySetMessage.replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                }else {
                                    p.sendMessage(MainDis.Prefix + MainDis.NoBalance);
                                }

                            }else {
                                Player online = t.getPlayer();
                                Main.getImplementer().withdrawPlayer(online.getName(), Main.getImplementer().getBalance(online));
                                Main.getImplementer().depositPlayer(online.getName(), Double.parseDouble(args[1]));
                                p.sendMessage(MainDis.Prefix +MainDis.MoneySetMessage.replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                            }
                        }
                    }else {
                        p.sendMessage(MainDis.Prefix +MainDis.WrongInput);
                    }

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return false;
    }
}
