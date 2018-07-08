package org.bvkatwijk.ochre.util.string;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.bvkatwijk.ochre.util.whitespace.WhiteSpace;
import org.junit.Assert;

public class StringHelper {

	private final WhiteSpace whiteSpace = new WhiteSpace();

	public void assertEqual(String expected, String target) {
		final String[] oneLines = lines(expected);
		final String[] otherLines = lines(target);

		assertLengthEquals(oneLines, otherLines);
		assertEachLineEquals(oneLines, otherLines);
	}

	private void assertEachLineEquals(final String[] oneLines, final String[] otherLines) {
		IntStream.range(0, oneLines.length)
				.forEach(i -> assertLineEquals(i, oneLines[i], otherLines[i]));
	}

	private void assertLengthEquals(final String[] oneLines, final String[] otherLines) {
		Arrays.stream(otherLines)
				.map(it -> it)
				.forEach(System.out::println);

		Assert.assertEquals(
				oneLines.length,
				otherLines.length);
	}

	private void assertLineEquals(int line, String expected, String actual) {
		Assert.assertEquals(
				render(line, expected),
				render(line, actual));
	}

	private String render(int line, String value) {
		return "[line " + line + "]" + showWhitespace(value);
	}

	private String showWhitespace(String value) {
		return this.whiteSpace.show(value);
	}

	private String[] lines(String value) {
		return value.split("\n");
	}

}
