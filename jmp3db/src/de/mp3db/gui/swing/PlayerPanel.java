package de.mp3db.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

import de.mp3db.gui.MP3PlayerPanel;
/**
 * @author alex
 *
 * 
 */
public class PlayerPanel extends JPanel implements MP3PlayerPanel {
	private MP3DBListener listener;

	private JProgressBar progressBar = null;
	
	public PlayerPanel() {
		listener = MP3DBListener.getInstance();

		BorderLayout playerLayout = new BorderLayout();
		this.setLayout(playerLayout);
		this.setBorder(new EtchedBorder());
		this.setPreferredSize(new Dimension(100, 60));
		this.add(this.initPlayer(), BorderLayout.NORTH);
		this.add(progressBar, BorderLayout.SOUTH);
	}

	public void setText(String message) {
		progressBar.setString(message);	
	}

	private JPanel initPlayer() {
		JPanel playPanel = new JPanel();
		JButton button;
		button =
			new JButton(
				new ImageIcon(
					getClass().getClassLoader().getResource(
						"icons/Previous16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("Previous");
		button.addActionListener(listener);
		playPanel.add(button);
		button =
			new JButton(
				new ImageIcon(
					getClass().getClassLoader().getResource(
						"icons/Play16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("Play");
		button.addActionListener(listener);
		playPanel.add(button);
		button =
			new JButton(
				new ImageIcon(
					getClass().getClassLoader().getResource(
						"icons/Pause16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("Pause");
		button.addActionListener(listener);
		playPanel.add(button);
		button =
			new JButton(
				new ImageIcon(
					getClass().getClassLoader().getResource(
						"icons/Stop16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("Stop");
		button.addActionListener(listener);
		playPanel.add(button);
		button =
			new JButton(
				new ImageIcon(
					getClass().getClassLoader().getResource(
						"icons/Next16.gif")));
		button.setPreferredSize(new Dimension(20, 20));
		button.setActionCommand("Next");
		button.addActionListener(listener);
		playPanel.add(button);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(100, 18));
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Dialog", Font.PLAIN, 10));
		progressBar.setString("Ready");

		return playPanel;
	}
	
	public synchronized void setBarPos(int pos, int max) {
		if(max != progressBar.getMaximum()) {
			progressBar.setMaximum(max);
		}
		progressBar.setValue(pos);
	}
	
	public synchronized void clrBar() {
		progressBar.setString("Ready");
		progressBar.setValue(0);
	}
}