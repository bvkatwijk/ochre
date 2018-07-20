package org.bvkatwijk.ochre.parser.identifier;

import java.util.ArrayList;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.pack.PackageParser;
import org.bvkatwijk.ochre.parser.type.TypeReferenceParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class QualifiedIdentifierParser extends BaseParser<QualifiedIdentifier> {

	public final PackageParser packageParser = PackageParser.create();
	public final TypeReferenceParser typeReferenceParser = TypeReferenceParser.create();

	public Rule QualifiedIdentifier() {
		return FirstOf(
				QualifiedIdentifierMatcher(),
				Type());
	}

	// Are stack values actually shared between parsers?
	public Rule QualifiedIdentifierMatcher() {
		return Sequence(
				this.packageParser.Package(),
				this.packageParser.PackageSeparator(),
				this.typeReferenceParser.Type(),
				push(new QualifiedIdentifier(this.packageParser.pop(1), this.typeReferenceParser.pop())));
	}

	public Rule Type() {
		return Sequence(
				this.typeReferenceParser.Type(),
				push(new QualifiedIdentifier(
						new Package(new ArrayList<>()),
						this.typeReferenceParser.pop())));
	}

	public static QualifiedIdentifierParser create() {
		return Parboiled.createParser(QualifiedIdentifierParser.class);
	}
}
