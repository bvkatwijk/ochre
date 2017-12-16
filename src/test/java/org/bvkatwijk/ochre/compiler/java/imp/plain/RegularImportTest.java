package org.bvkatwijk.ochre.compiler.java.imp.plain;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class RegularImportTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "import a.b.C;"
				+ "\n" + ""
				+ "\n" + "class Example() {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "import a.b.C;"
				+ "\n" + ""
				+ "\n" + "public class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
