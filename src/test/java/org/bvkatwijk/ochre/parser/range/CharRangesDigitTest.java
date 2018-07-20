package org.bvkatwijk.ochre.parser.range;

import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class CharRangesDigitTest extends BaseParserTest<String> {

	@Test
	public void testDigit_0() {
		compile("0");
	}

	@Test
	public void testDigit_1() {
		compile("1");
	}

	@Test
	public void testDigit_8() {
		compile("8");
	}

	@Test
	public void testDigit_9() {
		compile("9");
	}

	@Override
	public Rule getRule() {
		return Parboiled.createParser(CharRanges.class).Digit();
	}

}
