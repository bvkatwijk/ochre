package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;
import org.junit.Ignore;

@Ignore
public class ClassConstructorTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "class Example(name: String) {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
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
