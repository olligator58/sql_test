import java.sql.*;

public class JdbcApplication1 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", "root");
             Statement statement = connection.createStatement()) {

            ResultSet set = statement.executeQuery("SELECT * FROM employee");
            ResultSetMetaData metaData = set.getMetaData();
            System.out.println("Информация о таблице:");
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                String name = metaData.getColumnName(column);
                String className = metaData.getColumnClassName(column);
                String typeName = metaData.getColumnTypeName(column);
                int type = metaData.getColumnType(column);
                System.out.println(name + "\t" + className + "\t" + typeName + "\t" + type);
            }

            System.out.println(String.format("\nИмя схемы запроса: %s", metaData.getSchemaName(1)));
            System.out.println(String.format("Имя таблицы запроса: %s", metaData.getTableName(1)));
            System.out.println(String.format("Всего колонок в результате запроса: %d", metaData.getColumnCount()));
            System.out.println(String.format("Имя третьей колонки: %s", metaData.getColumnName(3)));
            System.out.println(String.format("Тип первой колонки (специальный код): %d", metaData.getColumnType(1)));
            System.out.println(String.format("Тип второй колонки: %s", metaData.getColumnTypeName(2)));
            System.out.println(String.format("Класс типа шестой колонки: %s", metaData.getColumnClassName(6)));
            System.out.println(String.format("Имя каталога пятой колонки: %s", metaData.getCatalogName(5)));
            System.out.println(String.format("1-я колонка автоинкрементная ? : %s", metaData.isAutoIncrement(1)));
            System.out.println(String.format("2-я колонка Nullable ?: %s", metaData.isNullable(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
