package org.bvkatwijk.ochre.lang.imp;

import org.junit.Assert;
import org.junit.Test;

public class ImportTest {

	public static class AsJavaStatement {

		@Test
		public void asJavaStatement_a() {
			Assert.assertEquals(
					"import A;",
					Import.of("A").asJavaStatement());
		}

		@Test
		public void asJavaStatement_b() {
			Assert.assertEquals(
					"import B;",
					Import.of("B").asJavaStatement());
		}

		@Test
		public void asJavaStatement_a_dot_B() {
			Assert.assertEquals(
					"import a.B;",
					Import.of("B", "a").asJavaStatement());
		}

	}

}
