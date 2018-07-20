package org.bvkatwijk.ochre.lang.imp;

import org.bvkatwijk.ochre.parser.identifier.QualifiedIdentifier;

import lombok.Value;

@Value
public class Import {

	QualifiedIdentifier qualifiedType;

	public String asJavaStatement() {
		return "import " + this.qualifiedType.asString() + ";";
	}

	public static Import of(String typeName, String... packages) {
		return new Import(QualifiedIdentifier.of(typeName, packages));
	}
}
