package org.bvkatwijk.ochre.lang.imp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;

@Value
public class ImportSection {

	List<Import> imports;

	public static ImportSection of(String... importIdentifiers) {
		return new ImportSection(Arrays.stream(importIdentifiers)
				.map(it -> new Import(it))
				.collect(Collectors.toList()));
	}

}
