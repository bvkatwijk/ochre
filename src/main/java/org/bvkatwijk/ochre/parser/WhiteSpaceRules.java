package org.bvkatwijk.ochre.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.SuppressNode;

public class WhiteSpaceRules extends BaseParser<String> {

	@SuppressNode
	public Rule Spacing() {
		return ZeroOrMore(FirstOf(
				WhiteSpace(),
				Comment(),
				EndOfLineComment()));
	}

	public Rule EndOfLineComment() {
		return Sequence("//", ZeroOrMore(TestNot(AnyOf("\r\n")), ANY), FirstOf("\r\n", '\r', '\n', EOI));
	}

	public Rule Comment() {
		return Sequence("/*", ZeroOrMore(TestNot("*/"), ANY), "*/");
	}

	public Rule WhiteSpace() {
		return OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace"));
	}
}
