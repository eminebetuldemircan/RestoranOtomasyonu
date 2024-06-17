import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOrderSystem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Restoran Sipariş Sistemi");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1200, 800);

            // Ana panel oluşturma
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.white); // Ana panel arka plan rengini beyaza ayarladık

            // Üst bölüm başlık paneli
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.ORANGE); // Başlık panel arka plan rengini koyu griye ayarladık
            titlePanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 40)); // Başlık panel boyutunu ayarladık

            JLabel titleLabel = new JLabel("Restoran Sipariş Sistemi");
            titleLabel.setForeground(Color.white); // Başlık yazısının rengini beyaz yaptık
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Başlık yazı tipini ve boyutunu ayarladık
            titlePanel.add(titleLabel);

            mainPanel.add(titlePanel, BorderLayout.NORTH);

            // Sekmeleri içerecek tab paneli
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setBackground(Color.YELLOW); // Sekme panel arka plan rengini açık griye ayarladık

            tabbedPane.addTab("Garson Paneli", new GarsonPanel());
            tabbedPane.addTab("Aşçı Paneli", new AsciPanel());
            tabbedPane.addTab("Yönetici Paneli", new YoneticiPanel());

            mainPanel.add(tabbedPane, BorderLayout.CENTER);

            mainFrame.add(mainPanel);
            mainFrame.setVisible(true);
        });
    }
}

class OrderData {
    private static List<Order> orders = new ArrayList<>();
    private static List<Order> preparedOrders = new ArrayList<>();

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void markAsPrepared(Order order) {
        orders.remove(order);
        preparedOrders.add(order);
    }

    public static List<Order> getPreparedOrders() {
        return preparedOrders;
    }

    public static void markAsServed(Order order) {
        preparedOrders.remove(order);
    }
}

class Order {
    private String orderId;
    private String tableNumber;
    private int personCount;
    private String items;
    private double totalPrice;

    public Order(String tableNumber, int personCount, String items, double totalPrice) {
        this.orderId = generateOrderId();
        this.tableNumber = tableNumber;
        this.personCount = personCount;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    private String generateOrderId() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public int getPersonCount() {
        return personCount;
    }

    public String getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Masa: " + tableNumber + ", Kişi Sayısı: " + personCount + ", Ürünler: " + items + ", Toplam Fiyat: " + totalPrice;
    }
}
