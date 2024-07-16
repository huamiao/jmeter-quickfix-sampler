package org.qa.jmeter.plugin.quickfix.samplers.gui;

import org.apache.jmeter.testelement.TestElement;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixOpenConnectionSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSampler;

public class QuickfixOpenConnectionSamplerGui extends QuickfixSamplerGui {

	private static final long serialVersionUID = -6261048961684844583L;
	private static final String SAMPLER_LABEL = "Quickfix Open Connection Sampler";

	private QuickfixOpenConnectionSamplerGuiPanel settingsPanel;

	public QuickfixOpenConnectionSamplerGui() {
		super();
		this.settingsPanel = new QuickfixOpenConnectionSamplerGuiPanel();
		this.add(settingsPanel);
	}

	@Override
	public void configure(TestElement element) {
		QuickfixSampler sampler = (QuickfixSampler) element;

		this.settingsPanel.getTxtConfigFilePathValue().setText(sampler.getClientConfigFile());
		this.settingsPanel.getTxtMessageCacheCapacityValue().setText(String.valueOf(sampler.getMessageCacheCapacity()));
		this.settingsPanel.getChckboxIsAcceptor().setSelected(sampler.getActAsAcceptor());
		this.settingsPanel.getChckbxWaitForLogon().setSelected(sampler.getWaitForLogon());
		this.settingsPanel.getTxtWaitForLogonTimeout().setText(String.valueOf(sampler.getWaitForLogonTimeout()));
		super.configure(sampler);
	}

	@Override
	public String getStaticLabel() {
		return QuickfixOpenConnectionSamplerGui.SAMPLER_LABEL;
	}

	public TestElement createTestElement() {
		QuickfixOpenConnectionSampler sampler = new QuickfixOpenConnectionSampler();
		this.configure(sampler);

		return sampler;
	}

	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		QuickfixSampler sampler = (QuickfixSampler) element;
		sampler.setClientConfigFile(this.settingsPanel.getTxtConfigFilePathValue().getText());
		sampler.setMessageCacheCapacity(
				Integer.parseInt(this.settingsPanel.getTxtMessageCacheCapacityValue().getText()));
		sampler.setActAsAcceptor(this.settingsPanel.getChckboxIsAcceptor().isSelected());
		sampler.setWaitForLogon(this.settingsPanel.getChckbxWaitForLogon().isSelected());
		sampler.setWaitForLogonTimeout(this.settingsPanel.getTxtWaitForLogonTimeout().getText());
	}

	public String getLabelResource() {
		return null;
	}

}
