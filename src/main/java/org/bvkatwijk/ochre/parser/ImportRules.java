package org.bvkatwijk.ochre.parser;

import java.util.ArrayList;
import java.util.List;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

public class ImportRules extends BaseParser<String> {

	final Actions actions = new Actions();
	final CharRanges ranges = new CharRanges();
	final WhiteSpaceRules whitespace = new WhiteSpaceRules();

	Var<List<String>> imports = new Var<>(new ArrayList<>());

	public Rule ImportsDeclaration() {
		return Sequence(OneOrMoreImports(), this.actions.registerImports(this.imports));
	}

	public Rule OneOrMoreImports() {
		return OneOrMore(ImportDeclaration());
	}

	public boolean addImport() {
		System.out.println("Adding " + match());
		return this.imports
				.get()
				.add(match().trim());
	}

	public Rule ImportDeclaration() {
		StringVar identifier = new StringVar();
		Var<List<String>> children = new Var<>(new ArrayList<>());
		System.out.println("trying to match import declaration");
		return Sequence(
				Sequence(this.IMPORT, this.actions.clear(children)),
				Sequence(QualifiedIdentifier(), this.actions.storeIdentifier(identifier)),
				Optional(ImportSubDeclaration(identifier, children)),
				Sequence(this.SEMI, this.actions.collapseChildren(this.imports, identifier, children)));
	}

	@SuppressSubnodes
	@MemoMismatches
	public Rule Identifier() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()),
				this.whitespace.Spacing());
	}

	public Rule ImportMember(StringVar parent, Var<List<String>> children) {
		return Sequence(QualifiedIdentifier(), this.actions.registerImportSubblock(parent, children));
	}

	public Rule ImportSubDeclaration(StringVar parent, Var<List<String>> children) {
		return Sequence(
				this.LWING,
				ImportMember(parent, children),
				ZeroOrMore(this.COMMA, ImportMember(parent, children)),
				this.RWING);
	}

	public Rule QualifiedIdentifier() {
		return Sequence(Identifier(), ZeroOrMore(this.DOT, Identifier()));
	}

	public boolean clear(Var<List<String>> children) {
		children.get().clear();
		return true;
	}

	public final Rule IMPORT = Keyword("import");
	public final Rule COMMA = Terminal(",");
	public final Rule DOT = Terminal(".");
	public final Rule LWING = Terminal("{");
	public final Rule RWING = Terminal("}");
	public final Rule SEMI = Terminal(";");

	@SuppressNode
	@DontLabel
	public Rule Keyword(String keyword) {
		return Terminal(keyword, this.ranges.LetterOrDigit());
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string) {
		return Sequence(string, this.whitespace.Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	public Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), this.whitespace.Spacing()).label('\'' + string + '\'');
	}
}
