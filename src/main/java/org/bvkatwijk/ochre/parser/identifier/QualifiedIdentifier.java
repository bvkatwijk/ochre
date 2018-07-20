package org.bvkatwijk.ochre.parser.identifier;

import org.bvkatwijk.ochre.lang.pack.Package;
import org.bvkatwijk.ochre.lang.type.Type;

import lombok.Value;

@Value
public class QualifiedIdentifier {

	Package pack;
	Type type;

	public QualifiedIdentifier prepend(Package parent) {
		return new QualifiedIdentifier(this.pack.prepend(parent), this.type);
	}

	public static QualifiedIdentifier of(String typeName, String... packages) {
		return new QualifiedIdentifier(
				Package.of(packages),
				new Type(typeName));
	}

	public String asString() {
		return this.pack.getPrependValue() + this.type.getName();
	}

}
