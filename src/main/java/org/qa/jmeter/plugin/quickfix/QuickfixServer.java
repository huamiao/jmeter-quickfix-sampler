package org.qa.jmeter.plugin.quickfix;

import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.Acceptor;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.NoopStoreFactory;
import quickfix.SLF4JLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

public class QuickfixServer extends QuickfixInstance {
	private Acceptor acceptor = null;

	/*
	 * Constructor of the Quickfix client
	 */
	public QuickfixServer(String settingsFile) {
		super(settingsFile);
	}

	public QuickfixServer(String settingsFile, int mqCapacity) {
		super(settingsFile, mqCapacity);
	}

	public void start() throws ConfigError {
		logger.debug("Starting quickfix server...");
		SessionSettings settings = new SessionSettings(settingsFile);
		MessageStoreFactory storeFactory = new NoopStoreFactory();
		LogFactory logFactory = new SLF4JLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();
		QuickfixApplication application = new QuickfixApplication(mqCapacity);
		acceptor = new SocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);

		acceptor.start();
		logger.debug("Quickfix server started...");
	}

	public void stop(boolean force) {
		logger.debug("Stopping quickfix server...");
		acceptor.stop(force);
		logger.debug(String.format("Quickfix server stopped %1$s", force ? "forcly" : "gracefully"));
	}

	public void stop() {
		stop(false);
	}

	public boolean isLoggedOn() {
		return this.acceptor.isLoggedOn();
	}
}
