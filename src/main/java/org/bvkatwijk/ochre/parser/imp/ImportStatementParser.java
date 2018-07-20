package org.bvkatwijk.ochre.parser.imp;

import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.keywords.KeywordParser;
import org.bvkatwijk.ochre.parser.symbol.SymbolParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;

public class ImportStatementParser extends BaseParser<List<Import>> {

	public final ImportQualificationParser importQualificationParser = ImportQualificationParser.create();
	public final KeywordParser keywordParser = KeywordParser.create();
	public final SymbolParser symbolParser = Parboiled.createParser(SymbolParser.class);

	public Rule ImportStatement() {
		return Sequence(
				ImportStatementMatcher(),
				push(this.importQualificationParser.pop().getChildren()));
	}

	public Rule ImportStatementMatcher() {
		return Sequence(
				this.keywordParser.Import(),
				this.importQualificationParser.RootImportQualification(),
				this.symbolParser.Semicolon());
	}

}
