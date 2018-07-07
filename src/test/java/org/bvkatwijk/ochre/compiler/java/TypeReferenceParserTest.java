package org.bvkatwijk.ochre.compiler.java;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParsingException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class TypeReferenceParserTest {

	private final Rule rule = Parboiled.createParser(TypeReferenceParser.class).Type();

	@Test
	public void testSingleLetterAType() {
		Assert.assertEquals(
				new Type("A"),
				compile("A"));
	}

	@Test
	public void testSingleLetterBType() {
		Assert.assertEquals(
				new Type("B"),
				compile("B"));
	}

	@Test
	public void testTwoLetterType_Aa() {
		Assert.assertEquals(
				new Type("Aa"),
				compile("Aa"));
	}

	@Test
	public void testTwoLetterType_Ab() {
		Assert.assertEquals(
				new Type("Ab"),
				compile("Ab"));
	}

	@Test
	public void testLetterAndDigitType_A1() {
		Assert.assertEquals(
				new Type("A1"),
				compile("A1"));
	}

	@Test(expected = ParsingException.class)
	public void typeReference_singleLowercase_throws() {
		compile("a");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_singleDigit_throws() {
		compile("1");
	}

	private Object compile(String string) {
		ParsingResult<List<Parameter>> parsingResult = new ReportingParseRunner<List<Parameter>>(this.rule)
				.run(string);
		if (parsingResult.hasErrors()) {
			System.out.println(ErrorUtils.printParseErrors(parsingResult));
			throw new ParsingException(
					"Parse errors found.");
		}
		return parsingResult.resultValue;
	}

}
