package org.bvkatwijk.ochre.compiler.java;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class FormalParameterParserTest {

	private final Rule rule = Parboiled.createParser(FormalParameterParser.class).FormalParameter();

	@Test
	public void testSingleParameter() {
		Assert.assertEquals(
				new Parameter("name", "String"),
				compile("name: String"));
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
