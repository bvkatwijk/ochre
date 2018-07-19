package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class PackageParser extends BaseParser<Package> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	public Rule Package() {
		return Sequence(
				PackageMatcher(),
				push(Package.of(match())));
	}

	Rule PackageMatcher() {
		return Sequence(
				OneOrMore(this.ranges.CharLowerAToLowerZ()),
				TestNot(FirstOf(
						this.ranges.Underscore(),
						this.ranges.CharUpperAToUpperZ(),
						this.ranges.Digit())));
	}

}
