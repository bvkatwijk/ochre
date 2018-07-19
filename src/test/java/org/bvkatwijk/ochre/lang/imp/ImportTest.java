package org.bvkatwijk.ochre.lang.imp;

import org.junit.Assert;
import org.junit.Test;

public class ImportTest {

	public static class AsJavaStatement {

		@Test
		public void asJavaStatement_a() {
			Assert.assertEquals(
					"import A;",
					new Import("A").asJavaStatement());
		}

		@Test
		public void asJavaStatement_b() {
			Assert.assertEquals(
					"import B;",
					new Import("B").asJavaStatement());
		}

	}

}
