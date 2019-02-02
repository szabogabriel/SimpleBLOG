package com.simpleblog.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.simpleblog.utils.IOUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicUserManagerTest {
	
	private static final String STRING_USERS_DIR = IOUtil.getTempFolder().getAbsolutePath() + "/users";
	private static final String STRING_USERS_FILE = STRING_USERS_DIR + "/users.properties";
	
	private static final File FILE_USERS_DIR = new File(STRING_USERS_DIR);
	private static final File FILE_USERS_FILE = new File(STRING_USERS_FILE);
	
	private static final String INITUSER = "inituser@gmail.com";
	private static final String INITPASSWORD = "initpassword";
	private static final String INITDATA = INITUSER + ".password=" + INITPASSWORD;
	
	private static final String UNAME = "user1@gmail.com";
	private static final String KEY_SALT = UNAME + ".salt";
	private static final String KEY_PSWD = UNAME + ".password";
	private static final String VAL_PSWD_1 = "user1password";
	private static final String VAL_PSWD_2 = "user1password2";
	
	private static BasicUserManager bum;
	
	@BeforeClass
	public static void prepareProps() {
		if (!FILE_USERS_DIR.exists()) {
			FILE_USERS_DIR.mkdir();
		}
		
		if (FILE_USERS_FILE.exists()) {
			FILE_USERS_FILE.delete();
		}
		
		try (FileOutputStream out = new FileOutputStream(FILE_USERS_FILE)) {
			out.write(INITDATA.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bum = new BasicUserManager(FILE_USERS_DIR);
	}
	
	@AfterClass
	public static void cleanup() {
		FILE_USERS_FILE.delete();
		FILE_USERS_DIR.delete();
	}
	
	@Test
	public void test01_createUser() {
		boolean ret = bum.createUser(UNAME, VAL_PSWD_1);
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(STRING_USERS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String salt = props.getProperty(KEY_SALT);
		String psswd = props.getProperty(KEY_PSWD);
		
		assertTrue(ret);
		assertNotNull(salt);
		assertNotNull(psswd);
		assertTrue(salt.length() > 0);
		assertTrue(psswd.length() > 0);
	}
	
	@Test
	public void test02_createInvalidUsers() {
		boolean ret = bum.createUser(null, "passwordForInvalid");
		assertFalse(ret);
		
		ret = bum.createUser("unameForInavlid", null);
		assertFalse(ret);
		
		ret = bum.createUser("", "passwordForInvalid");
		assertFalse(ret);
		
		ret = bum.createUser("unameForInavlid", "");
		assertFalse(ret);
	}
	
	@Test
	public void test03_isKnownUser() {
		boolean ret = bum.isKnownUsername(UNAME);
		assertTrue(ret);
		
		ret = bum.isKnownUsername("invalidUsername");
		assertFalse(ret);
		
		ret = bum.isKnownUsername(UNAME + "_invalidated");
		assertFalse(ret);
		
		ret = bum.isKnownUsername("prefixed_" + UNAME);
		assertFalse(ret);
		
		ret = bum.isKnownUsername("");
		assertFalse(ret);
		
		ret = bum.isKnownUsername(null);
		assertFalse(ret);
	}
	
	@Test
	public void test04_isCorrectCredentials() {
		boolean ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_1);
		assertTrue(ret);
		
		ret = bum.isCorrectCredentials(null, VAL_PSWD_1);
		assertFalse(ret);
		
		ret = bum.isCorrectCredentials("", VAL_PSWD_1);
		assertFalse(ret);
		
		ret = bum.isCorrectCredentials(UNAME, null);
		assertFalse(ret);
		
		ret = bum.isCorrectCredentials(UNAME, "");
		assertFalse(ret);
		
		ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_2);
		assertFalse(ret);
	}

	@Test
	public void test05_changePassword() {
		boolean ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_1);
		assertTrue(ret);
		
		ret = bum.changePassword(UNAME, VAL_PSWD_1);
		assertTrue(ret);
		
		ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_1);
		assertTrue(ret);
		
		ret = bum.changePassword(UNAME, null);
		assertFalse(ret);
		
		ret = bum.changePassword(UNAME, "");
		assertFalse(ret);
		
		ret = bum.changePassword(UNAME, VAL_PSWD_2);
		assertTrue(ret);
		
		ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_1);
		assertFalse(ret);
		
		ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_2);
		assertTrue(ret);
	}
	
	@Test
	public void test06_removeUser() {
		boolean ret = bum.isCorrectCredentials(UNAME, VAL_PSWD_1);
		assertFalse(ret);
		
		ret = bum.removeUser(UNAME, VAL_PSWD_1);
		assertFalse(ret);
		
		ret = bum.removeUser(UNAME, null);
		assertFalse(ret);
		
		ret = bum.removeUser(UNAME, "");
		assertFalse(ret);
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(STRING_USERS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(props.containsKey(UNAME + ".salt"));
		
		ret = bum.removeUser(UNAME, VAL_PSWD_2);
		assertTrue(ret);
		
		ret = bum.isKnownUsername(UNAME);
		assertFalse(ret);
		
		try {
			props.load(new FileInputStream(STRING_USERS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse(props.containsKey(UNAME));
	}
	
	@Test
	public void test07_inituserTest() {
		boolean ret = bum.isKnownUsername(INITUSER);
		assertTrue(ret);
		
		ret = bum.isCorrectCredentials(INITUSER, INITPASSWORD);
		assertTrue(ret);
	}
}
