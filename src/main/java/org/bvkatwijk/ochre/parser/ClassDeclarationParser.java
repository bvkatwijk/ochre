package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.compiler.java.FormalParameterGroupParser;
import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

public class ClassDeclarationParser extends BaseParser<ClassDeclaration> implements Spacing {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);
	public final IdentifierRules identifierParser = Parboiled.createParser(IdentifierRules.class);
	public final FormalParameterGroupParser formalParameterGroupParser = Parboiled
			.createParser(FormalParameterGroupParser.class);

	public Rule TypeDeclaration() {
		return Sequence(
				ClassAndIdentifier(),
				Optional(this.formalParameterGroupParser.FormalParameters()),
				ClassBody(),
				push(new ClassDeclaration()));
	}

	public Rule ClassAndIdentifier() {
		return Sequence(
				Class(),
				this.identifierParser.Identifier());
	}

	public Rule Class() {
		return Keyword("class");
	}

	@SuppressNode
	@DontLabel
	public Rule Keyword(String keyword) {
		return Terminal(keyword, this.ranges.LetterOrDigit());
	}

	public Rule ClassBody() {
		return Sequence(
				this.LWING,
				this.RWING);
	}

	public final Rule LWING = Terminal("{");
	public final Rule RWING = Terminal("}");

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string) {
		return Sequence(string, Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), Spacing()).label('\'' + string + '\'');
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
