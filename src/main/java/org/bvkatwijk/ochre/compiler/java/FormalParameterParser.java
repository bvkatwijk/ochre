package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.StringVar;

public class FormalParameterParser extends BaseParser<Parameter> implements Spacing {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	Rule FormalParameter() {
		StringVar type = new StringVar();
		StringVar name = new StringVar();
		return Sequence(
				FormalParameterMatcher(type, name),
				push(new Parameter(name.get(), type.get())));
	}

	Rule FormalParameterMatcher(StringVar type, StringVar name) {
		return Sequence(Identifier(name),
				this.COLON,
				Type(type));
	}

	Rule Identifier(StringVar name) {
		return Sequence(
				IdentifierMatcher(),
				name.set(match().trim()));
	}

	Rule IdentifierMatcher() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()),
				Spacing());
	}

	Rule Type(StringVar type) {
		return Identifier(type);
	}

	final Rule COLON = Terminal(":");

	@DontLabel
	@SuppressNode
	public Rule Terminal(String string) {
		return Sequence(string, Spacing()).label('\'' + string + '\'');
	}

	@Override
	public Rule Any() {
		return ANY;
	}

	@Override
	public Rule EOI() {
		return EOI;
	}

}
