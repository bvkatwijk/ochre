package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.junit.Assert;
import org.junit.Test;

public class PackageParserTest extends BasePackageParserTest {

	@Test
	public void testPackage_a() {
		Assert.assertEquals(
				new Package("a"),
				compile("a"));
	}

	@Test
	public void testPackage_b() {
		Assert.assertEquals(
				new Package("b"),
				compile("b"));
	}
}
