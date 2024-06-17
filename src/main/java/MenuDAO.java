import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    private static final String url = "jdbc:mysql://localhost:3306/restoran_db";
    private static final String username = "root";
    private static final String password = "Mysql.1234";

    private Connection connection;

    public MenuDAO() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Veritabanı bağlantısı başarıyla sağlandı.");
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantısı sağlanırken hata oluştu: " + e.getMessage());
        }
    }

    public List<MenuItem> getAllMenuItems() throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String description = rs.getString("description");
                menuItems.add(new MenuItem(id, name, price, category, description));
            }
        }
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) throws SQLException {
        String query = "INSERT INTO menu_items (name, price, category, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setString(3, menuItem.getCategory());
            pstmt.setString(4, menuItem.getDescription());
            pstmt.executeUpdate();
        }
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException {
        String query = "UPDATE menu_items SET name = ?, price = ?, category = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setString(3, menuItem.getCategory());
            pstmt.setString(4, menuItem.getDescription());
            pstmt.setInt(5, menuItem.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteMenuItem(int id) throws SQLException {
        String query = "DELETE FROM menu_items WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Örnek: Son eklenen öğe ID'sini getir
    public int getLatestItemId() throws SQLException {
        int latestId = -1;
        String query = "SELECT MAX(id) AS max_id FROM menu_items";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                latestId = rs.getInt("max_id");
            }
        }
        return latestId;
    }

    // Bağlantıyı kapat
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantısı kapatılırken hata oluştu: " + e.getMessage());
        }
    }

}
