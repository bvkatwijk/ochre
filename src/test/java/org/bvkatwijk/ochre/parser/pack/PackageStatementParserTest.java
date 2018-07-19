package org.bvkatwijk.ochre.parser.pack;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.junit.Assert;
import org.junit.Test;

public class PackageStatementParserTest extends BasePackageStatementParserTest {

	public static class Single extends BasePackageStatementParserTest {

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

	}

	public static class Multiple extends BasePackageStatementParserTest {

		@Test
		public void packageStatement_a() {
			Assert.assertEquals(Package.of("a", "b"),
					compile("package a.b;"));
		}

	}
}
