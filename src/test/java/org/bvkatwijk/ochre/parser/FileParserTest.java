package org.bvkatwijk.ochre.parser;

import org.junit.Assert;
import org.junit.Test;

public class FileParserTest extends BaseFileParserTest {

	@Test
	public void file_shouldBeValid() {
		Assert.assertEquals(
				new CompilationUnit(),
				compile(""));
	}
}
