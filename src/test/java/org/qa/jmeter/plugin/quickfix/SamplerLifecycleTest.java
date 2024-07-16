package org.qa.jmeter.plugin.quickfix;

import java.io.UnsupportedEncodingException;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixCloseConnectionSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixOpenConnectionSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSingleReadSampler;
import org.qa.jmeter.plugin.quickfix.samplers.QuickfixSingleWriteSampler;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import quickfix.ConfigError;
import quickfix.SessionNotFound;

/**
 * Unit test for quickfix samplers.
 */
@Test(testName = "SamplerLifecycleTest", description = "Tests for QuickfixSampler")
public class SamplerLifecycleTest {
	QuickfixInstance server;
	QuickfixInstance client;

	@AfterClass
	public void cleanup() {
		QuickfixInstance instance = QuickfixInstance.INSTANCE.get();
		if (instance != null) {
			instance.stop(true);
		}
	}

	@Test
	public void testOpenConnectionSamplerAsAcceptor() throws UnsupportedEncodingException {
		QuickfixOpenConnectionSampler opSampler = new QuickfixOpenConnectionSampler();
		opSampler.setClientConfigFile("quickfix_acceptor.properties");
		opSampler.setActAsAcceptor(true);
		SampleResult r = opSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertNotNull(QuickfixInstance.INSTANCE.get());
		server = QuickfixInstance.INSTANCE.get();
		QuickfixInstance.INSTANCE.set(null);
	}

	@Test(dependsOnMethods = { "testOpenConnectionSamplerAsAcceptor" })
	public void testOpenConnectionSamplerAsClient() throws ConfigError, InterruptedException {
		QuickfixOpenConnectionSampler opSampler = new QuickfixOpenConnectionSampler();
		opSampler.setClientConfigFile("quickfix_config.properties");
		SampleResult r = opSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertNotNull(QuickfixInstance.INSTANCE.get());
		Assert.assertTrue(QuickfixInstance.INSTANCE.get().isLoggedOn());
		client = QuickfixInstance.INSTANCE.get();
	}

	@Test(dependsOnMethods = { "testOpenConnectionSamplerAsClient" })
	public void testSingleWriteMessageSampler() throws SessionNotFound {
		QuickfixInstance.INSTANCE.set(server);
		QuickfixSingleWriteSampler swSampler = new QuickfixSingleWriteSampler();
		swSampler.setMessageString(
				"{\"header\":{\"8\":\"FIXT.1.1\",\"35\":\"W\",\"49\":\"*\",\"56\":\"*\"},\"field\":{\"1000\":\"test message 中文\"}}");
		SampleResult r = swSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertEquals(r.getErrorCount(), 0);
	}

	@Test(dependsOnMethods = { "testSingleWriteMessageSampler" })
	public void testSingleReadMessageSampler() throws SessionNotFound {
		QuickfixInstance.INSTANCE.set(client);
		QuickfixSingleReadSampler srSampler = new QuickfixSingleReadSampler();
		SampleResult r = srSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertTrue(r.getResponseDataAsString().contains("1000=test message 中文"),
				"Response does not contain '1000=test':" + r.getResponseDataAsString());
		Assert.assertEquals(r.getErrorCount(), 0);
	}

	@Test(dependsOnMethods = { "testOpenConnectionSamplerAsClient",
			"testSingleReadMessageSampler" }, ignoreMissingDependencies = true)
	public void testCloseConnectionSampler() throws ConfigError, InterruptedException {
		QuickfixCloseConnectionSampler clSampler = new QuickfixCloseConnectionSampler();
		SampleResult r = clSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertFalse(QuickfixClient.INSTANCE.get().isLoggedOn());

		QuickfixInstance.INSTANCE.set(server);
		clSampler = new QuickfixCloseConnectionSampler();
		r = clSampler.sample(new Entry());
		Assert.assertEquals(r.getResponseCode(), "200", r.getResponseMessage());
		Assert.assertFalse(QuickfixClient.INSTANCE.get().isLoggedOn());
	}
}
