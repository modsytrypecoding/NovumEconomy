package de.Initium.Eco.Dispatcher;

import de.Initium.Eco.Main.Main;
import org.bukkit.plugin.java.JavaPlugin;

public class MainDis extends JavaPlugin {

    public static final String Prefix = Main.config().getString("settings.Messages.Prefix");
    public static final String CurName = Main.config().getString("settings.Eco.currencyName");
    public static final String NoPerms = Main.config().getString("settings.Messages.NoPerms");
    public static final Double StartMoney = Main.config().getDouble("settings.Eco.startMoney");
    public static final String WährungCreationErfolg = Main.config().getString("settings.Messages.WährungsNamenBestätigung");
    public static final String SameCurrencyName = Main.config().getString("settings.Messages.GleicherWährungsName");
    public static final String SameName = Main.config().getString("settings.Messages.NichtSelber");
    public static final String MissingMainPerms = Main.config().getString("settings.Messages.NoMainPerms");
    public static final String WrongInput = Main.config().getString("settings.Messages.UngültigerBetrag");
    public static final String NoBalance = Main.config().getString("settings.Messages.KeinKonto");
    public static final String InputNumberToBig = Main.config().getString("settings.Messages.GeldBetragZuHoch");
    public static final String MoneyGiveSuccess = Main.config().getString("settings.Messages.GeldErfolgreichGeschickt");
    public static final String MoneyPaySuccess = Main.config().getString("settings.Messages.GeldErfolgreichGezahlt");
    public static final String MoneyReceiveSuccess = Main.config().getString("settings.Messages.GeldErfolgreichErhalten");
    public static final String MoneySetSuccess = Main.config().getString("settings.Messages.GeldErfolgreichGesetzt");
    public static final String MoneyTakeSuccess = Main.config().getString("settings.Messages.GeldErfolgreichGenommen");
    public static final String MoneyTakeError = Main.config().getString("settings.Messages.FehlerGeldNehmen");
    public static final String MoneyTakeMessage = Main.config().getString("settings.Messages.FehlerGeldNehmen");
    public static final String MoneySetMessage = Main.config().getString("settings.Messages.GeldGesetztNachricht");
    public static final String Balance = Main.config().getString("settings.Messages.KontoStand");
    public static final String PageNumberOver0 = Main.config().getString("settings.Messages.PageNumberOver0");
    public static final String BalanceStranger = Main.config().getString("settings.Messages.KontoStandFremd");
    public static final String BalanceToLow = Main.config().getString("settings.Messages.ZuWenigGeld");



}
