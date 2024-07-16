package org.qa.jmeter.plugin.quickfix.samplers;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.qa.jmeter.plugin.quickfix.QuickfixInstance;
import org.qa.jmeter.plugin.quickfix.entities.QuickfixMessage;

import quickfix.Session;
import quickfix.SessionNotFound;

public class QuickfixSingleWriteSampler extends QuickfixSampler implements Interruptible {
	private static final long serialVersionUID = 3913423582436782214L;

	public SampleResult sample(Entry arg0) {
		SampleResult result = new SampleResult();
		result.setSampleLabel(this.getName());
		
		String messageString = this.getMessageString();

		// send message
		result.sampleStart();
		QuickfixMessage message = new QuickfixMessage(messageString);
		try {
			Session.sendToTarget(message.getMessage());
		} catch (SessionNotFound e) {
			this.interrupt();
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			return result;
		}
		result.sampleEnd();

		result.setResponseCodeOK();
		result.setResponseMessage("Sent quickfix message.");
		StringBuilder sb = new StringBuilder();
		sb.append("CreatedTimestamp:" + message.getCreatedTimestamp() + System.lineSeparator());
		sb.append("Message body:" + message.getMessage().toString() + System.lineSeparator());
		result.setResponseData(sb.toString(), this.getMessageEncoding());
		result.setSuccessful(true);

		return result;
	}

	// close connection when received stop test command
	public boolean interrupt() {
		QuickfixInstance instance = QuickfixInstance.INSTANCE.get();
		instance.stop();

		return !instance.isLoggedOn();
	}
}
