package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.compiler.OchreCompiler;
import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OchreToJavaCompiler implements OchreCompiler {

	@Override
	public String compile(String source) {
		return parse(source).resultValue;
	}

	private ParsingResult<String> parse(String source) {
		ParsingResult<String> parsingResult = new ReportingParseRunner<String>(
				Parboiled.createParser(OchreRules.class)
				.CompilationUnit())
				.run(source);
		if(parsingResult.hasErrors()) {
			log.error(ErrorUtils.printParseErrors(parsingResult));
			throw new IllegalStateException(
					"Parse errors found.");
		}
		return parsingResult;
	}

}
