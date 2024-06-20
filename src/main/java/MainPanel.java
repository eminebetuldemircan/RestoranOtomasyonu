import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new BorderLayout());

        // Arka Plan Görüntü Paneli
        String imagePath = "C:\\Users\\emine\\IdeaProjects\\RestaurantAutomation\\src\\main\\resources\\images\\my_image.jpg";
        ImagePanel backgroundPanel = new ImagePanel(imagePath);

        // Ana panelin boyutunu arka plan görüntüsünün boyutuna ayarla
        backgroundPanel.setPreferredSize(new Dimension(800, 600));

        // Ana panelin üzerine kart paneli ve buton paneli ekleyin
        add(backgroundPanel, BorderLayout.CENTER);

        // Buton Paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 6));

        //JButton userManagementButton = new JButton("Kullanıcı Yönetimi");
        JButton tableManagementButton = new JButton("Masa Yönetimi");
        JButton menuManagementButton = new JButton("Menü Yönetimi");
        JButton orderManagementButton = new JButton("Sipariş Yönetimi");
        JButton inventoryManagementButton = new JButton("Envanter Yönetimi");
        JButton billingButton = new JButton("Fatura ve Ödeme");

       // buttonPanel.add(userManagementButton);
        buttonPanel.add(tableManagementButton);
        buttonPanel.add(menuManagementButton);
        buttonPanel.add(orderManagementButton);
        buttonPanel.add(inventoryManagementButton);
        buttonPanel.add(billingButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Kart Paneli
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Yönetim Panellerini Kart Paneline Ekleyin
       // cardPanel.add(new UserManagementPanel(), "Kullanıcı Yönetimi");
        cardPanel.add(new TableManagementPanel(), "Masa Yönetimi");
        cardPanel.add(new MenuManagementPanel(), "Menü Yönetimi");
        cardPanel.add(new OrderManagementPanel(), "Sipariş Yönetimi");
        cardPanel.add(new InventoryManagementPanel(), "Envanter Yönetimi");
        cardPanel.add(new BillingPanel(), "Fatura ve Ödeme");

        add(cardPanel, BorderLayout.CENTER);

        // Butonlara Eylem Dinleyicileri Ekleyin
        //userManagementButton.addActionListener(e -> cardLayout.show(cardPanel, "Kullanıcı Yönetimi"));
        tableManagementButton.addActionListener(e -> cardLayout.show(cardPanel, "Masa Yönetimi"));
        menuManagementButton.addActionListener(e -> cardLayout.show(cardPanel, "Menü Yönetimi"));
        orderManagementButton.addActionListener(e -> cardLayout.show(cardPanel, "Sipariş Yönetimi"));
        inventoryManagementButton.addActionListener(e -> cardLayout.show(cardPanel, "Envanter Yönetimi"));
        billingButton.addActionListener(e -> cardLayout.show(cardPanel, "Fatura ve Ödeme"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Restoran Yönetim Sistemi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);

            frame.setVisible(true);
        });
    }
}
