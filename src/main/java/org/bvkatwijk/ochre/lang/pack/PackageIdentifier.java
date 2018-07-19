package org.bvkatwijk.ochre.lang.pack;

import lombok.Value;

@Value
public class PackageIdentifier {

	String name;

	public static PackageIdentifier of(String name) {
		return new PackageIdentifier(name);
	}
}
