import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class YoneticiPanel extends JPanel {

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
        setBackground(Color.white); // Arka plan rengini beyaza ayarladık

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
        mainPanel.setBackground(Color.white); // Arka plan rengini beyaza ayarladık

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
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240)); // Panel arka plan rengini açık yeşil tonunda ayarladık
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Kullanıcı Yönetimi", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));

        // Kullanıcıları gösterecek tablo
        String[] columns = {"ID", "Kullanıcı Adı", "Rol", "Şifre"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        userTable.setBackground(new Color(255, 250, 205)); // Tablo arka plan rengini açık sarı tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Kullanıcı ekleme paneli
        JPanel addUserPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        addUserPanel.setBackground(new Color(240, 255, 240)); // Panel arka plan rengini açık yeşil tonunda ayarladık

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
        addUserButton.setBackground(new Color(25, 69, 0)); // Butonun arka plan rengini turuncu tonunda ayarladık
        addUserButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        addUserButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser(usernameField.getText(), new String(passwordField.getPassword()), (String) roleComboBox.getSelectedItem());
            }
        });

        JButton removeUserButton = new JButton("Kullanıcı Sil");
        removeUserButton.setBackground(new Color(25, 69, 0)); // Butonun arka plan rengini turuncu tonunda ayarladık
        removeUserButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        removeUserButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
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

    private void addUser(String username, String password, String role) {
        // Kullanıcı ekleme işlemleri burada yapılacak
        // Örneğin, yeni bir kullanıcı nesnesi oluşturup tabloya ekleyebilirsiniz
        int id = tableModel.getRowCount() + 1;
        Object[] rowData = {id, username, role, password};
        tableModel.addRow(rowData);
    }

    private void removeUser() {
        // Seçilen kullanıcıyı silme işlemleri burada yapılacak
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }

    private JPanel createOrderManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Metin alanı arka plan rengini açık mavi tonunda ayarladık
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Sipariş Yönetimi", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setBackground(new Color(240, 248, 255)); // Metin alanı arka plan rengini açık mavi tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        JButton viewOrdersButton = new JButton("Siparişleri Görüntüle");
        viewOrdersButton.setBackground(new Color(255, 97, 0)); // Butonun arka plan rengini turuncu tonunda ayarladık
        viewOrdersButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        viewOrdersButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
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

    private void viewOrders() {
        // Siparişleri gösterme işlemleri burada yapılacak
        orderTextArea.setText(""); // Önce alanı temizleyelim
        for (String order : orders) {
            orderTextArea.append(order + "\n");
        }
    }

    private JPanel createMenuManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Panel arka plan rengini açık mavi tonunda ayarladık
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Menü Yönetimi", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));

        menuTextArea = new JTextArea(10, 30);
        menuTextArea.setBackground(new Color(240, 248, 255)); // Metin alanı arka plan rengini açık mavi tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(menuTextArea);

        JButton editMenuButton = new JButton("Menüyü Düzenle");
        editMenuButton.setBackground(new Color(70, 130, 180)); // Butonun arka plan rengini lacivert tonunda ayarladık
        editMenuButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        editMenuButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        editMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenu();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(editMenuButton        , BorderLayout.SOUTH);

        return panel;
    }

    private void editMenu() {
        // Menüyü düzenleme işlemleri burada yapılacak
        menuTextArea.setText(""); // Önce alanı temizleyelim
        for (String menuItem : menuItems) {
            menuTextArea.append(menuItem + "\n");
        }
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Metin alanı arka plan rengini açık mavi tonunda ayarladık
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Raporlar", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));

        reportsTextArea = new JTextArea(10, 30);
        reportsTextArea.setBackground(new Color(240, 248, 255)); // Metin alanı arka plan rengini açık mavi tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(reportsTextArea);

        JButton generateReportsButton = new JButton("Raporları Oluştur");
        generateReportsButton.setBackground(new Color(60, 179, 113)); // Butonun arka plan rengini yeşil tonunda ayarladık
        generateReportsButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        generateReportsButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        generateReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReports();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(generateReportsButton, BorderLayout.SOUTH);

        return panel;
    }

    private void generateReports() {
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
        for (String report : reports) {
            reportsTextArea.append(report + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Yönetici Paneli");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new YoneticiPanel());
                frame.pack();
                frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
                frame.setVisible(true);
            }
        });
    }
}
