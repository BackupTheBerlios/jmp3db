package de.mp3db.gui.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 * @author alex
 *
 * 
 */
public class StatusPanel extends JPanel {
	private JLabel statusLabel = new JLabel("Status", SwingConstants.LEFT);


	public StatusPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(new EtchedBorder());
		this.setPreferredSize(new Dimension(100, 25));

		statusLabel.setFont(new Font("Verdana", Font.PLAIN, 10));

		this.add(statusLabel);
	}

	/**		Gibt Statusmeldungen in Statuszeile aus.
	 * 
	 * 		@param String message enthält die Nachricht
	 */
	public void setStatus(String message) {
		statusLabel.setText("");
		statusLabel.setText(message);
	}

	/**		Löscht Statuszeile.
	 * 
	 */
	public void clearStatus() {
		statusLabel.setText("");
	}
	
	public JPanel getPanel() {
		return this;
	}
	
}
