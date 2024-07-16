package org.qa.jmeter.plugin.quickfix.samplers.gui;

import org.apache.jmeter.testelement.TestElement;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSingleReadSampler;

public class QuickfixSingleReadSamplerGui extends QuickfixSamplerGui {

	private static final long serialVersionUID = -3358970650823101401L;
	private static final String SAMPLER_LABEL = "Quickfix Single Read Sampler";

	private QuickfixSingleReadSamplerGuiPanel settingsPanel;

	public QuickfixSingleReadSamplerGui() {
		super();
		this.settingsPanel = new QuickfixSingleReadSamplerGuiPanel();
		this.add(settingsPanel);
	}

	@Override
	public String getStaticLabel() {
		return QuickfixSingleReadSamplerGui.SAMPLER_LABEL;
	}

	@Override
	public void configure(TestElement element) {
		QuickfixSampler sampler = (QuickfixSampler) element;

		this.settingsPanel.getTxtMessageReadTimeoutInSecondsValue()
				.setText(String.valueOf(sampler.getMessageReadTimeout()));
		this.settingsPanel.getTxtMessageEncoding().setText(sampler.getMessageEncoding());
		super.configure(sampler);
	}

	public TestElement createTestElement() {
		QuickfixSingleReadSampler sampler = new QuickfixSingleReadSampler();
		this.configure(sampler);
		
		return sampler;
	}

	public String getLabelResource() {
		return null;
	}

	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		QuickfixSampler sampler = (QuickfixSampler) element;
		sampler.setMessageReadTimeout(this.settingsPanel.getTxtMessageReadTimeoutInSecondsValue().getText());
		sampler.setMessageEncoding(this.settingsPanel.getTxtMessageEncoding().getText());
	}

}
