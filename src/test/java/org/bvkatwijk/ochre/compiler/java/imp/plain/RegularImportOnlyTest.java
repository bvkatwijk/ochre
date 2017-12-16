package org.bvkatwijk.ochre.compiler.java.imp.plain;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.java.OchreToJavaCompiler;
import org.bvkatwijk.ochre.compiler.java.PartialOchreToJavaTest;
import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Rule;

public class RegularImportOnlyTest extends PartialOchreToJavaTest {

	@Override
	public String ochre() {
		return "import a.b.C;";
	}

	@Override
	public String java() {
		return "import a.b.C;";
	}

	@Override
	public Function<OchreRules, Rule> topic() {
		return OchreRules::ImportsDeclaration;
	}

	@Override
	public Function<OchreToJavaCompiler, String> resultSupplier() {
		return compiler -> compiler.compileAndGetRules(ochre(), topic()).getImportResult().get();
	}

}
