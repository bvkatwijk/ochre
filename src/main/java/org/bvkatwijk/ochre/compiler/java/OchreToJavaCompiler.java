package org.bvkatwijk.ochre.compiler.java;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.OchreCompiler;
import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
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
		return parse(source, OchreRules::CompilationUnit).resultValue;
	}

	public String compile(String source, Function<OchreRules, Rule> ruleFunction) {
		return parse(source, ruleFunction).resultValue;
	}

	private ParsingResult<String> parse(String source, Function<OchreRules, Rule> ruleFunction) {
		ParsingResult<String> parsingResult = new ReportingParseRunner<String>(
				ruleFunction.apply(Parboiled.createParser(OchreRules.class)))
				.run(source);
		if(parsingResult.hasErrors()) {
			log.error(ErrorUtils.printParseErrors(parsingResult));
			throw new IllegalStateException(
					"Parse errors found.");
		}
		return parsingResult;
	}

}
