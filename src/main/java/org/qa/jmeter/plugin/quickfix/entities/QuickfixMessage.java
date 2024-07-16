package org.qa.jmeter.plugin.quickfix.entities;

import java.util.Iterator;

import org.json.JSONObject;

import quickfix.FieldMap;
import quickfix.IntField;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.SessionID;
import quickfix.StringField;

public class QuickfixMessage {
	private Message message;
	private SessionID sessionId;
	private long createdTimestamp;

	public QuickfixMessage() {
		this(new Message());
	}

	public QuickfixMessage(Message message) {
		this.setCreatedTimestamp(System.currentTimeMillis());
		this.setMessage(message);
	}

	public QuickfixMessage(String messageAsJson) {
		this(convertJsonMessageToMessage(messageAsJson));
	}

	public QuickfixMessage(Message message, SessionID sessionId) {
		this(message);
		this.setSessionId(sessionId);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public SessionID getSessionId() {
		return sessionId;
	}

	public void setSessionId(SessionID sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Convert message as json string to qf message. JSON format should be as
	 * below:<br>
	 * {header:{"name":"value","name1":"value1"},"field":{"name":"value","name1":"value1"}}
	 * 
	 * @param jsonString
	 *            A JSON string in correct format
	 * 
	 * @return {@link quickfix.Message} The message in Quickfix format
	 */
	private static Message convertJsonMessageToMessage(String jsonString) {
		Message msg = new Message();

		Header header = msg.getHeader();
		JSONObject jsonObj = new JSONObject(jsonString);

		// convert headers
		JSONObject jsonHeaderObj = jsonObj.getJSONObject("header");
		parseFieldsToMap(jsonHeaderObj, header);

		// convert fields
		JSONObject jsonFieldsObj = jsonObj.getJSONObject("field");
		parseFieldsToMap(jsonFieldsObj, msg);

		return msg;
	}

	private static void parseFieldsToMap(JSONObject field, FieldMap fieldMap) {
		Iterator<?> keys = field.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Object value = field.get(key);
			addToFieldMap(fieldMap, Integer.parseInt(key), value);
		}
	}

	private static void addToFieldMap(FieldMap map, int key, Object value) {
		if (value instanceof Integer) {
			map.setField(new IntField(key, (Integer) value));
		} else {
			map.setField(new StringField(key, (String) value));
		}
	}
}
