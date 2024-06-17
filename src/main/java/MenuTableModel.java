import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class MenuTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Name", "Category", "Price", "Description"};
    private List<MenuItem> menuItems;

    public MenuTableModel() {
        menuItems = new ArrayList<>();
        refresh();
    }

    public void refresh() {
        MenuDAO menuDAO = new MenuDAO();
        try {
            menuItems = menuDAO.getAllMenuItems();
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return menuItems.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem item = menuItems.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
                return item.getName();
            case 2:
                return item.getCategory();
            case 3:
                return item.getPrice();
            case 4:
                return item.getDescription();
            default:
                return null;
        }
    }

    public MenuItem getMenuItemAt(int rowIndex) {
        return menuItems.get(rowIndex);
    }
}
