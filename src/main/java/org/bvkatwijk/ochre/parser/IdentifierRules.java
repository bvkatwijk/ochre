package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressSubnodes;

public class IdentifierRules extends BaseParser<String> {

	private final CharRanges ranges = new CharRanges();
	private final WhiteSpaceRules whitespace = new WhiteSpaceRules();

	@SuppressSubnodes
	@MemoMismatches
	public Rule Identifier() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()),
				this.whitespace.Spacing());
	}

}
