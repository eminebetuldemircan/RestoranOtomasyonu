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

/*class GarsonPanel extends JPanel {
    private JTextField tableField;
    private JTextField personCountField;
    private JTextField itemField;
    private JTextField priceField;
    private DefaultTableModel tableModel;
    private DefaultTableModel preparedTableModel;
    private JTable orderTable;

    public GarsonPanel() {
        setLayout(new BorderLayout());

        // Sipariş Bilgisi Paneli
        JPanel orderInfoPanel = new JPanel();
        orderInfoPanel.setBorder(BorderFactory.createTitledBorder("Sipariş Bilgisi"));
        orderInfoPanel.setLayout(new GridLayout(5, 2));

        orderInfoPanel.add(new JLabel("Masa Numarası:"));
        tableField = new JTextField();
        orderInfoPanel.add(tableField);

        orderInfoPanel.add(new JLabel("Kişi Sayısı:"));
        personCountField = new JTextField();
        orderInfoPanel.add(personCountField);

        orderInfoPanel.add(new JLabel("Ürünler:"));
        itemField = new JTextField();
        orderInfoPanel.add(itemField);

        orderInfoPanel.add(new JLabel("Toplam Fiyat:"));
        priceField = new JTextField();
        orderInfoPanel.add(priceField);

        JButton addOrderButton = new JButton("Sipariş Ekle");
        addOrderButton.addActionListener(new AddOrderListener());
        orderInfoPanel.add(addOrderButton);

        add(orderInfoPanel, BorderLayout.NORTH);

        // Sipariş Listesi Paneli
        JPanel orderListPanel = new JPanel();
        orderListPanel.setBorder(BorderFactory.createTitledBorder("Mevcut Siparişler"));
        orderListPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderListPanel.add(scrollPane, BorderLayout.CENTER);

        JButton showOrdersButton = new JButton("Siparişleri Göster");
        showOrdersButton.addActionListener(new ShowOrdersListener());
        orderListPanel.add(showOrdersButton, BorderLayout.SOUTH);

        add(orderListPanel, BorderLayout.CENTER);

        // Hazırlanan Siparişler Paneli
        JPanel preparedOrderListPanel = new JPanel();
        preparedOrderListPanel.setBorder(BorderFactory.createTitledBorder("Hazırlanan Siparişler"));
        preparedOrderListPanel.setLayout(new BorderLayout());

        preparedTableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable preparedOrderTable = new JTable(preparedTableModel);
        JScrollPane preparedScrollPane = new JScrollPane(preparedOrderTable);
        preparedOrderListPanel.add(preparedScrollPane, BorderLayout.CENTER);

        JButton showPreparedOrdersButton = new JButton("Hazırlanan Siparişleri Göster");
        showPreparedOrdersButton.addActionListener(new ShowPreparedOrdersListener());
        preparedOrderListPanel.add(showPreparedOrdersButton, BorderLayout.SOUTH);

        add(preparedOrderListPanel, BorderLayout.SOUTH);

        // Servis Yapıldı Butonu
        JButton serveButton = new JButton("Servis Yapıldı");
        serveButton.addActionListener(new ServeOrderListener());
        add(serveButton, BorderLayout.PAGE_END);
    }

    private class AddOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tableNumber = tableField.getText();
            int personCount = Integer.parseInt(personCountField.getText());
            String items = itemField.getText();
            double totalPrice = Double.parseDouble(priceField.getText());

            Order order = new Order(tableNumber, personCount, items, totalPrice);
            OrderData.addOrder(order);

            tableModel.addRow(new Object[]{tableNumber, personCount, items, totalPrice});
        }
    }

    private class ShowOrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0);
            List<Order> orders = OrderData.getOrders();
            for (Order order : orders) {
                tableModel.addRow(new Object[]{order.getTableNumber(), order.getPersonCount(), order.getItems(), order.getTotalPrice()});
            }
        }
    }

    private class ShowPreparedOrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            preparedTableModel.setRowCount(0);
            List<Order> preparedOrders = OrderData.getPreparedOrders();
            for (Order order : preparedOrders) {
                preparedTableModel.addRow(new Object[]{order.getTableNumber(), order.getPersonCount(), order.getItems(), order.getTotalPrice()});
            }
        }
    }

    private class ServeOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = preparedTableModel.getRowCount() - 1;
            if (selectedRow >= 0) {
                String tableNumber = (String) preparedTableModel.getValueAt(selectedRow, 0);
                int personCount = (int) preparedTableModel.getValueAt(selectedRow, 1);
                String items = (String) preparedTableModel.getValueAt(selectedRow, 2);
                double totalPrice = (double) preparedTableModel.getValueAt(selectedRow, 3);

                Order order = new Order(tableNumber, personCount, items, totalPrice);
                OrderData.markAsServed(order);

                preparedTableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(GarsonPanel.this, "Masa " + tableNumber + " siparişi servis edildi.", "Servis Tamamlandı", JOptionPane.INFORMATION_MESSAGE);

                // Fatura kesme işlemi
                String invoice = "Fatura\n";
                invoice += "Masa: " + tableNumber + "\n";
                invoice += "Kişi Sayısı: " + personCount + "\n";
                invoice += "Ürünler: " + items + "\n";
                invoice += "Toplam Fiyat: " + totalPrice + " TL\n";
                JOptionPane.showMessageDialog(GarsonPanel.this, invoice, "Fatura", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}

class AsciPanel extends JPanel {
    private DefaultTableModel tableModel;
    private DefaultTableModel preparedTableModel;

    public AsciPanel() {
        setLayout(new BorderLayout());

        // Sipariş Hazırlama Paneli
        JPanel prepareOrderPanel = new JPanel();
        prepareOrderPanel.setBorder(BorderFactory.createTitledBorder("Sipariş Hazırlama"));
        prepareOrderPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        prepareOrderPanel.add(scrollPane, BorderLayout.CENTER);

        JButton prepareOrderButton = new JButton("Hazırla");
        prepareOrderButton.addActionListener(new PrepareOrderListener());
        prepareOrderPanel.add(prepareOrderButton, BorderLayout.SOUTH);

        add(prepareOrderPanel, BorderLayout.NORTH);

        // Hazırlanan Siparişler Paneli
        JPanel preparedOrderPanel = new JPanel();
        preparedOrderPanel.setBorder(BorderFactory.createTitledBorder("Hazırlanan Siparişler"));
        preparedOrderPanel.setLayout(new BorderLayout());

        preparedTableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable preparedOrderTable = new JTable(preparedTableModel);
        JScrollPane preparedScrollPane = new JScrollPane(preparedOrderTable);
        preparedOrderPanel.add(preparedScrollPane, BorderLayout.CENTER);

        JButton showPreparedOrdersButton = new JButton("Hazırlanan Siparişleri Göster");
        showPreparedOrdersButton.addActionListener(new ShowPreparedOrdersListener());
        preparedOrderPanel.add(showPreparedOrdersButton, BorderLayout.SOUTH);

        add(preparedOrderPanel, BorderLayout.CENTER);
    }

    private class PrepareOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tableModel.getRowCount() - 1;
            if (selectedRow >= 0) {
                String tableNumber = (String) tableModel.getValueAt(selectedRow, 0);
                int personCount = (int) tableModel.getValueAt(selectedRow, 1);
                String items = (String) tableModel.getValueAt(selectedRow, 2);
                double totalPrice = (double) tableModel.getValueAt(selectedRow, 3);

                Order order = new Order(tableNumber, personCount, items, totalPrice);
                OrderData.markAsPrepared(order);

                tableModel.removeRow(selectedRow);
                preparedTableModel.addRow(new Object[]{tableNumber, personCount, items, totalPrice});
            }
        }
    }

    private class ShowPreparedOrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            preparedTableModel.setRowCount(0);
            List<Order> preparedOrders = OrderData.getPreparedOrders();
            for (Order order : preparedOrders) {
                preparedTableModel.addRow(new Object[]{order.getTableNumber(), order.getPersonCount(), order.getItems(), order.getTotalPrice()});
            }
        }
    }
}

class YoneticiPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> roleComboBox;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JTextArea orderTextArea;
    private JTextArea menuTextArea;
    private JTextArea reportsTextArea;

    private List<String> orders;
    private List<String> menuItems;
    private List<String> reports;

    public YoneticiPanel() {
        setLayout(new BorderLayout());

        orders = new ArrayList<>();
        menuItems = new ArrayList<>();
        reports = new ArrayList<>();

        // Test verileri
        orders.add("Sipariş 1: Pizza");
        orders.add("Sipariş 2: Hamburger");
        menuItems.add("Menü Öğesi  1: Pizza");
        menuItems.add("Menü Öğesi 2: Hamburger");
        reports.add("Rapor 1: Günlük Satış Raporu");
        reports.add("Rapor 2: Haftalık Satış Raporu");

        // Ana panel oluşturma
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Kullanıcı yönetimi paneli oluşturma
        JPanel userPanel = createUserManagementPanel();
        mainPanel.add(userPanel);

        // Sipariş yönetimi paneli oluşturma
        JPanel orderPanel = createOrderManagementPanel();
        mainPanel.add(orderPanel);

        // Menü yönetimi paneli oluşturma
        JPanel menuPanel = createMenuManagementPanel();
        mainPanel.add(menuPanel);

        // Raporlar paneli oluşturma
        JPanel reportsPanel = createReportsPanel();
        mainPanel.add(reportsPanel);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Kullanıcı Yönetimi"));

        // Kullanıcıları gösterecek tablo
        String[] columns = {"ID", "Kullanıcı Adı", "Rol", "Şifre"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Kullanıcı ekleme paneli
        JPanel addUserPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Şifre:");
        passwordField = new JPasswordField(20);
        JLabel roleLabel = new JLabel("Rol:");
        roleComboBox = new JComboBox<>(new String[]{"garson", "aşçı", "yönetici"});

        addUserPanel.add(usernameLabel);
        addUserPanel.add(usernameField);
        addUserPanel.add(passwordLabel);
        addUserPanel.add(passwordField);
        addUserPanel.add(roleLabel);
        addUserPanel.add(roleComboBox);

        JButton addUserButton = new JButton("Kullanıcı Ekle");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser(usernameField.getText(), new String(passwordField.getPassword()), (String) roleComboBox.getSelectedItem());
            }
        });

        JButton removeUserButton = new JButton("Kullanıcı Sil");
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser();
            }
        });

        addUserPanel.add(addUserButton);
        addUserPanel.add(removeUserButton);

        panel.add(addUserPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOrderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Sipariş Yönetimi"));

        orderTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        JButton viewOrdersButton = new JButton("Siparişleri Görüntüle");
        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrders();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(viewOrdersButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMenuManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Menü Yönetimi"));

        menuTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(menuTextArea);

        JButton editMenuButton = new JButton("Menüyü Düzenle");
        editMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(editMenuButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Raporlar"));

        reportsTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(reportsTextArea);

        JButton generateReportButton = new JButton("Rapor Oluştur");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(generateReportButton, BorderLayout.SOUTH);

        return panel;
    }

    private void addUser(String username, String password, String role) {
        // Kullanıcı ekleme işlemi
        Object[] row = {tableModel.getRowCount() + 1, username, role, password};
        tableModel.addRow(row);
    }

    private void removeUser() {
        // Kullanıcı silme işlemi
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek istediğiniz kullanıcıyı seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tableModel.removeRow(selectedRow);
    }

    private void viewOrders() {
        // Siparişleri gösterme işlemi
        StringBuilder sb = new StringBuilder();
        for (String order : orders) {
            sb.append(order).append("\n");
        }
        orderTextArea.setText(sb.toString());
    }

    private void editMenu() {
        // Menüyü düzenleme işlemi
        StringBuilder sb = new StringBuilder();
        for (String menuItem : menuItems) {
            sb.append(menuItem).append("\n");
        }
        menuTextArea.setText(sb.toString());
    }

    private void generateReport() {
        // Rapor oluşturma işlemi
        StringBuilder sb = new StringBuilder();

        // Örneğin, günlük satış raporu
        sb.append("Günlük Satış Raporu:\n");
        sb.append("Tarih: 2024-06-20\n");
        sb.append("Toplam Satış Miktarı: 500 TL\n\n");

        // Örneğin, haftalık satış raporu
        sb.append("Haftalık Satış Raporu:\n");
        sb.append("2024-06-15 - 2024-06-20\n");
        sb.append("Toplam Satış Miktarı: 2500 TL\n\n");

        // Örneğin, aylık satış raporu
        sb.append("Aylık Satış Raporu:\n");
        sb.append("Ay: Haziran 2024\n");
        sb.append("Toplam Satış Miktarı: 10000 TL\n\n");

        // En popüler menü öğeleri ve müşteri tercihleri analizi
        sb.append("En Popüler Menü Öğeleri:\n");
        sb.append("1. Pizza - 150 sipariş\n");
        sb.append("2. Hamburger - 120 sipariş\n\n");

        sb.append("Müşteri Tercihleri Analizi:\n");
        sb.append("Nakit: %40\n");
        sb.append("Kredi Kartı: %30\n");
        sb.append("Mobil Ödeme: %30\n");

        reportsTextArea.setText(sb.toString());
    }
}
*/


