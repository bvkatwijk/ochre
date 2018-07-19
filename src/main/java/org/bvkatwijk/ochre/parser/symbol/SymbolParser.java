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
		return Sequence(
				Terminal("."),
				push(Symbol.DOT));
	}

	public Rule Comma() {
		return Sequence(
				Terminal(","),
				push(Symbol.COMMA));
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
		return Sequence(
				Terminal(Symbol.OPENBRACKET.getValue()),
				push(Symbol.OPENBRACKET));
	}

	public Rule CloseBracket() {
		return Sequence(
				Terminal(Symbol.CLOSEBRACKET.getValue()),
				push(Symbol.CLOSEBRACKET));
	}

	public Rule Semicolon() {
		return Sequence(
				Terminal(Symbol.SEMI.getValue()),
				push(Symbol.SEMI));
	}
}
