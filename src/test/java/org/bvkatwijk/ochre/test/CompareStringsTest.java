package org.bvkatwijk.ochre.test;

import org.bvkatwijk.ochre.util.string.StringHelper;

public abstract class CompareStringsTest {

	public void assertStringEquals(String expected, String actual) {
		new StringHelper().assertEqual(expected, actual);
	}

}
