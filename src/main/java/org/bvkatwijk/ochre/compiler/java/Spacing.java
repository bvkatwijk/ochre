package org.bvkatwijk.ochre.compiler.java;

import org.parboiled.Rule;
import org.parboiled.annotations.SuppressNode;

public interface Spacing {

	@SuppressNode
	public default Rule spacing() {
		return ZeroOrMore(FirstOf(
				OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),
				Sequence("/*", ZeroOrMore(TestNot("*/"), Any()), "*/"),
				Sequence(
						"//",
						ZeroOrMore(TestNot(AnyOf("\r\n")), Any()),
						FirstOf("\r\n", '\r', '\n', EOI()))));
	}

	public Rule Sequence(Object object1, Object object2, Object... objects);

	public Rule FirstOf(Object object1, Object object2, Object... objects);

	public Rule ZeroOrMore(Object object);

	public Rule ZeroOrMore(Object object1, Object object2, Object... objects);

	public Rule OneOrMore(Object object);

	public Rule TestNot(Object object);

	public Rule AnyOf(String string);

	public Rule Any();

	public Rule EOI();
}
