package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.bvkatwijk.ochre.parser.ClassDeclarationParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.errors.ParsingException;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

public class BaseClassDeclarationParserTest {

	private final Rule rule = Parboiled.createParser(ClassDeclarationParser.class).TypeDeclaration();

	public ClassDeclaration compile(String string) {
		ParsingResult<ClassDeclaration> parsingResult = new ReportingParseRunner<ClassDeclaration>(this.rule)
				.run(string);
		if (parsingResult.hasErrors()) {
			System.out.println(ErrorUtils.printParseErrors(parsingResult));
			throw new ParsingException(
					"Parse errors found.");
		}
		return parsingResult.resultValue;
	}

}
