package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class PackageParser extends BaseParser<Package> {

	public final PackageIdentifierParser packageIdentifierParser = PackageIdentifierParser.create();

	public static PackageParser create() {
		return Parboiled.createParser(PackageParser.class);
	}

	public Rule Package() {
		return Sequence(
				this.packageIdentifierParser.PackageIdentifier(),
				push(Package.of(this.packageIdentifierParser.pop())));
	}

}
