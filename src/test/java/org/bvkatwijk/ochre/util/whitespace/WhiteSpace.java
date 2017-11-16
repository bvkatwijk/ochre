package org.bvkatwijk.ochre.util.whitespace;

import org.junit.Assert;
import org.junit.Test;

/**
 * Helper class for verifying whitespaces
 */
public class WhiteSpace {

	/**
	 * Convert supplied text to displayed whitespace characters
	 */
	public String show(String value) {
		return value
				.replace("\n", "\\n")
				.replace("\t", "\\t")
				.replace(" ", "+");
	}

	@Test
	public void whiteSpace_shouldShowTab() {
		Assert.assertEquals("\\t", show("\t"));
	}

	@Test
	public void whiteSpace_shouldShowTabMultiple() {
		Assert.assertEquals("\\t\\t", show("\t\t"));
	}

	@Test
	public void whiteSpace_shouldConvertSpaceToPlus() {
		Assert.assertEquals("+", show(" "));
	}

	@Test
	public void whiteSpace_shouldConvertSpaceToPlusMultiple() {
		Assert.assertEquals("++", show("  "));
	}

	@Test
	public void whiteSpace_shouldShowNewline() {
		Assert.assertEquals("\\n", show("\n"));
	}

	@Test
	public void whiteSpace_shouldShowNewlineMultiple() {
		Assert.assertEquals("\\n\\n", show("\n\n"));
	}

}
