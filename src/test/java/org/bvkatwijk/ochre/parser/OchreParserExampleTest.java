package org.bvkatwijk.ochre.parser;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;

public class OchreParserExampleTest {

	private static final String source = ""
			+ "\n" + "public class Example {"
			+ "\n" + ""
			+ "\n" + "}";

	@Test
	public void parser_shouldParseClassName() {
		ParsingResult<Object> result = parse();

		System.out.println(ParseTreeUtils.printNodeTree(result));
		//		System.out.println(result.parseTreeRoot.getChildren());

		//		Assert.assertEquals(
		//				"Example",
		//				result);
	}

	private ParsingResult<Object> parse() {
		ParsingResult<Object> parsingResult = new ReportingParseRunner<Object>(
				Parboiled.createParser(OchreRules.class)
				.CompilationUnit())
				.run(source);
		if(parsingResult.hasErrors()) {
			throw new IllegalStateException(
					"Parse errors found: " + ErrorUtils.printParseErrors(parsingResult));
		}
		return parsingResult;
	}

}
