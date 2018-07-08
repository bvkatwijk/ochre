package org.bvkatwijk.ochre.parser;

import java.util.List;

import org.bvkatwijk.ochre.lang.Import;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author bvkatwijk
 */
public class ImportStatementParserTest extends BaseImportStatementParserTest {

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
