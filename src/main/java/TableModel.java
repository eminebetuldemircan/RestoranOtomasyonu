import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel {

    private List<Table> tables;
    private String[] columnNames = {"ID", "Ad", "Durum"};

    public TableModel() {
        this.tables = new ArrayList<>();
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
        fireTableDataChanged();
    }

    public Table getTableAt(int row) {
        return tables.get(row);
    }

    @Override
    public int getRowCount() {
        return tables.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Table table = tables.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return table.getId();
            case 1:
                return table.getName();
            case 2:
                return table.getStatus();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
