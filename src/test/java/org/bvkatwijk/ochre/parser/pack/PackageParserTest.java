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

	@Test
	public void package_b() {
		Assert.assertEquals(Package.of("b"),
				compile("b"));
	}

	@Test
	public void package_a_dot_b() {
		Assert.assertEquals(Package.of("a", "b"),
				compile("a.b"));
	}

	@Test
	public void package_a_dot_b_dot_c() {
		Assert.assertEquals(Package.of("a", "b", "c"),
				compile("a.b.c"));
	}

	@Override
	public Rule getRule() {
		return PackageParser.create().Package();
	}

}
