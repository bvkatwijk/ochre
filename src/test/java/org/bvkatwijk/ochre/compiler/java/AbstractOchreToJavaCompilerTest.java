package org.bvkatwijk.ochre.compiler.java;

import java.util.function.Function;

import org.bvkatwijk.ochre.parser.OchreRules;
import org.bvkatwijk.ochre.test.CompareStringsTest;
import org.junit.Test;
import org.parboiled.Rule;

public abstract class AbstractOchreToJavaCompilerTest extends CompareStringsTest {

	public abstract String ochre();

	public abstract String java();

	public Function<OchreRules, Rule> topic() {
		return OchreRules::CompilationUnit;
	}

	@Test
	public void compiler_shouldCompileOchreSource_toJavaTarget() {
		assertStringEquals(java(), new OchreToJavaCompiler().compile(ochre(), topic()));
	}

}
