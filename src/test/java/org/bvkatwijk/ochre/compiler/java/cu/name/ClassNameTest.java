package org.bvkatwijk.ochre.compiler.java.cu.name;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class ClassNameTest extends AbstractOchreToJavaCompilerTest{

	@Override
	public String ochre() {
		return ""
				+ "\n" + "class ClassName {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "public class ClassName {"
				+ "\n" + ""
				+ "\n" + "}";
	}


}
