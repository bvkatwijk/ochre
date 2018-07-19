package org.bvkatwijk.ochre.parser.imp;

import org.bvkatwijk.ochre.lang.imp.ImportSection;
import org.junit.Assert;
import org.junit.Test;

public class ImportSectionParserTest extends BaseImportSectionParserTest {

	public static class SingleImport extends BaseImportSectionParserTest {

		@Test
		public void testImport_elaborateCase() {
			Assert.assertEquals(
					ImportSection.of("A"),
					compile("import A;"));
		}
	}

	public static class TwoImports extends BaseImportSectionParserTest {

		@Test
		public void testImport_elaborateCase() {
			Assert.assertEquals(
					ImportSection.of("A", "B"),
					compile("import A; import B;"));
		}
	}
}
