package com.github.jezstewartdev.kata.stringcalculator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringCalculator {

	private static final String DEFAULT_DELIMETER = ",";
	private static final String NEW_LINE = "\n";
	private static final String START_CUSTOM_DELIMETER = "//";
	private static final String END_CUSTOM_DELIMETER = "\n";
	private static final String START_MULTICHARACTER_DELIMETER = "\\[";
	private static final String END_MULTICHARACTER_DELIMETER = "\\]";
	private static final String CUSTOM_DELIMITER_SPECIFIED_EMPTY = String.format("%s(.|(%s.*%s)*)%s",
			START_CUSTOM_DELIMETER, START_MULTICHARACTER_DELIMETER, END_MULTICHARACTER_DELIMETER, END_CUSTOM_DELIMETER);
	private static final String CUSTOM_DELIMITER_SPECIFIED = CUSTOM_DELIMITER_SPECIFIED_EMPTY + "(.|\\n)*";
	
	private class Args {
	    List<String> delimeters;
	    String numbersString;
	  }

	public int add(String argString) {
		if (argString.isEmpty() || argString.matches(CUSTOM_DELIMITER_SPECIFIED_EMPTY)) return 0;
		Args args = splitArgStringIntoDelimetersAndNumbersString(argString);
		String splitRegex = createSplitRegex(args.delimeters);
		String[] numbers = args.numbersString.split(splitRegex);
		return calculateSum(numbers);
	}

	private Args splitArgStringIntoDelimetersAndNumbersString(String argString) {
		Args args = new Args();
		args.delimeters = new ArrayList<>();
		args.delimeters.add(NEW_LINE);
		if (argString.matches(CUSTOM_DELIMITER_SPECIFIED)) {
			String[] splitArgString = argString
					.split(String.format("%s|%s", START_CUSTOM_DELIMETER, END_CUSTOM_DELIMETER), 3);
			for (String d : splitArgString[1].replaceAll(START_MULTICHARACTER_DELIMETER, "").split(END_MULTICHARACTER_DELIMETER)) {
				args.delimeters.add(d);
			}	
			args.numbersString = splitArgString[2];
		} else {
			args.delimeters.add(DEFAULT_DELIMETER);
			args.numbersString = argString;
		}
		return args;
	}

	private String createSplitRegex(List<String> delimeters) {
		StringBuilder splitRegex = new StringBuilder();
		for (Iterator<String> it = delimeters.iterator(); it.hasNext();) {
		    splitRegex.append(String.format("(%s)",it.next()));
		    if (it.hasNext()) {
		        splitRegex.append("|");
		    }
		}
		return splitRegex.toString();
	}

	private int calculateSum(String[] numbers) {
		int sum = 0;
		List<Integer> negatives = new ArrayList<>();
		for (String number : numbers) {
			int numberInt = Integer.parseInt(number);
			if (numberInt < 0)
				negatives.add(numberInt);
			else if (numberInt <= 1000)
				sum += numberInt;
		}
		if (!negatives.isEmpty())
			throw new IllegalArgumentException(String.format("Negatives not allowed: %s", negatives.toString()));
		return sum;
	}
	
	

}
