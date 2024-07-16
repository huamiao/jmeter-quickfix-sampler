package org.qa.jmeter.plugin.quickfix.samplers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.qa.jmeter.plugin.quickfix.QuickfixApplication;
import org.qa.jmeter.plugin.quickfix.QuickfixInstance;
import org.qa.jmeter.plugin.quickfix.entities.QuickfixMessage;

public class QuickfixSingleReadSampler extends QuickfixSampler implements Interruptible {
	private static final long serialVersionUID = 1619416789621413481L;

	public SampleResult sample(Entry arg0) {
		SampleResult result = new SampleResult();
		result.setSampleLabel(this.getName());

		int timeout = this.getMessageReadTimeout();
		ArrayBlockingQueue<QuickfixMessage> messageQueue = QuickfixApplication.MESSAGE_QUEUE.get();
		String encoding = this.getMessageEncoding();

		// check if connection has been opened
		if (messageQueue == null) {
			result.setResponseCode("500");
			result.setResponseMessage("Quickfix connection seems not be opened.");
			result.setSuccessful(false);
			return result;
		}

		// get 1 message from message queue
		result.sampleStart();
		QuickfixMessage message = null;
		try {
			message = messageQueue.poll(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			this.interrupt();
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			return result;
		}
		result.sampleEnd();

		if (message == null) {
			result.setResponseCode("504");
			result.setResponseMessage(
					String.format("Sampler received no response (timeout) in %1$s seconds.", timeout));
			result.setSuccessful(false);
			return result;
		}
		result.setResponseCodeOK();
		result.setResponseMessage("Received quickfix message.");
		StringBuilder sb = new StringBuilder();
		sb.append("Session ID:" + message.getSessionId() + System.lineSeparator());
		sb.append("CreatedTimestamp:" + message.getCreatedTimestamp() + System.lineSeparator());
		sb.append("Message body:" + message.getMessage().toString() + System.lineSeparator());
		result.setResponseData(sb.toString(), encoding);
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
