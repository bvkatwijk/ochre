package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;

public class TypeReferenceParser extends BaseParser<Type> {

	public final CharRanges ranges = new CharRanges();

	public Rule Type() {
		return Sequence(
				Sequence(
						this.ranges.CharUpperAToUpperZ(),
						Optional(this.ranges.LetterOrDigit())),
				push(new Type(match())));
	}

}
