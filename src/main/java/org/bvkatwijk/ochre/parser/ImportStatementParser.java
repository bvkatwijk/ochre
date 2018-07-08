package org.bvkatwijk.ochre.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.compiler.java.TypeReferenceParser;
import org.bvkatwijk.ochre.lang.Import;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

/**
 * Parser for import statements, i.e. import a.b { C, d.E }
 *
 * @author boris
 */
@BuildParseTree
public class ImportStatementParser extends BaseParser<List<Import>> implements Spacing {

	public final TypeReferenceParser type = Parboiled.createParser(TypeReferenceParser.class);
	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	public Rule ImportStatement() {
		StringVar parent = new StringVar();
		Var<List<String>> children = new Var<>(new ArrayList<>());
		StringVar current = new StringVar();
		return Sequence(
				this.IMPORT,
				Optional(QualifiedIdentifier(), current.set(match())),
				Optional(ImportSubDeclaration(parent, children)),
				this.SEMI,
				push(Arrays.asList(new Import(current.get()))));
	}

	public Rule ImportSubDeclaration(StringVar parent, Var<List<String>> children) {
		return Sequence(
				this.LWING,
				ImportMember(parent, children),
				ZeroOrMore(this.COMMA, ImportMember(parent, children)),
				this.RWING);
	}

	public Rule ImportMember(StringVar parent, Var<List<String>> children) {
		return QualifiedIdentifier();
	}

	public Rule QualifiedIdentifier() {
		return Sequence(
				Optional(PackageSection()),
				this.type.Type());
	}

	public Rule PackageSection() {
		return OneOrMore(Sequence(
				this.ranges.CharLowerAToLowerZ(),
				this.DOT));
	}

	public final Rule IMPORT = Keyword("import");
	public final Rule COMMA = Terminal(",");
	public final Rule DOT = Terminal(".");
	public final Rule LWING = Terminal("{");
	public final Rule RWING = Terminal("}");
	public final Rule SEMI = Terminal(";");

	@SuppressNode
	@DontLabel
	public Rule Keyword(String keyword) {
		return Terminal(keyword, this.ranges.LetterOrDigit());
	}

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
