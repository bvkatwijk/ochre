package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.test.CompareStringsTest;
import org.junit.Test;

public abstract class AbstractOchreToJavaCompilerTest extends CompareStringsTest {

	public abstract String ochre();

	public abstract String java();

	@Test
	public void compiler_shouldCompileOchreSource_toJavaTarget() {
		assertStringEquals(java(), new OchreToJavaCompiler().compile(ochre()));
	}

}
