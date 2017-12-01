package com.tatemylove.SwiftEconomy.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQL {
    public static Connection connection;

    public MySQL (String ip, String userName, String password, String db){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
            createEconomy();
            createLockedAccounts();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void createEconomy() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SWIFTeco(uuid varchar(36), money int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createLockedAccounts() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SWIFTlocked(uuid varchar(36), status varchar(36))");
        ps.executeUpdate();
        ps.close();
    }
}
