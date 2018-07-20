package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.errors.ParsingException;

public class ImportQualificationParserTest {

	public static class IsolatedType extends BaseImportQualificationParserTest {

		@Test
		public void importQualification_A() {
			Assert.assertEquals(
					new ImportQualification(List.of(new Import("A"))),
					compile("A"));
		}

		@Test
		public void importQualification_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(new Import("B"))),
					compile("B"));
		}

		@Test
		public void importQualification_Type() {
			Assert.assertEquals(
					new ImportQualification(List.of(new Import("Type"))),
					compile("Type"));
		}

		@Test(expected = ParsingException.class)
		public void importQualification_a_throws() {
			compile("a");
		}

		@Test(expected = ParsingException.class)
		public void importQualification_a_dot_throws() {
			compile("a.");
		}

		@Test(expected = ParsingException.class)
		public void importQualification_dot_A_throws() {
			compile(".A");
		}
	}

	public static class BracketedSingleType extends BaseImportQualificationParserTest {

		@Test
		public void importQualification_bracketed_A() {
			Assert.assertEquals(
					new ImportQualification(List.of(new Import("A"))),
					compile("{ A }"));
		}
	}

	public static class BaseImportQualificationParserTest extends BaseParserTest<ImportQualification> {

		@Override
		public Rule getRule() {
			return ImportQualificationParser.create().ImportQualification();
		}
	}

}
