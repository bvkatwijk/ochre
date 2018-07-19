package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;

public class PackageStatementParserTest extends BaseParserTest<Package> {

	@Test
	public void packageStatement_a() {
		Assert.assertEquals(Package.of("a"),
				compile("package a;"));
	}

	@Test
	public void packageStatement_b() {
		Assert.assertEquals(Package.of("b"),
				compile("package b;"));
	}

	@Test
	public void packageStatement_name() {
		Assert.assertEquals(Package.of("name"),
				compile("package name;"));
	}

	@Override
	public Rule getRule() {
		return PackageStatementParser.create().PackageStatement();
	}

}
