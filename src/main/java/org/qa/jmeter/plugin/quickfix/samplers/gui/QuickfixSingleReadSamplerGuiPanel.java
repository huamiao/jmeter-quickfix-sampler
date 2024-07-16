package org.qa.jmeter.plugin.quickfix.samplers.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class QuickfixSingleReadSamplerGuiPanel extends JPanel {
	private static final long serialVersionUID = -9052883595527855214L;

	private JTextField txtMessageReadTimeoutInSecondsValue;
	private JTextField txtMessageEncoding;
	
	public JTextField getTxtMessageReadTimeoutInSecondsValue() {
		return txtMessageReadTimeoutInSecondsValue;
	}

	public JTextField getTxtMessageEncoding() {
		return txtMessageEncoding;
	}

	public QuickfixSingleReadSamplerGuiPanel() {
		setLayout(null);

		JLabel lblMessageReadTimeoutInSeconds = new JLabel("Message read timeout in seconds:");
		lblMessageReadTimeoutInSeconds.setBounds(10, 22, 198, 31);
		lblMessageReadTimeoutInSeconds.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblMessageReadTimeoutInSeconds);

		txtMessageReadTimeoutInSecondsValue = new JTextField("message_read_timeout_in_seconds");
		txtMessageReadTimeoutInSecondsValue.setBounds(218, 22, 222, 31);
		txtMessageReadTimeoutInSecondsValue.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtMessageReadTimeoutInSecondsValue);

		JLabel lblEncoding = new JLabel("Message encoding:");
		lblEncoding.setHorizontalAlignment(SwingConstants.LEFT);
		lblEncoding.setBounds(10, 63, 113, 31);
		add(lblEncoding);

		txtMessageEncoding = new JTextField("message_encoding");
		txtMessageEncoding.setHorizontalAlignment(SwingConstants.LEFT);
		txtMessageEncoding.setBounds(133, 63, 307, 31);
		add(txtMessageEncoding);
	}
}
