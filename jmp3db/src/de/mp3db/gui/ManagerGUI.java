package de.mp3db.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.jgoodies.plaf.FontSizeHints;
import com.jgoodies.plaf.Options;

import de.mp3db.gui.swing.MP3Edit;
import de.mp3db.gui.swing.MainPanel;
import de.mp3db.gui.swing.ManagerMenu;
import de.mp3db.gui.swing.PlayerPanel;
import de.mp3db.gui.swing.PlaylistPanel;
import de.mp3db.gui.swing.StatusPanel;
import de.mp3db.gui.swing.TreePanel;

/**
 * 	@author alex
 *		
 * 	Repräsentiert die Manager GUI
 */
public class ManagerGUI {
	private static ManagerGUI instance;

	private JFrame gui = new JFrame("MP3Manager");	
	private StatusPanel status;
	private ManagerMenu menu;

	private MP3Panel playlist;
	private MP3Panel mainlist;
	private MP3TreePanel tree;
	private MP3PlayerPanel player;
	private MP3MultiPanel multi;

	private JPanel rightPanel;
	private JPanel centerPanel;
	
	/**		Konstruktor
	 * 
	 */
	public ManagerGUI() {
		instance = this;	
		this.startSwingGui();
	}
	
	public static ManagerGUI getInstance() {
		if(instance == null) {
			instance = new ManagerGUI();
		}
		return instance;
	}

	private void startSwingGui() {
		this.configureUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Starting Swing GUI");
				
		menu = new ManagerMenu();
		status = new StatusPanel();				

		playlist = new PlaylistPanel();
		mainlist = new MainPanel();
		tree = new TreePanel();
		player = new PlayerPanel();
		multi = new MP3Edit();
		
		gui.setJMenuBar(menu.getMenu());

		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(200, 100));

		gui.getContentPane().add(rightPanel, BorderLayout.EAST);
		gui.getContentPane().add(status.getPanel(), BorderLayout.SOUTH);
		
		rightPanel.add((JPanel)playlist, BorderLayout.CENTER);		
		rightPanel.add((JPanel)player, BorderLayout.NORTH);
		gui.getContentPane().add((JPanel)tree, BorderLayout.WEST);		

		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add((JPanel)mainlist, BorderLayout.CENTER);
		centerPanel.add((JTabbedPane)multi, BorderLayout.SOUTH);

		gui.getContentPane().add(centerPanel, BorderLayout.CENTER);

		gui.setLocation(100, 100);
		gui.setSize(900, 600);
		gui.setVisible(true);
	}
	
	private void configureUI() {
		UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
		Options.setGlobalFontSizeHints(FontSizeHints.MIXED);

		try {
			// jgoodies.com Plastic XP Look and Feel
			// Please see the license agreement
			UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
			//UIManager.setLookAndFeel("com.incors.plaf.kunststoff.KunststoffLookAndFeel");
		} catch (Exception e) {
			System.err.println("Cannot set look & feel: " + e);
		}
	}
	
	public void setStatus(String message) {
		status.clearStatus();
		status.setStatus(message);
	}
	
	public MP3Panel getPlaylist() {
		return playlist;
	}
	
	public MP3Panel getMainlist() {
		return mainlist;
	}
	
	public MP3TreePanel getTree() {
		return tree;
	}
	
	public MP3PlayerPanel getPlayer() {
		return player;
	}
	
	public MP3MultiPanel getMultiPanel() {
		return this.multi;
	}
}