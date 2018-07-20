package org.bvkatwijk.ochre.parser.identifier;

import org.bvkatwijk.ochre.parser.pack.PackageParser;
import org.bvkatwijk.ochre.parser.type.TypeReferenceParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class QualifiedIdentifierParser extends BaseParser<QualifiedIdentifier> {

	public final PackageParser packageParser = PackageParser.create();
	public final TypeReferenceParser type = Parboiled.createParser(TypeReferenceParser.class);

	public Rule QualifiedIdentifier() {
		return Sequence(
				QualifiedIdentifierMatcher(),
				push(new QualifiedIdentifier(match())));
	}

	public Rule QualifiedIdentifierMatcher() {
		return Sequence(
				Optional(this.packageParser.Package(),
						this.packageParser.PackageSeparator()),
				this.type.Type());
	}

	public static QualifiedIdentifierParser create() {
		return Parboiled.createParser(QualifiedIdentifierParser.class);
	}
}
