package org.bvkatwijk.ochre.compiler.java;

import java.util.Arrays;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImportSection {

	private final @NonNull String imports;

	public String asJava() {
		return parse("\n" + imports.trim());
	}

	private String parse(String imports) {
		return Arrays.stream(imports.split("\nimport "))
				.skip(1)
				.reduce("", (a, b) -> a + "\nimport " + b)
				.trim();
	}

}
