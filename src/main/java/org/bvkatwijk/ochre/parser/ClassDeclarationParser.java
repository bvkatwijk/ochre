package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.compiler.java.FormalParameterGroupParser;
import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.parser.identifier.IdentifierRules;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

public class ClassDeclarationParser extends BaseParser<ClassDeclaration> implements Spacing {

	public final KeywordParser keywordParser = KeywordParser.create();
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
				this.keywordParser.Class(),
				this.identifierParser.Identifier());
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

	@Override
	public Rule Any() {
		return ANY;
	}

	@Override
	public Rule EOI() {
		return EOI;
	}
}
