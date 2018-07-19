package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.compiler.java.cu.BaseParserTest;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class BasePackageParserTest extends BaseParserTest<Package> {

	@Override
	public Rule getRule() {
		return Parboiled.createParser(PackageParser.class).Package();
	}

}
