package org.bvkatwijk.ochre.parser.cl;

import org.bvkatwijk.ochre.compiler.java.FormalParameterGroupParser;
import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.lang.cl.ClassDeclaration;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.bvkatwijk.ochre.parser.type.TypeReferenceParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.StringVar;

public class ClassDeclarationParser extends BaseParser<ClassDeclaration> implements Spacing {

	public final TypeReferenceParser typeReferenceParser = TypeReferenceParser.create();
	public final KeywordParser keywordParser = KeywordParser.create();
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
				Optional(FormalParameters()),
				Optional(Spacing()),
				ClassBody());
	}

	public Rule FormalParameters() {
		return this.formalParameterGroupParser.FormalParameters();
	}

	public Rule ClassAndIdentifier(StringVar name) {
		return Sequence(
				this.keywordParser.Class(),
				Sequence(
						this.typeReferenceParser.Type(),
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
