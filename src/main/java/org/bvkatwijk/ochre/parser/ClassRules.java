package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.parser.keywords.Keyword;
import org.parboiled.BaseParser;
import org.parboiled.Rule;

public class ClassRules extends BaseParser<String> {

	private final OchreRules ochre = new OchreRules();

	public Rule TypeDeclaration() {
		return Sequence(
				Optional(Keyword.VALUE.rule(this.ochre)),
				this.ochre.ClassAndIdentifier(),
				Optional(this.ochre.FormalParameters()),
				ClassBody());
	}

	public Rule ClassBody() {
		return Sequence(
				this.ochre.LWING,
				this.ochre.RWING,
				this.ochre.addClassBodyElements());
	}

}
