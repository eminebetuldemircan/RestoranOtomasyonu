import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AsciPanel extends JPanel {
    private DefaultTableModel tableModel;
    private DefaultTableModel preparedTableModel;

    public AsciPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white); // Arka plan rengini beyaza ayarladık

        // Sipariş Hazırlama Paneli
        JPanel prepareOrderPanel = new JPanel();
        prepareOrderPanel.setBackground(new Color(255, 228, 225)); // Panel arka plan rengini pembe tonunda ayarladık
        prepareOrderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Sipariş Hazırlama", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));
        prepareOrderPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable orderTable = new JTable(tableModel);
        orderTable.setBackground(new Color(255, 250, 205)); // Tablo arka plan rengini açık sarı tonunda ayarladık
        JScrollPane scrollPane = new JScrollPane(orderTable);
        prepareOrderPanel.add(scrollPane, BorderLayout.CENTER);

        JButton prepareOrderButton = new JButton("Hazırla");
        prepareOrderButton.setBackground(new Color(255, 69, 0)); // Butonun arka plan rengini turuncu tonunda ayarladık
        prepareOrderButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        prepareOrderButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        prepareOrderButton.addActionListener(new PrepareOrderListener());
        prepareOrderPanel.add(prepareOrderButton, BorderLayout.SOUTH);

        add(prepareOrderPanel, BorderLayout.NORTH);

        // Hazırlanan Siparişler Paneli
        JPanel preparedOrderPanel = new JPanel();
        preparedOrderPanel.setBackground(new Color(240, 255, 240)); // Panel arka plan rengini açık yeşil tonunda ayarladık
        preparedOrderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "Hazırlanan Siparişler", 0, 0, new Font("Arial", Font.BOLD, 14), Color.darkGray));
        preparedOrderPanel.setLayout(new BorderLayout());

        preparedTableModel = new DefaultTableModel(new String[]{"Masa Numarası", "Kişi Sayısı", "Ürünler", "Toplam Fiyat"}, 0);
        JTable preparedOrderTable = new JTable(preparedTableModel);
        preparedOrderTable.setBackground(new Color(240, 255, 240)); // Tablo arka plan rengini açık yeşil tonunda ayarladık
        JScrollPane preparedScrollPane = new JScrollPane(preparedOrderTable);
        preparedOrderPanel.add(preparedScrollPane, BorderLayout.CENTER);

        JButton showPreparedOrdersButton = new JButton("Hazırlanan Siparişleri Göster");
        showPreparedOrdersButton.setBackground(new Color(255, 69, 0)); // Butonun arka plan rengini turuncu tonunda ayarladık
        showPreparedOrdersButton.setForeground(Color.white); // Butonun yazı rengini beyaz yaptık
        showPreparedOrdersButton.setFocusPainted(false); // Butonun etrafındaki vurguyu kaldırdık
        showPreparedOrdersButton.addActionListener(new ShowPreparedOrdersListener());
        preparedOrderPanel.add(showPreparedOrdersButton, BorderLayout.SOUTH);

        add(preparedOrderPanel, BorderLayout.CENTER);

        // Örnek siparişler ekleyelim
        addExampleOrders();
    }

    private void addExampleOrders() {
        Order order1 = new Order("Bahçe Masa 1", 4, "Pizza, Salata, Su", 315.00);
        Order order2 = new Order("Salon Masa 12", 2, "Lahmacun, Baklava, Ayran", 250.00);
        Order order3 = new Order("Bahçe Masa 3", 1, "Pide, Kola", 125.00);
        Order order4 = new Order("Salon Masa 14", 3, "Kebap, Künefe, Soda", 400.00);


        OrderData.addOrder(order1);
        OrderData.addOrder(order2);
        OrderData.addOrder(order3);
        OrderData.addOrder(order4);

        tableModel.addRow(new Object[]{order1.getTableNumber(), order1.getPersonCount(), order1.getItems(), order1.getTotalPrice()});
        tableModel.addRow(new Object[]{order2.getTableNumber(), order2.getPersonCount(), order2.getItems(), order2.getTotalPrice()});
        tableModel.addRow(new Object[]{order3.getTableNumber(), order3.getPersonCount(), order3.getItems(), order3.getTotalPrice()});
        tableModel.addRow(new Object[]{order4.getTableNumber(), order4.getPersonCount(), order4.getItems(), order4.getTotalPrice()});
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Aşçı Ekranı");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);
                frame.add(new AsciPanel());
                frame.setVisible(true);
            }
        });
    }
}
