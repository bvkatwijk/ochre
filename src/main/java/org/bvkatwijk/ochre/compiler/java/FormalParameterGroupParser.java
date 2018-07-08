package org.bvkatwijk.ochre.compiler.java;

import java.util.ArrayList;
import java.util.List;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.Cached;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.Var;

public class FormalParameterGroupParser extends BaseParser<List<Parameter>> implements Spacing {

	public final FormalParameterParser formalParameterParser = Parboiled.createParser(FormalParameterParser.class);

	public Rule FormalParameters() {
		Var<List<Parameter>> parameters = new Var<>(new ArrayList<>());
		return Sequence(
				this.LPAR,
				Optional(FormalParameterDecls(parameters)),
				this.RPAR,
				push(parameters.get()));
	}

	@Cached
	public Rule FormalParameterDecls(Var<List<Parameter>> parameters) {
		return Sequence(
				this.formalParameterParser.FormalParameter(),
				parameters.get().add(this.formalParameterParser.pop()),
				Optional(this.COMMA, FormalParameterDecls(parameters)));
	}

	final Rule LPAR = Terminal("(");
	final Rule RPAR = Terminal(")");
	final Rule COMMA = Terminal(",");

	@SuppressNode
	@DontLabel
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
