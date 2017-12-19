package org.bvkatwijk.ochre.compiler.java.imp.stack;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class StackedDoubleImportTest2 extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "import a.b { C, d.E };"
				+ "\n" + ""
				+ "\n" + "class Example() {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "import a.b.C;"
				+ "\n" + "import a.b.d.E;"
				+ "\n" + ""
				+ "\n" + "public class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
