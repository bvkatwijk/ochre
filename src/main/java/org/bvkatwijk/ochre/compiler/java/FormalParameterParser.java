package org.bvkatwijk.ochre.compiler.java;

import java.util.List;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

public class FormalParameterParser extends BaseParser<Parameter> implements Spacing {

	final CharRanges ranges = new CharRanges();

	Rule FormalParameter() {
		StringVar type = new StringVar();
		StringVar name = new StringVar();
		return Sequence(
				Identifier(name),
				this.COLON,
				Type(type),
				push(new Parameter(name.get(), type.get())));
	}

	Rule FormalParameter(Var<List<Parameter>> parameters) {
		StringVar type = new StringVar();
		StringVar name = new StringVar();
		return Sequence(
				Identifier(name),
				this.COLON,
				Type(type),
				parameters.get().add(new Parameter(name.get(), type.get())));
	}

	Rule Identifier(StringVar name) {
		return Sequence(
				Sequence(
						this.ranges.Letter(),
						ZeroOrMore(this.ranges.LetterOrDigit()),
						Spacing()),
				name.set(match().trim()));
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
