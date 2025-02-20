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

public class Give implements CommandExecutor {

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

                    if(p.hasPermission("NE.eco.give")) {
                        if(args.length== 2) {
                            if(isDouble(args[1])) {
                                if(!(args[1].contains(".") || args[1].contains("-"))) {
                                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                                    if(!t.isOnline()) {
                                        if(t.hasPlayedBefore()) {
                                            if(Double.parseDouble(args[1]) > Integer.MAX_VALUE) {
                                                p.sendMessage(MainDis.Prefix +MainDis.InputNumberToBig); //config
                                            }else {
                                                Main.getImplementer().depositPlayer(t, Double.parseDouble(args[1]));
                                                p.sendMessage(MainDis.Prefix +MainDis.MoneyGiveSuccess.replace("%target%", t.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                            }
                                        }else {
                                            p.sendMessage(MainDis.Prefix + MainDis.NoBalance);
                                        }

                                    }else {
                                        Player online = t.getPlayer();
                                        if(Double.parseDouble(args[1]) > Integer.MAX_VALUE) {
                                            p.sendMessage(MainDis.Prefix +MainDis.InputNumberToBig); //config
                                        }else {
                                            Main.getImplementer().depositPlayer(online.getName(), Double.parseDouble(args[1]));
                                            p.sendMessage(MainDis.Prefix +MainDis.MoneyGiveSuccess.replace("%target%", t.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                        }
                                    }
                                }else {
                                    p.sendMessage(MainDis.Prefix +MainDis.WrongInput);
                                }

                            }else{
                                p.sendMessage(MainDis.Prefix +MainDis.WrongInput); //config
                            }
                        }else {
                            p.sendMessage("ArgsFehler "); //config
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



        return false;
    }
}
