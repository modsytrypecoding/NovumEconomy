package de.Initium.Eco.Util.Commands;

import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Money implements CommandExecutor {
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
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (!(p.hasPermission("NE.eco.Main"))) {
                p.sendMessage(MainDis.MissingMainPerms); //config
                return true;
            }
            if(args.length == 0) {
                if(Main.playerBank.containsKey(p.getUniqueId())) {
                    p.sendMessage(MainDis.Balance.replace("%value%", String.valueOf(Main.getImplementer().getBalance(p.getName()))).replace("%currency%", MainDis.CurName));
                }
            }
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("top")) {
                        Object temp = parseUIntOrNull(args[1]);
                        if(temp == null)
                        {
                            p.sendMessage(MainDis.PageNumberOver0);
                        }else {
                            Integer arg_numb = (Integer) temp;
                            //Sort HashMap Values after Size
                            Map<UUID, Double> temp_map_sorted = sort(Main.playerBank);

                            //Get the highest possible Site-Number
                            int temp_seitenanzahl = (int) Math.ceil(temp_map_sorted.size() * 0.1);

                            //Check args[1] above highest Page
                            // if so -> get the last Page
                            int siteToShow;
                            if(temp_seitenanzahl < arg_numb) siteToShow = temp_seitenanzahl;
                            else siteToShow = arg_numb;

                            //Calculation of first and last Position of Player to get
                            int showFirst = siteToShow * 10 - 9;
                            int showLast = siteToShow * 10 - 9 + 10 -1;

                            //If calculated last Position is higher than all positions, limit last position
                            if (showLast > temp_map_sorted.size())
                                showLast = Main.playerBank.size();

                            //Creation of ArrayList of all Gilden to show
                            ArrayList<UUID> showGilden = new ArrayList<>();
                            for(int i = showFirst; i <= showLast; i++)
                            {
                                showGilden.add(new ArrayList<>(temp_map_sorted.keySet()).get(i-1));
                            }
                            p.sendMessage(getMSG(siteToShow));
                        }






                }
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("top")) {
                    p.sendMessage(getMSG(1));
                }else {
                    if(p.hasPermission("NE.eco.seeMoney")) {
                        OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                        if(!t.isOnline()) {
                            if(Main.playerBank.containsKey(t.getUniqueId())) {
                                p.sendMessage(MainDis.BalanceStranger.replace("%target%", t.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(t.getName()))).replace("%currency%", MainDis.CurName));
                            }else {
                                p.sendMessage(MainDis.NoBalance); //config
                            }
                        }else {
                            Player online = t.getPlayer();
                            if(Main.playerBank.containsKey(online.getUniqueId())) {
                                p.sendMessage(MainDis.BalanceStranger.replace("%target%", online.getName()).replace("%value%", String.valueOf(Main.getImplementer().getBalance(online.getName()))).replace("%currency%", MainDis.CurName));
                            }else {
                                p.sendMessage(MainDis.NoBalance); //config
                            }

                        }
                    }
                }

            }
        }
        return false;
    }
    public static Map<UUID, Double> sort(Map<UUID, Double> unsortedMap)
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
        for(Map.Entry<UUID, Double> entry : sort(Main.playerBank).entrySet())
        {
            OfflinePlayer off = Bukkit.getOfflinePlayer(entry.getKey());
            MSG += i + ": " + off.getName() +  ": §a" + entry.getValue() + "§r " + MainDis.CurName +  "\n";
            i++;
        }
        return MSG;
    }

}
