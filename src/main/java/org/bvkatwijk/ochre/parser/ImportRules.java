package org.bvkatwijk.ochre.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	private final CharRanges ranges = new CharRanges();
	private final WhiteSpaceRules whitespace = new WhiteSpaceRules();

	Var<List<String>> imports = new Var<>(new ArrayList<>());

	public Rule ImportsDeclaration() {
		return Sequence(OneOrMoreImports(), registerImports());
	}

	public boolean registerImports() {
		return push(generateResult());
	}

	public String generateResult() {
		return this.imports
				.get()
				.stream()
				.map(String::trim)
				.map(it -> "import " + it + ";")
				.collect(Collectors.joining("\n"));
	}

	public boolean addPackageDeclaration() {
		return push(pop() + "\n" + match().trim() + "\n");
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
		List<String> children = new ArrayList<>();
		System.out.println("trying to match import declaration");
		return Sequence(
				this.IMPORT, clear(children),
				QualifiedIdentifier(), identifier.set(match().trim()),
				Optional(ImportSubDeclaration(identifier, children)),
				this.SEMI, collapseChildren(identifier, children));
	}

	public boolean collapseChildren(StringVar identifier, List<String> children) {
		System.out.println("collapseChildren of " + identifier.get() + " size " + children.size());
		if (children.isEmpty()) {
			this.imports
					.get()
					.add(identifier.get());
		} else {
			this.imports
					.get()
					.addAll(children);
		}
		return true;
	}

	@SuppressSubnodes
	@MemoMismatches
	public Rule Identifier() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()),
				this.whitespace.Spacing());
	}

	public Rule ImportMember(StringVar parent, List<String> children) {
		return Sequence(QualifiedIdentifier(), registerImportSubblock(parent, children));
	}

	public Rule ImportSubDeclaration(StringVar parent, List<String> children) {
		return Sequence(
				this.LWING,
				ImportMember(parent, children),
				ZeroOrMore(this.COMMA, ImportMember(parent, children)),
				this.RWING);
	}

	public boolean registerImportSubblock(StringVar parent, List<String> children) {
		children.add(parent.get() + "." + match());
		return true;
	}

	public Rule QualifiedIdentifier() {
		return Sequence(Identifier(), ZeroOrMore(this.DOT, Identifier()));
	}

	public boolean clear(List<String> children) {
		children.clear();
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
