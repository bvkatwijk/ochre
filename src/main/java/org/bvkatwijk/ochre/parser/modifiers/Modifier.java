package org.bvkatwijk.ochre.parser.modifiers;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Modifier {

	PUBLIC("public"),
	PROTECTED("protected"),
	PRIVATE("private"),
	STATIC("static"),
	ABSTRACT("abstract"),
	FINAL("final"),
	NATIVE("native"),
	SYNCHRONIZED("synchronized"),
	TRANSIENT("transient"),
	VOLATILE("volatile"),
	STRICTFP("strictfp"),
	;

	private final String string;

	public static Object[] getStrings() {
		return Arrays.stream(Modifier.values())
				.map(Modifier::getString)
				.toArray();
	}

}
