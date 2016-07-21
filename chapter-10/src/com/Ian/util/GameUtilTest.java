package com.Ian.util;

import org.junit.Test;

import junit.framework.TestCase;

public class GameUtilTest extends TestCase {

	@Test
	public void testVerifyNumber() {
		String str = "1234";
		assertEquals(true, GameUtil.verifyNumber(str));
		str = "12e4";
		assertEquals(false, GameUtil.verifyNumber(str));
	}

	@Test
	public void testVerifyRepeat() {
		String str = "1234";
		assertEquals(false, GameUtil.verifyRepeat(str));
		str = "1244";
		assertEquals(true, GameUtil.verifyRepeat(str));
	}

	@Test
	public void testGuessResult() {
		String str = "1234";
		String str1 = "1206";
		assertEquals("2A1B", GameUtil.guessResult(str, str1));
	}

	@Test 
	public void testGenerateRandNumber(){
		assertEquals(4, GameUtil.generateRandNumber().length());
	}
}
