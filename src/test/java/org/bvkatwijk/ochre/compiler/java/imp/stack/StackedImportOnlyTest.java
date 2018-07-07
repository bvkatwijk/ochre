package org.bvkatwijk.ochre.compiler.java.imp.stack;

import java.util.function.Function;

import org.bvkatwijk.ochre.compiler.java.imp.plain.NewPartialOchreToJavaTest;
import org.bvkatwijk.ochre.parser.ImportRules;
import org.junit.Ignore;
import org.parboiled.Rule;

@Ignore
public class StackedImportOnlyTest extends NewPartialOchreToJavaTest {

	@Override
	public String ochre() {
		return "import a.b { C };";
	}

	@Override
	public String java() {
		return "import a.b.C;";
	}

	@Override
	public Function<ImportRules, Rule> topic() {
		return ImportRules::ImportsDeclaration;
	}

}
