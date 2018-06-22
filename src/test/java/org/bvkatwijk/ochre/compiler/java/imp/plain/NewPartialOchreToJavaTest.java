package org.bvkatwijk.ochre.compiler.java.imp.plain;

import java.util.function.Function;

import org.bvkatwijk.ochre.parser.ImportRules;
import org.bvkatwijk.ochre.test.CompareStringsTest;
import org.junit.Test;
import org.parboiled.Rule;

public abstract class NewPartialOchreToJavaTest extends CompareStringsTest {

	/** Ochre source code input */
	public abstract String ochre();

	/** expected Java source code output */
	public abstract String java();

	/** Rule to use for parsing */
	public abstract Function<ImportRules, Rule> topic();

	@Test
	public void compiler_shouldCompileOchreSource_toJavaTarget() {
		assertStringEquals(
				java(),
				compiledOchre());
	}

	private String compiledOchre() {
		return new PartialOchreToJavaCompiler(topic()).compile(ochre());
	}

}
