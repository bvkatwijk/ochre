package org.bvkatwijk.ochre.compiler.java.cu.value;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;

public class TwoParamsValueClassTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "value class ClassName(one: TypeOne, two: TypeTwo) {"
				+ "\n" + ""
				+ "\n" + "}";
	}

	@Override
	public String java() {
		return ""
				+ "\n" + "public class ClassName {"
				+ "\n" + ""
				+ "\n" + "\t" + "private final TypeOne one;"
				+ "\n" + "\t" + "private final TypeTwo two;"
				+ "\n" + ""
				+ "\n" + "\t" + "public ClassName(TypeOne one, TypeTwo two) {"
				+ "\n" + "\t" + "\t" + "this.one = one;"
				+ "\n" + "\t" + "\t" + "this.two = two;"
				+ "\n" + "\t" + "}"
				+ "\n" + ""
				+ "\n" + "\t" + "public TypeOne getOne() {"
				+ "\n" + "\t" + "\t" + "return this.one;"
				+ "\n" + "\t" + "}"
				+ "\n" + ""
				+ "\n" + "\t" + "public TypeTwo getTwo() {"
				+ "\n" + "\t" + "\t" + "return this.two;"
				+ "\n" + "\t" + "}"
				+ "\n" + ""
				+ "\n" + "}";
	}

}
