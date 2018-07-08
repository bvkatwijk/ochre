package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.junit.Assert;
import org.junit.Test;

public class PublicClassTest extends BaseClassDeclarationParserTest {

	@Test
	public void compileBase() {
		Assert.assertEquals(
				new ClassDeclaration(),
				compile("class Example {}"));
	}

}
