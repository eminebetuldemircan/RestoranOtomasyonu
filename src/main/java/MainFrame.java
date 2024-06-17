import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Restoran Otomasyon Sistemi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));

        JButton userManagementButton = new JButton("Kullanıcı Yönetimi");
        JButton tableManagementButton = new JButton("Masa Yönetimi");
        JButton menuManagementButton = new JButton("Menü Yönetimi");
        JButton orderManagementButton = new JButton("Sipariş Yönetimi");
        JButton inventoryManagementButton = new JButton("Envanter Yönetimi");
        JButton billingButton = new JButton("Fatura ve Ödeme");

        panel.add(userManagementButton);
        panel.add(tableManagementButton);
        panel.add(menuManagementButton);
        panel.add(orderManagementButton);
        panel.add(inventoryManagementButton);
        panel.add(billingButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
