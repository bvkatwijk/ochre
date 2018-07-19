package org.bvkatwijk.ochre.parser.cu;

import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.bvkatwijk.ochre.parser.cu.CompilationUnit;
import org.bvkatwijk.ochre.parser.cu.CompilationUnitParser;
import org.parboiled.Rule;

public class BaseCompilationUnitParserTest extends BaseParserTest<CompilationUnit> {

	@Override
	public Rule getRule() {
		return CompilationUnitParser.create().File();
	}

}
