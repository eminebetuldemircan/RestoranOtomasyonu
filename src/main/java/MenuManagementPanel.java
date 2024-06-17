import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MenuManagementPanel extends JPanel {
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField descriptionField;
    private JComboBox<String> categoryComboBox;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel statusLabel;
    private MenuDAO menuDAO;

    public MenuManagementPanel() {
        menuDAO = new MenuDAO();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 240, 240)); // Arka plan rengini açık gri tonunda ayarladık

        // Üst Panel: Menü Formu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Ürün Bilgileri", 0, 0, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        formPanel.setBackground(Color.WHITE); // Panel arka plan rengini beyaz yaptık

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Ürün Adı:"), gbc);

        gbc.gridx = 1;
        itemNameField = new JTextField(20);
        formPanel.add(itemNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Fiyatı:"), gbc);

        gbc.gridx = 1;
        itemPriceField = new JTextField(20);
        formPanel.add(itemPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Kategori:"), gbc);

        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(new String[]{"Yemek", "İçecek", "Tatlı"});
        formPanel.add(categoryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Açıklama:"), gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        // Buton Paneli: Ekle, Güncelle, Sil
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(200, 200, 200)); // Buton paneli arka plan rengini açık gri tonunda ayarladık

        addButton = new JButton("Ekle");
        addButton.setBackground(new Color(34, 139, 34)); // Yeşil tonunda ekle butonu rengi
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        buttonPanel.add(addButton);

        updateButton = new JButton("Güncelle");
        updateButton.setBackground(new Color(0, 0, 255)); // Mavi tonunda güncelle butonu rengi
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        buttonPanel.add(updateButton);

        deleteButton = new JButton("Sil");
        deleteButton.setBackground(new Color(178, 34, 34)); // Kırmızı tonunda sil butonu rengi
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        buttonPanel.add(deleteButton);

        // Form ve Buton Panelini Birleştir
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        inputPanel.setBackground(new Color(240, 240, 240)); // Input panel arka plan rengini açık gri tonunda ayarladık
        add(inputPanel, BorderLayout.NORTH);

        // Orta Panel: Menü Tablosu
        tableModel = new DefaultTableModel(new Object[]{"ID", "Ürün Adı", "Fiyat", "Kategori", "Açıklama"}, 0);
        menuTable = new JTable(tableModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.getTableHeader().setReorderingAllowed(false);
        menuTable.setBackground(Color.WHITE);
        menuTable.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Alt Panel: Durum Etiketi
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);

        // Menü öğelerini yükle
        loadMenuItems();
    }

    private void loadMenuItems() {
        try {
            List<MenuItem> menuItems = menuDAO.getAllMenuItems();
            for (MenuItem item : menuItems) {
                tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getPrice(), item.getCategory(), item.getDescription()});
            }
        } catch (SQLException e) {
            statusLabel.setText("Menü öğeleri yüklenirken hata: " + e.getMessage());
        }
    }

    private void addItem() {
        String name = itemNameField.getText();
        double price;
        try {
            price = Double.parseDouble(itemPriceField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Geçersiz fiyat değeri.");
            return;
        }
        String category = (String) categoryComboBox.getSelectedItem();
        String description = descriptionField.getText();

        try {
            menuDAO.addMenuItem(new MenuItem(name, price, category, description));
            tableModel.addRow(new Object[]{menuDAO.getLatestItemId(), name, price, category, description});
            statusLabel.setText("Ürün başarıyla eklendi.");
        } catch (SQLException e) {
            statusLabel.setText("Ürün eklenirken hata: " + e.getMessage());
        }
    }

    private void updateItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = itemNameField.getText();
            double price;
            try {
                price = Double.parseDouble(itemPriceField.getText());
            } catch (NumberFormatException e) {
                statusLabel.setText("Geçersiz fiyat değeri.");
                return;
            }
            String category = (String) categoryComboBox.getSelectedItem();
            String description = descriptionField.getText();
            int id = (int) tableModel.getValueAt(selectedRow, 0);

            try {
                menuDAO.updateMenuItem(new MenuItem(id, name, price, category, description));
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(price, selectedRow, 2);
                tableModel.setValueAt(category, selectedRow, 3);
                tableModel.setValueAt(description, selectedRow, 4);
                statusLabel.setText("Ürün başarıyla güncellendi.");
            } catch (SQLException e) {
                statusLabel.setText("Ürün güncellenirken hata: " + e.getMessage());
            }
        } else {
            statusLabel.setText("Lütfen güncellenecek bir ürün seçin.");
        }
    }

    private void deleteItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);

            try {
                menuDAO.deleteMenuItem(id);
                tableModel.removeRow(selectedRow);
                statusLabel.setText("Ürün başarıyla silindi.");
            } catch (SQLException e) {
                statusLabel.setText("Ürün silinirken hata: " + e.getMessage());
            }
        } else {
            statusLabel.setText("Lütfen silinecek bir ürün seçin.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Menü Yönetimi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new MenuManagementPanel());
            frame.setVisible(true);
        });
    }
}
