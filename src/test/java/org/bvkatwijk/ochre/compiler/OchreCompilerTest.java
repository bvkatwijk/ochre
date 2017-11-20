package org.bvkatwijk.ochre.compiler;

import org.junit.Assert;
import org.junit.Test;

import net.bytebuddy.jar.asm.ClassReader;

public class OchreCompilerTest {

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

	private static final ClassReader result = new ClassReader(new OchreToByteCompiler().compile(source));

	@Test
	public void className_shouldBe_Example() {
		Assert.assertEquals(
				"org/bvkatwijk/ochre/compiler/Example",
				result.getClassName());
	}

}
