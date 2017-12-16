package org.bvkatwijk.ochre.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bvkatwijk.ochre.compiler.java.Field;
import org.bvkatwijk.ochre.format.Indenter;
import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.common.Reference;
import org.parboiled.support.StringVar;

public class OchreRules extends BaseParser<String> {

	private final Indenter indenter = new Indenter("\t");
	private final CharRanges ranges = new CharRanges(this);

	final List<Field> classConstructorFields = new ArrayList<>();
	StringVar type = new StringVar();
	StringVar pack = new StringVar();
	Reference<Boolean> createGetters = new Reference<>(false);

	public Rule CompilationUnit() {
		return Sequence(
				push(""),
				Spacing(),
				Optional(PackageDeclaration(), push(pop() + "\n" + match().trim() + "\n")),
				Optional(ImportDeclaration(), push(pop() + "\n" + match().trim() + "\n")),
				TypeDeclaration(),
				EOI);
	}

	public Rule ImportDeclaration() {
		return Sequence(
				IMPORT,
				QualifiedIdentifier(),
				// Optional(DOT, STAR),
				SEMI);
	}

	public Rule PackageDeclaration() {
		return Sequence(ZeroOrMore(Annotation()), Sequence(PACKAGE, QualifiedIdentifier(), SEMI));
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
				VALUE,
				createGetters.set(true));
	}

	public Rule ClassAndIdentifier() {
		return Sequence(
				Class(),
				Sequence(Identifier(), storeIdentifier()),
				push(pop() + "\npublic class " + type.get()));
	}

	public boolean storeIdentifier() {
		java.lang.String match = match();
		System.out.println("Storing type: " + match());
		return type.set(match);
	}

	public boolean push() {
		return push(match());
	}

	public Rule FormalParameters() {
		return Sequence(LPAR, Optional(FormalParameterDecls()), RPAR, push(pop() + " "));
	}

	public final Rule BREAK = Keyword("break");
	public final Rule CASE = Keyword("case");
	public final Rule CATCH = Keyword("catch");
	public final Rule CLASS = Keyword("class");
	public final Rule CONTINUE = Keyword("continue");
	public final Rule DEFAULT = Keyword("default");
	public final Rule DO = Keyword("do");
	public final Rule ELSE = Keyword("else");
	public final Rule ENUM = Keyword("enum");
	public final Rule EXTENDS = Keyword("extends");
	public final Rule FINALLY = Keyword("finally");
	public final Rule FINAL = Keyword("final");
	public final Rule FOR = Keyword("for");
	public final Rule IF = Keyword("if");
	public final Rule IMPLEMENTS = Keyword("implements");
	public final Rule IMPORT = Keyword("import");
	public final Rule INTERFACE = Keyword("interface");
	public final Rule INSTANCEOF = Keyword("instanceof");
	public final Rule NEW = Keyword("new");
	public final Rule PACKAGE = Keyword("package");
	public final Rule RETURN = Keyword("return");
	public final Rule STATIC = Keyword("static");
	public final Rule SUPER = Keyword("super");
	public final Rule SWITCH = Keyword("switch");
	public final Rule SYNCHRONIZED = Keyword("synchronized");
	public final Rule THIS = Keyword("this");
	public final Rule THROWS = Keyword("throws");
	public final Rule THROW = Keyword("throw");
	public final Rule TRY = Keyword("try");
	public final Rule VALUE = Keyword("value");
	public final Rule VOID = Keyword("void");
	public final Rule WHILE = Keyword("while");

	@MemoMismatches
	public Rule Annotation() {
		return Sequence(
				AT,
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
						COLON,
						Type(), push(1, match())),
				handleClassParameter(),
				Optional(COMMA, FormalParameterDecls()));
	}

	public boolean handleClassParameter() {
		String type = pop(1);
		String name = pop(0);
		System.out.println("Storing " + type + " " + name + " in classbodyelements.");
		classConstructorFields.add(new Field(name, type));
		return true;
	}

	public Rule FormalParameterDeclsRest() {
		return FirstOf(
				Sequence(VariableDeclaratorId(), Optional(COMMA, FormalParameterDecls())),
				Sequence(ELLIPSIS, VariableDeclaratorId()));
	}

	@MemoMismatches
	public Rule BasicType() {
		return Sequence(
				FirstOf("byte", "short", "char", "int", "long", "float", "double", "boolean"),
				TestNot(LetterOrDigit()),
				Spacing());
	}

	public Rule Expression() {
		return Sequence(
				ConditionalExpression(),
				ZeroOrMore(AssignmentOperator(), ConditionalExpression()));
	}

	public Rule AssignmentOperator() {
		return FirstOf(EQU, PLUSEQU, MINUSEQU, STAREQU, DIVEQU, ANDEQU, OREQU, HATEQU, MODEQU, SLEQU, SREQU, BSREQU);
	}

	public Rule VariableDeclaratorId() {
		return Identifier();
	}

	public Rule SingleElementAnnotationRest() {
		return Sequence(LPAR, ElementValue(), RPAR);
	}

	public Rule Type() {
		return Sequence(FirstOf(BasicType(), ClassType()), ZeroOrMore(Dim()));
	}

	public Rule AnnotationRest() {
		return FirstOf(NormalAnnotationRest(), SingleElementAnnotationRest());
	}

	public Rule NormalAnnotationRest() {
		return Sequence(LPAR, Optional(ElementValuePairs()), RPAR);
	}

	public Rule ElementValueArrayInitializer() {
		return Sequence(LWING, Optional(ElementValues()), Optional(COMMA), RWING);
	}

	public Rule ElementValues() {
		return Sequence(ElementValue(), ZeroOrMore(COMMA, ElementValue()));
	}

	public Rule ElementValuePairs() {
		return Sequence(ElementValuePair(), ZeroOrMore(COMMA, ElementValuePair()));
	}

	public Rule ElementValuePair() {
		return Sequence(Identifier(), EQU, ElementValue());
	}

	public Rule ElementValue() {
		return FirstOf(ConditionalExpression(), Annotation(), ElementValueArrayInitializer());
	}

	public Rule ConditionalExpression() {
		return Sequence(
				ConditionalOrExpression(),
				ZeroOrMore(QUERY, Expression(), COLON, ConditionalOrExpression()));
	}

	public Rule ConditionalOrExpression() {
		return Sequence(
				ConditionalAndExpression(),
				ZeroOrMore(OROR, ConditionalAndExpression()));
	}

	public Rule ConditionalAndExpression() {
		return Sequence(
				InclusiveOrExpression(),
				ZeroOrMore(ANDAND, InclusiveOrExpression()));
	}

	public Rule InclusiveOrExpression() {
		return Sequence(
				ExclusiveOrExpression(),
				ZeroOrMore(OR, ExclusiveOrExpression()));
	}

	public Rule ExclusiveOrExpression() {
		return Sequence(
				AndExpression(),
				ZeroOrMore(HAT, AndExpression()));
	}

	public Rule AndExpression() {
		return Sequence(
				EqualityExpression(),
				ZeroOrMore(AND, EqualityExpression()));
	}

	public Rule EqualityExpression() {
		return Sequence(
				RelationalExpression(),
				ZeroOrMore(FirstOf(EQUAL, NOTEQUAL), RelationalExpression()));
	}

	public Rule RelationalExpression() {
		return Sequence(
				ShiftExpression(),
				ZeroOrMore(
						FirstOf(
								Sequence(FirstOf(LE, GE, LT, GT), ShiftExpression()),
								Sequence(INSTANCEOF, ReferenceType()))));
	}

	public Rule ShiftExpression() {
		return Sequence(
				AdditiveExpression(),
				ZeroOrMore(FirstOf(SL, SR, BSR), AdditiveExpression()));
	}

	public Rule AdditiveExpression() {
		return Sequence(
				MultiplicativeExpression(),
				ZeroOrMore(FirstOf(PLUS, MINUS), MultiplicativeExpression()));
	}

	public Rule MultiplicativeExpression() {
		return Sequence(
				UnaryExpression(),
				ZeroOrMore(FirstOf(STAR, DIV, MOD), UnaryExpression()));
	}

	public Rule UnaryExpression() {
		return FirstOf(
				Sequence(PrefixOp(), UnaryExpression()),
				Sequence(LPAR, Type(), RPAR, UnaryExpression()),
				Sequence(Primary(), ZeroOrMore(Selector()), ZeroOrMore(PostFixOp())));
	}

	public Rule Primary() {
		return FirstOf(
				MethodReferenceExpression(),
				LambdaExpression(),
				ParExpression(),
				Sequence(
						NonWildcardTypeArguments(),
						FirstOf(ExplicitGenericInvocationSuffix(), Sequence(THIS, Arguments()))),
				Sequence(THIS, Optional(Arguments())),
				Sequence(SUPER, SuperSuffix()),
				Literal(),
				Sequence(NEW, Creator()),
				Sequence(QualifiedIdentifier(), Optional(IdentifierSuffix())),
				Sequence(BasicType(), ZeroOrMore(Dim()), DOT, CLASS),
				Sequence(VOID, DOT, CLASS));
	}

	public Rule Creator() {
		return FirstOf(
				Sequence(Optional(NonWildcardTypeArguments()), CreatedName(), ClassCreatorRest()),
				Sequence(Optional(NonWildcardTypeArguments()), FirstOf(ClassType(), BasicType()), ArrayCreatorRest()));
	}

	public Rule CreatedName() {
		return Sequence(
				Identifier(), Optional(NonWildcardTypeArguments()),
				ZeroOrMore(DOT, Identifier(), Optional(NonWildcardTypeArguments())));
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
				LBRK,
				FirstOf(
						Sequence(RBRK, ZeroOrMore(Dim()), ArrayInitializer()),
						Sequence(Expression(), RBRK, ZeroOrMore(DimExpr()), ZeroOrMore(Dim()))));
	}

	public Rule ArrayInitializer() {
		return Sequence(
				LWING,
				Optional(
						VariableInitializer(),
						ZeroOrMore(COMMA, VariableInitializer())),
				Optional(COMMA),
				RWING);
	}

	public Rule VariableInitializer() {
		return FirstOf(ArrayInitializer(), Expression());
	}

	public Rule IdentifierSuffix() {
		return FirstOf(
				Sequence(LBRK,
						FirstOf(
								Sequence(RBRK, ZeroOrMore(Dim()), DOT, CLASS),
								Sequence(Expression(), RBRK))),
				Arguments(),
				Sequence(
						DOT,
						FirstOf(
								CLASS,
								ExplicitGenericInvocation(),
								THIS,
								Sequence(SUPER, Arguments()),
								Sequence(NEW, Optional(NonWildcardTypeArguments()), InnerCreator()))));
	}

	public Rule Literal() {
		return Sequence(
				FirstOf(
						FloatLiteral(),
						IntegerLiteral(),
						CharLiteral(),
						StringLiteral(),
						Sequence("true", TestNot(LetterOrDigit())),
						Sequence("false", TestNot(LetterOrDigit())),
						Sequence("null", TestNot(LetterOrDigit()))),
				Spacing());
	}

	@SuppressSubnodes
	public Rule IntegerLiteral() {
		return Sequence(FirstOf(HexNumeral(), OctalNumeral(), DecimalNumeral()), Optional(AnyOf("lL")));
	}

	@SuppressSubnodes
	public Rule DecimalNumeral() {
		return FirstOf('0', Sequence(CharOneToNine(), ZeroOrMore(Digit())));
	}

	public Rule CharOneToNine() {
		return CharRange('1', '9');
	}

	@MemoMismatches
	public Rule HexNumeral() {
		return Sequence('0', IgnoreCase('x'), OneOrMore(HexDigit()));
	}

	public Rule HexDigit() {
		return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), Digit());
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
				Sequence(OneOrMore(Digit()), '.', ZeroOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
				Sequence('.', OneOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(Digit()), Exponent(), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(Digit()), Optional(Exponent()), AnyOf("fFdD")));
	}

	public Rule Exponent() {
		return Sequence(AnyOf("eE"), Optional(AnyOf("+-")), OneOrMore(Digit()));
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
		return Sequence(AnyOf("pP"), Optional(AnyOf("+-")), OneOrMore(Digit()));
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
		return Sequence(LPAR, Expression(), RPAR);
	}

	public Rule PrefixOp() {
		return FirstOf(INC, DEC, BANG, TILDA, PLUS, MINUS);
	}

	public Rule PostFixOp() {
		return FirstOf(INC, DEC);
	}

	public Rule Selector() {
		return FirstOf(
				Sequence(DOT, Identifier(), Optional(Arguments())),
				Sequence(DOT, ExplicitGenericInvocation()),
				Sequence(DOT, THIS),
				Sequence(DOT, SUPER, SuperSuffix()),
				Sequence(DOT, NEW, Optional(NonWildcardTypeArguments()), InnerCreator()),
				DimExpr());
	}

	public Rule DimExpr() {
		return Sequence(LBRK, Expression(), RBRK);
	}

	public Rule ClassCreatorRest() {
		return Sequence(Arguments(), Optional(ClassBody()));
	}

	public Rule ExplicitGenericInvocation() {
		return Sequence(NonWildcardTypeArguments(), ExplicitGenericInvocationSuffix());
	}

	public Rule NonWildcardTypeArguments() {
		return Sequence(LPOINT, ReferenceType(), ZeroOrMore(COMMA, ReferenceType()), RPOINT);
	}

	public Rule ExplicitGenericInvocationSuffix() {
		return FirstOf(
				Sequence(SUPER, SuperSuffix()),
				Sequence(Identifier(), Arguments()));
	}

	public Rule SuperSuffix() {
		return FirstOf(Arguments(), Sequence(DOT, Identifier(), Optional(Arguments())));
	}

	public Rule Arguments() {
		return Sequence(
				LPAR,
				Optional(Expression(), ZeroOrMore(COMMA, Expression())),
				RPAR);
	}

	public Rule MethodReferenceExpression() {
		return Sequence(
				FirstOf(
						THIS,
						ClassType()),
				DOUBLECOLON,
				Identifier());
	}

	public Rule LambdaExpression() {
		return Sequence(
				FormalParameterDeclsRest(),
				LAMBDA,
				Expression());
	}

	public Rule Dim() {
		return Sequence(LBRK, RBRK);
	}

	public Rule ClassType() {
		return Sequence(
				Identifier(), Optional(TypeArguments()),
				ZeroOrMore(DOT, Identifier(), Optional(TypeArguments())));
	}

	public Rule TypeArguments() {
		return Sequence(LPOINT, TypeArgument(), ZeroOrMore(COMMA, TypeArgument()), RPOINT);
	}

	public Rule TypeArgument() {
		return FirstOf(
				ReferenceType(),
				Sequence(QUERY, Optional(FirstOf(EXTENDS, SUPER), ReferenceType())));
	}

	public Rule ReferenceType() {
		return FirstOf(
				Sequence(BasicType(), OneOrMore(Dim())),
				Sequence(ClassType(), ZeroOrMore(Dim())));
	}

	public Rule QualifiedIdentifier() {
		return Sequence(Identifier(), ZeroOrMore(DOT, Identifier()));
	}

	public Rule Class() {
		return CLASS;
	}

	public Rule ClassBody() {
		return Sequence(
				LWING,
				RWING,
				push(pop()
						+ "{"
						+ "\n"
						+ insertElements()
						+ "\n"
						+ "}"
						+ "\n"));
	}

	public String insertElements() {
		return classConstructorFields.isEmpty()
				? ""
				: insertClassBodyElements();
	}

	public String insertClassBodyElements() {
		return ""
				+ "\n" + insertClassBodyFields()
				+ "\n"
				+ "\n" + indenter.indent(insertClassBodyConstructor())
				+ insertGetters()
				+ "\n";
	}

	public String insertGetters() {
		return createGetters.get()
				? "\n"
						+ "\n"
						+ indenter.indent(classConstructorFields
								.stream()
								.map(field -> field.asGetter(indenter))
								.collect(Collectors.joining("\n\n")))
				: "";
	}

	public String insertClassBodyConstructor() {
		return "public " + type.get() + "(" + fieldParameters() + ") {"
				+ "\n" + indenter.indent(fieldAssignments())
				+ "\n" + "}"
				+ "\n";
	}

	public String fieldAssignments() {
		return classConstructorFields
				.stream()
				.map(Field::asAssignment)
				.collect(Collectors.joining("\n"));
	}

	public String fieldParameters() {
		return classConstructorFields
				.stream()
				.map(Field::asParam)
				.collect(Collectors.joining(", "));
	}

	public String insertClassBodyFields() {
		return classConstructorFields
				.stream()
				.map(it -> indenter.indent(it.asDeclaration()))
				.collect(Collectors.joining("\n"));
	}

	@SuppressSubnodes
	@MemoMismatches
	public Rule Identifier() {
		return Sequence(
				Letter(),
				ZeroOrMore(LetterOrDigit()),
				Spacing());
	}

	public Rule Letter() {
		return FirstOf(CharLowerAToLowerZ(), CharUpperAToUpperZ(), '_', '$');
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
	public Rule Keyword(String keyword) {
		return Terminal(keyword, LetterOrDigit());
	}

	public Rule CharUpperAToUpperZ() {
		return CharRange('A', 'Z');
	}

	public Rule CharLowerAToLowerZ() {
		return CharRange('a', 'z');
	}

	@MemoMismatches
	public Rule LetterOrDigit() {
		return FirstOf(CharLowerAToLowerZ(), CharUpperAToUpperZ(), Digit(), '_', '$');
	}

	public Rule Digit() {
		return ranges.ZeroToNine();
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

	@SuppressNode
	public Rule Spacing() {
		return ZeroOrMore(FirstOf(

				// whitespace
				OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

				// traditional comment
				Sequence("/*", ZeroOrMore(TestNot("*/"), ANY), "*/"),

				// end of line comment
				Sequence(
						"//",
						ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
						FirstOf("\r\n", '\r', '\n', EOI))));
	}

}
