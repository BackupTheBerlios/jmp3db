package de.mp3db.gui.swing;

import java.awt.Event;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author alex
 *
 * 
 */
public class ManagerMenu {
	private MP3DBListener listener;
	
	private JMenu menu = new JMenu();
	private JMenuBar jMenuBar1 = new JMenuBar();

	private JMenuItem connectItem = new JMenuItem("Connect", 'n');
	private JMenuItem scanItem = new JMenuItem("Scan", 's');
	private JMenuItem clearItem = new JMenuItem("ClearDB", 'c');
	private JMenuItem reloadDBItem = new JMenuItem("ReloadDB", 'r');
	private JMenuItem quitItem = new JMenuItem("Quit", 'Q');

	public ManagerMenu() {
		listener = MP3DBListener.getInstance();

//		menu.setFont(new Font("Verdana", Font.BOLD, 10));
		connectItem.addActionListener(listener);
		scanItem.addActionListener(listener);
		clearItem.addActionListener(listener);
		reloadDBItem.addActionListener(listener);
		quitItem.addActionListener(listener);

/*		connectItem.setFont(new Font("Verdana", Font.BOLD, 10));
		scanItem.setFont(new Font("Verdana", Font.BOLD, 10));
		clearItem.setFont(new Font("Verdana", Font.BOLD, 10));
		reloadDBItem.setFont(new Font("Verdana", Font.BOLD, 10));
		quitItem.setFont(new Font("Verdana", Font.BOLD, 10));
*/		
		menu.setMnemonic('F');
		setCtrlAccelerator(connectItem, 'N');
		menu.add(connectItem);
		menu.addSeparator();
		setCtrlAccelerator(scanItem, 'S');
		menu.add(scanItem);
		menu.addSeparator();
		setCtrlAccelerator(clearItem, 'C');
		menu.add(clearItem);
		setCtrlAccelerator(reloadDBItem, 'r');
		menu.add(reloadDBItem);
		menu.addSeparator();
		quitItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Exit16.gif")));
		menu.add(quitItem);
		
		menu.setText("File");
		jMenuBar1.add(menu);
	}
	
	public JMenuBar getMenu() {
		return jMenuBar1;
	}

	private void setCtrlAccelerator(JMenuItem mi, char acc) {
		KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
		mi.setAccelerator(ks);
	}
}
