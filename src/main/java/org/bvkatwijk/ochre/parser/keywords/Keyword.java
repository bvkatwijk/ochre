package org.bvkatwijk.ochre.parser.keywords;

import java.util.Arrays;

import org.bvkatwijk.ochre.parser.OchreRules;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Keyword {

	ASSERT("assert"),
	BREAK("break"),
	CASE("case"),
	CATCH("catch"),
	CLASS("class"),
	CONST("const"),
	CONTINUE("continue"),
	DEFAULT("default"),
	DO("do"),
	ELSE("else"),
	ENUM("enum"),
	EXTENDS("extends"),
	FINALLY("finally"),
	FINAL("final"),
	FOR("for"),
	GOTO("goto"),
	IF("if"),
	IMPLEMENTS("implements"),
	IMPORT("import"),
	INTERFACE("interface"),
	INSTANCEOF("instanceof"),
	NEW("new"),
	PACKAGE("package"),
	RETURN("return"),
	STATIC("static"),
	SUPER("super"),
	SWITCH("switch"),
	SYNCHRONIZED("synchronized"),
	THIS("this"),
	THROWS("throws"),
	THROW("throw"),
	TRY("try"),
	VOID("void"),
	WHILE("while"),
	;

	private final String string;

	public static Object[] getStrings() {
		return Arrays.stream(Keyword.values()).map(Keyword::getString).toArray();
	}

	@SuppressNode
	@DontLabel
	public Rule rule(OchreRules rules) {
		return rules.ForKeyword(this.string);
	}

}
