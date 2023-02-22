import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcApplication2 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", "root");
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM user");
            result.next();
            System.out.println(String.format("Кол-во строк в таблице user: %d", result.getInt(1)));

            result = statement.executeQuery("SELECT COUNT(*) FROM employee");
            result.next();
            System.out.println(String.format("Кол-во строк в таблице employee: %d", result.getInt(1)));

            result = statement.executeQuery("SELECT COUNT(*) FROM task");
            result.next();
            System.out.println(String.format("Кол-во строк в таблице task: %d", result.getInt(1)));

            int rowsCount = statement.executeUpdate("UPDATE employee SET " +
                                                         "  salary = salary + 1000");
            System.out.println("Кол-во сотрудников с прибавленной зарплатой: " + rowsCount);
            // возвращаем з/п назад
            statement.executeUpdate("UPDATE employee SET " +
                                         "  salary = salary - 1000");

            //когда точно не знаем что в запросе: Query или Update
            boolean hasResult = statement.execute("SELECT COUNT(*) FROM user");
            if (hasResult) {
                result = statement.getResultSet();
                result.next();
                System.out.println("Результат запроса: " + result.getInt(1));
            } else {
                rowsCount = statement.getUpdateCount();
                System.out.println("Строк изменено " + rowsCount);
            }

            //добавляем нового сотрудника
            rowsCount = statement.executeUpdate("INSERT INTO employee (id, name, occupation, salary, age, " +
                                                     "join_date) VALUES" +
                                                     "(50, 'Кирпичев Семен', 'дворник', 15000, 48, '2022-06-07')");
            if (rowsCount == 1) {
                System.out.println("Новый сотрудник добавлен");
            }
            // удаляем только что добавленного сотрудника
            rowsCount = statement.executeUpdate("DELETE FROM employee " +
                                                     "WHERE id = 50");
            if (rowsCount == 1) {
                System.out.println("Новый сотрудник удален");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
