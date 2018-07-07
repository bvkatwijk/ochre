package org.bvkatwijk.ochre.parser;

import java.util.List;

import org.bvkatwijk.ochre.compiler.java.Parameter;
import org.bvkatwijk.ochre.lang.Import;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParsingException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

/**
 *
 * @author bvkatwijk
 */
public class ImportStatementParserTest {

	private final Rule rule = Parboiled.createParser(ImportStatementParser.class).ImportStatement();

	@Test
	public void testSingleLetterAType() {
		Assert.assertEquals(
				List.of(new Import("A")),
				compile("import A"));
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
