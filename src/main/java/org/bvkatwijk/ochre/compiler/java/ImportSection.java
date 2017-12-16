package org.bvkatwijk.ochre.compiler.java;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImportSection {

	private final @NonNull String imports;

	public String asJava() {
		return imports;
	}

}
