package org.bvkatwijk.ochre.compiler.java;

public class OchreToJavaCompilerTest extends AbstractOchreToJavaCompilerTest {

	@Override
	String ochre() {
		return ""
				+ "\n" + "class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	String java() {
		return ""
				+ "\n" + "public class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
