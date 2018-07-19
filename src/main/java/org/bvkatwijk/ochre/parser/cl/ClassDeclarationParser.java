package org.bvkatwijk.ochre.parser.cl;

import org.bvkatwijk.ochre.compiler.java.FormalParameterGroupParser;
import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.bvkatwijk.ochre.parser.identifier.IdentifierRules;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.StringVar;

public class ClassDeclarationParser extends BaseParser<ClassDeclaration> implements Spacing {

	public final KeywordParser keywordParser = KeywordParser.create();
	public final IdentifierRules identifierParser = Parboiled.createParser(IdentifierRules.class);
	public final FormalParameterGroupParser formalParameterGroupParser = Parboiled
			.createParser(FormalParameterGroupParser.class);

	public static ClassDeclarationParser create() {
		return Parboiled.createParser(ClassDeclarationParser.class);
	}

	public Rule TypeDeclaration() {
		StringVar name = new StringVar();
		return Sequence(
				ClassDeclarationMatcher(name),
				push(new ClassDeclaration(name.get())));
	}

	public Rule ClassDeclarationMatcher(StringVar name) {
		return Sequence(
				ClassAndIdentifier(name),
				Optional(this.formalParameterGroupParser.FormalParameters()),
				ClassBody());
	}

	public Rule ClassAndIdentifier(StringVar name) {
		return Sequence(
				this.keywordParser.Class(),
				Sequence(
						this.identifierParser.Identifier(),
						name.set(match())));
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
