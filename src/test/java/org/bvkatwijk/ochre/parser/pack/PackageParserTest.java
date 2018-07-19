package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;

public class PackageParserTest extends BaseParserTest<Package> {

	@Test
	public void package_a() {
		Assert.assertEquals(Package.of("a"),
				compile("a"));
	}

	@Override
	public Rule getRule() {
		return PackageParser.create().Package();
	}

}
