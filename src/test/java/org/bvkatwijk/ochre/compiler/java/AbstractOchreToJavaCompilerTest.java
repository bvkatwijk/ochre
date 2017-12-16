package org.bvkatwijk.ochre.compiler.java;

import java.util.function.Function;

import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Rule;

public abstract class AbstractOchreToJavaCompilerTest extends PartialOchreToJavaTest {

	@Override
	public abstract String ochre();

	@Override
	public abstract String java();

	@Override
	public Function<OchreRules, Rule> topic() {
		return OchreRules::CompilationUnit;
	}

	@Override
	public Function<OchreToJavaCompiler, String> resultSupplier() {
		return compiler -> compiler.compile(ochre(), topic());
	}

}
