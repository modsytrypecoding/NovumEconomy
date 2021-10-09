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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Money implements CommandExecutor {

    public static HashMap<String, Double> Purse = new HashMap<>();

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
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
                    if(args.length == 0) {
                        p.sendMessage(MainDis.Prefix +MainDis.Balance.replace("%value%", String.valueOf(Main.getImplementer().getBalance(p.getName()))).replace("%currency%", MainDis.CurName));
                    }
                    if(args.length == 2) {
                        if(args[0].equalsIgnoreCase("top")) {
                            Object temp = parseUIntOrNull(args[1]);
                            if(temp == null)
                            {
                                p.sendMessage(MainDis.Prefix +MainDis.PageNumberOver0);
                            }else {
                                Integer arg_numb = (Integer) temp;
                                Map<String, Double> temp_map_sorted = sort(Purse);
                                int temp_seitenanzahl = (int) Math.ceil(temp_map_sorted.size() * 0.1);
                                int siteToShow;
                                if(temp_seitenanzahl < arg_numb) siteToShow = temp_seitenanzahl;
                                else siteToShow = arg_numb;
                                int showFirst = siteToShow * 10 - 9;
                                int showLast = siteToShow * 10 - 9 + 10 -1;
                                if (showLast > temp_map_sorted.size()) {
                                    showLast = Purse.size();
                                }
                                p.sendMessage(getMSG(siteToShow));
                            }
                        }
                    }
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("top")) {
                            try {
                                PreparedStatement statement = MySqlConnector.connection.prepareStatement("SELECT * FROM PlayerPurse");
                                ResultSet result = statement.executeQuery();
                                while(result.next()) {
                                    String UUID = result.getString("UUID");
                                    Double purse = result.getDouble("Purse");
                                    Purse.put(UUID, purse);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } {

                            }
                            p.sendMessage(getMSG(1));

                        }else {
                            if(p.hasPermission("NE.eco.seeMoney")) {
                                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                                if(!t.isOnline()) {
                                    if(t.hasPlayedBefore()) {
                                        p.sendMessage(MainDis.Prefix +MainDis.BalanceStranger.replace("%target%", t.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(t))).replace("%currency%", MainDis.CurName));

                                    }else {
                                        p.sendMessage(MainDis.Prefix + MainDis.NoBalance);
                                    }
                                }else {
                                    Player online = t.getPlayer();
                                    p.sendMessage(MainDis.Prefix +MainDis.BalanceStranger.replace("%target%", online.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(online.getName()))).replace("%currency%", MainDis.CurName));


                                }
                            }
                        }

                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return false;
    }
    public static Map<String, Double> sort(Map<String, Double> unsortedMap)
    {
        //sorts given Map after DoubleValue highest Value at the Top
        return unsortedMap.entrySet().stream()
                .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
    }

    public static Object parseUIntOrNull(String value) {
        //if parsable return int temp if not null
        try {
            if(isInt(value)) {
                int temp =  Integer.parseInt(value);
                if(temp > 0) return temp;
            }
            return null;

        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static String getMSG(Integer site)
    {
        //Message-Creation (PlayerName : Value CurrencyName)
        String MSG = "====Spieler-Top===Seite " + site + "====\n";
        int i = site * 10 -9;
        for(Map.Entry<String, Double> entry : sort(Purse).entrySet())
        {
            OfflinePlayer off = Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey()));
            MSG += i + ": " + off.getName() +  ": §a" + entry.getValue() + "§r " + MainDis.CurName +  "\n";
            i++;
        }
        return MSG;
    }

}
