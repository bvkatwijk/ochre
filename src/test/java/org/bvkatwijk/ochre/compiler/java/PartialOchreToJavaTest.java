package org.bvkatwijk.ochre.compiler.java;

import java.util.function.Function;

import org.bvkatwijk.ochre.parser.OchreRules;
import org.bvkatwijk.ochre.test.CompareStringsTest;
import org.junit.Test;
import org.parboiled.Rule;

public abstract class PartialOchreToJavaTest extends CompareStringsTest {

	/** Ochre source code input */
	public abstract String ochre();

	/** expected Java source code output */
	public abstract String java();

	/** Rule to use for parsing */
	public abstract Function<OchreRules, Rule> topic();

	/** method of achieving end result */
	public abstract Function<OchreToJavaCompiler, String> resultSupplier();

	@Test
	public void compiler_shouldCompileOchreSource_toJavaTarget() {
		assertStringEquals(
				java(),
				resultSupplier().apply(new OchreToJavaCompiler()));
	}

}
