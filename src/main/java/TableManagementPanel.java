import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableManagementPanel extends JPanel {
    private JPanel bahcePaneli;
    private JPanel salonPaneli;
    private JButton[] bahceMasalari;
    private JButton[] salonMasalari;
    private int[] masaMusteriSayisi;

    public TableManagementPanel() {
        setLayout(new GridLayout(2, 1, 10, 10)); // Ekranı ikiye bölelim

        salonPaneli = new JPanel();
        salonPaneli.setBorder(BorderFactory.createTitledBorder("Bahçe Masaları"));
        salonPaneli.setLayout(new GridLayout(2, 5, 10, 10));
        bahcePaneli = new JPanel();
        bahcePaneli.setBorder(BorderFactory.createTitledBorder("Salon Masaları"));
        bahcePaneli.setLayout(new GridLayout(3, 5, 10, 10));

        salonMasalari = new JButton[10];
        bahceMasalari = new JButton[15];
        masaMusteriSayisi = new int[25]; // Toplam 25 masa var

        for (int i = 0; i < 10; i++) {
            int masaNumarasi = i + 1;
            salonMasalari[i] = new JButton("Bahçe Masa " + masaNumarasi);
            salonMasalari[i].setBackground(Color.GREEN); // Salon masaları sarı
            salonMasalari[i].addActionListener(new MasaActionDinleyicisi(masaNumarasi));
            salonPaneli.add(salonMasalari[i]);
        }

        for (int i = 0; i < 15; i++) {
            int masaNumarasi = i + 11; // Bahçe masaları 11-25 numaralı
            bahceMasalari[i] = new JButton("Salon Masa " + masaNumarasi);
            bahceMasalari[i].setBackground(Color.PINK); // Bahçe masaları yeşil
            bahceMasalari[i].addActionListener(new MasaActionDinleyicisi(masaNumarasi));
            bahcePaneli.add(bahceMasalari[i]);
        }

        add(salonPaneli);
        add(bahcePaneli);
    }

    private class MasaActionDinleyicisi implements ActionListener {
        private int masaNumarasi;

        public MasaActionDinleyicisi(int masaNumarasi) {
            this.masaNumarasi = masaNumarasi;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel musteriPaneli = new JPanel(new GridLayout(2, 5, 10, 10));
            for (int i = 1; i <= 6; i++) {
                JButton musteriButonu = new JButton(String.valueOf(i));
                musteriButonu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        masaMusteriSayisi[masaNumarasi - 1] = Integer.parseInt(musteriButonu.getText());
                    }
                });
                musteriPaneli.add(musteriButonu);
            }

            int sonuc = JOptionPane.showConfirmDialog(null, musteriPaneli, "Müşteri Sayısını Seçin", JOptionPane.OK_CANCEL_OPTION);
            if (sonuc == JOptionPane.OK_OPTION && masaMusteriSayisi[masaNumarasi - 1] > 0) {
                if (masaNumarasi <= 10) {
                    salonMasalari[masaNumarasi - 1].setBackground(Color.RED); // Rezerve edilmiş salon masaları
                } else {
                    bahceMasalari[masaNumarasi - 11].setBackground(Color.RED); // Rezerve edilmiş bahçe masaları
                }

                String[] kategoriler = {"Ana Yemek", "Tatlı", "İçecek"};
                int kategoriSecimi = JOptionPane.showOptionDialog(null, "Bir Kategori Seçin", "Kategori Seçimi", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, kategoriler, kategoriler[0]);

                if (kategoriSecimi != -1) {
                    String[] menu = {};
                    switch (kategoriSecimi) {
                        case 0:
                            menu = new String[]{"Kebap", "Pide", "Lahmacun"};
                            break;
                        case 1:
                            menu = new String[]{"Baklava", "Sütlaç", "Künefe"};
                            break;
                        case 2:
                            menu = new String[]{"Ayran", "Kola", "Çay"};
                            break;
                    }

                    String siparis = (String) JOptionPane.showInputDialog(null, "Siparişinizi Seçin:", "Sipariş Listesi", JOptionPane.QUESTION_MESSAGE, null, menu, menu[0]);
                    if (siparis != null && !siparis.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Masa " + masaNumarasi + " için sipariş alındı:\n" + siparis,
                                "Sipariş Alındı",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
}
