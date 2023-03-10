import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcApplication6 {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test",
                "root", "root")) {
            Employee newEmployee = new Employee(250, "Сумароков Петр", "Аналитик", 150000,
                    38, new java.util.Date(120, 5, 23));
            // добавляем нового сотрудника
            System.out.println(addEmployee(connection, newEmployee));
            // удаляем добавленного сотрудника
            System.out.println(deleteEmployee(connection, newEmployee));
            // выводим данные сотрудника с id = 5
            System.out.println(getEmployeeById(connection, 5));
            // выводим данные всех сотрудников
            System.out.println("Данные всех сотрудников: ");
            for (Employee employee : getAllEmployees(connection)) {
                System.out.println(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean addEmployee(Connection connection, Employee employee) {
        String sql = "INSERT INTO employee (id, name, occupation, salary, age, join_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getOccupation());
            statement.setInt(4, employee.getSalary());
            statement.setInt(5, employee.getAge());
            statement.setDate(6, new Date(employee.getJoinDate().getTime()));
            int rowsCount = statement.executeUpdate();
            return rowsCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteEmployee(Connection connection, Employee employee) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employee.getId());
            int rowsCount = statement.executeUpdate();
            return rowsCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Employee getEmployeeById(Connection connection, int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                return null;
            }
            return getEmployeeFromResultSet(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Employee> getAllEmployees(Connection connection) {
        List<Employee> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                result.add(getEmployeeFromResultSet(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Employee getEmployeeFromResultSet(ResultSet resultSet) {
        Employee employee = new Employee();
        try {
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setOccupation(resultSet.getString("occupation"));
            employee.setSalary(resultSet.getInt("salary"));
            employee.setAge(resultSet.getInt("age"));
            employee.setJoinDate(resultSet.getDate("join_date"));
            return employee;
        } catch (Exception ignored) {
        }
        return null;
    }
}
