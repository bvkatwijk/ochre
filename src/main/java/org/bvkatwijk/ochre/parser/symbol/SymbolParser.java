package org.bvkatwijk.ochre.parser.symbol;

import org.bvkatwijk.ochre.parser.WhiteSpaceRules;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

public class SymbolParser extends BaseParser<Symbol> {

	public final WhiteSpaceRules whitespace = Parboiled.createParser(WhiteSpaceRules.class);

	public Rule Dot() {
		return Terminal(Symbol.DOT.getValue());
	}

	public Rule Comma() {
		return Terminal(Symbol.COMMA.getValue());
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

	public Rule OpenBracket() {
		return Terminal(Symbol.OPENBRACKET.getValue());
	}

	public Rule CloseBracket() {
		return Terminal(Symbol.CLOSEBRACKET.getValue());
	}

	public Rule Semicolon() {
		return Terminal(Symbol.SEMI.getValue());
	}
}
