package org.bvkatwijk.ochre.parser;

import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParsingException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public abstract class BaseParserTest<E> {

	public abstract Rule getRule();

	public E compile(String string) {
		ParsingResult<E> parsingResult = new ReportingParseRunner<E>(getRule())
				.run(string);
		if (parsingResult.hasErrors()) {
			System.out.println(ErrorUtils.printParseErrors(parsingResult));
			throw new ParsingException(
					"Parse errors found.");
		}
		return parsingResult.resultValue;
	}

}
