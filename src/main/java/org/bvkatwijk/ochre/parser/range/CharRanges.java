package org.bvkatwijk.ochre.parser.range;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharRanges {

	private final BaseParser<String> parser;

	public Rule ZeroToNine() {
		return parser.CharRange('0', '9');
	}

	public Rule CharLowerAToLowerZ() {
		return parser.CharRange('a', 'z');
	}

}
