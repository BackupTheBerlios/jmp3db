package de.mp3db.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import de.mp3db.gui.MP3Panel;
import de.mp3db.util.MP3Song;
/**
 * @author alex
 *
 */
public class PlaylistPanel extends JPanel implements MP3Panel {
	private MP3DBListener listener;

	private JTable playlistTable = new JTable();
	private JScrollPane playlistScrollPane = new JScrollPane();

	String[] tableHeader =  new String[] { "Title", "Artist", "Zeit" };	
	MP3TableModel tableModel;
	
	public PlaylistPanel() {
		listener = MP3DBListener.getInstance();

		JTableHeader header = playlistTable.getTableHeader();
		//header.setFont(new Font(null, Font.BOLD, 10));
		
		tableModel = new MP3TableModel(tableHeader.length);
		this.setLayout(new BorderLayout());
		TitledBorder test = new TitledBorder("Playlist");
		//test.setTitleFont(new Font(null, Font.BOLD, 10));
		this.setBorder(test);
		this.add(playlistScrollPane, BorderLayout.CENTER);
		this.add(this.initPanel(), BorderLayout.SOUTH);		
	}

	private JPanel initPanel() {
		JPanel extra = new JPanel();
		JButton button;
		// Add Button
		button = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/Add16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("addPlaylist");
		button.setToolTipText("Add Song");
		button.addActionListener(listener);
		extra.add(button);
		// Remove Button
		button = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/Remove16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("rmvSong");
		button.setToolTipText("Remove Song");
		button.addActionListener(listener);
		extra.add(button);
		button = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/Delete16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("clrPlaylist");
		button.setToolTipText("Clear Playlist");
		button.addActionListener(listener);
		extra.add(button);
		button = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/Export16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("loadPlaylist");
		button.setToolTipText("Load Playlist");
		button.addActionListener(listener);
		extra.add(button);
		button = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icons/Import16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("savePlaylist");
		button.setToolTipText("Save Playlist");
		button.addActionListener(listener);
		extra.add(button);

		DefaultTableColumnModel cm = new DefaultTableColumnModel();
		for (int i = 0; i < tableHeader.length; ++i) {
			int width = 0;
			switch(i) {
				case 0 : width = 80; 
				break;
				case 1 : width = 80;
				break;
				case 2 : width = 40;
				break;
			}
			TableColumn col = new TableColumn(i, width);
			col.setHeaderValue(tableHeader[i]);
			cm.addColumn(col);
		}		

		playlistTable.setModel(tableModel);
		playlistTable.addMouseListener(listener);
		playlistTable.setColumnModel(cm);
		//playlistTable.setFont(new Font("Verdana", Font.PLAIN, 10));
		playlistTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		playlistTable.setRowSelectionAllowed(true);
		playlistTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistScrollPane.setViewportView(playlistTable);

		return extra;
	}
	
	public void clear() {
		tableModel.clear();
	}

	public void add(MP3Song newRow) {
		String length = "";
		if(newRow.getLength() > 1) {
			length = newRow.getLength()/60 + ":";
			if(newRow.getLength()%60 < 10)
				length += newRow.getLength()%60 + "0";
			else
				length += newRow.getLength()%60;
		}
		tableModel.add(new String[] { newRow.getTitle(), newRow.getArtist(), length });
	}
	
	public int getSelection() {
		return playlistTable.getSelectedRow();
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
		playlistTable.setRowSelectionInterval(row, row);
	}

}