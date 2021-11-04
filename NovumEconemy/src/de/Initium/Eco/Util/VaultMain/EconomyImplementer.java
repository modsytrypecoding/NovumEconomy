package de.Initium.Eco.Util.VaultMain;

import de.Initium.Eco.Main.MySqlConnector;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {


    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double purse = 0;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("SELECT balance FROM PlayerInformationen WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if(!(result == null)) {
                while (result.next()) {
                    purse = result.getDouble("balance");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purse;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        double purse = 0;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("SELECT balance FROM PlayerInformationen WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if(!(result == null)) {
                while (result.next()) {
                    purse = result.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purse;

    }

    @Override
    public double getBalance(String s, String s1) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double purse = 0;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("SELECT balance FROM PlayerInformationen WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if(!(result == null)) {
                while (result.next()) {
                    purse = result.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purse;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        UUID uuid = offlinePlayer.getUniqueId();
        double purse = 0;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("SELECT balance FROM PlayerInformationen WHERE UUID = ?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if(!(result == null)) {
                while (result.next()) {
                    purse = result.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purse;
    }

    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double oldbalance = getBalance(p.getName());
        //check if oldbalance > v
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, oldbalance - v);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldbalance = getBalance(offlinePlayer);
        //check if oldbalance > v
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, oldbalance - v);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double oldbalance = getBalance(p.getName());
        //check if oldbalance > v
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, oldbalance - v);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldbalance = getBalance(offlinePlayer);
        //check if oldbalance > v
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, oldbalance - v);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double oldbalance = getBalance(p.getName());
        double newbalance = oldbalance + v;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, newbalance);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldbalance = getBalance(offlinePlayer);
        double newbalance = oldbalance + v;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, newbalance);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        Player p = Bukkit.getPlayer(s);
        UUID uuid = p.getUniqueId();
        double oldbalance = getBalance(p.getName());
        double newbalance = oldbalance + v;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, newbalance);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldbalance = getBalance(offlinePlayer);
        double newbalance = oldbalance + v;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        try {
            statement2 = MySqlConnector.connection.prepareStatement("USE Storage");
            statement2.execute();
            statement = MySqlConnector.connection.prepareStatement("UPDATE PlayerInformationen SET balance = ? WHERE UUID = ?");
            statement.setDouble(1, newbalance);
            statement.setString(2, uuid.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
