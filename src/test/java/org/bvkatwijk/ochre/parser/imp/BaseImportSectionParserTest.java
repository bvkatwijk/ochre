package org.bvkatwijk.ochre.parser.imp;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.bvkatwijk.ochre.lang.imp.ImportSection;
import org.parboiled.Rule;

public class BaseImportSectionParserTest extends BaseParserTest<ImportSection> {

	@Override
	public Rule getRule() {
		return ImportSectionParser.create().ImportSection();
	}

}
