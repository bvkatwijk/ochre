package org.bvkatwijk.ochre.compiler.java.cu;

import org.bvkatwijk.ochre.lang.cl.ClassDeclaration;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.errors.ParsingException;

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
		public void compile_class_Name() {
			Assert.assertEquals(
					new ClassDeclaration("Name"),
					compile("class Name {}"));
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

	@Test(expected = ParsingException.class)
	public void compile_class_missingBody_throws() {
		compile("class A");
	}

	@Test(expected = ParsingException.class)
	public void compile_class_lowercase_throws() {
		compile("class a {}");
	}

	@Test(expected = ParsingException.class)
	public void compile_class_missingClosingBracket_throws() {
		compile("class A {");
	}

	@Test(expected = ParsingException.class)
	public void compile_class_missingOpeningBracket_throws() {
		compile("class A }");
	}

	@Test(expected = ParsingException.class)
	public void compile_class_missingClassKeyword_throws() {
		compile("A {");
	}

	@Test(expected = ParsingException.class)
	public void compile_class_missingTypeName_throws() {
		compile("class {");
	}
}
