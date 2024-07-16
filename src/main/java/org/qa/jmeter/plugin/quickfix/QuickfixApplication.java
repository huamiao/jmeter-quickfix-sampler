package org.qa.jmeter.plugin.quickfix;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.management.OperationsException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qa.jmeter.plugin.quickfix.entities.QuickfixMessage;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.MessageCracker;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

public class QuickfixApplication extends MessageCracker implements Application {
	public static final ThreadLocal<ArrayBlockingQueue<QuickfixMessage>> MESSAGE_QUEUE = new ThreadLocal<ArrayBlockingQueue<QuickfixMessage>>();

	private Logger logger = LogManager.getLogger(this.getClass());
	private ArrayBlockingQueue<QuickfixMessage> messageQueue = QuickfixApplication.MESSAGE_QUEUE.get();

	public QuickfixApplication(int mqCapacity) {
		if (messageQueue == null) {
			messageQueue = new ArrayBlockingQueue<QuickfixMessage>(mqCapacity);
			QuickfixApplication.MESSAGE_QUEUE.set(messageQueue);
		}
	}

	public void onCreate(SessionID sessionId) {
		logger.debug("Quickfix application created with sessionid:" + sessionId);

	}

	public void onLogon(SessionID sessionId) {
		logger.debug("Quickfix application logged on with sessionid:" + sessionId);
	}

	public void onLogout(SessionID sessionId) {
		logger.debug("Quickfix application logged out with sessionid:" + sessionId);
	}

	public void toAdmin(Message message, SessionID sessionId) {
		logger.debug("Received message to admin in session:" + sessionId);
		logger.debug(message.toString());
	}

	public void fromAdmin(Message message, SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		logger.debug("Received message from admin in session:" + sessionId);
		logger.debug(message.toString());
	}

	public void toApp(Message message, SessionID sessionId) throws DoNotSend {
		logger.debug("Received message to app in session:" + sessionId);
		logger.debug(message.toString());
	}

	public void fromApp(Message message, SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		logger.debug("Received message from app in session:" + sessionId);
		logger.debug(message.toString());

		// put message into a public thread queue for jmeter sampler
		QuickfixMessage qfMessage = new QuickfixMessage(message, sessionId);
		qfMessage.setCreatedTimestamp(System.currentTimeMillis());
		try {
			boolean success = messageQueue.offer(qfMessage, 100, TimeUnit.MILLISECONDS);
			if (!success) {
				throw new OperationsException("Failed to add message to the message cache. Maybe the cache is full?");
			}
		} catch (InterruptedException e) {
			logger.warn("Error occurred when receiving message from app.", e);
		} catch (OperationsException e) {
			logger.warn("Operation failed.", e);
		}
	}

}
