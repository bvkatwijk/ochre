package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.PackageIdentifier;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class PackageIdentifierParser extends BaseParser<PackageIdentifier> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	public static PackageIdentifierParser create() {
		return Parboiled.createParser(PackageIdentifierParser.class);
	}

	public Rule Package() {
		return Sequence(
				PackageMatcher(),
				push(PackageIdentifier.of(match())));
	}

	public Rule PackageMatcher() {
		return Sequence(
				OneOrMore(this.ranges.CharLowerAToLowerZ()),
				TestNot(FirstOf(
						this.ranges.Underscore(),
						this.ranges.CharUpperAToUpperZ(),
						this.ranges.Digit())));
	}

}
