import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserManagementPanel extends ImagePanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private Map<String, String> users; // Kullanıcı adı ve şifrelerin saklandığı harita

    public UserManagementPanel(String imagePath) {
        super(imagePath); // Arka plan görüntüsünü ayarla
        setLayout(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(20, 20, 20, 20);

        // Örnek kullanıcılar ekle
        users = new HashMap<>();
        users.put("admin", "adminpass");
        users.put("garson", "garsonpass");
        users.put("asci", "ascipass");

        JLabel titleLabel = new JLabel("Kullanıcı Girişi");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 0, 0));
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 2;
        gbcMain.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbcMain);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false); // Arka planı saydam yap
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        usernameLabel.setForeground(new Color(0, 0, 0));
        gbcInput.gridx = 0;
        gbcInput.gridy = 0;
        gbcInput.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(usernameLabel, gbcInput);

        usernameField = new JTextField(15); // Kullanıcı adı alanı boyutu azaltıldı
        gbcInput.gridx = 1;
        gbcInput.gridy = 0;
        gbcInput.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(usernameField, gbcInput);

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(0, 0, 0));
        gbcInput.gridx = 0;
        gbcInput.gridy = 1;
        gbcInput.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(passwordLabel, gbcInput);

        passwordField = new JPasswordField(15); // Şifre alanı boyutu azaltıldı
        gbcInput.gridx = 1;
        gbcInput.gridy = 1;
        gbcInput.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(passwordField, gbcInput);

        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 2;
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, gbcMain);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Arka planı saydam yap
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new LoginActionListener());
        loginButton.setBackground(new Color(32, 24, 145));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.ipadx = 20;
        gbcButtons.ipady = 10;
        buttonPanel.add(loginButton, gbcButtons);

        JButton cancelButton = new JButton("Vazgeç");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        cancelButton.setBackground(new Color(192, 57, 43));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        gbcButtons.gridx = 1;
        gbcButtons.gridy = 0;
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.ipadx = 20;
        gbcButtons.ipady = 10;
        gbcButtons.insets = new Insets(10, 20, 10, 10);
        buttonPanel.add(cancelButton, gbcButtons);

        gbcMain.gridx = 0;
        gbcMain.gridy = 2;
        gbcMain.gridwidth = 2;
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbcMain);
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(UserManagementPanel.this, "Kullanıcı adı ve şifre boş olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(UserManagementPanel.this, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                // Yönetici paneline geçiş burada yapılabilir
                 //new YoneticiPanel().setVisible(true); // Yönetici paneli örneği oluşturuldu

                // Bu kısma, yönetici paneline geçiş kodu eklenebilir.
            } else {
                JOptionPane.showMessageDialog(UserManagementPanel.this, "Geçersiz kullanıcı adı veya şifre.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean authenticateUser(String username, String password) {
            // Kullanıcı adı ve şifreyi doğrula
            return users.containsKey(username) && users.get(username).equals(password);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kullanıcı Girişi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300); // Çerçeve boyutu ayarlandı

        // Arka plan görüntüsünü ayarla
        String imagePath = "C:\\Users\\emine\\IdeaProjects\\RestaurantAutomation\\src\\main\\resources\\images\\my_image.jpg";
        UserManagementPanel panel = new UserManagementPanel(imagePath);
        frame.add(panel);
        frame.pack(); // Çerçevenin boyutunu bileşenlerin boyutlarına göre ayarla
        frame.setLocationRelativeTo(null); // Çerçeveyi ekranın ortasına konumlandır

        frame.setVisible(true);
    }
}
