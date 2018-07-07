package org.bvkatwijk.ochre.compiler.java.imp.stack;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;
import org.junit.Ignore;

@Ignore
public class StackedDoubleImportTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "import a.b { C, D };"
				+ "\n" + ""
				+ "\n" + "class Example() {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "import a.b.C;"
				+ "\n" + "import a.b.D;"
				+ "\n" + ""
				+ "\n" + "public class Example {"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
