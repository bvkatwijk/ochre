package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.PackageIdentifier;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.errors.ParsingException;

public class PackageIdentifierParserTest extends BasePackageIdentifierParserTest {

	@Test
	public void testPackage_a() {
		Assert.assertEquals(
				PackageIdentifier.of("a"),
				compile("a"));
	}

	@Test
	public void testPackage_b() {
		Assert.assertEquals(
				PackageIdentifier.of("b"),
				compile("b"));
	}

	@Test
	public void testPackage_ab() {
		Assert.assertEquals(
				PackageIdentifier.of("ab"),
				compile("ab"));
	}

	@Test(expected = ParsingException.class)
	public void testPackage_A_throws() {
		compile("A");
	}

	@Test(expected = ParsingException.class)
	public void testPackage_Ab_throws() {
		compile("Ab");
	}

	@Test(expected = ParsingException.class)
	public void testPackage_aB_throws() {
		compile("aB");
	}

	@Test(expected = ParsingException.class)
	public void testPackage_a1_throws() {
		compile("a1");
	}

	@Test(expected = ParsingException.class)
	public void testPackage_UPPERCASE_throws() {
		compile("UPPERCASE");
	}

	@Test(expected = ParsingException.class)
	public void testPackage_a_underscore_b_throws() {
		compile("a_b");
	}
}
