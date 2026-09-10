package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private String url = "jdbc:mysql://127.0.0.1:3306/learning";
    private String root = "root";
    private String password = "kamikotosUzUme19993573547";

    public Connection setting() {
        try {
            return DriverManager.getConnection(this.url, this.root, this.password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

