package org.bvkatwijk.ochre.lang.pack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Value;

@Value
public class Package {

	List<PackageIdentifier> packageIdentifiers;

	public static Package of(String... packageIdentifiers) {
		return new Package(Arrays.stream(packageIdentifiers)
				.map(string -> new PackageIdentifier(string))
				.collect(Collectors.toList()));
	}

	public static Package of(PackageIdentifier... identifiers) {
		return new Package(List.of(identifiers));
	}

	public String getValue() {
		return this.packageIdentifiers
				.stream()
				.map(PackageIdentifier::getName)
				.collect(Collectors.joining("."));
	}

	public Package prepend(Package parent) {
		return new Package(
				Stream.of(parent.getPackageIdentifiers(), this.packageIdentifiers)
						.flatMap(List::stream)
						.collect(Collectors.toList()));
	}

	public String getPrependValue() {
		return this.getValue() == "" ? "" : this.getValue() + ".";
	}

}
