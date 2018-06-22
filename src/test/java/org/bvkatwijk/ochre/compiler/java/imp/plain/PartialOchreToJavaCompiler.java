package org.bvkatwijk.ochre.compiler.java.imp.plain;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.OchreCompiler;
import org.bvkatwijk.ochre.parser.ImportRules;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PartialOchreToJavaCompiler implements OchreCompiler {

	private final Function<ImportRules, Rule> ruleFunction;

	@Override
	public String compile(String source) {
		return parse(source).resultValue;
	}

	public ImportRules compileAndGetRules(String source) {
		ImportRules rules = createParser();
		ParsingResult<String> parsingResult = new ReportingParseRunner<String>(
				this.ruleFunction.apply(rules))
						.run(source);
		if (parsingResult.hasErrors()) {
			log.error(ErrorUtils.printParseErrors(parsingResult));
			throw new IllegalStateException(
					"Parse errors found.");
		}
		return rules;
	}

	private ParsingResult<String> parse(String source) {
		ParsingResult<String> parsingResult = new ReportingParseRunner<String>(
				this.ruleFunction.apply(createParser()))
						.run(source);
		if (parsingResult.hasErrors()) {
			log.error(ErrorUtils.printParseErrors(parsingResult));
			throw new IllegalStateException(
					"Parse errors found.");
		}
		return parsingResult;
	}

	private ImportRules createParser() {
		return Parboiled.createParser(ImportRules.class);
	}

}
