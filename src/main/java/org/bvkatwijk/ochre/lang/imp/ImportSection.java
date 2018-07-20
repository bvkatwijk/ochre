package org.bvkatwijk.ochre.lang.imp;

import java.util.List;

import lombok.Value;

@Value
public class ImportSection {

	List<Import> imports;

	public static ImportSection of(Import... imports) {
		return new ImportSection(List.of(imports));
	}

}
