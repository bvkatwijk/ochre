package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class IdentifierRules extends BaseParser<String> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);
	public final WhiteSpaceRules whitespace = Parboiled.createParser(WhiteSpaceRules.class);

	public Rule Identifier() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()),
				this.whitespace.Spacing());
	}

}
