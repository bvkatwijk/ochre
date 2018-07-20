package org.bvkatwijk.ochre.parser.identifier;

import java.util.List;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.lang.type.Type;
import org.bvkatwijk.ochre.parser.BaseParserTest;
import org.junit.Assert;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.errors.ParsingException;

public class QualifiedIdentifierParserTest {

	public static class TypeNameOnly extends BaseQualifiedIdentifierParserTest {

		@Test
		public void qualifiedIdentifier_A() {
			Assert.assertEquals(
					new QualifiedIdentifier(new Package(List.of()), new Type("A")),
					compile("A"));
		}

		@Test
		public void qualifiedIdentifier_B() {
			Assert.assertEquals(
					new QualifiedIdentifier(new Package(List.of()), new Type("B")),
					compile("B"));
		}

		@Test
		public void qualifiedIdentifier_Type() {
			Assert.assertEquals(
					new QualifiedIdentifier(new Package(List.of()), new Type("Type")),
					compile("Type"));
		}

		@Test(expected = ParsingException.class)
		public void qualifiedIdentifier_lowerCaseType_throws() {
			compile("a");
		}

		@Test(expected = ParsingException.class)
		public void qualifiedIdentifier_missingType_throws() {
			compile("a.");
		}

		@Test(expected = ParsingException.class)
		public void qualifiedIdentifier_missingPackage_throws() {
			compile(".A");
		}
	}

	public static class SinglePackageAndType extends BaseQualifiedIdentifierParserTest {

		@Test
		public void qualifiedIdentifier_a_dot_B() {
			Assert.assertEquals(
					new QualifiedIdentifier(Package.of("a"), new Type("B")),
					compile("a.B"));
		}
	}

	public static class MultiplePackagesAndType extends BaseQualifiedIdentifierParserTest {

		@Test
		public void qualifiedIdentifier_a_dot_dot_C() {
			Assert.assertEquals(
					new QualifiedIdentifier(Package.of("a", "b"), new Type("C")),
					compile("a.b.C"));
		}

		@Test
		public void qualifiedIdentifier_multiplePackages_andType() {
			Assert.assertEquals(
					new QualifiedIdentifier(Package.of("a", "b", "c", "d", "e"), new Type("F")),
					compile("a.b.c.d.e.F"));
		}
	}

	private static class BaseQualifiedIdentifierParserTest extends BaseParserTest<QualifiedIdentifier> {

		@Override
		public Rule getRule() {
			return QualifiedIdentifierParser.create().QualifiedIdentifier();
		}

	}
}
