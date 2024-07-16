package org.qa.jmeter.plugin.quickfix.samplers;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.qa.jmeter.plugin.quickfix.QuickfixClient;
import org.qa.jmeter.plugin.quickfix.QuickfixInstance;
import org.qa.jmeter.plugin.quickfix.QuickfixServer;
import org.quickfixj.CharsetSupport;

public class QuickfixOpenConnectionSampler extends QuickfixSampler implements Interruptible {
	private static final long serialVersionUID = -176168036545024721L;

	private QuickfixInstance instance;

	public SampleResult sample(Entry arg0) {
		SampleResult result = new SampleResult();
		result.setSampleLabel(this.getName());

		instance = QuickfixInstance.INSTANCE.get();
		String configFile = this.getClientConfigFile();
		int queueCapacity = this.getMessageCacheCapacity();
		boolean isAcceptor = this.getActAsAcceptor();
		boolean waitForLogon = this.getWaitForLogon();
		long waitForLogonTimeout = this.getWaitForLogonTimeout();

		// stop & clean client if exists
		if (instance != null) {
			instance.stop();
			instance = null;
		}

		result.sampleStart();
		try {
			CharsetSupport.setCharset(this.getMessageEncoding());
			if (isAcceptor) {
				instance = new QuickfixServer(configFile, queueCapacity);
			} else {
				instance = new QuickfixClient(configFile, queueCapacity);
			}
			instance.start();
			if ((!isAcceptor) || (isAcceptor && waitForLogon)) {
				this.waitClientLogon(waitForLogonTimeout);
			}
		} catch (Exception e) {
			this.interrupt();
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage(e.getMessage());
			result.setSuccessful(false);
			return result;
		}
		result.sampleEnd();
		result.setResponseCodeOK();
		result.setResponseMessageOK();
		result.setSuccessful(true);

		return result;
	}

	private void waitClientLogon(long timeout) {
		long start = System.currentTimeMillis();
		while (!instance.isLoggedOn()) {
			long now = System.currentTimeMillis();
			if (now - start > timeout) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				instance.stop();
			}
		}
		if (!instance.isLoggedOn()) {
			throw new RuntimeException("Failed to start quickfix instance.");
		}
	}

	public boolean interrupt() {
		instance = QuickfixInstance.INSTANCE.get();

		instance.stop();
		return !instance.isLoggedOn();
	}
}
