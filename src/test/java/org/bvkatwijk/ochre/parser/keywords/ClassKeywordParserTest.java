package org.bvkatwijk.ochre.parser.keywords;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;

public class ClassKeywordParserTest extends BaseParserTest<Keyword> {

	@Test
	public void testClassKeyword() {
		Assert.assertEquals(
				Keyword.CLASS,
				compile("class"));
	}

	@Override
	public Rule getRule() {
		return KeywordParser.create().Class();
	}

}
