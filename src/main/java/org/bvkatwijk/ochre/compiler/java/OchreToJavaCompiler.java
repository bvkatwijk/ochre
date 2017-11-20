package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.compiler.OchreCompiler;
import org.bvkatwijk.ochre.parser.OchreNewRules;
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
		System.out.println(parse(source));

		return ""
		+ "\n" + "public " + source.trim();
	}

	private ParsingResult<Object> parse(String source) {
		ParsingResult<Object> parsingResult = new ReportingParseRunner<Object>(
				Parboiled.createParser(OchreNewRules.class)
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
