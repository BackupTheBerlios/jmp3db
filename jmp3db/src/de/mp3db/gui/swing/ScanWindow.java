package de.mp3db.gui.swing;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import de.mp3db.DBTree;
import de.mp3db.db.ScanEvent;
import de.mp3db.db.ScanListener;

/**
 * @author alex
 *
 */
public class ScanWindow extends JDialog implements ScanListener, ActionListener {

	private JProgressBar bar;
	private JPanel panel;
	private JLabel label;
	private JButton start; 
	private JButton abort;
	private JFileChooser open;
	private JButton openButton;
	private JTextField openField;
	
	private boolean hasStart = false;

	public ScanWindow() {
		this.setModal(true);
		this.setTitle("Scan");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		bar = new JProgressBar();
		start = new JButton("Start");
		abort = new JButton("Abort");
		label = new JLabel("FileName: ");
		
		openButton = new JButton("Open");
		openField = new JTextField();
		
		openButton.addActionListener(this);
		start.addActionListener(this);
		abort.addActionListener(this);
		DBTree.getInstance().addScanListener(this);

		double sizes[][] = {{80, 520 }, // Columns
							{20, 20, 20 }}; // Rows
		
		TableLayout layout = new TableLayout(sizes);
		JPanel noPanel = new JPanel();
		noPanel.setLayout(layout);
		noPanel.add(openButton, "0, 0");
		noPanel.add(openField, "1, 0");
		noPanel.add(abort, "0, 1");
		noPanel.add(label, "1, 1");
		noPanel.add(start, "0, 2");
		noPanel.add(bar, "1, 2");
		this.getContentPane().add(noPanel);
		this.pack();
		this.setLocation(300,200);
		this.setVisible(true);
		
	}
	
	/* (Kein Javadoc)
	 * @see de.mp3db.db.ScanListener#updatePos(de.mp3db.db.ScanEvent)
	 */
	public synchronized void updatePos(ScanEvent event) {
		if(bar.getMaximum() != event.getMax()) {
			bar.setMaximum(event.getMax()); 		
		}
		bar.setValue(event.getPos());
		bar.setString(event.getPos() + " / " + event.getMax());
		label.setText("FileName: " + event.getFileName());
		if(event.getMax() == event.getPos()) {
			this.setVisible(false);
			DBTree.getInstance().reloadDB();
		}
	}

	/* (Kein Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equalsIgnoreCase("Start")) {
			if(!hasStart && openField.getText().length() != 0) { 
				DBTree.getInstance().scan(new File(openField.getText()));
			}
		}
		else if(command.equalsIgnoreCase("Abort")) {
			if(!hasStart) {
				this.setVisible(false);
			}
		}
		else if(command.equalsIgnoreCase("Open")) {
			open = new JFileChooser();
			open.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			open.showDialog(this.getFocusOwner(), "Open");
			openField.setText(open.getSelectedFile().toString());	
		}
	}
}