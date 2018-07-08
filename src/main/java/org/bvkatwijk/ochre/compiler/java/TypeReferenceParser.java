package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class TypeReferenceParser extends BaseParser<Type> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	public Rule Type() {
		return Sequence(
				Sequence(
						this.ranges.CharUpperAToUpperZ(),
						Optional(OneOrMore(this.ranges.LetterOrDigit()))),
				push(new Type(match())));
	}

}
