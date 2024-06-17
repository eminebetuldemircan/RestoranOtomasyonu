import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderManagementPanel extends JPanel {
    private JTextField customerNameField;
    private JComboBox<String> tableComboBox;
    private JComboBox<String> menuItemComboBox;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton processOrderButton;
    private JButton sendToChefButton;
    private JButton sendToBarButton;
    private JLabel statusLabel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public OrderManagementPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Üst Panel: Sipariş Formu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sipariş Bilgileri"));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Müşteri Adı:"), gbc);

        gbc.gridx = 1;
        customerNameField = new JTextField(20);
        formPanel.add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Masa:"), gbc);

        gbc.gridx = 1;
        tableComboBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            tableComboBox.addItem("Masa " + i);
        }
        formPanel.add(tableComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Menü Öğesi:"), gbc);

        gbc.gridx = 1;
        menuItemComboBox = new JComboBox<>();
        menuItemComboBox.addItem("Seçiniz");
        menuItemComboBox.addItem("Menü 1");
        menuItemComboBox.addItem("Menü 2");
        menuItemComboBox.addItem("Kebap");
        menuItemComboBox.addItem("Pide");
        menuItemComboBox.addItem("Lahmacun");
        menuItemComboBox.addItem("Baklava");
        menuItemComboBox.addItem("Künefe");
        menuItemComboBox.addItem("Sütlaç");
        menuItemComboBox.addItem("Ayran");
        menuItemComboBox.addItem("Kola");
        menuItemComboBox.addItem("Çay");
        formPanel.add(menuItemComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addButton = new JButton("Ekle");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(new Color(34, 139, 34)); // Yeşil tonu
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        formPanel.add(addButton, gbc);

        gbc.gridx = 1;
        updateButton = new JButton("Güncelle");
        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setBackground(new Color(30, 144, 255)); // Mavi tonu
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        formPanel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        deleteButton = new JButton("Sil");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setBackground(new Color(220, 20, 60)); // Kırmızı tonu
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        processOrderButton = new JButton("İşle");
        processOrderButton.setPreferredSize(new Dimension(100, 30));
        processOrderButton.setBackground(new Color(255, 165, 0)); // Turuncu tonu
        processOrderButton.setForeground(Color.WHITE);
        processOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });
        formPanel.add(processOrderButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        sendToChefButton = new JButton("Aşçıya Yolla");
        sendToChefButton.setPreferredSize(new Dimension(120, 30));
        sendToChefButton.setBackground(new Color(255, 255, 0)); // Sarı tonu
        sendToChefButton.setForeground(Color.BLACK);
        sendToChefButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendToChef();
            }
        });
        formPanel.add(sendToChefButton, gbc);

        gbc.gridx = 1;
        sendToBarButton = new JButton("Bara Yolla");
        sendToBarButton.setPreferredSize(new Dimension(120, 30));
        sendToBarButton.setBackground(new Color(0, 255, 255)); // Cyan tonu
        sendToBarButton.setForeground(Color.BLACK);
        sendToBarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendToBar();
            }
        });
        formPanel.add(sendToBarButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Orta Panel: Sipariş Tablosu
        tableModel = new DefaultTableModel(new Object[]{"Sipariş ID", "Müşteri", "Masa", "Menü Öğesi", "Durum", "Sipariş Zamanı"}, 0);
        orderTable = new JTable(tableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Sadece tek satır seçimine izin ver
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Tablo düzenlemeleri
        orderTable.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTable.setRowHeight(25);

        // Alt Panel: Durum Etiketi
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);

        // Siparişleri yükle (örnek veri)
        loadOrders();
    }

    private void loadOrders() {
        LocalDateTime now = LocalDateTime.now();

        tableModel.addRow(new Object[]{1, "Ahmet Yılmaz", "Masa 3", "Yemek 1", "Hazırlanıyor", now.format(formatter)});
        tableModel.addRow(new Object[]{2, "Ayşe Demir", "Masa 5", "İçecek 2", "Sipariş Verildi", now.format(formatter)});
        tableModel.addRow(new Object[]{3, "Mehmet Kaya", "Masa 2", "Yemek 2", "Sipariş Verildi", now.format(formatter)});
        tableModel.addRow(new Object[]{4, "Fatma Ay", "Masa 8", "İçecek 1", "Hazırlanıyor", now.format(formatter)});
        tableModel.addRow(new Object[]{5, "Ayşenur Demir", "Masa 6", "Yemek 1", "Sipariş Verildi", now.format(formatter)});
    }

    private void addOrder() {
        String customerName = customerNameField.getText();
        String table = (String) tableComboBox.getSelectedItem();
        String menuItem = (String) menuItemComboBox.getSelectedItem();
        String status = "Sipariş Verildi";

        LocalDateTime now = LocalDateTime.now();

        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, customerName, table, menuItem, status, now.format(formatter)});
        statusLabel.setText("Sipariş başarıyla eklendi.");
    }

    private void updateOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String customerName = customerNameField.getText();
            String table = (String) tableComboBox.getSelectedItem();
            String menuItem = (String) menuItemComboBox.getSelectedItem();
            String status = "Hazırlanıyor";

            LocalDateTime now = LocalDateTime.now();

            tableModel.setValueAt(customerName, selectedRow, 1);
            tableModel.setValueAt(table, selectedRow, 2);
            tableModel.setValueAt(menuItem, selectedRow, 3);
            tableModel.setValueAt(status, selectedRow, 4);
            tableModel.setValueAt(now.format(formatter), selectedRow, 5);

            statusLabel.setText("Sipariş başarıyla güncellendi.");
        } else {
            statusLabel.setText("Lütfen güncellenecek bir sipariş seçin.");
        }
    }

    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
            statusLabel.setText("Sipariş başarıyla silindi.");
        } else {
            statusLabel.setText("Lütfen silinecek bir sipariş seçin.");
        }
    }

    private void processOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt("Hazır", selectedRow, 4);
            statusLabel.setText("Sipariş hazırlandı.");
        } else {
            statusLabel.setText("Lütfen işleme alınacak bir sipariş seçin.");
        }
    }

    private void sendToChef() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt("Aşçıya Yollandı", selectedRow, 4);
            statusLabel.setText("Sipariş aşçıya yönlendirildi.");
        } else {
            statusLabel.setText("Lütfen gönderilecek bir sipariş seçin.");
        }
    }

    private void sendToBar() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt("Bara Yollandı", selectedRow, 4);
            statusLabel.setText("Sipariş bara yönlendirildi.");
        } else {
            statusLabel.setText("Lütfen gönderilecek bir sipariş seçin.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Sipariş Yönetimi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.add(new OrderManagementPanel());
                frame.setVisible(true);
            }
        });
    }
}
