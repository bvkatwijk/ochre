package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;

import lombok.Value;

@Value
public class ImportQualification {

	List<Import> children;

	public static ImportQualification of(Import... imports) {
		return new ImportQualification(List.of(imports));
	}

}
