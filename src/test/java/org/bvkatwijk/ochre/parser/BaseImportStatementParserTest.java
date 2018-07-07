package org.bvkatwijk.ochre.parser;

import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParsingException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public abstract class BaseImportStatementParserTest<E> {

	private final Rule rule = Parboiled.createParser(ImportStatementParser.class).ImportStatement();

	public E compile(String string) {
		ParsingResult<E> parsingResult = new ReportingParseRunner<E>(this.rule)
				.run(string);
		if (parsingResult.hasErrors()) {
			System.out.println(ErrorUtils.printParseErrors(parsingResult));
			throw new ParsingException(
					"Parse errors found.");
		}
		return parsingResult.resultValue;
	}
}
