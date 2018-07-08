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
	public void testimport_singleLetterA() {
		Assert.assertEquals(
				List.of(new Import("A")),
				compile("import A"));
	}

	@Test
	public void testimport_singleLetterB() {
		Assert.assertEquals(
				List.of(new Import("B")),
				compile("import B"));
	}

}
