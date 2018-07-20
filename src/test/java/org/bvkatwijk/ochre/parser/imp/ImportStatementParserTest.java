package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.errors.ParsingException;

public class ImportStatementParserTest {

	public static class TypeOnly extends BaseImportStatementParserTest {

		@Test
		public void testimport_A() {
			Assert.assertEquals(
					List.of(Import.of("A")),
					compile("import A;"));
		}

		@Test
		public void testimport_B() {
			Assert.assertEquals(
					List.of(Import.of("B")),
					compile("import B;"));
		}

		@Test
		public void testimport_Type() {
			Assert.assertEquals(
					List.of(Import.of("Type")),
					compile("import Type;"));
		}
	}

	public static class SingleQualifiedType extends BaseImportStatementParserTest {

		@Test
		public void testimport_aDotB() {
			Assert.assertEquals(
					List.of(Import.of("B", "a")),
					compile("import a.B;"));
		}

		@Test
		public void testimport_aDotBDotC() {
			Assert.assertEquals(
					List.of(Import.of("C", "a", "b")),
					compile("import a.b.C;"));
		}
	}

	public static class CommaSeperatedImports extends BaseImportStatementParserTest {

		@Test
		public void testTwoImports_A_B() {
			Assert.assertEquals(
					List.of(Import.of("A"), Import.of("B")),
					compile("import A, B;"));
		}

		@Test
		public void testThreeImports_A_B_C() {
			Assert.assertEquals(
					List.of(Import.of("A"), Import.of("B"), Import.of("C")),
					compile("import A, B, C;"));
		}

		@Test(expected = ParsingException.class)
		public void testTwoImports_missingComma_throws() {
			compile("import A B;");
		}

		@Test(expected = ParsingException.class)
		public void testTwoImports_missingFirstType_throws() {
			compile("import , B;");
		}

		@Test(expected = ParsingException.class)
		public void testTwoImports_missingSecondType_throws() {
			compile("import A, ;");
		}
	}

	public static class BraceImports extends BaseImportStatementParserTest {

		@Test
		public void testBraceImport_A() {
			Assert.assertEquals(
					List.of(Import.of("A")),
					compile("import { A };"));
		}

		@Test
		public void testBraceImport_aDotB() {
			Assert.assertEquals(
					List.of(Import.of("B", "a")),
					compile("import { a.B };"));
		}

		@Test
		public void testBraceTwoImports_A_B() {
			Assert.assertEquals(
					List.of(Import.of("A"), Import.of("B")),
					compile("import { A, B };"));
		}

		@Test
		public void testImport_elaborateCase() {
			Assert.assertEquals(
					List.of(Import.of("B", "a"), Import.of("D", "c"), Import.of("E")),
					compile("import { a.B, c.D }, E;"));
		}

		@Test
		public void testImport_veryElaborateCase() {
			Assert.assertEquals(
					List.of(
							Import.of("B", "a"),
							Import.of("E", "c", "d"),
							Import.of("F", "c"),
							Import.of("H", "c", "g"),
							Import.of("I")),
					compile("import { a.B, c { d.E, F, g.H } }, I;"));
		}

		@Test
		public void testImport_veryElaborateCase2() {
			Assert.assertEquals(
					List.of(
							Import.of("B", "a"),
							Import.of("E", "c", "d"),
							Import.of("F"),
							Import.of("H", "g"),
							Import.of("I"),
							Import.of("J")),
					compile("import { a.B, { c.d.E, F, g.H }, I }, J;"));
		}
	}

	public static class Errors extends BaseImportStatementParserTest {

		@Test(expected = ParsingException.class)
		public void testImport_packageOnly_throws() {
			compile("import a;");
		}

		@Test(expected = ParsingException.class)
		public void testImport_packageSegmentOnly_throws() {
			compile("import a.b;");
		}

		@Test(expected = ParsingException.class)
		public void testImport_missingQualification_throws() {
			compile("import ;");
		}

		@Test(expected = ParsingException.class)
		public void testImport_missingSemi_throws() {
			compile("import A");
		}

		@Test(expected = ParsingException.class)
		public void testImport_missingImportKeyword_throws() {
			compile("A;");
		}
	}
}
