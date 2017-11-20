package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;

public class OchreNewRules extends BaseParser<Object> {

	private final CharRanges ranges = new CharRanges(this);

	public Rule CompilationUnit() {
		return Sequence(
				Spacing(),
				TypeDeclaration(),
				EOI);
	}

	Rule TypeDeclaration() {
		return Sequence(
				CLASS,
				Identifier(),
				ClassBody()
				);
	}

	Rule ClassBody() {
		return Sequence(
				LWING,
				RWING
				);
	}

	@SuppressSubnodes
	@MemoMismatches
	Rule Identifier() {
		return Sequence(Letter(), ZeroOrMore(LetterOrDigit()), Spacing());
	}

	Rule Letter() {
		return FirstOf(CharLowerAToLowerZ(), CharUpperAToUpperZ(), '_', '$');
	}

	public final Rule CLASS = Keyword("class");
	public final Rule SEMI = Terminal(";");
	public final Rule LWING = Terminal("{");
	public final Rule RWING = Terminal("}");

	@SuppressNode
	@DontLabel
	Rule Keyword(String keyword) {
		return Terminal(keyword, LetterOrDigit());
	}

	Rule CharUpperAToUpperZ() {
		return CharRange('A', 'Z');
	}

	Rule CharLowerAToLowerZ() {
		return CharRange('a', 'z');
	}

	@MemoMismatches
	Rule LetterOrDigit() {
		return FirstOf(CharLowerAToLowerZ(), CharUpperAToUpperZ(), Digit(), '_', '$');
	}

	Rule Digit() {
		return ranges.ZeroToNine();
	}

	@SuppressNode
	@DontLabel
	Rule Terminal(String string) {
		return Sequence(string, Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	Rule Spacing() {
		return ZeroOrMore(FirstOf(

				// whitespace
				OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

				// traditional comment
				Sequence("/*", ZeroOrMore(TestNot("*/"), ANY), "*/"),

				// end of line comment
				Sequence(
						"//",
						ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
						FirstOf("\r\n", '\r', '\n', EOI)
						)
				));
	}

}