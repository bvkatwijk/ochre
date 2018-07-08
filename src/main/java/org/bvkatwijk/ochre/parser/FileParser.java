package org.bvkatwijk.ochre.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

public class FileParser extends BaseParser<CompilationUnit> {

	public Rule File() {
		return Sequence(
				EMPTY,
				push(new CompilationUnit()));
	}

}
