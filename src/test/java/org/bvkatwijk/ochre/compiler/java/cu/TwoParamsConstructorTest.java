package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;
import org.junit.Ignore;

@Ignore
public class TwoParamsConstructorTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public String ochre() {
		return ""
				+ "\n" + "class ClassName(one: TypeOne, two: TypeTwo) {"
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
				+ "\n" + "}";
	}

}
