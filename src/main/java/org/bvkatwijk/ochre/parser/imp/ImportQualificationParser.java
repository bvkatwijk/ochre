package org.bvkatwijk.ochre.parser.imp;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.WhiteSpaceRules;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.bvkatwijk.ochre.parser.pack.PackageParser;
import org.bvkatwijk.ochre.parser.symbol.SymbolParser;
import org.bvkatwijk.ochre.parser.type.TypeReferenceParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.Cached;
import org.parboiled.support.Var;

public class ImportQualificationParser extends BaseParser<ImportQualification> {

	public final KeywordParser keywordParser = KeywordParser.create();
	public final SymbolParser symbolParser = Parboiled.createParser(SymbolParser.class);
	public final WhiteSpaceRules whitespace = Parboiled.createParser(WhiteSpaceRules.class);
	public final TypeReferenceParser type = Parboiled.createParser(TypeReferenceParser.class);
	public final PackageParser packageParser = PackageParser.create();

	public static ImportQualificationParser create() {
		return Parboiled.createParser(ImportQualificationParser.class);
	}

	@Cached
	public Rule ImportQualification() {
		Var<List<Import>> children = new Var<>(new ArrayList<>());
		return Sequence(
				FirstOf(
						QualifiedIdentifier(children),
						BracketedQualifiedIdentifier(children)),
				push(new ImportQualification(children.get())));
	}

	public Rule QualifiedIdentifier(Var<List<Import>> children) {
		return Sequence(
				QualifiedIdentifierMatcher(),
				children.get().add(new Import(match())));
	}

	@Cached
	public Rule BracketedQualifiedIdentifier(Var<List<Import>> children) {
		return Sequence(
				this.symbolParser.OpenBracket(),
				QualifiedIdentifier(children),
				Optional(this.whitespace.Spacing()),
				this.symbolParser.CloseBracket());
	}

	public Rule QualifiedIdentifierMatcher() {
		return Sequence(
				Optional(this.packageParser.Package(), this.packageParser.PackageSeparator()),
				this.type.Type());
	}

}
