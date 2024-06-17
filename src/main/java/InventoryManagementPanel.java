import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementPanel extends JPanel {
    private JTextField itemNameField;
    private JTextField itemKilogramField;
    private JTextField itemPriceField;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel statusLabel;

    public InventoryManagementPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Form Paneli
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        TitledBorder formBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 102), 2), "Ürün Bilgileri");
        formBorder.setTitleJustification(TitledBorder.CENTER);
        formBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        formPanel.setBorder(BorderFactory.createCompoundBorder(formBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        formPanel.setBackground(new Color(240, 240, 240));

        formPanel.add(new JLabel("Ürün Adı:"));
        itemNameField = new JTextField();
        formPanel.add(itemNameField);

        formPanel.add(new JLabel("Kilogram:"));
        itemKilogramField = new JTextField();
        formPanel.add(itemKilogramField);

        formPanel.add(new JLabel("Fiyat:"));
        itemPriceField = new JTextField();
        formPanel.add(itemPriceField);

        addButton = new JButton("Ekle");
        addButton.setBackground(new Color(34, 139, 34)); // Yeşil tonu
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        formPanel.add(addButton);

        updateButton = new JButton("Güncelle");
        updateButton.setBackground(new Color(30, 144, 255)); // Mavi tonu
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        formPanel.add(updateButton);

        deleteButton = new JButton("Sil");
        deleteButton.setBackground(new Color(220, 20, 60)); // Kırmızı tonu
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);

        // Tablo Paneli
        tableModel = new DefaultTableModel(new Object[]{"ID", "Ürün Adı", "Kilogram", "Fiyat"}, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(0, 102, 102), 2)));
        add(scrollPane, BorderLayout.CENTER);

        // Tablo düzenlemeleri
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 14));
        inventoryTable.setRowHeight(25);

        // Durum Etiketi
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);

        // Örnek veri yükleme
        loadInventory();
    }

    private void loadInventory() {
        // Veritabanından envanteri yükleme yerine örnek veri ekleyelim
        tableModel.addRow(new Object[]{1, "Un", "50 kg", 150.00});
        tableModel.addRow(new Object[]{2, "Şeker", "50 kg", 98.90});
        tableModel.addRow(new Object[]{3, "Tuz", "75 kg", 120.75 });
        tableModel.addRow(new Object[]{4, "Domates", "25 kg", 89.99});
        tableModel.addRow(new Object[]{5, "Biber", "25 kg", 80.00});
    }

    private void addItem() {
        String name = itemNameField.getText();
        String kilogram = itemKilogramField.getText();
        double price = Double.parseDouble(itemPriceField.getText());

        // Veritabanına ekleme yerine örnek veri ekleme
        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, name, kilogram, price});

        statusLabel.setText("Ürün başarıyla eklendi.");
    }

    private void updateItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = itemNameField.getText();
            String kilogram = itemKilogramField.getText();
            double price = Double.parseDouble(itemPriceField.getText());

            // Veritabanında güncelleme yerine örnek veri güncelleme
            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(kilogram, selectedRow, 2);
            tableModel.setValueAt(price, selectedRow, 3);

            statusLabel.setText("Ürün başarıyla güncellendi.");
        } else {
            statusLabel.setText("Lütfen güncellenecek bir ürün seçin.");
        }
    }

    private void deleteItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Veritabanından silme yerine örnek veri silme
            tableModel.removeRow(selectedRow);

            statusLabel.setText("Ürün başarıyla silindi.");
        } else {
            statusLabel.setText("Lütfen silinecek bir ürün seçin.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Envanter Yönetimi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);
                frame.add(new InventoryManagementPanel());
                frame.setVisible(true);
            }
        });
    }
}
