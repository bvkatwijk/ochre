package org.bvkatwijk.ochre.compiler;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class ExampleOneTest extends AbstractOchreToJavaCompilerTest {

	private static final String source = ""
			+ "\n" + "package org.bvkatwijk.ochre.compiler;"
			+ "\n" + ""
			+ "\n" + "class Example(name: String) {"
			+ "\n" + ""
			+ "\n" + "}";

	private static final String target = ""
			+ "\n" + "package org.bvkatwijk.ochre.compiler;"
			+ "\n" + ""
			+ "\n" + "public class Example {"
			+ "\n" + ""
			+ "\n" + "\t" + "private final String name;"
			+ "\n" + ""
			+ "\n" + "\t" + "public Example(String name) {"
			+ "\n" + "\t" + "\t" + "this.name = name;"
			+ "\n" + "\t" + "}"
			+ "\n" + ""
			+ "\n" + "}";

}
