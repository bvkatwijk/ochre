package org.bvkatwijk.ochre.compiler.java.imp.stack;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.java.imp.plain.NewPartialOchreToJavaTest;
import org.bvkatwijk.ochre.parser.ImportRules;
import org.parboiled.Rule;

public class StackedTripleImportTest extends NewPartialOchreToJavaTest {

	@Override
	public String ochre() {
		return "import a.b { C, E, F };";
	}

	@Override
	public String java() {
		return ""
				+ "import a.b.C;" + "\n"
				+ "import a.b.E;" + "\n"
				+ "import a.b.F;";
	}

	@Override
	public Function<ImportRules, Rule> topic() {
		return ImportRules::ImportsDeclaration;
	}

}
