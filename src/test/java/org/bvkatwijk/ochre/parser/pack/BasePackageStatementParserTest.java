package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.parboiled.Rule;

public class BasePackageStatementParserTest extends BaseParserTest<Package> {

	@Override
	public Rule getRule() {
		return PackageStatementParser.create().PackageStatement();
	}

}
