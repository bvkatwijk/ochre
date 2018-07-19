package org.bvkatwijk.ochre.parser.imp;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.WhiteSpaceRules;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.bvkatwijk.ochre.parser.pack.PackageParser;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.bvkatwijk.ochre.parser.symbol.SymbolParser;
import org.bvkatwijk.ochre.parser.type.TypeReferenceParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

/**
 * Parser for import statements, i.e. import a.b { C, d.E }
 *
 * @author boris
 */
@BuildParseTree
public class ImportStatementParser extends BaseParser<List<Import>> {

	public final KeywordParser keywordParser = KeywordParser.create();
	public final SymbolParser symbolParser = Parboiled.createParser(SymbolParser.class);
	public final WhiteSpaceRules whitespace = Parboiled.createParser(WhiteSpaceRules.class);
	public final TypeReferenceParser type = Parboiled.createParser(TypeReferenceParser.class);
	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);
	public final PackageParser packageParser = PackageParser.create();

	public Rule ImportStatement() {
		Var<List<Import>> children = new Var<>(new ArrayList<>());
		return Sequence(
				ImportStatementMatcher(children),
				push(children.get()));
	}

	Rule ImportStatementMatcher(Var<List<Import>> children) {
		return Sequence(
				this.keywordParser.Import(),
				ImportQualification(children),
				Optional(OneOrMore(this.symbolParser.Comma(), ImportQualification(children))),
				this.symbolParser.Semicolon());
	}

	public Rule ImportQualification(Var<List<Import>> children) {
		StringVar parent = new StringVar();
		return FirstOf(
				Sequence(QualifiedIdentifier(), children.get().add(new Import(match()))),
				ImportSubDeclaration(parent, children));
	}

	public Rule ImportSubDeclaration(StringVar parent, Var<List<Import>> children) {
		return Sequence(
				this.symbolParser.OpenBracket(),
				ImportMember(children),
				ZeroOrMore(this.symbolParser.Comma(), ImportMember(children)),
				Optional(this.whitespace.Spacing()),
				this.symbolParser.CloseBracket());
	}

	public Rule ImportMember(Var<List<Import>> children) {
		return Sequence(QualifiedIdentifier(), children.get().add(new Import(match())));
	}

	public Rule QualifiedIdentifier() {
		return Sequence(
				Optional(this.packageParser.Package(), this.packageParser.PackageSeparator()),
				this.type.Type());
	}

}
