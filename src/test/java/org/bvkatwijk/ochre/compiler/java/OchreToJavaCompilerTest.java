package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.test.CompareStringsTest;
import org.junit.Test;

public class OchreToJavaCompilerTest extends CompareStringsTest {

	private static final String source = ""
			+ "\n" + "class Example {"
			+ "\n" + ""
			+ "\n" + "}";

	private static final String target = ""
			+ "\n" + "public class Example {"
			+ "\n" + ""
			+ "\n" + "}";

	@Test
	public void result_shouldBeCorrect() {
		assertStringEquals(target, new OchreToJavaCompiler().compile(source));
	}

}
