package org.bvkatwijk.ochre.lang.imp;

import lombok.Value;

@Value
public class Import {

	String qualifiedType;

	public String asJavaStatement() {
		return "import " + this.qualifiedType + ";";
	}

}
