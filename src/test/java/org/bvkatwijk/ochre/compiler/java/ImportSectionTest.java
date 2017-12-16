package org.bvkatwijk.ochre.compiler.java;

import org.junit.Assert;
import org.junit.Test;

public class ImportSectionTest {

	@Test
	public void section_withEmptyString_isValid() {
		new ImportSection("");
	}

	@Test(expected = Exception.class)
	public void section_withNullString_Throws() {
		new ImportSection(null);
	}

	@Test
	public void asJava_withEmptyString_returnsEmpty() {
		Assert.assertEquals("", new ImportSection("").asJava());
	}

	@Test
	public void asJava_withSingleImport_returnsCorrectly() {
		Assert.assertEquals("import a.b.C", new ImportSection("import a.b.C").asJava());
	}

}
