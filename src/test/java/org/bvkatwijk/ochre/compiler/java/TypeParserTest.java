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

public class TypeParserTest {

	private final Rule rule = Parboiled.createParser(TypeParser.class).Type();

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

	@Test(expected = ParsingException.class)
	public void singleType_mustBeUpperCaseLetter() {
		compile("a");
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
