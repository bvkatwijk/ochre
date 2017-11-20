package org.bvkatwijk.ochre.compiler;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;
import org.junit.Ignore;

@Ignore("TO DO")
public class ExampleOneTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "package org.bvkatwijk.ochre.compiler;"
				+ "\n" + ""
				+ "\n" + "class Example(name: String) {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
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

}
