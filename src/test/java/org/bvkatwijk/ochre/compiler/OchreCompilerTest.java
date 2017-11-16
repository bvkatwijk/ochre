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

	private static final OchreCompiler COMPILER = new OchreCompiler();

	@Test
	public void className_shouldBe_Example() {
		Assert.assertEquals(
				"org/bvkatwijk/ochre/compiler/Example",
				new ClassReader(COMPILER.compile(source)).getClassName());
	}

}
