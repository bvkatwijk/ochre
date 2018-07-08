package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.bvkatwijk.ochre.parser.ClassDeclarationParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class BaseClassDeclarationParserTest extends BaseParserTest<ClassDeclaration> {

	@Override
	public Rule getRule() {
		return Parboiled.createParser(ClassDeclarationParser.class).TypeDeclaration();
	}

}
