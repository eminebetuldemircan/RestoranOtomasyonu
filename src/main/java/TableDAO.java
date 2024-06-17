import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/restoran_dbschema?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Mysql.1234";

    private static final String INSERT_TABLE_SQL = "INSERT INTO tables (name, status) VALUES (?, ?)";
    private static final String UPDATE_TABLE_SQL = "UPDATE tables SET customer_name = ?, reservation_time = ?, status = ? WHERE id = ?";
    private static final String DELETE_TABLE_SQL = "DELETE FROM tables WHERE id = ?";
    private static final String SELECT_ALL_TABLES_SQL = "SELECT * FROM tables";

    public TableDAO() {
        // JDBC Driver'ı yükleme (gerekli değilse kaldırılabilir, modern JDBC sürücüleri otomatik olarak yüklenir)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Tüm masaları getirme
    public List<Table> getAllTables() throws SQLException {
        List<Table> tables = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TABLES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String status = resultSet.getString("status");
                String customerName = resultSet.getString("customer_name");
                String reservationTime = resultSet.getString("reservation_time");
                Table table = new Table(id, name, status, customerName, reservationTime);
                tables.add(table);
            }
        }
        return tables;
    }

    // Masa ekleme
    public boolean addTable(Table table) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, table.getName());
            preparedStatement.setString(2, table.getStatus());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    table.setId(generatedKeys.getInt(1));
                    return true;
                }
            }
            return false;
        }
    }

    // Masa güncelleme
    public boolean updateTable(Table table) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TABLE_SQL)) {
            preparedStatement.setString(1, table.getCustomerName());
            preparedStatement.setString(2, table.getReservationTime());
            preparedStatement.setString(3, table.getStatus());
            preparedStatement.setInt(4, table.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Masa silme
    public boolean deleteTable(int tableId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TABLE_SQL)) {
            preparedStatement.setInt(1, tableId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Müşteri atama ve rezervasyon yapma
    public boolean assignCustomer(int tableId, String customerName, String reservationTime) throws SQLException {
        String status = "Reserved"; // Varsayılan durumu "Reserved" olarak atıyoruz
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TABLE_SQL)) {
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, reservationTime);
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4, tableId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
