package de.mp3db.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.mp3db.gui.MP3TreePanel;
import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Genre;
import de.mp3db.util.MP3Year;

/**
 * @author alex
 *
 */
public class TreePanel extends JPanel implements MP3TreePanel {
	private MP3DBListener listener;

	private DefaultMutableTreeNode base 			= new DefaultMutableTreeNode("Pool");
	private DefaultMutableTreeNode artistNode 	= new DefaultMutableTreeNode("Artists");
	private DefaultMutableTreeNode genreNode 	= new DefaultMutableTreeNode("Genres");
	private DefaultMutableTreeNode albumNode 	= new DefaultMutableTreeNode("Albums");
	private DefaultMutableTreeNode yearNode 	= new DefaultMutableTreeNode("Years");
	private DefaultMutableTreeNode allNode 		= new DefaultMutableTreeNode("All");
	private DefaultMutableTreeNode searchNode 	= new DefaultMutableTreeNode("Search");
	private DefaultMutableTreeNode specialNode 	= new DefaultMutableTreeNode("Special");
	private DefaultMutableTreeNode playlistNode	= new DefaultMutableTreeNode("Playlists");

	private JScrollPane treeScrollPane = new JScrollPane();
	private JTree jTree1 = new JTree(base);

	private DefaultTreeModel tModel;

	public TreePanel() {
		listener = MP3DBListener.getInstance();
		
		this.initTree();		
	}
	
	/**		Initailisiert den Tree
	 * 
	 */
	private void initTree() {
		base.add(artistNode);
		base.add(genreNode);
		base.add(albumNode);
		base.add(yearNode);
		base.add(allNode);
//		base.add(searchNode);
//		base.add(specialNode);
//		base.add(playlistNode);

		tModel = new DefaultTreeModel(base);

		treeScrollPane.setPreferredSize(new Dimension(250, 100));
		treeScrollPane.setViewportView(jTree1);

		TreeSelectionModel tsm = new DefaultTreeSelectionModel();
		tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		jTree1.setSelectionModel(tsm);
		jTree1.setRootVisible(true);
		//jTree1.setFont(new Font("Verdana", Font.BOLD, 10));		
		this.setLayout(new BorderLayout());
		this.add(treeScrollPane, BorderLayout.CENTER);
		
		jTree1.setCellRenderer(new MP3TreeRenderer());
		jTree1.addTreeSelectionListener(listener);
	}
	
	public void clear() {
		jTree1.collapsePath(new TreePath(artistNode.getPath()));
		jTree1.collapsePath(new TreePath(genreNode.getPath()));
		jTree1.collapsePath(new TreePath(albumNode.getPath()));
		jTree1.collapsePath(new TreePath(yearNode.getPath()));
		jTree1.collapsePath(new TreePath(allNode.getPath()));

//		jTree1.updateUI();
		
		artistNode.removeAllChildren();
		genreNode.removeAllChildren();
		albumNode.removeAllChildren();
		yearNode.removeAllChildren();
		allNode.removeAllChildren();
		
		tModel.nodeChanged(artistNode);
		tModel.nodeChanged(genreNode);
		tModel.nodeChanged(albumNode);
		tModel.nodeChanged(yearNode);
		tModel.nodeChanged(allNode);
	}
	
	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3TreePanel#addToAlbum(de.mp3db.util.MP3Album)
	 */
	public void addToAlbum(MP3Album newNode) {
		albumNode.add(new DefaultMutableTreeNode(newNode));
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3TreePanel#addToArtist(de.mp3db.util.MP3Artist)
	 */
	public void addToArtist(MP3Artist newNode) {
		artistNode.add(new DefaultMutableTreeNode(newNode));
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3TreePanel#addToGenre(de.mp3db.util.MP3Genre)
	 */
	public void addToGenre(MP3Genre newNode) {
		genreNode.add(new DefaultMutableTreeNode(newNode));
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3TreePanel#addToYear(de.mp3db.util.MP3Year)
	 */
	public void addToYear(MP3Year newNode) {
		yearNode.add(new DefaultMutableTreeNode(newNode));
	}

}

class MP3TreeRenderer extends DefaultTreeCellRenderer {

	private ImageIcon song;
	private ImageIcon folder;
	private ImageIcon artistIcon;

	public MP3TreeRenderer() {
		song = new ImageIcon(getClass().getClassLoader().getResource("icons/song.gif"));
		folder = new ImageIcon(getClass().getClassLoader().getResource("icons/folder.gif"));
		// albumIcon = new ImageIcon("icons/album.gif");
		// artistIcon = new ImageIcon("icons/artist.gif");
	}
	
	/* (Kein Javadoc)
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5, boolean arg6) {
		super.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
			
		Object userObject = ((DefaultMutableTreeNode)(arg1)).getUserObject();
		if(userObject instanceof MP3Artist) {
			setIcon(song);	
		}
		if(userObject instanceof MP3Year) {
			setIcon(song);	
		}
		if(userObject instanceof MP3Album) {
			setIcon(song);	
		}
		if(userObject instanceof MP3Genre) {
			setIcon(song);	
		}
		if(userObject instanceof String) {
			setIcon(folder);	
		}
		return this;
	}
}