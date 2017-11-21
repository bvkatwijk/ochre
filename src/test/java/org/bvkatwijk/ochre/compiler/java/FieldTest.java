package org.bvkatwijk.ochre.compiler.java;

import org.junit.Assert;
import org.junit.Test;

public class FieldTest {

	private static final Field field = new Field("name", "Type");

	@Test
	public void field_asParam() {
		Assert.assertEquals("Type name", field.asParam());
	}

	@Test
	public void field_asDeclaration() {
		Assert.assertEquals("private final Type name;", field.asDeclaration());
	}

	@Test
	public void field_asAssignment() {
		Assert.assertEquals("this.name = name;", field.asAssignment());
	}

}
