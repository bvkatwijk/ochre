package org.bvkatwijk.ochre.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bvkatwijk.ochre.compiler.java.Field;
import org.bvkatwijk.ochre.compiler.java.Spacing;
import org.bvkatwijk.ochre.format.Indenter;
import org.bvkatwijk.ochre.parser.keywords.Keyword;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

import lombok.Getter;

public class OchreRules extends BaseParser<String> implements Spacing {

	final Indenter indenter = new Indenter("\t");
	final CharRanges ranges = Parboiled.createParser(CharRanges.class);
	final ImportRules importRules = Parboiled.createParser(ImportRules.class);

	Var<List<Field>> classConstructorFields = new Var<>(new ArrayList<>());
	StringVar type = new StringVar();
	StringVar pack = new StringVar();
	Var<List<String>> imports = new Var<>(new ArrayList<>());
	@Getter
	StringVar importResult = new StringVar();

	Var<Boolean> createGetters = new Var<>(false);

	public Rule CompilationUnit() {
		return Sequence(
				push(""),
				Spacing(),
				Optional(PackageDeclaration(), addPackageDeclaration()),
				Optional(this.importRules.ImportsDeclaration()),
				TypeDeclaration(),
				EOI);
	}

	public boolean addPackageDeclaration() {
		return push(pop() + "\n" + match().trim() + "\n");
	}

	public Rule PackageDeclaration() {
		return Sequence(ZeroOrMore(Annotation()),
				Sequence(Keyword.PACKAGE.rule(this), QualifiedIdentifier(), this.SEMI));
	}

	public Rule TypeDeclaration() {
		return Sequence(
				Optional(Value()),
				ClassAndIdentifier(),
				Optional(FormalParameters()),
				ClassBody());
	}

	public Rule Value() {
		return Sequence(
				Keyword.VALUE.rule(this),
				this.createGetters.set(true));
	}

	public Rule ClassAndIdentifier() {
		return Sequence(
				Class(),
				Sequence(Identifier(), storeIdentifier()),
				push(pop() + "\npublic class " + this.type.get() + " "));
	}

	public boolean storeIdentifier() {
		// java.lang.String match = match();
		// System.out.println("Storing type: " + match());
		return this.type.set(match());
	}

	public Rule FormalParameters() {
		return Sequence(this.LPAR, Optional(FormalParameterDecls()), this.RPAR, push(pop() + " "));
	}

	@MemoMismatches
	public Rule Annotation() {
		return Sequence(
				this.AT,
				QualifiedIdentifier(),
				Optional(AnnotationRest()));
	}

	public Rule MethodParameterDecls() {
		return FormalParameterDecls();
	}

	public Rule FormalParameterDecls() {
		return Sequence(
				ZeroOrMore(Annotation()),
				Sequence(
						FormalParameterDeclsRest(), push(0, match()),
						this.COLON,
						Type(), push(1, match())),
				handleClassParameter(),
				Optional(this.COMMA, FormalParameterDecls()));
	}

	public boolean handleClassParameter() {
		this.classConstructorFields
				.get().add(new Field(pop(0), pop(1)));
		return true;
	}

	public Rule FormalParameterDeclsRest() {
		return FirstOf(
				Sequence(VariableDeclaratorId(), Optional(this.COMMA, FormalParameterDecls())),
				Sequence(this.ELLIPSIS, VariableDeclaratorId()));
	}

	@MemoMismatches
	public Rule BasicType() {
		return Sequence(
				FirstOf("byte", "short", "char", "int", "long", "float", "double", "boolean"),
				TestNot(this.ranges.LetterOrDigit()),
				Spacing());
	}

	public Rule Expression() {
		return Sequence(
				ConditionalExpression(),
				ZeroOrMore(AssignmentOperator(), ConditionalExpression()));
	}

	public Rule AssignmentOperator() {
		return FirstOf(this.EQU, this.PLUSEQU, this.MINUSEQU, this.STAREQU, this.DIVEQU, this.ANDEQU, this.OREQU,
				this.HATEQU, this.MODEQU, this.SLEQU, this.SREQU, this.BSREQU);
	}

	public Rule VariableDeclaratorId() {
		return Identifier();
	}

	public Rule SingleElementAnnotationRest() {
		return Sequence(this.LPAR, ElementValue(), this.RPAR);
	}

	public Rule Type() {
		return Sequence(FirstOf(BasicType(), ClassType()), ZeroOrMore(Dim()));
	}

	public Rule AnnotationRest() {
		return FirstOf(NormalAnnotationRest(), SingleElementAnnotationRest());
	}

	public Rule NormalAnnotationRest() {
		return Sequence(this.LPAR, Optional(ElementValuePairs()), this.RPAR);
	}

	public Rule ElementValueArrayInitializer() {
		return Sequence(this.LWING, Optional(ElementValues()), Optional(this.COMMA), this.RWING);
	}

	public Rule ElementValues() {
		return Sequence(ElementValue(), ZeroOrMore(this.COMMA, ElementValue()));
	}

	public Rule ElementValuePairs() {
		return Sequence(ElementValuePair(), ZeroOrMore(this.COMMA, ElementValuePair()));
	}

	public Rule ElementValuePair() {
		return Sequence(Identifier(), this.EQU, ElementValue());
	}

	public Rule ElementValue() {
		return FirstOf(ConditionalExpression(), Annotation(), ElementValueArrayInitializer());
	}

	public Rule ConditionalExpression() {
		return Sequence(
				ConditionalOrExpression(),
				ZeroOrMore(this.QUERY, Expression(), this.COLON, ConditionalOrExpression()));
	}

	public Rule ConditionalOrExpression() {
		return Sequence(
				ConditionalAndExpression(),
				ZeroOrMore(this.OROR, ConditionalAndExpression()));
	}

	public Rule ConditionalAndExpression() {
		return Sequence(
				InclusiveOrExpression(),
				ZeroOrMore(this.ANDAND, InclusiveOrExpression()));
	}

	public Rule InclusiveOrExpression() {
		return Sequence(
				ExclusiveOrExpression(),
				ZeroOrMore(this.OR, ExclusiveOrExpression()));
	}

	public Rule ExclusiveOrExpression() {
		return Sequence(
				AndExpression(),
				ZeroOrMore(this.HAT, AndExpression()));
	}

	public Rule AndExpression() {
		return Sequence(
				EqualityExpression(),
				ZeroOrMore(this.AND, EqualityExpression()));
	}

	public Rule EqualityExpression() {
		return Sequence(
				RelationalExpression(),
				ZeroOrMore(FirstOf(this.EQUAL, this.NOTEQUAL), RelationalExpression()));
	}

	public Rule RelationalExpression() {
		return Sequence(
				ShiftExpression(),
				ZeroOrMore(
						FirstOf(
								Sequence(FirstOf(this.LE, this.GE, this.LT, this.GT), ShiftExpression()),
								Sequence(Keyword.INSTANCEOF.rule(this), ReferenceType()))));
	}

	public Rule ShiftExpression() {
		return Sequence(
				AdditiveExpression(),
				ZeroOrMore(FirstOf(this.SL, this.SR, this.BSR), AdditiveExpression()));
	}

	public Rule AdditiveExpression() {
		return Sequence(
				MultiplicativeExpression(),
				ZeroOrMore(FirstOf(this.PLUS, this.MINUS), MultiplicativeExpression()));
	}

	public Rule MultiplicativeExpression() {
		return Sequence(
				UnaryExpression(),
				ZeroOrMore(FirstOf(this.STAR, this.DIV, this.MOD), UnaryExpression()));
	}

	public Rule UnaryExpression() {
		return FirstOf(
				Sequence(PrefixOp(), UnaryExpression()),
				Sequence(this.LPAR, Type(), this.RPAR, UnaryExpression()),
				Sequence(Primary(), ZeroOrMore(Selector()), ZeroOrMore(PostFixOp())));
	}

	public Rule Primary() {
		return FirstOf(
				MethodReferenceExpression(),
				LambdaExpression(),
				ParExpression(),
				Sequence(
						NonWildcardTypeArguments(),
						FirstOf(ExplicitGenericInvocationSuffix(), Sequence(Keyword.THIS.rule(this), Arguments()))),
				Sequence(Keyword.THIS.rule(this), Optional(Arguments())),
				Sequence(Keyword.SUPER.rule(this), SuperSuffix()),
				Literal(),
				Sequence(Keyword.NEW.rule(this), Creator()),
				Sequence(QualifiedIdentifier(), Optional(IdentifierSuffix())),
				Sequence(BasicType(), ZeroOrMore(Dim()), this.DOT, Keyword.CLASS.rule(this)),
				Sequence(Keyword.VOID.rule(this), this.DOT, Keyword.CLASS.rule(this)));
	}

	public Rule Creator() {
		return FirstOf(
				Sequence(Optional(NonWildcardTypeArguments()), CreatedName(), ClassCreatorRest()),
				Sequence(Optional(NonWildcardTypeArguments()), FirstOf(ClassType(), BasicType()), ArrayCreatorRest()));
	}

	public Rule CreatedName() {
		return Sequence(
				Identifier(), Optional(NonWildcardTypeArguments()),
				ZeroOrMore(this.DOT, Identifier(), Optional(NonWildcardTypeArguments())));
	}

	public Rule InnerCreator() {
		return Sequence(Identifier(), ClassCreatorRest());
	}

	// The following is more generous than JLS 15.10. According to that
	// definition,
	// BasicType must be followed by at least one DimExpr or by
	// ArrayInitializer.
	public Rule ArrayCreatorRest() {
		return Sequence(
				this.LBRK,
				FirstOf(
						Sequence(this.RBRK, ZeroOrMore(Dim()), ArrayInitializer()),
						Sequence(Expression(), this.RBRK, ZeroOrMore(DimExpr()), ZeroOrMore(Dim()))));
	}

	public Rule ArrayInitializer() {
		return Sequence(
				this.LWING,
				Optional(
						VariableInitializer(),
						ZeroOrMore(this.COMMA, VariableInitializer())),
				Optional(this.COMMA),
				this.RWING);
	}

	public Rule VariableInitializer() {
		return FirstOf(ArrayInitializer(), Expression());
	}

	public Rule IdentifierSuffix() {
		return FirstOf(
				Sequence(this.LBRK,
						FirstOf(
								Sequence(this.RBRK, ZeroOrMore(Dim()), this.DOT, Keyword.CLASS.rule(this)),
								Sequence(Expression(), this.RBRK))),
				Arguments(),
				Sequence(
						this.DOT,
						FirstOf(
								Keyword.CLASS.rule(this),
								ExplicitGenericInvocation(),
								This(),
								Sequence(Super(), Arguments()),
								Sequence(New(), Optional(NonWildcardTypeArguments()), InnerCreator()))));
	}

	public Rule Literal() {
		return Sequence(
				FirstOf(
						FloatLiteral(),
						IntegerLiteral(),
						CharLiteral(),
						StringLiteral(),
						Sequence("true", TestNot(this.ranges.LetterOrDigit())),
						Sequence("false", TestNot(this.ranges.LetterOrDigit())),
						Sequence("null", TestNot(this.ranges.LetterOrDigit()))),
				Spacing());
	}

	@SuppressSubnodes
	public Rule IntegerLiteral() {
		return Sequence(FirstOf(HexNumeral(), OctalNumeral(), DecimalNumeral()), Optional(AnyOf("lL")));
	}

	@SuppressSubnodes
	public Rule DecimalNumeral() {
		return FirstOf('0', Sequence(CharOneToNine(), ZeroOrMore(this.ranges.ZeroToNine())));
	}

	public Rule CharOneToNine() {
		return CharRange('1', '9');
	}

	@MemoMismatches
	public Rule HexNumeral() {
		return Sequence('0', IgnoreCase('x'), OneOrMore(HexDigit()));
	}

	public Rule HexDigit() {
		return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), this.ranges.ZeroToNine());
	}

	@SuppressSubnodes
	public Rule OctalNumeral() {
		return Sequence('0', OneOrMore(CharRange('0', '7')));
	}

	public Rule FloatLiteral() {
		return FirstOf(HexFloat(), DecimalFloat());
	}

	@SuppressSubnodes
	public Rule DecimalFloat() {
		return FirstOf(
				Sequence(OneOrMore(this.ranges.ZeroToNine()), '.', ZeroOrMore(this.ranges.ZeroToNine()),
						Optional(Exponent()),
						Optional(AnyOf("fFdD"))),
				Sequence('.', OneOrMore(this.ranges.ZeroToNine()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(this.ranges.ZeroToNine()), Exponent(), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(this.ranges.ZeroToNine()), Optional(Exponent()), AnyOf("fFdD")));
	}

	public Rule Exponent() {
		return Sequence(AnyOf("eE"), Optional(AnyOf("+-")), OneOrMore(this.ranges.ZeroToNine()));
	}

	@SuppressSubnodes
	public Rule HexFloat() {
		return Sequence(HexSignificant(), BinaryExponent(), Optional(AnyOf("fFdD")));
	}

	public Rule HexSignificant() {
		return FirstOf(
				Sequence(FirstOf("0x", "0X"), ZeroOrMore(HexDigit()), '.', OneOrMore(HexDigit())),
				Sequence(HexNumeral(), Optional('.')));
	}

	public Rule BinaryExponent() {
		return Sequence(AnyOf("pP"), Optional(AnyOf("+-")), OneOrMore(this.ranges.ZeroToNine()));
	}

	public Rule CharLiteral() {
		return Sequence(
				'\'',
				FirstOf(Escape(), Sequence(TestNot(AnyOf("'\\")), ANY)).suppressSubnodes(),
				'\'');
	}

	public Rule StringLiteral() {
		return Sequence(
				'"',
				ZeroOrMore(
						FirstOf(
								Escape(),
								Sequence(TestNot(AnyOf("\r\n\"\\")), ANY))).suppressSubnodes(),
				'"');
	}

	public Rule Escape() {
		return Sequence('\\', FirstOf(AnyOf("btnfr\"\'\\"), OctalEscape(), UnicodeEscape()));
	}

	public Rule OctalEscape() {
		return FirstOf(
				Sequence(CharRange('0', '3'), CharRange('0', '7'), CharRange('0', '7')),
				Sequence(CharRange('0', '7'), CharRange('0', '7')),
				CharRange('0', '7'));
	}

	public Rule UnicodeEscape() {
		return Sequence(OneOrMore('u'), HexDigit(), HexDigit(), HexDigit(), HexDigit());
	}

	public Rule ParExpression() {
		return Sequence(this.LPAR, Expression(), this.RPAR);
	}

	public Rule PrefixOp() {
		return FirstOf(this.INC, this.DEC, this.BANG, this.TILDA, this.PLUS, this.MINUS);
	}

	public Rule PostFixOp() {
		return FirstOf(this.INC, this.DEC);
	}

	public Rule Selector() {
		return FirstOf(
				Sequence(this.DOT, Identifier(), Optional(Arguments())),
				Sequence(this.DOT, ExplicitGenericInvocation()),
				Sequence(this.DOT, This()),
				Sequence(this.DOT, Super(), SuperSuffix()),
				Sequence(this.DOT, New(), Optional(NonWildcardTypeArguments()), InnerCreator()),
				DimExpr());
	}

	public Rule This() {
		return Keyword.THIS.rule(this);
	}

	public Rule Super() {
		return Keyword.SUPER.rule(this);
	}

	public Rule New() {
		return Keyword.NEW.rule(this);
	}

	public Rule DimExpr() {
		return Sequence(this.LBRK, Expression(), this.RBRK);
	}

	public Rule ClassCreatorRest() {
		return Sequence(Arguments(), Optional(ClassBody()));
	}

	public Rule ExplicitGenericInvocation() {
		return Sequence(NonWildcardTypeArguments(), ExplicitGenericInvocationSuffix());
	}

	public Rule NonWildcardTypeArguments() {
		return Sequence(this.LPOINT, ReferenceType(), ZeroOrMore(this.COMMA, ReferenceType()), this.RPOINT);
	}

	public Rule ExplicitGenericInvocationSuffix() {
		return FirstOf(
				Sequence(Super(), SuperSuffix()),
				Sequence(Identifier(), Arguments()));
	}

	public Rule SuperSuffix() {
		return FirstOf(Arguments(), Sequence(this.DOT, Identifier(), Optional(Arguments())));
	}

	public Rule Arguments() {
		return Sequence(
				this.LPAR,
				Optional(Expression(), ZeroOrMore(this.COMMA, Expression())),
				this.RPAR);
	}

	public Rule MethodReferenceExpression() {
		return Sequence(
				FirstOf(
						This(),
						ClassType()),
				this.DOUBLECOLON,
				Identifier());
	}

	public Rule LambdaExpression() {
		return Sequence(
				FormalParameterDeclsRest(),
				this.LAMBDA,
				Expression());
	}

	public Rule Dim() {
		return Sequence(this.LBRK, this.RBRK);
	}

	public Rule ClassType() {
		return Sequence(
				Identifier(), Optional(TypeArguments()),
				ZeroOrMore(this.DOT, Identifier(), Optional(TypeArguments())));
	}

	public Rule TypeArguments() {
		return Sequence(this.LPOINT, TypeArgument(), ZeroOrMore(this.COMMA, TypeArgument()), this.RPOINT);
	}

	public Rule TypeArgument() {
		return FirstOf(
				ReferenceType(),
				Sequence(this.QUERY, Optional(FirstOf(Keyword.EXTENDS.rule(this), Super()), ReferenceType())));
	}

	public Rule ReferenceType() {
		return FirstOf(
				Sequence(BasicType(), OneOrMore(Dim())),
				Sequence(ClassType(), ZeroOrMore(Dim())));
	}

	public Rule QualifiedIdentifier() {
		return Sequence(Identifier(), ZeroOrMore(this.DOT, Identifier()));
	}

	public Rule Class() {
		return Keyword.CLASS.rule(this);
	}

	public Rule ClassBody() {
		return Sequence(
				Optional(Spacing()),
				this.LWING,
				this.RWING,
				addClassBodyElements());
	}

	public boolean addClassBodyElements() {
		return push(pop()
				+ "{"
				+ "\n"
				+ insertElements()
				+ "\n"
				+ "}"
				+ "\n");
	}

	public String insertElements() {
		return this.classConstructorFields
				.get().isEmpty()
						? ""
						: insertClassBodyElements();
	}

	public String insertClassBodyElements() {
		return ""
				+ "\n" + insertClassBodyFields()
				+ "\n"
				+ "\n" + this.indenter.indent(insertClassBodyConstructor())
				+ insertGetters()
				+ "\n";
	}

	public String insertGetters() {
		return this.createGetters.get()
				? "\n"
						+ "\n"
						+ this.indenter.indent(this.classConstructorFields
								.get()
								.stream()
								.map(field -> field.asGetter(this.indenter))
								.collect(Collectors.joining("\n\n")))
				: "";
	}

	public String insertClassBodyConstructor() {
		return "public " + this.type.get() + "(" + fieldParameters() + ") {"
				+ "\n" + this.indenter.indent(fieldAssignments())
				+ "\n" + "}"
				+ "\n";
	}

	public String fieldAssignments() {
		return this.classConstructorFields
				.get()
				.stream()
				.map(Field::asAssignment)
				.collect(Collectors.joining("\n"));
	}

	public String fieldParameters() {
		return this.classConstructorFields
				.get()
				.stream()
				.map(Field::asParam)
				.collect(Collectors.joining(", "));
	}

	public String insertClassBodyFields() {
		return this.classConstructorFields
				.get()
				.stream()
				.map(it -> this.indenter.indent(it.asDeclaration()))
				.collect(Collectors.joining("\n"));
	}

	@SuppressSubnodes
	public Rule Identifier() {
		return Sequence(
				this.ranges.Letter(),
				ZeroOrMore(this.ranges.LetterOrDigit()));
	}

	final Rule AT = Terminal("@");
	final Rule AND = Terminal("&", AnyOf("=&"));
	final Rule ANDAND = Terminal("&&");
	final Rule ANDEQU = Terminal("&=");
	final Rule BANG = Terminal("!", Ch('='));
	final Rule BSR = Terminal(">>>", Ch('='));
	final Rule BSREQU = Terminal(">>>=");
	final Rule COLON = Terminal(":", Ch(':'));
	final Rule DOUBLECOLON = Terminal("::");
	final Rule COMMA = Terminal(",");
	final Rule DEC = Terminal("--");
	final Rule DIV = Terminal("/", Ch('='));
	final Rule DIVEQU = Terminal("/=");
	final Rule DOT = Terminal(".");
	final Rule ELLIPSIS = Terminal("...");
	final Rule EQU = Terminal("=", Ch('='));
	final Rule EQUAL = Terminal("==");
	final Rule GE = Terminal(">=");
	final Rule GT = Terminal(">", AnyOf("=>"));
	final Rule HAT = Terminal("^", Ch('='));
	final Rule HATEQU = Terminal("^=");
	final Rule INC = Terminal("++");
	final Rule LAMBDA = Terminal("->");
	final Rule LBRK = Terminal("[");
	final Rule LE = Terminal("<=");
	final Rule LPAR = Terminal("(");
	final Rule LPOINT = Terminal("<");
	final Rule LT = Terminal("<", AnyOf("=<"));
	final Rule LWING = Terminal("{");
	final Rule MINUS = Terminal("-", AnyOf("=->"));
	final Rule MINUSEQU = Terminal("-=");
	final Rule MOD = Terminal("%", Ch('='));
	final Rule MODEQU = Terminal("%=");
	final Rule NOTEQUAL = Terminal("!=");
	final Rule OR = Terminal("|", AnyOf("=|"));
	final Rule OREQU = Terminal("|=");
	final Rule OROR = Terminal("||");
	final Rule PLUS = Terminal("+", AnyOf("=+"));
	final Rule PLUSEQU = Terminal("+=");
	final Rule QUERY = Terminal("?");
	final Rule RBRK = Terminal("]");
	final Rule RPAR = Terminal(")");
	final Rule RPOINT = Terminal(">");
	final Rule RWING = Terminal("}");
	final Rule SEMI = Terminal(";");
	final Rule SL = Terminal("<<", Ch('='));
	final Rule SLEQU = Terminal("<<=");
	final Rule SR = Terminal(">>", AnyOf("=>"));
	final Rule SREQU = Terminal(">>=");
	final Rule STAR = Terminal("*", Ch('='));
	final Rule STAREQU = Terminal("*=");
	final Rule TILDA = Terminal("~");

	@SuppressNode
	@DontLabel
	public Rule ForKeyword(String keyword) {
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
