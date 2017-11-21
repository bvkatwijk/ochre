package org.bvkatwijk.ochre.format;

import org.junit.Assert;
import org.junit.Test;

public class IndenterTest {

	private static final Indenter indenter = new Indenter("\t");
	private static final Indenter spaceIndenter = new Indenter("  ");

	@Test
	public void indenter_tabs_onSingleLine_shouldIndent() {
		Assert.assertEquals(
				"\ttext",
				indenter.indent("text"));
	}

	@Test
	public void indenter_tabs_onMultipleLines_shouldIndent() {
		Assert.assertEquals(
				"\tfirstLine\n\tSecondLine",
				indenter.indent("firstLine\nSecondLine"));
	}

	@Test
	public void indenter_tabs_onMultipleLines_shouldTrimEmptyLines() {
		Assert.assertEquals(
				"\tfirstLine\n\n\tSecondLine",
				indenter.indent("firstLine\n\nSecondLine"));
	}

	@Test
	public void indenter_spaces_onSingleLine_shouldIndent() {
		Assert.assertEquals(
				"  text",
				spaceIndenter.indent("text"));
	}

	@Test
	public void indenter_spaces_onMultipleLines_shouldIndent() {
		Assert.assertEquals(
				"  firstLine\n  SecondLine",
				spaceIndenter.indent("firstLine\nSecondLine"));
	}

	@Test
	public void indenter_spaces_onMultipleLines_shouldTrimEmptyLines() {
		Assert.assertEquals(
				"  firstLine\n\n  SecondLine",
				spaceIndenter.indent("firstLine\n\nSecondLine"));
	}

	@Test
	public void indenter_tabs_shouldIndentJavaMethodCorrectly() {
		Assert.assertEquals(
				"\t@Override"
						+ "\n\t" + "public BuildSingleFieldSample firstField(String firstField) {"
						+ "\n\t" + "\tthis.firstField = firstField;"
						+ "\n\t" + "\treturn this;"
						+ "\n\t" + "}",
				indenter.indent("@Override"
						+ "\n" + "public BuildSingleFieldSample firstField(String firstField) {"
						+ "\n" + "\tthis.firstField = firstField;"
						+ "\n" + "\treturn this;"
						+ "\n" + "}"));
	}

}
