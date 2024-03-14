package com.github.jezstewartdev.kata.stringcalculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestStringCalculator {
	
	StringCalculator sc = new StringCalculator();
	
	@Test
	void addEmptyStringShouldReturn0() {
		assertEquals(0, sc.add(""));
	}
	
	@Test
	void add1NumberShouldReturnNumber() {
		assertEquals(5, sc.add("5"));
	}
	
	@Test
	void add2NumbersShouldReturnSumOfThoseTwoNumbers() {
		assertEquals(7, sc.add("3,4"));
	}
	
	@Test
	void add3NumbersShouldReturnSumOfThose3Numbers() {
		assertEquals(12, sc.add("3,4,5"));
	}
	
	@Test
	void add3NumbersShouldReturnSumOfThose3NumbersWhenNewlineIncluded() {
		assertEquals(12, sc.add("3\n4,5"));
	}
	
	@Test
	void addUsingColonAsDelimiter() {
		assertEquals(12, sc.add("//:\n3\n4:5"));
	}
	
	@Test
	void addUsingColonAsDelimiterWithNoNumbers() {
		assertEquals(0, sc.add("//:\n"));
	}
	
	@Test
	void addWithNegativeNumbersShouldThrowExceptionListingAllNegatitves() {
		Exception thrown = assertThrows(IllegalArgumentException.class, ()-> sc.add("//:\n3\n4:5:-7:-36"));
		assertEquals("Negatives not allowed: [-7, -36]", thrown.getMessage());
	}
	
	@Test
	void addNumbersGreaterThan1000ShouldBeIgnored() {
		assertEquals(1012, sc.add("1000,1001,3,4,5"));
	}
	
	@Test
	void regexSquareBrackets() {
		assertTrue("[]".matches("\\[\\]"));
	}
	
	@Test
	void addUsingColonAndAmpersandAsDelimiter() {
		assertEquals(12, sc.add("//[:&]\n3\n4:&5"));
	}
	
	@Test
	void addUsingMultipleDelimeters() {
		assertEquals(25, sc.add("//[:&][<>][~~~]\n3\n4:&5<>6~~~7"));
	}

}
