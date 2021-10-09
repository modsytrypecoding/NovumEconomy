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

public class Take implements CommandExecutor {

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
                        p.sendMessage(MainDis.Prefix +MainDis.MissingMainPerms);
                        return true;
                    }

                    if(p.hasPermission("NE.eco.take")) {
                        if(isDouble(args[1])) {
                            if(args[0].equalsIgnoreCase(p.getName())) {
                                p.sendMessage(MainDis.Prefix +MainDis.SameName);
                            }else {
                                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                                if(!t.isOnline()) {
                                    if(t.hasPlayedBefore()) {
                                        if(Main.getImplementer().getBalance(t) < Double.parseDouble(args[1])) {
                                            p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeError); //config
                                            p.sendMessage(MainDis.Prefix +MainDis.BalanceStranger.replace("%target%", t.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(t))).replace("%currency%", MainDis.CurName));
                                        }else {
                                            Main.getImplementer().withdrawPlayer(t, Double.parseDouble(args[1]));
                                            p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeSuccess.replace("%target%", t.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                            p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeMessage.replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                        }
                                    }else {
                                        p.sendMessage(MainDis.Prefix + MainDis.NoBalance);
                                    }

                                }else {
                                    Player online = t.getPlayer();
                                    if(Main.getImplementer().getBalance(online) < Double.parseDouble(args[1])) {
                                        p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeError);
                                        p.sendMessage(MainDis.Prefix +MainDis.BalanceStranger.replace("%target%", t.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(online.getName()))).replace("%currency%", MainDis.CurName));
                                    }else {
                                        Main.getImplementer().withdrawPlayer(online, Double.parseDouble(args[1]));
                                        p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeSuccess.replace("%target%", online.getName()).replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                        p.sendMessage(MainDis.Prefix +MainDis.MoneyTakeMessage.replace("%value%", args[1]).replace("%currency%", MainDis.CurName));
                                    }
                                }
                            }


                        }else {
                            p.sendMessage(MainDis.Prefix +MainDis.WrongInput); //config
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
