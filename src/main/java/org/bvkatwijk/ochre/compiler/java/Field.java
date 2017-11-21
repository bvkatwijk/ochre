package org.bvkatwijk.ochre.compiler.java;

import org.apache.commons.lang3.StringUtils;
import org.bvkatwijk.ochre.format.Indenter;

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

	public String asAssignment() {
		return "this." + name + " = " + name + ";";
	}

	public String asGetter(Indenter indenter) {
		return "public " + type + " get" + StringUtils.capitalize(name) + "() {"
				+ "\n" + indenter.indent("return this." + name + ";")
				+ "\n" + "}";
	}

}
