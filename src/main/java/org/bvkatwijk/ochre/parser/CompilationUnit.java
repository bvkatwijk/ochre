package org.bvkatwijk.ochre.parser;

import java.util.List;

import org.bvkatwijk.ochre.lang.Import;

import lombok.Value;

@Value
public class CompilationUnit {

	List<Import> imports;

}
