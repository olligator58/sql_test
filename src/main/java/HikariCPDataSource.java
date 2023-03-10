import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class HikariCPDataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private HikariCPDataSource() {

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String occupation = resultSet.getString("occupation");
                int salary = resultSet.getInt("salary");
                int age = resultSet.getInt("age");
                Date date = resultSet.getDate("join_date");
                System.out.println(String.format("%d.\tid = %d, name = %s, occupation = %s, salary = %d, age = %d, " +
                                "join_date = %s",
                        resultSet.getRow(), id, name, occupation, salary, age, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
