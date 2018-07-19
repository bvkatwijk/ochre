package org.bvkatwijk.ochre.parser.imp;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.lang.imp.ImportSection;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.support.Var;

public class ImportSectionParser extends BaseParser<ImportSection> {

	public final ImportStatementParser importStatementParser = Parboiled.createParser(ImportStatementParser.class);

	public static ImportSectionParser create() {
		return Parboiled.createParser(ImportSectionParser.class);
	}

	public Rule ImportSection() {
		Var<List<Import>> imports = new Var<>(new ArrayList<>());
		return Sequence(
				OneOrMore(SingleImportStatement(imports)),
				push(new ImportSection(imports.get())));
	}

	Rule SingleImportStatement(Var<List<Import>> imports) {
		return Sequence(
				this.importStatementParser.ImportStatement(),
				imports.get().addAll(this.importStatementParser.pop()));
	}

}
