package org.bvkatwijk.ochre.parser.symbol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Symbol {

	COMMA(","),
	DOT("."),
	OPENBRACKET("{"),
	CLOSEBRACKET("}"),
	SEMI(";"),
	;

	private final String value;
}
