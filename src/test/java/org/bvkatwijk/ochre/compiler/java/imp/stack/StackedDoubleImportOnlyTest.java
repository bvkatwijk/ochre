package org.bvkatwijk.ochre.compiler.java.imp.stack;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.java.imp.plain.NewPartialOchreToJavaTest;
import org.bvkatwijk.ochre.parser.ImportRules;
import org.parboiled.Rule;

public class StackedDoubleImportOnlyTest extends NewPartialOchreToJavaTest {

	@Override
	public String ochre() {
		return "import a.b { C, d.E };";
	}

	@Override
	public String java() {
		return ""
				+ "import a.b.C;" + "\n"
				+ "import a.b.d.E;";
	}

	@Override
	public Function<ImportRules, Rule> topic() {
		return ImportRules::ImportsDeclaration;
	}
}
