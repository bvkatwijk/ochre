package org.bvkatwijk.ochre.parser.identifier;

import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.errors.ParsingException;

public class QualifiedIdentifierParserTest {

	public static class TypeNameOnly extends BaseQualifiedIdentifierParserTest {

		@Test
		public void qualifiedIdentifier_A() {
			Assert.assertEquals(
					new QualifiedIdentifier("A"),
					compile("A"));
		}

		@Test
		public void qualifiedIdentifier_B() {
			Assert.assertEquals(
					new QualifiedIdentifier("B"),
					compile("B"));
		}

		@Test
		public void qualifiedIdentifier_Type() {
			Assert.assertEquals(
					new QualifiedIdentifier("Type"),
					compile("Type"));
		}

		@Test(expected = ParsingException.class)
		public void qualifiedIdentifier_lowerCaseType_throws() {
			compile("a");
		}

		@Test(expected = ParsingException.class)
		public void qualifiedIdentifier_missingType_throws() {
			compile("a.");
		}
	}

	private static class BaseQualifiedIdentifierParserTest extends BaseParserTest<QualifiedIdentifier> {

		@Override
		public Rule getRule() {
			return QualifiedIdentifierParser.create().QualifiedIdentifier();
		}

	}
}
