package org.qa.jmeter.plugin.quickfix.samplers.gui;

import org.apache.jmeter.testelement.TestElement;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSingleWriteSampler;

public class QuickfixSingleWriteSamplerGui extends QuickfixSamplerGui {

	private static final long serialVersionUID = 8165955487640601264L;
	private static final String SAMPLER_LABEL = "Quickfix Single Write Sampler";
	private QuickfixSingleWriteSamplerGuiPanel settingsPanel;

	public QuickfixSingleWriteSamplerGui() {
		super();
		this.settingsPanel = new QuickfixSingleWriteSamplerGuiPanel();
		this.add(settingsPanel);
	}

	@Override
	public String getStaticLabel() {
		return QuickfixSingleWriteSamplerGui.SAMPLER_LABEL;
	}

	@Override
	public void configure(TestElement element) {
		QuickfixSampler sampler = (QuickfixSampler) element;
		this.settingsPanel.getTxtMessage().setText(String.valueOf(sampler.getMessageString()));

		super.configure(sampler);
	}

	public TestElement createTestElement() {
		QuickfixSingleWriteSampler sampler = new QuickfixSingleWriteSampler();
		this.configure(sampler);
		
		return sampler;
	}

	public String getLabelResource() {
		return null;
	}

	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);
		QuickfixSingleWriteSampler sampler = (QuickfixSingleWriteSampler) element;
		sampler.setMessageString(this.settingsPanel.getTxtMessage().getText());
	}

}
