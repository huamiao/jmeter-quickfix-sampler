package org.qa.jmeter.plugin.quickfix.samplers.gui;

import java.awt.BorderLayout;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

public abstract class QuickfixSamplerGui extends AbstractSamplerGui {

	private static final long serialVersionUID = -131394912581723713L;

	public QuickfixSamplerGui() {
		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(this.makeBorder());
		this.add(this.makeTitlePanel(), BorderLayout.NORTH);
	}

	@Override
	public abstract String getStaticLabel();
}
