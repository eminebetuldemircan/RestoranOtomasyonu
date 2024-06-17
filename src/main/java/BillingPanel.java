import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

public class BillingPanel extends JPanel {
    private JTextField orderIdField;
    private JTextField tableNumberField;
    private JTextField customerNameField;
    private JTextField totalAmountField;
    private JComboBox<String> paymentMethodComboBox;
    private JButton generateBillButton;
    private JButton processPaymentButton;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public BillingPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Paneli
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Ödeme İşlemleri", 2, 0, new Font("Arial", Font.BOLD, 16), Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        formPanel.add(new JLabel("Sipariş ID:"));
        orderIdField = new JTextField();
        formPanel.add(orderIdField);

        formPanel.add(new JLabel("Masa Numarası:"));
        tableNumberField = new JTextField();
        formPanel.add(tableNumberField);

        formPanel.add(new JLabel("Müşteri Adı:"));
        customerNameField = new JTextField();
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Toplam Tutar:"));
        totalAmountField = new JTextField();
        totalAmountField.setEditable(false);
        formPanel.add(totalAmountField);

        formPanel.add(new JLabel("Ödeme Yöntemi:"));
        paymentMethodComboBox = new JComboBox<>(new String[]{"Nakit", "Kredi Kartı", "Mobil Ödeme"});
        formPanel.add(paymentMethodComboBox);

        generateBillButton = new JButton("Fatura Oluştur");
        generateBillButton.setBackground(new Color(34, 139, 34)); // Yeşil tonu
        generateBillButton.setForeground(Color.WHITE);
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateBill();
            }
        });
        formPanel.add(generateBillButton);

        processPaymentButton = new JButton("Ödeme İşlemini Tamamla");
        processPaymentButton.setBackground(new Color(30, 144, 255)); // Mavi tonu
        processPaymentButton.setForeground(Color.WHITE);
        processPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
        formPanel.add(processPaymentButton);

        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(statusLabel);

        add(formPanel, BorderLayout.NORTH);

        // Siparişler Tablosu
        tableModel = new DefaultTableModel(new Object[]{"Sipariş ID", "Masa Numarası", "Müşteri Adı", "Toplam Tutar"}, 0);
        orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // Örnek siparişleri yükleme
        loadOrders();
    }

    private void generateBill() {
        String orderId = orderIdField.getText();
        String tableNumber = tableNumberField.getText();
        String customerName = customerNameField.getText();
        try {
            double totalAmount = calculateTotalAmount(orderId);
            totalAmountField.setText(String.format("%.2f", totalAmount));
            statusLabel.setText("Fatura oluşturuldu. Masa: " + tableNumber + ", Müşteri: " + customerName);
            // Yeni fatura oluşturulduktan sonra siparişleri güncelleyebiliriz
            updateOrders();
        } catch (SQLException e) {
            statusLabel.setText("Fatura oluşturulurken hata: " + e.getMessage());
        }
    }

    private double calculateTotalAmount(String orderId) throws SQLException {
        // Gerçek veritabanı sorgusu ile sipariş ID'ye göre toplam tutarı hesapla
        // Bu sadece örnek bir dönüş
        return 250.00;
    }

    private void processPayment() {
        String orderId = orderIdField.getText();
        String tableNumber = tableNumberField.getText();
        String customerName = customerNameField.getText();
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        double totalAmount = Double.parseDouble(totalAmountField.getText());

        try {
            savePayment(orderId, tableNumber, customerName, totalAmount, paymentMethod);
            statusLabel.setText("Ödeme işlemi başarıyla tamamlandı. Masa: " + tableNumber);
            // Ödeme işlemi tamamlandıktan sonra siparişleri güncelleyebiliriz
            updateOrders();
        } catch (SQLException e) {
            statusLabel.setText("Ödeme işlemi sırasında hata: " + e.getMessage());
        }
    }

    private void savePayment(String orderId, String tableNumber, String customerName, double totalAmount, String paymentMethod) throws SQLException {
        // Gerçek veritabanı işlemleri burada yapılır
        // Örneğin:
        // String query = "INSERT INTO payments (order_id, table_number, customer_name, amount, method) VALUES (?, ?, ?, ?, ?)";
        // PreparedStatement statement = connection.prepareStatement(query);
        // statement.setString(1, orderId);
        // statement.setString(2, tableNumber);
        // statement.setString(3, customerName);
        // statement.setDouble(4, totalAmount);
        // statement.setString(5, paymentMethod);
        // statement.executeUpdate();
    }

    private void loadOrders() {
        // Örnek siparişleri rastgele yükleme
        Random random = new Random();
        addOrderToTable(String.valueOf(random.nextInt(1000)), "Bahçe Masa 1", "John Doe", 150.00);
        addOrderToTable(String.valueOf(random.nextInt(1000)), "Salon Masa 12", "Jane Smith", 100.00);
        addOrderToTable(String.valueOf(random.nextInt(1000)), "Bahçe Masa 3", "Michael Johnson", 200.00);
    }

    private void updateOrders() {
        // Siparişleri güncelleme işlemi
        clearTable();
        loadOrders(); // Örnek olarak aynı siparişleri tekrar yüklüyoruz, gerçekte veritabanından çekilebilir
    }

    private void addOrderToTable(String orderId, String tableNumber, String customerName, double totalAmount) {
        // Yeni siparişi tabloya ekler
        tableModel.addRow(new Object[]{orderId, tableNumber, customerName, totalAmount});
    }

    private void clearTable() {
        // Tabloyu temizler
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Ödeme ve Fatura Yönetimi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);
                frame.add(new BillingPanel());
                frame.setVisible(true);
            }
        });
    }
}
