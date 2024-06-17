import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            backgroundImage = ImageIO.read(imageFile);
            setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this)));
        } catch (IOException e) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        // Swing işlemlerini Event Dispatch Thread üzerinde yapmak önemlidir.
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arka Plan Görüntü Paneli");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Örneğin, proje içindeki resources/images klasöründen my_image.jpg görüntüsünü yükle
            String imagePath = "C:\\Users\\emine\\IdeaProjects\\RestaurantAutomation\\src\\main\\resources\\images\\my_image.jpg";
            ImagePanel panel = new ImagePanel(imagePath);

            frame.add(panel);
            frame.pack(); // Frame'in boyutunu bileşenlerin boyutlarına göre otomatik ayarla
            frame.setLocationRelativeTo(null); // Frame'i ekranın ortasına konumlandır
            frame.setVisible(true);
        });
    }
}
