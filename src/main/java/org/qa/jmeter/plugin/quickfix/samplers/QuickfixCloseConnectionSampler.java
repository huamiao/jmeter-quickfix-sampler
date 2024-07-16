package org.qa.jmeter.plugin.quickfix.samplers;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.qa.jmeter.plugin.quickfix.QuickfixInstance;

public class QuickfixCloseConnectionSampler extends QuickfixSampler implements Interruptible {
	private static final long serialVersionUID = -176168036545024721L;

	private QuickfixInstance instance;

	public SampleResult sample(Entry arg0) {
		SampleResult result = new SampleResult();
		result.setSampleLabel(this.getName());

		instance = QuickfixInstance.INSTANCE.get();

		result.sampleStart();

		if (instance == null) {
			result.sampleEnd();
			result.setResponseCodeOK();
			result.setResponseMessageOK();
			result.setSuccessful(true);
			return result;
		}

		instance.stop();

		result.sampleEnd();
		result.setResponseCodeOK();
		result.setResponseMessageOK();
		result.setSuccessful(true);

		return result;
	}

	public boolean interrupt() {
		instance = QuickfixInstance.INSTANCE.get();
		
		instance.stop();
		return !instance.isLoggedOn();
	}
}
