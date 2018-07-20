package org.bvkatwijk.ochre.parser.range;

import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ParsingException;

public class CharRangesLetterTest extends BaseParserTest<String> {

	@Test
	public void test_CharUpperAToUpperZ_A() {
		compile("A");
	}

	@Test
	public void test_CharUpperAToUpperZ_B() {
		compile("B");
	}

	@Test
	public void test_CharUpperAToUpperZ_Y() {
		compile("Y");
	}

	@Test
	public void test_CharUpperAToUpperZ_Z() {
		compile("Z");
	}

	@Test(expected = ParsingException.class)
	public void test_CharUpperAToUpperZ_1_throws() {
		compile("1");
	}

	@Test(expected = ParsingException.class)
	public void test_CharUpperAToUpperZ_z_throws() {
		compile("a");
	}

	@Test(expected = ParsingException.class)
	public void test_CharUpperAToUpperZ_a_throws() {
		compile("z");
	}

	@Test(expected = ParsingException.class)
	public void test_CharUpperAToUpperZ_underscore_throws() {
		compile("_");
	}

	@Override
	public Rule getRule() {
		return Parboiled.createParser(CharRanges.class).CharUpperAToUpperZ();
	}
}
