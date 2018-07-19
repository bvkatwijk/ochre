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
			public void package_shouldHaveIdenfitier_withName_A() {
				Assert.assertEquals(
						"a",
						SINGLE.getPackageIdentifiers().get(0).getName());
			}

		}

	}
}
