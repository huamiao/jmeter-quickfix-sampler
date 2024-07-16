package org.qa.jmeter.plugin.quickfix.samplers.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class QuickfixOpenConnectionSamplerGuiPanel extends JPanel {

	private static final long serialVersionUID = -9052883595527855214L;

	private JTextField txtConfigFilePathValue;
	private JTextField txtMessageCacheCapacityValue;
	private JCheckBox chckbxIsAcceptor;
	private JCheckBox chckbxWaitForLogon;
	private JTextField txtWaitForLogonTimeout;
	private JLabel lblWaitForLogonTimeout;

	public JTextField getTxtConfigFilePathValue() {
		return txtConfigFilePathValue;
	}

	public JTextField getTxtMessageCacheCapacityValue() {
		return txtMessageCacheCapacityValue;
	}

	public JCheckBox getChckboxIsAcceptor() {
		return chckbxIsAcceptor;
	}
	
	public JCheckBox getChckbxWaitForLogon() {
		return chckbxWaitForLogon;
	}

	public JTextField getTxtWaitForLogonTimeout() {
		return txtWaitForLogonTimeout;
	}

	public QuickfixOpenConnectionSamplerGuiPanel() {
		setLayout(null);

		JLabel lblConfigFilePath = new JLabel("Config file:");
		lblConfigFilePath.setBounds(10, 10, 78, 29);
		lblConfigFilePath.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblConfigFilePath);

		JLabel lblMessageCacheCapacity = new JLabel("Message cache capacity:");
		lblMessageCacheCapacity.setBounds(10, 45, 146, 29);
		lblMessageCacheCapacity.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblMessageCacheCapacity);

		txtMessageCacheCapacityValue = new JTextField("message_cache_capacity");
		txtMessageCacheCapacityValue.setBounds(166, 45, 274, 29);
		txtMessageCacheCapacityValue.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtMessageCacheCapacityValue);

		txtConfigFilePathValue = new JTextField("quickfix_client_config_file");
		txtConfigFilePathValue.setBounds(98, 10, 342, 29);
		txtConfigFilePathValue.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtConfigFilePathValue);

		JLabel lblIsAcceptor = new JLabel("Act as an Acceptor:");
		lblIsAcceptor.setHorizontalAlignment(SwingConstants.LEFT);
		lblIsAcceptor.setBounds(10, 84, 146, 29);
		add(lblIsAcceptor);

		chckbxIsAcceptor = new JCheckBox("Check me if you want to open as an Acceptor.");
		chckbxIsAcceptor.setBounds(131, 87, 309, 23);
		add(chckbxIsAcceptor);
		
		chckbxWaitForLogon = new JCheckBox("Wait for client to logon?");
		chckbxWaitForLogon.setBounds(10, 126, 185, 24);
		add(chckbxWaitForLogon);
		
		txtWaitForLogonTimeout = new JTextField("Timeout for waiting client to logon");
		txtWaitForLogonTimeout.setBounds(362, 121, 78, 29);
		txtWaitForLogonTimeout.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtWaitForLogonTimeout);
		
		lblWaitForLogonTimeout = new JLabel("Logon Timeout:");
		lblWaitForLogonTimeout.setHorizontalAlignment(SwingConstants.LEFT);
		lblWaitForLogonTimeout.setBounds(261, 121, 91, 29);
		add(lblWaitForLogonTimeout);
	}
}
