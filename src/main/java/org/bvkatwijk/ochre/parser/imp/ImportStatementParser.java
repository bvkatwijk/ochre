package org.bvkatwijk.ochre.parser.imp;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.compiler.java.TypeReferenceParser;
import org.bvkatwijk.ochre.lang.imp.Import;
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
		Var<List<Import>> children = new Var<>(new ArrayList<>());
		return Sequence(
				ImportStatementMatcher(children),
				push(children.get()));
	}

	Rule ImportStatementMatcher(Var<List<Import>> children) {
		return Sequence(this.IMPORT,
				ImportQualification(children),
				Optional(OneOrMore(this.COMMA, ImportQualification(children))),
				this.SEMI);
	}

	public Rule ImportQualification(Var<List<Import>> children) {
		StringVar parent = new StringVar();
		return FirstOf(
				Sequence(QualifiedIdentifier(), children.get().add(new Import(match()))),
				ImportSubDeclaration(parent, children));
	}

	public Rule ImportSubDeclaration(StringVar parent, Var<List<Import>> children) {
		return Sequence(
				this.LWING,
				ImportMember(children),
				ZeroOrMore(this.COMMA, ImportMember(children)),
				Optional(Spacing()),
				this.RWING);
	}

	public Rule ImportMember(Var<List<Import>> children) {
		return Sequence(QualifiedIdentifier(), children.get().add(new Import(match())));
	}

	public Rule QualifiedIdentifier() {
		return Sequence(
				Optional(PackageSection()),
				this.type.Type());
	}

	public Rule PackageSection() {
		return OneOrMore(Sequence(
				PackageIdentifier(),
				PackageSeparator()));
	}

	public Rule PackageIdentifier() {
		return this.ranges.CharLowerAToLowerZ();
	}

	public Rule PackageSeparator() {
		return this.DOT;
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
