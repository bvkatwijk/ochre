package org.bvkatwijk.ochre.parser.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.WhiteSpaceRules;
import org.bvkatwijk.ochre.parser.identifier.QualifiedIdentifierParser;
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
	public final QualifiedIdentifierParser qualifiedIdentifierParser = QualifiedIdentifierParser.create();

	public static ImportQualificationParser create() {
		return Parboiled.createParser(ImportQualificationParser.class);
	}

	public Rule RootImportQualification() {
		Var<List<Import>> imports = new Var<>(new ArrayList<>());
		return Sequence(
				ImportQualification(imports),
				push(new ImportQualification(imports.get())));
	}

	@Cached
	public Rule ImportQualification(Var<List<Import>> imports) {
		return Sequence(
				FirstOf(
						QualifiedBrackets(imports),
						BracketedImportQualification(imports),
						QualifiedIdentifier(imports)),
				Optional(this.symbolParser.Comma(), ImportQualification(imports)));
	}

	@Cached
	public Rule QualifiedBrackets(Var<List<Import>> imports) {
		return Sequence(
				this.packageParser.Package(),
				Optional(this.whitespace.Spacing()),
				BracketedImportQualification(imports),
				imports.set(addPackage(imports.get(), this.packageParser.pop())));
	}

	public List<Import> addPackage(List<Import> children, Package pack) {
		return children.stream()
				.map(child -> new Import(child.getQualifiedType().prepend(pack)))
				.collect(Collectors.toList());
	}

	public Rule QualifiedIdentifier(Var<List<Import>> children) {
		return Sequence(
				this.qualifiedIdentifierParser.QualifiedIdentifier(),
				children.get().add(new Import(this.qualifiedIdentifierParser.pop())));
	}

	@Cached
	public Rule BracketedImportQualification(Var<List<Import>> children) {
		return Sequence(
				this.symbolParser.OpenBracket(),
				ImportQualification(children),
				Optional(this.whitespace.Spacing()),
				this.symbolParser.CloseBracket());
	}

}
