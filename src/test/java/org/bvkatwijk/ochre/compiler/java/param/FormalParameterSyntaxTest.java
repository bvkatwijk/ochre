package org.bvkatwijk.ochre.compiler.java.param;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.java.AbstractOchreToJavaCompilerTest;
import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Rule;

public class FormalParameterSyntaxTest extends AbstractOchreToJavaCompilerTest {

	@Override
	public Function<OchreRules, Rule> topic() {
		return OchreRules::FormalParameterDecls;
	}

	@Override
	public String ochre() {
		return "name: String";
	}

	@Override
	public String java() {
		return "String name";
	}

}
