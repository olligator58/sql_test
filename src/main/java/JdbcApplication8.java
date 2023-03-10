import com.sun.rowset.JdbcRowSetImpl;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class JdbcApplication8 {

    public static void main(String[] args) {
        // создаем кэшированный RowSet из ResultSet
        CachedRowSet rowSet = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test",
                                                                "root", "root");
             Statement statement = connection.createStatement()) {

            RowSetFactory factory = RowSetProvider.newFactory();
            rowSet = factory.createCachedRowSet();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            rowSet.populate(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // читаем данные из кэшированного RowSet после закрытия соединения с базой
        System.out.println("Данные из CachedRowSet: ");
        printEmployee(rowSet);

        // создаем полностью автономный RowSet с собственным подключением к базе
        try {
            JdbcRowSet rowSet1 = RowSetProvider.newFactory().createJdbcRowSet();
            rowSet1.setUrl("jdbc:mysql://localhost:3306/test");
            rowSet1.setUsername("root");
            rowSet1.setPassword("root");

            rowSet1.setCommand("SELECT * FROM employee");
            rowSet1.execute();
            System.out.println("Данные из JdbcRowSet: ");
            printEmployee(rowSet1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // создаем RowSet с подключением к текущему соединению
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test",
                                                                "root", "root")) {
            JdbcRowSet rowSet2 = new JdbcRowSetImpl(connection);
            rowSet2.setCommand("SELECT * FROM employee");
            rowSet2.execute();
            System.out.println("Данные из другого JdbcRowSet: ");
            printEmployee(rowSet2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //добавление строк через RowSet
        try {
            // только автономный RowSet может вносить изменения в базу
            CachedRowSet rs = RowSetProvider.newFactory().createCachedRowSet();
            rs.setUrl("jdbc:mysql://localhost:3306/test");
            rs.setUsername("root");
            rs.setPassword("root");

            rs.setCommand("SELECT * FROM employee");
            rs.execute();

            rs.moveToInsertRow();
            rs.updateInt("id", 101);
            rs.updateString("name", "Шипкин Николай");
            rs.updateString("occupation", "Веб дизайнер");
            rs.updateInt("salary", 150000);
            rs.updateInt("age", 38);
            rs.updateDate("join_date", new Date(999999999));
            rs.insertRow();
            rs.moveToCurrentRow();
            rs.beforeFirst();
            System.out.println("Данные из обновленного RowSet: ");
            printEmployee(rs);
            // теперь еще нужно залить изменения в базу
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                                                               "root", "root");
            connection.setAutoCommit(false); //нужно для синхронизации
            rs.acceptChanges(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // удаляем добавленного сотрудника
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test",
                "root", "root");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM employee WHERE id = 101");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printEmployee(RowSet rowSet) {
        try {
            while (rowSet.next()) {
                int id = rowSet.getInt("id");
                String name = rowSet.getString("name");
                String occupation = rowSet.getString("occupation");
                int salary = rowSet.getInt("salary");
                int age = rowSet.getInt("age");
                Date date = rowSet.getDate("join_date");
                System.out.println(String.format("%d.\tid = %d, name = %s, occupation = %s, salary = %d, age = %d, " +
                                "join_date = %s",
                        rowSet.getRow(), id, name, occupation, salary, age, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
