package de.mp3db.gui.swing;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * @author alex
 *
 */
public class MP3TableModel extends AbstractTableModel {
	protected Vector data = new Vector();
	private int colCount = 0;
	
	public MP3TableModel(int colums) {
		colCount = colums;
	}
	
	public void add(Object[] newRow) {
		data.add(newRow);
		fireTableDataChanged();
	}

	public void clear() {
		data.clear();
		fireTableDataChanged();		
	}
	
	public void remove(int pos) {
		data.removeElementAt(pos);
		fireTableDataChanged();
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return colCount;
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.size();
	}

	/* (Kein Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int arg0, int arg1) {
		String tmp[] = (String[])data.get(arg0);
		return (Object)tmp[arg1];
	}
}