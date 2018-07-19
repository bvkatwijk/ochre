package org.bvkatwijk.ochre.parser.cu;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.junit.Assert;
import org.junit.Test;

public class CompilationUnitParserTest extends BaseCompilationUnitParserTest {

	@Test
	public void testFile_empty() {
		Assert.assertEquals(
				new CompilationUnit(List.of()),
				compile(""));
	}

	@Test
	public void testFile_withImportSection_singleImport() {
		Assert.assertEquals(
				new CompilationUnit(List.of(new Import("A"))),
				compile("import A;"));
	}

	@Test
	public void testFile_withImportSection_twoImports() {
		Assert.assertEquals(
				new CompilationUnit(List.of(new Import("A"), new Import("B"))),
				compile("import A, B;"));
	}
}
