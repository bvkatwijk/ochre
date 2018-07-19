package org.bvkatwijk.ochre.parser.imp;

import org.bvkatwijk.ochre.lang.imp.ImportSection;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.parboiled.Rule;

public class BaseImportSectionParserTest extends BaseParserTest<ImportSection> {

	@Override
	public Rule getRule() {
		return ImportSectionParser.create().ImportSection();
	}

}
