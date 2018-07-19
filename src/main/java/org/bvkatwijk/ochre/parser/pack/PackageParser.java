package org.bvkatwijk.ochre.parser.pack;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.lang.pack.PackageIdentifier;
import org.bvkatwijk.ochre.parser.symbol.SymbolParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.support.Var;

public class PackageParser extends BaseParser<Package> {

	public final SymbolParser symbolParser = Parboiled.createParser(SymbolParser.class);
	public final PackageIdentifierParser packageIdentifierParser = PackageIdentifierParser.create();

	public static PackageParser create() {
		return Parboiled.createParser(PackageParser.class);
	}

	public Rule Package() {
		Var<List<PackageIdentifier>> packageIdentifers = new Var<>(new ArrayList<>());
		return Sequence(
				PackageMatcher(packageIdentifers),
				push(new Package(packageIdentifers.get())));
	}

	public Rule PackageMatcher(Var<List<PackageIdentifier>> packageIdentifers) {
		return Sequence(
				PackageIdentifier(packageIdentifers),
				ZeroOrMore(
						PackageSeparator(),
						PackageIdentifier(packageIdentifers)));
	}

	public Rule PackageIdentifier(Var<List<PackageIdentifier>> packageIdentifers) {
		return Sequence(
				this.packageIdentifierParser.PackageIdentifier(),
				packageIdentifers.get().add(this.packageIdentifierParser.pop()));
	}

	public Rule PackageSeparator() {
		return this.symbolParser.Dot();
	}
}
