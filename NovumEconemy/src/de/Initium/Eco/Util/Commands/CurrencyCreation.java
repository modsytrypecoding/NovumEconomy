package de.Initium.Eco.Util.Commands;

import de.Initium.Eco.Dispatcher.MainDis;
import de.Initium.Eco.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CurrencyCreation implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (!(p.hasPermission("NE.eco.Main"))) {
                p.sendMessage(MainDis.Prefix +MainDis.MissingMainPerms); //config
                return true;
            }
            if(p.hasPermission("NE.eco.createCurrency")) {
                if(args[0].equalsIgnoreCase(Main.config().getString("settings.Eco.currencyName"))) {
                    p.sendMessage(MainDis.Prefix +MainDis.SameCurrencyName);
                }else {
                    Main.config().set("settings.Eco.currencyName", args[0]); //args[1] = currency Name
                    p.sendMessage(MainDis.Prefix + MainDis.WährungCreationErfolg.replace("%currency%", args[0]));
                }
            }else {
                p.sendMessage(MainDis.Prefix +MainDis.NoPerms);
            }
        }


        return false;
    }
}
