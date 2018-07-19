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
		return Sequence(
				CharRange('a', 'z'),
				push(match()));
	}

	public Rule CharUpperAToUpperZ() {
		return Sequence(
				CharRange('A', 'Z'),
				push(match()));
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
