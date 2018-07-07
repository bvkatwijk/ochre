package org.bvkatwijk.ochre.parser;

import java.util.Arrays;
import java.util.List;

import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.compiler.java.TypeReferenceParser;
import org.bvkatwijk.ochre.lang.Import;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.SuppressNode;

/**
 * Parser for import statements, i.e. import a.b { C, d.E }
 *
 * @author boris
 */
@BuildParseTree
public class ImportStatementParser extends BaseParser<List<Import>> implements Spacing {

	public TypeReferenceParser type = Parboiled.createParser(TypeReferenceParser.class);
	public final CharRanges ranges = new CharRanges();

	public Rule ImportStatement() {
		return Sequence(
				this.IMPORT,
				this.type.Type(),
				push(Arrays.asList(new Import(match()))));
	}

	public final Rule IMPORT = Keyword("import");

	@SuppressNode
	@DontLabel
	public Rule Keyword(String keyword) {
		return Terminal(keyword, this.ranges.LetterOrDigit());
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string) {
		return Sequence(string, Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), Spacing()).label('\'' + string + '\'');
	}

	@Override
	public Rule Any() {
		return ANY;
	}

	@Override
	public Rule EOI() {
		return EOI;
	}

}
