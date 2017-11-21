package org.bvkatwijk.ochre.compiler.java;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Field {

	private final String name;
	private final String type;

	public String asParam() {
		return type + " " + name;
	}

	public String asDeclaration() {
		return "private final " + type + " " + name + ";";
	}

}
