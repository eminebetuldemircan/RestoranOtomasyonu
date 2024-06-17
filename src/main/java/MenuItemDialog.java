import javax.swing.*;
import java.awt.*;

public class MenuItemDialog extends JDialog {
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField descriptionField;
    private boolean succeeded;
    private MenuItem menuItem;

    public MenuItemDialog(MenuItem menuItem) {
        this.menuItem = menuItem;
        setTitle(menuItem == null ? "Add Menu Item" : "Update Menu Item");
        setModal(true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        panel.add(categoryField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        if (menuItem != null) {
            nameField.setText(menuItem.getName());
            categoryField.setText(menuItem.getCategory());
            priceField.setText(String.valueOf(menuItem.getPrice()));
            descriptionField.setText(menuItem.getDescription());
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> onSave());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancel());

        JPanel buttons = new JPanel();
        buttons.add(saveButton);
        buttons.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void onSave() {
        String name = nameField.getText();
        String category = categoryField.getText();
        double price = Double.parseDouble(priceField.getText());
        String description = descriptionField.getText();

        if (menuItem == null) {
            menuItem = new MenuItem(name, price, category, description);
        } else {
            menuItem.setName(name);
            menuItem.setCategory(category);
            menuItem.setPrice(price);
            menuItem.setDescription(description);
        }

        succeeded = true;
        setVisible(false);
    }

    private void onCancel() {
        succeeded = false;
        setVisible(false);
    }
}
