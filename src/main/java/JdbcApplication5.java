import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

public class JdbcApplication5 {

    public static void main(String[] args) {
        String sql = "INSERT INTO employee (id, name, occupation, salary, age, join_date) VALUES" +
                     "(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                                                                      "root", "root");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 200; i < 210; i++) {
                statement.setInt(1, i);
                statement.setString(2, String.format("model.Employee #%d", i));
                statement.setString(3, String.format("Making job #%d", i));
                statement.setInt(4, i * 1000);
                statement.setInt(5, i - 170);
                statement.setDate(6, new Date(new java.util.Date().getTime()));
                statement.addBatch();
            }
            // выполняем групповое добавление записей
            int[] results = statement.executeBatch();
            System.out.println(Arrays.toString(results));

            //удалаяем добавленные данные
            statement.executeUpdate("DELETE FROM employee WHERE id >= 200");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
