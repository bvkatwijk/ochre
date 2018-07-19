package org.bvkatwijk.ochre.parser.cu;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.lang.imp.Import;
import org.bvkatwijk.ochre.parser.cu.CompilationUnit;
import org.bvkatwijk.ochre.parser.imp.ImportStatementParser;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.support.Var;

public class CompilationUnitParser extends BaseParser<CompilationUnit> {

	public final ImportStatementParser importStatementParser = Parboiled.createParser(ImportStatementParser.class);

	public Rule File() {
		Var<List<Import>> imports = new Var<>(new ArrayList<>());
		return Sequence(
				Optional(
						this.importStatementParser.ImportStatement(),
						imports.set(this.importStatementParser.pop())),
				push(new CompilationUnit(imports.get())));
	}

	public static CompilationUnitParser create() {
		return Parboiled.createParser(CompilationUnitParser.class);
	}

}
