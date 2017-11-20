package org.bvkatwijk.ochre.compiler.java;

public class OchreToJavaCompilerTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "public class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
