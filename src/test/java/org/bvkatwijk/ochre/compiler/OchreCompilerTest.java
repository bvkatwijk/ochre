package org.bvkatwijk.ochre.compiler;

import org.junit.Test;

public class OchreCompilerTest {

	@Test
	public void testCompiler() {
		new OchreCompiler(this.getClass().getResourceAsStream("Example.ochre"));
	}

}
