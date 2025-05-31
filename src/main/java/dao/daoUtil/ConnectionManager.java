package dao.daoUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String DB_URL_KEY = "db.url";

    private ConnectionManager() {
    }

    static {
        loadDriver();
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(DB_URL_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
