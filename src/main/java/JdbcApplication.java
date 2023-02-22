import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

public class JdbcApplication {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", "root");
             Statement statement = connection.createStatement()) {

            System.out.println("Данные таблицы user:");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int level = resultSet.getInt(3);
                Date date = resultSet.getDate(4);
                System.out.println(String.format("%d.\tid = %d, name = %s, level = %d, created_date = %s",
                                   resultSet.getRow(), id, name, level, date));
            }

            System.out.println("\nДанные таблицы employee:");
            resultSet = statement.executeQuery("SELECT * FROM employee");
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

            System.out.println("\nДанные таблицы task:");
            resultSet = statement.executeQuery("SELECT * FROM task");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Integer eId = resultSet.getObject("employee_id", Integer.class);
                /*int employeeId = resultSet.getInt("employee_id"); // можно и так, по-старинке
                Integer eId  = (resultSet.wasNull()) ? null : employeeId ;*/
                String name = resultSet.getString("name");
                Date deadline = resultSet.getDate("deadline");
                System.out.println(String.format("%d.\tid = %d, employee_id = %d, name = %s, deadline = %s",
                                   resultSet.getRow(), id, eId, name, deadline));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
