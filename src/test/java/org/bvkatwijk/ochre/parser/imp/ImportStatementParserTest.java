package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.errors.ParsingException;

public class ImportStatementParserTest extends BaseImportStatementParserTest {

	public static class TypeOnly extends BaseImportStatementParserTest {

		@Test
		public void testimport_A() {
			Assert.assertEquals(
					List.of(new Import("A")),
					compile("import A;"));
		}

		@Test
		public void testimport_B() {
			Assert.assertEquals(
					List.of(new Import("B")),
					compile("import B;"));
		}

		@Test
		public void testimport_Type() {
			Assert.assertEquals(
					List.of(new Import("Type")),
					compile("import Type;"));
		}
	}

	public static class SingleQualifiedType extends BaseImportStatementParserTest {

		@Test
		public void testimport_aDotB() {
			Assert.assertEquals(
					List.of(new Import("a.B")),
					compile("import a.B;"));
		}

		@Test
		public void testimport_aDotBDotC() {
			Assert.assertEquals(
					List.of(new Import("a.b.C")),
					compile("import a.b.C;"));
		}
	}

	public static class CommaSeperatedImports extends BaseImportStatementParserTest {

		@Test
		public void testTwoImports_A_B() {
			Assert.assertEquals(
					List.of(new Import("A"), new Import("B")),
					compile("import A, B;"));
		}

		@Test
		public void testThreeImports_A_B_C() {
			Assert.assertEquals(
					List.of(new Import("A"), new Import("B"), new Import("C")),
					compile("import A, B, C;"));
		}
	}

	public static class BraceImports extends BaseImportStatementParserTest {

		@Test
		public void testBraceImport_A() {
			Assert.assertEquals(
					List.of(new Import("A")),
					compile("import { A };"));
		}

		@Test
		public void testBraceImport_aDotB() {
			Assert.assertEquals(
					List.of(new Import("a.B")),
					compile("import { a.B };"));
		}

		@Test
		public void testBraceTwoImports_A_B() {
			Assert.assertEquals(
					List.of(new Import("A"), new Import("B")),
					compile("import { A, B };"));
		}

		@Test
		public void testImport_elaborateCase() {
			Assert.assertEquals(
					List.of(new Import("a.B"), new Import("c.D"), new Import("E")),
					compile("import { a.B, c.D }, E;"));
		}
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
