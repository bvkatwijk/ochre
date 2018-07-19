package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class PackageStatementParser extends BaseParser<Package> {

	public final KeywordParser keywordParser = KeywordParser.create();
	public final PackageIdentifierParser packageIdentifierParser = PackageIdentifierParser.create();

	public static PackageStatementParser create() {
		return Parboiled.createParser(PackageStatementParser.class);
	}

	public Rule PackageStatement() {
		return Sequence(
				this.keywordParser.Package(),
				this.packageIdentifierParser.PackageIdentifier(),
				push(Package.of(this.packageIdentifierParser.pop())));
	}

}
