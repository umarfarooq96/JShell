package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import validation.Validator;

public class ValidatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidName() {
		assertEquals(true, Validator.isValidName("popd"));
		assertEquals(false, Validator.isValidName("lzoek"));
	}

	@Test
	public void testRedirectionCheck() {
		assertEquals(true, Validator.redirectionRequired(new String[] { ">", "file.txt" }));
	}

}
