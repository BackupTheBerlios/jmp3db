package de.mp3db.gui.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import de.mp3db.gui.MP3Panel;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 * 
 */
public class MainPanel extends JPanel implements MP3Panel {
	private MP3DBListener listener;
	
	private JTable mainTable = new JTable();
	private JScrollPane tableScrollPane = new JScrollPane();

	String[] tableHeader = new String[] { "Title", "Artist", "Album", "No",  "Year", "Genre", "Bitrate", "Filename"};
	MP3TableModel tableModel; 
	
	public MainPanel() {
		listener = MP3DBListener.getInstance();
		tableModel = new MP3TableModel(tableHeader.length);

		this.initMainPanel();
		this.setLayout(new BorderLayout());
		this.add(tableScrollPane, BorderLayout.CENTER);
	}
	
	private void initMainPanel() {
		DefaultTableColumnModel cm = new DefaultTableColumnModel();
		for (int i = 0; i < tableHeader.length; ++i) {
			int width = 0;
			switch(i) {
				case 0 : width = 150; 
				break;
				case 1 : width = 150;
				break;
				case 2 : width = 150;
				break;
				case 3 : width = 40;
				break;
				case 4 : width = 40;
				break;
				case 5 : width = 90;
				break;
				case 6 : width = 40;
				break;
				case 7 : width = 200;
				break;
			}
			TableColumn col = new TableColumn(i, width);
			col.setHeaderValue(tableHeader[i]);
			cm.addColumn(col);
		}
		JTableHeader header = mainTable.getTableHeader();
		//header.setFont(new Font(null, Font.BOLD, 10));
		mainTable.setModel(tableModel);
		mainTable.addMouseListener(listener);
		mainTable.setColumnModel(cm);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mainTable.setRowSelectionAllowed(true);
		mainTable.setColumnSelectionAllowed(false);
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//mainTable.setFont(new Font("Verdana", Font.PLAIN, 10));	
		tableScrollPane.setViewportView(mainTable);
	}

	public void clear() {
		tableModel.clear();
	}

	public void add(MP3Song newRow) {
		tableModel.add(new String[] {newRow.getTitle(), newRow.getArtist(), newRow.getAlbum(), String.valueOf(newRow.getTrackNo()), String.valueOf(newRow.getYear()), String.valueOf(newRow.getGenre()), String.valueOf(newRow.getBitrate()), newRow.getFileName()} );
	}

	public int getSelection() {
		return mainTable.getSelectedRow();
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3Panel#remove()
	 */
	public void remove(int pos) {
		tableModel.remove(pos);
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3Panel#setSelection(int)
	 */
	public void setSelection(int row) {
		mainTable.setRowSelectionInterval(row, row);

	}

}