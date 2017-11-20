package org.bvkatwijk.ochre.compiler.java;

import org.bvkatwijk.ochre.compiler.OchreCompiler;

public class OchreToJavaCompiler implements OchreCompiler {

	@Override
	public String compile(String source) {
		return ""
				+ "\n" + "public " + source.trim();
	}

}
