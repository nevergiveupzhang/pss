package com.psssytem.server.util;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.psssystem.server.serviceimpl.storageserviceimpl.AlarmOrderSerivceImpl;
import com.psssystem.server.util.LogUtils;

public class LoggerUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Logger logger=Logger.getLogger(AlarmOrderSerivceImpl.class.getName());
		LogUtils.setLogProperties(logger,AlarmOrderSerivceImpl.class.getName());
		logger.entering("AlarmOrderSerivceImpl", "before addOrder");
	}

}
