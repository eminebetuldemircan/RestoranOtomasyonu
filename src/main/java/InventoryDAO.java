import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private Connection connection;

    public InventoryDAO() {
        // Veritabanı bağlantısını burada başlatın
        connect();
    }

    private void connect() {
        // Veritabanı bağlantısını kurma kodu
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Veritabanına bağlandı.");
        } catch (SQLException e) {
            System.out.println("Veritabanına bağlanırken hata oluştu.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        // Veritabanı bağlantısını kapatma kodu
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            } catch (SQLException e) {
                System.out.println("Veritabanı bağlantısı kapatılırken hata oluştu.");
                e.printStackTrace();
            }
        }
    }

    public List<InventoryItem> getAllItems() throws SQLException {
        List<InventoryItem> items = new ArrayList<>();

        // Veritabanından tüm envanter öğelerini getirme SQL sorgusu
        String query = "SELECT * FROM inventory";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                InventoryItem item = new InventoryItem(id, name, quantity, price);
                items.add(item);
            }
        }

        return items;
    }

    public void addItem(InventoryItem item) throws SQLException {
        // Veritabanına yeni bir envanter öğesi ekleme SQL sorgusu
        String query = "INSERT INTO inventory (name, quantity, price) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setDouble(3, item.getPrice());

            pstmt.executeUpdate();
        }
    }

    public void updateItem(InventoryItem item) throws SQLException {
        // Veritabanında bir envanter öğesini güncelleme SQL sorgusu
        String query = "UPDATE inventory SET name = ?, quantity = ?, price = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getId());

            pstmt.executeUpdate();
        }
    }

    public void deleteItem(int itemId) throws SQLException {
        // Veritabanından bir envanter öğesi silme SQL sorgusu
        String query = "DELETE FROM inventory WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, itemId);

            pstmt.executeUpdate();
        }
    }
}
