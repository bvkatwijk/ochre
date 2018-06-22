package org.bvkatwijk.ochre.parser.range;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.MemoMismatches;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharRanges {

	private final BaseParser<String> parser;

	public Rule Digit() {
		return ZeroToNine();
	}

	public Rule ZeroToNine() {
		return this.parser.CharRange('0', '9');
	}

	public Rule CharLowerAToLowerZ() {
		return this.parser.CharRange('a', 'z');
	}

	public Rule CharUpperAToUpperZ() {
		return this.parser.CharRange('A', 'Z');
	}

	public Rule Letter() {
		return this.parser.FirstOf(
				CharLowerAToLowerZ(),
				CharUpperAToUpperZ(), '_', '$');
	}

	@MemoMismatches
	public Rule LetterOrDigit() {
		return this.parser.FirstOf(
				CharLowerAToLowerZ(),
				CharUpperAToUpperZ(),
				ZeroToNine(),
				'_',
				'$');
	}

}
