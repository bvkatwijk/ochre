package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class BaseFileParserTest extends BaseParserTest<CompilationUnit> {

	@Override
	public Rule getRule() {
		return Parboiled.createParser(FileParser.class).File();
	}

}
