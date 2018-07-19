package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class BaseImportStatementParserTest extends BaseParserTest<List<Import>> {

	@Override
	public Rule getRule() {
		return Parboiled.createParser(ImportStatementParser.class).ImportStatement();
	}

}
