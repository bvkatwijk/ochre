package org.bvkatwijk.ochre.parser.range;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class CharRangesDigitTest extends BaseParserTest<String> {

	@Test
	public void testDigit_0() {
		Assert.assertEquals("0", compile("0"));
	}

	@Test
	public void testDigit_1() {
		Assert.assertEquals("1", compile("1"));
	}

	@Test
	public void testDigit_8() {
		Assert.assertEquals("8", compile("8"));
	}

	@Test
	public void testDigit_9() {
		Assert.assertEquals("9", compile("9"));
	}

	@Override
	public Rule getRule() {
		return Parboiled.createParser(CharRanges.class).Digit();
	}

}
