package org.bvkatwijk.ochre.lang.pack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;

@Value
public class PackageSegment {

	List<PackageIdentifier> packageIdentifiers;

	public static Package of(String... packageIdentifiers) {
		return new Package(Arrays.stream(packageIdentifiers)
				.map(string -> new PackageIdentifier(string))
				.collect(Collectors.toList()));
	}

}
