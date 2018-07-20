package org.bvkatwijk.ochre.parser.type;

import org.bvkatwijk.ochre.lang.type.Type;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class TypeReferenceParser extends BaseParser<Type> {

	public final CharRanges ranges = Parboiled.createParser(CharRanges.class);

	public static TypeReferenceParser create() {
		return Parboiled.createParser(TypeReferenceParser.class);
	}

	public Rule Type() {
		return Sequence(
				TypeMatcher(),
				push(new Type(match())));
	}

	Rule TypeMatcher() {
		return Sequence(
				this.ranges.CharUpperAToUpperZ(),
				Optional(OneOrMore(this.ranges.LetterOrDigit())));
	}

}
