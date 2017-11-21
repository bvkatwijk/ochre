package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class PublicClassTest extends AbstractOchreToJavaCompilerTest {

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
