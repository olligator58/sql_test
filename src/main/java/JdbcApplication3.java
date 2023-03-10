import java.sql.*;

public class JdbcApplication3 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", "root");
             Statement statement = connection.createStatement() ) {

            try {
                connection.setAutoCommit(false); //отменяем автокоммит при выполнении execute
                int rowsCount1 = statement.executeUpdate("UPDATE employee SET" +
                                                              " salary = salary + 1000");
                int rowsCount2 = statement.executeUpdate("UPDATE employee SET" +
                                                              " salary = salary + 1000");
                int rowsCount3 = statement.executeUpdate("UPDATE опечатка приведет к исключению");
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
            }
            // пример с точкой останова
            try {
                connection.setAutoCommit(false);
                int rowsCount1 = statement.executeUpdate("UPDATE employee SET" +
                                                              " salary = salary + 1000");
                int rowsCount2 = statement.executeUpdate("UPDATE employee SET" +
                                                              " salary = salary - 1000");
                Savepoint savepoint = connection.setSavepoint();
                try {
                    int rowsCount3 = statement.executeUpdate("UPDATE опечатка приведет к исключению");
                } catch (Exception e) {
                    connection.rollback(savepoint);
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
