package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.bvkatwijk.ochre.parser.cl.ClassDeclarationParser;
import org.parboiled.Rule;

public class BaseClassDeclarationParserTest extends BaseParserTest<ClassDeclaration> {

	@Override
	public Rule getRule() {
		return ClassDeclarationParser.create().TypeDeclaration();
	}

}
