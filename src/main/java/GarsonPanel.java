import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GarsonPanel extends JPanel {
    private JTextField tableField;
    private JTextField personCountField;
    private JTextField itemField;
    private JTextField priceField;
    private DefaultTableModel tableModel;

    public GarsonPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white); // Arka plan rengini beyaza ayarladık

        // Sipariş Bilgisi Paneli
        JPanel orderInfoPanel = new JPanel();
        orderInfoPanel.setBackground(new Color(230, 230, 250)); // Panel arka plan rengini lavanta tonunda ayarladık
        orderInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Sipariş Bilgisi", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));
        orderInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));

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
        addOrderButton.setBackground(new Color(70, 130, 180)); // Butonun arka plan rengini lacivert tonunda ayarladık
        addOrderButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        addOrderButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        addOrderButton.addActionListener(new AddOrderListener());
        orderInfoPanel.add(addOrderButton);

        add(orderInfoPanel, BorderLayout.NORTH);

        // Sipariş Listesi Paneli
        JPanel orderListPanel = new JPanel();
        orderListPanel.setBackground(new Color(240, 248, 255)); // Panel arka plan rengini açık mavi tonunda ayarladık
        orderListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Mevcut Siparişler", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));
        orderListPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable orderTable = new JTable(tableModel);
        orderTable.setBackground(new Color(255, 250, 205)); // Tablo arka plan rengini açık sarı tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderListPanel.add(scrollPane, BorderLayout.CENTER);

        JButton showOrdersButton = new JButton("Siparişleri Göster");
        showOrdersButton.setBackground(new Color(70, 130, 180)); // Butonun arka plan rengini lacivert tonunda ayarladık
        showOrdersButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        showOrdersButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        showOrdersButton.addActionListener(new ShowOrdersListener());
        orderListPanel.add(showOrdersButton, BorderLayout.SOUTH);

        add(orderListPanel, BorderLayout.CENTER);
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
}
