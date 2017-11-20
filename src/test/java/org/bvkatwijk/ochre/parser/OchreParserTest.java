package org.bvkatwijk.ochre.parser;

import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class OchreParserTest {

	private static final String source = ""
			+ "\n" + "package org.bvkatwijk.ochre.compiler;"
			+ "\n" + ""
			+ "\n" + "class Example {"
			+ "\n" + ""
			+ "\n" + "}";

	@Test
	public void parser_test() {
		ParsingResult<Object> result = run(Parboiled.createParser(OchreRules.class).CompilationUnit().suppressNode(), source);
		if (!result.matched) {
			System.out.printf("\nParse error(s):\n%s", ErrorUtils.printParseErrors(result));
		} else {
			System.out.print(this.getClass().getSimpleName() + ": Parsing Successful.");
		}
	}

	protected ParsingResult<Object> run(Rule rootRule, String sourceText) {
		return new ReportingParseRunner<Object>(rootRule).run(sourceText);
	}


}