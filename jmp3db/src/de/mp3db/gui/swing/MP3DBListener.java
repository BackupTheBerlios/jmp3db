package de.mp3db.gui.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import de.mp3db.DBTree;
import de.mp3db.MP3Player;
import de.mp3db.Mainlist;
import de.mp3db.MultiPanel;
import de.mp3db.Playlist;
import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Genre;
import de.mp3db.util.MP3Year;

/**
 * @author alex
 *
 */
public class MP3DBListener implements ActionListener, TreeSelectionListener, MouseListener {
	private static MP3DBListener instance;
	 
	public MP3DBListener() {
		 instance = this;
	}
	
	public static MP3DBListener getInstance() {
		if(instance == null) {
			instance = new MP3DBListener();
		}
		return instance;
	}
	
	/* (Kein Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ev) {
		String command = ev.getActionCommand();
		// Menuitems
		if(command.equals("Quit"))
			System.exit(0);
		else if(command.equals("ClearDB")) {
			DBTree.getInstance().cleanDB();
			Mainlist.getInstance().clear();
			Playlist.getInstance().clear();
		}
		else if(command.equals("Scan"))
			new ScanWindow();
		else if(command.equals("Connect"))
			DBTree.getInstance().connect();
		else if(command.equals("ReloadDB")) {
			DBTree.getInstance().reloadDB();
			Mainlist.getInstance().clear();
			Playlist.getInstance().clear();
		}
		// Playeritems
		else if(command.equals("Play"))
			MP3Player.getInstance().play();
		else if (command.equals("Pause"))
			MP3Player.getInstance().pause();
		else if (command.equals("Stop"))
			MP3Player.getInstance().stop();
		else if (command.equals("Previous"))
			MP3Player.getInstance().prev();
		else if (command.equals("Next"))
			MP3Player.getInstance().next();
		// Playlistitems
		else if (command.equals("addPlaylist")) {
			if(Mainlist.getInstance().getSelection() != null) {
				MP3Player.getInstance().addPlaylist(Mainlist.getInstance().getSelection());
			}
		}
		else if (command.equals("clrPlaylist")) {
			MP3Player.getInstance().clearPlaylist();
		}
		else if (command.equals("rmvSong")) {
			Playlist.getInstance().removeSelection();
		}
		// Edititems
		else if(command.equals("saveSong")) {
			DBTree.getInstance().save(MultiPanel.getInstance().changedSong());
		}
		else if(command.equals("abortEdit")) {
			
		}
		else if(command.equals("resetEdit")) {
			MultiPanel.getInstance().resetEdit();
		}
		else if(command.equals("Search")) {
			MultiPanel.getInstance().search();
		}
	}

	/* (Kein Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent event) {
		if(event.getSource() instanceof JTree) {
			try {
				JTree eventTree = (JTree)event.getSource();
				Object selectedTreeComponent = ((DefaultMutableTreeNode)eventTree.getLastSelectedPathComponent()).getUserObject();

				if(selectedTreeComponent instanceof MP3Artist) {
					Vector artSongs = DBTree.getInstance().getSongsByArtist(((MP3Artist)selectedTreeComponent).getID());
					Mainlist.getInstance().add(artSongs);
				}
				else if(selectedTreeComponent instanceof MP3Album) {
					Vector albSongs = DBTree.getInstance().getSongsByAlbum(((MP3Album)selectedTreeComponent).getID());
					Mainlist.getInstance().add(albSongs);
				}
				else if(selectedTreeComponent instanceof MP3Year) {
					Vector yearSongs = DBTree.getInstance().getSongsByYear(((MP3Year)selectedTreeComponent).getID());
					Mainlist.getInstance().add(yearSongs);
				}
				else if(selectedTreeComponent instanceof MP3Genre) {
					Vector genreSongs = DBTree.getInstance().getSongsByGenre(((MP3Genre)selectedTreeComponent).getID());
					Mainlist.getInstance().add(genreSongs);
				}
				else if(selectedTreeComponent instanceof String) {
					if (selectedTreeComponent.toString().equals("All")) {
						Vector actSongs = DBTree.getInstance().getActSongs();
						Mainlist.getInstance().add(actSongs);
					}
				}
			}
			catch(Exception ex) {
				System.out.println("Unable to get Object, because : " + ex);
			}
		}
	}
	
	/* (Kein Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {
		JTable tmp = (JTable)arg0.getSource();
		
		if(tmp.getColumnCount() == 8) {
			// Mainlist Table
			if(arg0.getClickCount() == 2 && arg0.getButton() == MouseEvent.BUTTON1) {
				if(Mainlist.getInstance().getSelection() != null) {
					MP3Player.getInstance().addPlaylist(Mainlist.getInstance().getSelection());
				}
			}
		}
		else {
			// Playlist Table
			if(arg0.getClickCount() == 2 && arg0.getButton() == MouseEvent.BUTTON1) {
				MP3Player.getInstance().selSong();
			}			
		}
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {

	}

	/* (Kein Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {

	}

	/* (Kein Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON3) {
			Object source = arg0.getSource();
			if(source instanceof JTable) {
				JTable tmp = (JTable)source;
				if(tmp.getColumnCount() == 8) {
					int row = tmp.rowAtPoint(new Point(arg0.getX(), arg0.getY()));
					Mainlist.getInstance().setSelection(row);
					MultiPanel.getInstance().edit(Mainlist.getInstance().getSelection());
				}
				else {
					
				}
			}
		}
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {

	}

}