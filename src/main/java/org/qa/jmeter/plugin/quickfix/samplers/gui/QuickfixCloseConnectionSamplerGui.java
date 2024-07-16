package org.qa.jmeter.plugin.quickfix.samplers.gui;

import org.apache.jmeter.testelement.TestElement;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixCloseConnectionSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSampler;

public class QuickfixCloseConnectionSamplerGui extends QuickfixSamplerGui {

	private static final long serialVersionUID = 221170673534687559L;
	
	private static final String SAMPLER_LABEL = "Quickfix Close Connection Sampler";

	public QuickfixCloseConnectionSamplerGui() {
		super();
	}
	
	@Override
	public void configure(TestElement element) {
		QuickfixSampler sampler = (QuickfixSampler) element;

		super.configure(sampler);
	}
	
	public TestElement createTestElement() {
		QuickfixCloseConnectionSampler sampler = new QuickfixCloseConnectionSampler();
		this.configure(sampler);
		
		return sampler;
	}

	public String getLabelResource() {
		return null;
	}

	@Override
	public String getStaticLabel() {
		return QuickfixCloseConnectionSamplerGui.SAMPLER_LABEL;
	}

	public void modifyTestElement(TestElement element) {
		super.configureTestElement(element);	
	}

}
