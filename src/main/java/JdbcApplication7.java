import java.sql.*;

public class JdbcApplication7 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                                                                "root", "root");
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                              ResultSet.CONCUR_UPDATABLE)) {
            // обновление данных через ResultSet
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                int level = resultSet.getInt("level");
                level++;
                resultSet.updateInt("level", level);
                resultSet.updateRow(); //без этого работать не будет
            }
            //возвращаем все взад
            statement.executeUpdate("UPDATE user SET level = level - 1");

            // добавление данных через ResultSet
            resultSet = statement.executeQuery("SELECT * FROM user");
            resultSet.moveToInsertRow();
            resultSet.updateInt("id", 101);
            resultSet.updateString("name", "Криворучко Семен Гаврилович");
            resultSet.updateInt("level", 100500);
            resultSet.updateDate("created_date", new Date(1234567890));
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            // удаляем криворучку
            statement.executeUpdate("DELETE FROM user WHERE id = 101");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
