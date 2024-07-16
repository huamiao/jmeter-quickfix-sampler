package org.qa.jmeter.plugin.quickfix;

import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.NoopStoreFactory;
import quickfix.SLF4JLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

public class QuickfixClient extends QuickfixInstance {
	private Initiator initiator = null;

	/*
	 * Constructor of the Quickfix client
	 */
	public QuickfixClient(String settingsFile) {
		super(settingsFile);
	}

	public QuickfixClient(String settingsFile, int mqCapacity) {
		super(settingsFile, mqCapacity);
	}

	public void start() throws ConfigError {
		logger.debug("Starting quickfix client...");
		SessionSettings settings = new SessionSettings(settingsFile);
		MessageStoreFactory storeFactory = new NoopStoreFactory();
		LogFactory logFactory = new SLF4JLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();
		QuickfixApplication application = new QuickfixApplication(mqCapacity);
		initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);

		initiator.start();
		logger.debug("Quickfix client started...");
	}

	public void stop(boolean force) {
		logger.debug("Stopping quickfix client...");
		initiator.stop(force);
		logger.debug(String.format("Quickfix client stopped %1$s", force ? "forcly" : "gracefully"));
	}

	public void stop() {
		stop(false);
	}

	public boolean isLoggedOn() {
		return this.initiator.isLoggedOn();
	}
}
