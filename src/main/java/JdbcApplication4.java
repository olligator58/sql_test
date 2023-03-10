import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JdbcApplication4 {

    public static void main(String[] args) {
        String sql = "INSERT INTO employee (id, name, occupation, salary, age, join_date) VALUES" +
                     "(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                                                                "root", "root");
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, 120);
            statement.setString(2, "Mister X");
            statement.setString(3, "Singer");
            statement.setInt(4, 500000);
            statement.setInt(5, 35);
            statement.setDate(6, new Date(new java.util.Date().getTime()));
            int rowsCount = statement.executeUpdate();
            System.out.println(rowsCount);

            //удаляем только что добавленную запись
            statement.executeUpdate("DELETE FROM employee WHERE id = 120");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
