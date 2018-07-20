package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.lang.pack.Package;
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
					new ImportQualification(List.of(Import.of("A"))),
					compile("A"));
		}

		@Test
		public void importQualification_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("B"))),
					compile("B"));
		}

		@Test
		public void importQualification_Type() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("Type"))),
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
					new ImportQualification(List.of(Import.of("A"))),
					compile("{ A }"));
		}

		@Test
		public void importQualification_bracketed_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("B"))),
					compile("{ B }"));
		}

		@Test
		public void importQualification_bracketed_a_dot_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("B", "a"))),
					compile("{ a.B }"));
		}

		@Test(expected = ParsingException.class)
		public void importQualification_missingCloseBracket_throws() {
			compile("{ a.B");
		}

		@Test(expected = ParsingException.class)
		public void importQualification_bracketed_missingType_throws() {
			compile("{ a }");
		}
	}

	public static class MultipleTypes extends BaseImportQualificationParserTest {

		@Test
		public void importQualification_multiple_A_comma_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("A"), Import.of("B"))),
					compile("A, B"));
		}

		@Test(expected = ParsingException.class)
		public void importQualification_multiple_missingFirstType_throws() {
			compile(", B");
		}
	}

	public static class BracketedMultipleTypes extends BaseImportQualificationParserTest {

		@Test
		public void importQualification_multiple_A_comma_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("A"), Import.of("B"))),
					compile("{ A, B }"));
		}
	}

	public static class QualifiedBracketedTypes extends BaseImportQualificationParserTest {

		@Test
		public void importQualification_qualifiedBracketedType_a_bracket_B() {
			Assert.assertEquals(
					new ImportQualification(List.of(Import.of("B", "a"))),
					compile("a { B }"));
		}

		@Test(expected = ParsingException.class)
		public void importQualification_multiple_missingFirstType_throws() {
			compile(", B");
		}
	}

	public static class AddPackage {

		@Test
		public void addPackage_singleImport() {
			Assert.assertEquals(
					List.of(Import.of("B", "a")),
					new ImportQualificationParser().addPackage(List.of(Import.of("B")), Package.of("a")));
		}

		@Test
		public void addPackage_twoImports() {
			Assert.assertEquals(
					List.of(Import.of("B", "a"), Import.of("C", "a")),
					new ImportQualificationParser().addPackage(List.of(Import.of("B"), Import.of("C")),
							Package.of("a")));
		}
	}

	private static class BaseImportQualificationParserTest extends BaseParserTest<ImportQualification> {

		@Override
		public Rule getRule() {
			return ImportQualificationParser.create().RootImportQualification();
		}
	}

}
