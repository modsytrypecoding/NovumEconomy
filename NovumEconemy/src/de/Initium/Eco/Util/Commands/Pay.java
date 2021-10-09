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

public class Pay implements CommandExecutor {

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
                    if(args.length == 2) {
                        if(isDouble(args[1])) {
                            OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                            if(args[0].equals(p.getName())) {
                                p.sendMessage(MainDis.Prefix +MainDis.SameName);
                            }else {
                                if(!t.isOnline()) {
                                    if(t.hasPlayedBefore()) {
                                        if(Double.parseDouble(args[1]) > Main.getImplementer().getBalance(p)) {
                                            p.sendMessage(MainDis.Prefix +MainDis.BalanceToLow); //config
                                        }else {
                                            Main.getImplementer().withdrawPlayer(p.getName(), Double.parseDouble(args[1]));
                                            Main.getImplementer().depositPlayer(t, Double.parseDouble(args[1]));
                                            p.sendMessage(MainDis.Prefix +MainDis.MoneyPaySuccess.replace("%target%", t.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                        }
                                    }else {
                                        p.sendMessage(MainDis.Prefix + MainDis.NoBalance);
                                    }

                                }else {
                                    Player online = t.getPlayer();
                                    if(Double.parseDouble(args[1]) > Main.getImplementer().getBalance(p)) {
                                        p.sendMessage(MainDis.Prefix +MainDis.BalanceToLow); //config
                                    }else {
                                        Main.getImplementer().withdrawPlayer(p.getName(), Double.parseDouble(args[1]));
                                        Main.getImplementer().depositPlayer(t, Double.parseDouble(args[1]));
                                        p.sendMessage(MainDis.Prefix +MainDis.MoneyPaySuccess.replace("%target%", online.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                        p.sendMessage(MainDis.Prefix +MainDis.MoneyReceiveSuccess.replace("%player%", p.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                    }
                                }



                            }
                        }else {
                            p.sendMessage(MainDis.Prefix +MainDis.WrongInput); //config
                        }
                    }else {
                        p.sendMessage("ArgsFehler"); //config
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



        return false;
    }
}
