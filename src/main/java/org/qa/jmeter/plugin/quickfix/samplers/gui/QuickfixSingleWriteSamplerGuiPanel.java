package org.qa.jmeter.plugin.quickfix.samplers.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import java.awt.Font;

public class QuickfixSingleWriteSamplerGuiPanel extends JPanel {

	private static final long serialVersionUID = 797557284955916165L;
	private JLabel lblMessage;
	private JTextPane txtMessage;

	public JTextPane getTxtMessage() {
		return txtMessage;
	}

	public QuickfixSingleWriteSamplerGuiPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Arial", Font.PLAIN, 18));
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblMessage);

		txtMessage = new JTextPane();
		add(txtMessage);
	}
}
