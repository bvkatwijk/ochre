package org.bvkatwijk.ochre.parser.keywords;

import org.bvkatwijk.ochre.parser.WhiteSpaceRules;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

public class KeywordParser extends BaseParser<Keyword> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);
	public final WhiteSpaceRules whitespace = Parboiled.createParser(WhiteSpaceRules.class);

	public static KeywordParser create() {
		return Parboiled.createParser(KeywordParser.class);
	}

	public Rule Class() {
		return Sequence(
				Keyword(Keyword.CLASS.getString()),
				push(Keyword.CLASS));
	}

	public Rule Import() {
		return Sequence(
				Keyword(Keyword.IMPORT.getString()),
				push(Keyword.IMPORT));
	}

	public Rule Package() {
		return Sequence(
				Keyword(Keyword.PACKAGE.getString()),
				push(Keyword.PACKAGE));
	}

	@SuppressNode
	@DontLabel
	public Rule Keyword(String keyword) {
		return Terminal(keyword, this.ranges.LetterOrDigit());
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string) {
		return Sequence(string, this.whitespace.Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), this.whitespace.Spacing()).label('\'' + string + '\'');
	}

}
