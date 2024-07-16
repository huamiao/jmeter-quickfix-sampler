package org.qa.jmeter.plugin.quickfix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import quickfix.ConfigError;

public abstract class QuickfixInstance {
	public static final ThreadLocal<QuickfixInstance> INSTANCE = new ThreadLocal<QuickfixInstance>();
	
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected String settingsFile = null;
	protected int mqCapacity = 1000;
	
	/*
	 * Constructor of the Quickfix client
	 */
	public QuickfixInstance(String settingsFile) {
		QuickfixInstance.INSTANCE.set(this);
		this.settingsFile = settingsFile;
	}
	
	public QuickfixInstance(String settingsFile, int mqCapacity) {
		this(settingsFile);
		this.mqCapacity = mqCapacity;
	}
	
	public abstract void start() throws ConfigError;
	
	public abstract void stop(boolean force);
	
	public abstract void stop();
	
	public abstract boolean isLoggedOn();
}
