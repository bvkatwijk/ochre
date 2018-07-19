package org.bvkatwijk.ochre.lang.pack;

import org.junit.Assert;
import org.junit.Test;

public class PackageTest {

	public static class Of {

		public static class SingleIdentifier {

			private static final Package SINGLE = Package.of("a");

			@Test
			public void package_shouldHaveOneIdenfitier() {
				Assert.assertEquals(
						1,
						SINGLE.getPackageIdentifiers().size());
			}

			@Test
			public void package_shouldHaveIdenfitier_withName_a() {
				Assert.assertEquals(
						"a",
						SINGLE.getPackageIdentifiers().get(0).getName());
			}

		}

		public static class MultipleIdentifiers {

			private static final Package MULTIPLE = Package.of("a", "b", "c");

			@Test
			public void package_shouldHaveOneIdenfitier() {
				Assert.assertEquals(
						3,
						MULTIPLE.getPackageIdentifiers().size());
			}

			@Test
			public void package_shouldHaveIdenfitier_withName_a() {
				Assert.assertEquals(
						"a",
						MULTIPLE.getPackageIdentifiers().get(0).getName());
			}

			@Test
			public void package_shouldHaveIdenfitier_withName_b() {
				Assert.assertEquals(
						"b",
						MULTIPLE.getPackageIdentifiers().get(1).getName());
			}

			@Test
			public void package_shouldHaveIdenfitier_withName_c() {
				Assert.assertEquals(
						"c",
						MULTIPLE.getPackageIdentifiers().get(2).getName());
			}

		}

	}
}
