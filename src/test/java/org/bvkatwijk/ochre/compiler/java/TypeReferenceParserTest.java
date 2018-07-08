package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ParsingException;

public class TypeReferenceParserTest extends BaseParserTest<Type> {

	@Test
	public void testSingleLetterAType() {
		Assert.assertEquals(
				new Type("A"),
				compile("A"));
	}

	@Test
	public void testSingleLetterBType() {
		Assert.assertEquals(
				new Type("B"),
				compile("B"));
	}

	@Test
	public void testTwoLetterType_Aa() {
		Assert.assertEquals(
				new Type("Aa"),
				compile("Aa"));
	}

	@Test
	public void testType_Some() {
		Assert.assertEquals(
				new Type("Some"),
				compile("Some"));
	}

	@Test
	public void testTwoLetterType_Ab() {
		Assert.assertEquals(
				new Type("Ab"),
				compile("Ab"));
	}

	@Test
	public void testLetterAndDigitType_A1() {
		Assert.assertEquals(
				new Type("A1"),
				compile("A1"));
	}

	@Test(expected = ParsingException.class)
	public void typeReference_singleLowercase_throws() {
		compile("a");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_startingLowercase_throws() {
		compile("aa");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_startingLowercase2_throws() {
		compile("aA");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_letterAndSymbol_throws() {
		compile("a+");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_letterAndSpace_throws() {
		compile("a ");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_spaceAndLetter_throws() {
		compile(" a");
	}

	@Test(expected = ParsingException.class)
	public void typeReference_singleDigit_throws() {
		compile("1");
	}

	@Override
	public Rule getRule() {
		return Parboiled.createParser(TypeReferenceParser.class).Type();
	}

}
