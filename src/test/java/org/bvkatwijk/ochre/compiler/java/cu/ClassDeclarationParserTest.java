package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.parser.ClassDeclaration;
import org.junit.Assert;
import org.junit.Test;

public class ClassDeclarationParserTest extends BaseClassDeclarationParserTest {

	public static class Public extends BaseClassDeclarationParserTest {

		@Test
		public void compile_class_A() {
			Assert.assertEquals(
					new ClassDeclaration("A"),
					compile("class A {}"));
		}

		@Test
		public void compile_class_B() {
			Assert.assertEquals(
					new ClassDeclaration("B"),
					compile("class B {}"));
		}

		@Test
		public void compile_class_A_withParentheses() {
			Assert.assertEquals(
					new ClassDeclaration("A"),
					compile("class A() {}"));
		}

		@Test
		public void compile_class_B_withParentheses() {
			Assert.assertEquals(
					new ClassDeclaration("B"),
					compile("class B() {}"));
		}
	}

}
