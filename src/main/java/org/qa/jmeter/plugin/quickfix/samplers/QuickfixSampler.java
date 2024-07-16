package org.qa.jmeter.plugin.quickfix.samplers;

import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.jmeter.samplers.AbstractSampler;
import org.qa.jmeter.plugin.quickfix.QuickfixApplication;
import org.qa.jmeter.plugin.quickfix.entities.QuickfixMessage;

public abstract class QuickfixSampler extends AbstractSampler {
	private static final long serialVersionUID = 6689067284224628855L;

	public static final String PK_QUICKFIX_CLIENT_CONFIG_FILE = "quickfix_client_config_file";
	public static final String PK_MESSAGE_CACHE_CAPACITY = "message_cache_capacity";
	public static final String PK_MESSAGE_READ_TIMEOUT_IN_SECONDS = "message_read_timeout_in_seconds";
	public static final String PK_MESSAGE_ENCODING = "message_encoding";
	public static final String PK_MESSAGE_SRING = "message_string";
	public static final String PK_ACT_AS_ACCEPTOR = "act_as_acceptor";
	public static final String PK_WAIT_FOR_LOGON = "wait_for_logon";
	public static final String PK_WAIT_FOR_LOGON_TIMEOUT = "wait_for_logon_timeout";

	protected ArrayBlockingQueue<QuickfixMessage> messageQueue = QuickfixApplication.MESSAGE_QUEUE.get();

	public String getClientConfigFile() {
		return this.getPropertyAsString(PK_QUICKFIX_CLIENT_CONFIG_FILE);
	}

	public void setClientConfigFile(String file) {
		this.setProperty(PK_QUICKFIX_CLIENT_CONFIG_FILE, file);
	}

	public int getMessageCacheCapacity() {
		return this.getPropertyAsInt(PK_MESSAGE_CACHE_CAPACITY, 1000);
	}

	public void setMessageCacheCapacity(int capacity) {
		this.setProperty(PK_MESSAGE_CACHE_CAPACITY, capacity);
	}

	public int getMessageReadTimeout() {
		return this.getPropertyAsInt(PK_MESSAGE_READ_TIMEOUT_IN_SECONDS, 30);
	}

	public void setMessageReadTimeout(String timeoutInSeconds) {
		this.setProperty(PK_MESSAGE_READ_TIMEOUT_IN_SECONDS, timeoutInSeconds);
	}

	public String getMessageEncoding() {
		return this.getPropertyAsString(PK_MESSAGE_ENCODING, Charset.defaultCharset().name());
	}

	public void setMessageEncoding(String encoding) {
		this.setProperty(PK_MESSAGE_ENCODING, encoding);
	}

	public String getMessageString() {
		return this.getPropertyAsString(PK_MESSAGE_SRING, "{\"header\":{},\"field\":{}}");
	}

	public void setMessageString(String message) {
		this.setProperty(PK_MESSAGE_SRING, message);
	}

	public boolean getActAsAcceptor() {
		return this.getPropertyAsBoolean(PK_ACT_AS_ACCEPTOR, false);
	}

	public void setActAsAcceptor(boolean actAsAcceptor) {
		this.setProperty(PK_ACT_AS_ACCEPTOR, actAsAcceptor);
	}

	public boolean getWaitForLogon() {
		return this.getPropertyAsBoolean(PK_WAIT_FOR_LOGON, false);
	}

	public void setWaitForLogon(boolean waitForLogon) {
		this.setProperty(PK_WAIT_FOR_LOGON, waitForLogon);
	}

	public long getWaitForLogonTimeout() {
		return this.getPropertyAsLong(PK_WAIT_FOR_LOGON_TIMEOUT, 30 * 1000);
	}

	public void setWaitForLogonTimeout(String waitForLogonTimeout) {
		this.setProperty(PK_WAIT_FOR_LOGON_TIMEOUT, waitForLogonTimeout);
	}
}
