package org.bvkatwijk.ochre.compiler.java;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class FormalParameterGroupParserTest {

	private final Rule rule = Parboiled.createParser(FormalParameterGroupParser.class).FormalParameters();

	@Test
	public void testEmptyParameterList() {
		Assert.assertEquals(
				List.of(),
				compile("()"));
	}

	@Test
	public void testEmptyParameterListWithSpaces() {
		Assert.assertEquals(
				List.of(),
				compile("( )"));
	}

	@Test
	public void testOneParameterList() {
		Assert.assertEquals(
				List.of(new Parameter("name", "Object")),
				compile("(name: Object)"));
	}

	@Test
	public void testTwoParameterList() {
		Assert.assertEquals(
				List.of(new Parameter("name", "Object"), new Parameter("some", "Other")),
				compile("(name: Object, some: Other)"));
	}

	private Object compile(String string) {
		ParsingResult<List<Parameter>> parsingResult = new ReportingParseRunner<List<Parameter>>(this.rule)
				.run(string);
		if (parsingResult.hasErrors()) {
			System.out.println(ErrorUtils.printParseErrors(parsingResult));
			throw new IllegalStateException(
					"Parse errors found.");
		}
		return parsingResult.resultValue;
	}
}
