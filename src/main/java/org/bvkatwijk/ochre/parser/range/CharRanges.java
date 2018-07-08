package org.bvkatwijk.ochre.parser.range;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.MemoMismatches;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharRanges extends BaseParser<String> {

	public Rule Digit() {
		return ZeroToNine();
	}

	public Rule ZeroToNine() {
		return Sequence(
				CharRange('0', '9'),
				push(match()));
	}

	public Rule CharLowerAToLowerZ() {
		return CharRange('a', 'z');
	}

	public Rule CharUpperAToUpperZ() {
		return CharRange('A', 'Z');
	}

	public Rule Letter() {
		return FirstOf(
				CharLowerAToLowerZ(),
				CharUpperAToUpperZ(), '_', '$');
	}

	@MemoMismatches
	public Rule LetterOrDigit() {
		return FirstOf(
				CharLowerAToLowerZ(),
				CharUpperAToUpperZ(),
				ZeroToNine(),
				'_',
				'$');
	}

}
