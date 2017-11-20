package org.bvkatwijk.ochre.parser;

import org.bvkatwijk.ochre.parser.range.CharRanges;
import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.DontLabel;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;

public class OchreRules extends BaseParser<Object> {

	private final CharRanges ranges = new CharRanges(this);

	public Rule CompilationUnit() {
		return Sequence(
				Spacing(),
				TypeDeclaration(),
				EOI);
	}

	Rule TypeDeclaration() {
		return Sequence(
				Class(),
				Identifier(),
				Optional(FormalParameters()),
				ClassBody()
				);
	}

	Rule FormalParameters() {
		return Sequence(LPAR, Optional(FormalParameterDecls()), RPAR);
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
	public final Rule VOID = Keyword("void");
	public final Rule WHILE = Keyword("while");

	@MemoMismatches
	Rule Annotation() {
		return Sequence(AT, QualifiedIdentifier(), Optional(AnnotationRest()));
	}

	Rule FormalParameterDecls() {
		return Sequence(ZeroOrMore(FirstOf(FINAL, Annotation())), Type(), FormalParameterDeclsRest());
	}

	Rule FormalParameterDeclsRest() {
		return FirstOf(
				Sequence(VariableDeclaratorId(), Optional(COMMA, FormalParameterDecls())),
				Sequence(ELLIPSIS, VariableDeclaratorId())
				);
	}

	@MemoMismatches
	Rule BasicType() {
		return Sequence(
				FirstOf("byte", "short", "char", "int", "long", "float", "double", "boolean"),
				TestNot(LetterOrDigit()),
				Spacing()
				);
	}

	Rule Expression() {
		return Sequence(
				ConditionalExpression(),
				ZeroOrMore(AssignmentOperator(), ConditionalExpression())
				);
	}

	Rule AssignmentOperator() {
		return FirstOf(EQU, PLUSEQU, MINUSEQU, STAREQU, DIVEQU, ANDEQU, OREQU, HATEQU, MODEQU, SLEQU, SREQU, BSREQU);
	}

	Rule VariableDeclaratorId() {
		return Sequence(Identifier(), ZeroOrMore(Dim()));
	}

	Rule SingleElementAnnotationRest() {
		return Sequence(LPAR, ElementValue(), RPAR);
	}

	Rule Type() {
		return Sequence(FirstOf(BasicType(), ClassType()), ZeroOrMore(Dim()));
	}

	Rule AnnotationRest() {
		return FirstOf(NormalAnnotationRest(), SingleElementAnnotationRest());
	}

	Rule NormalAnnotationRest() {
		return Sequence(LPAR, Optional(ElementValuePairs()), RPAR);
	}

	Rule ElementValueArrayInitializer() {
		return Sequence(LWING, Optional(ElementValues()), Optional(COMMA), RWING);
	}

	Rule ElementValues() {
		return Sequence(ElementValue(), ZeroOrMore(COMMA, ElementValue()));
	}

	Rule ElementValuePairs() {
		return Sequence(ElementValuePair(), ZeroOrMore(COMMA, ElementValuePair()));
	}

	Rule ElementValuePair() {
		return Sequence(Identifier(), EQU, ElementValue());
	}

	Rule ElementValue() {
		return FirstOf(ConditionalExpression(), Annotation(), ElementValueArrayInitializer());
	}

	Rule ConditionalExpression() {
		return Sequence(
				ConditionalOrExpression(),
				ZeroOrMore(QUERY, Expression(), COLON, ConditionalOrExpression())
				);
	}

	Rule ConditionalOrExpression() {
		return Sequence(
				ConditionalAndExpression(),
				ZeroOrMore(OROR, ConditionalAndExpression())
				);
	}

	Rule ConditionalAndExpression() {
		return Sequence(
				InclusiveOrExpression(),
				ZeroOrMore(ANDAND, InclusiveOrExpression())
				);
	}

	Rule InclusiveOrExpression() {
		return Sequence(
				ExclusiveOrExpression(),
				ZeroOrMore(OR, ExclusiveOrExpression())
				);
	}

	Rule ExclusiveOrExpression() {
		return Sequence(
				AndExpression(),
				ZeroOrMore(HAT, AndExpression())
				);
	}

	Rule AndExpression() {
		return Sequence(
				EqualityExpression(),
				ZeroOrMore(AND, EqualityExpression())
				);
	}

	Rule EqualityExpression() {
		return Sequence(
				RelationalExpression(),
				ZeroOrMore(FirstOf(EQUAL, NOTEQUAL), RelationalExpression())
				);
	}

	Rule RelationalExpression() {
		return Sequence(
				ShiftExpression(),
				ZeroOrMore(
						FirstOf(
								Sequence(FirstOf(LE, GE, LT, GT), ShiftExpression()),
								Sequence(INSTANCEOF, ReferenceType())
								)
						)
				);
	}

	Rule ShiftExpression() {
		return Sequence(
				AdditiveExpression(),
				ZeroOrMore(FirstOf(SL, SR, BSR), AdditiveExpression())
				);
	}

	Rule AdditiveExpression() {
		return Sequence(
				MultiplicativeExpression(),
				ZeroOrMore(FirstOf(PLUS, MINUS), MultiplicativeExpression())
				);
	}

	Rule MultiplicativeExpression() {
		return Sequence(
				UnaryExpression(),
				ZeroOrMore(FirstOf(STAR, DIV, MOD), UnaryExpression())
				);
	}

	Rule UnaryExpression() {
		return FirstOf(
				Sequence(PrefixOp(), UnaryExpression()),
				Sequence(LPAR, Type(), RPAR, UnaryExpression()),
				Sequence(Primary(), ZeroOrMore(Selector()), ZeroOrMore(PostFixOp()))
				);
	}
	Rule Primary() {
		return FirstOf(
				MethodReferenceExpression(),
				LambdaExpression(),
				ParExpression(),
				Sequence(
						NonWildcardTypeArguments(),
						FirstOf(ExplicitGenericInvocationSuffix(), Sequence(THIS, Arguments()))
						),
				Sequence(THIS, Optional(Arguments())),
				Sequence(SUPER, SuperSuffix()),
				Literal(),
				Sequence(NEW, Creator()),
				Sequence(QualifiedIdentifier(), Optional(IdentifierSuffix())),
				Sequence(BasicType(), ZeroOrMore(Dim()), DOT, CLASS),
				Sequence(VOID, DOT, CLASS)
				);
	}

	Rule Creator() {
		return FirstOf(
				Sequence(Optional(NonWildcardTypeArguments()), CreatedName(), ClassCreatorRest()),
				Sequence(Optional(NonWildcardTypeArguments()), FirstOf(ClassType(), BasicType()), ArrayCreatorRest())
				);
	}

	Rule CreatedName() {
		return Sequence(
				Identifier(), Optional(NonWildcardTypeArguments()),
				ZeroOrMore(DOT, Identifier(), Optional(NonWildcardTypeArguments()))
				);
	}

	Rule InnerCreator() {
		return Sequence(Identifier(), ClassCreatorRest());
	}

	// The following is more generous than JLS 15.10. According to that definition,
	// BasicType must be followed by at least one DimExpr or by ArrayInitializer.
	Rule ArrayCreatorRest() {
		return Sequence(
				LBRK,
				FirstOf(
						Sequence(RBRK, ZeroOrMore(Dim()), ArrayInitializer()),
						Sequence(Expression(), RBRK, ZeroOrMore(DimExpr()), ZeroOrMore(Dim()))
						)
				);
	}

	Rule ArrayInitializer() {
		return Sequence(
				LWING,
				Optional(
						VariableInitializer(),
						ZeroOrMore(COMMA, VariableInitializer())
						),
				Optional(COMMA),
				RWING
				);
	}

	Rule VariableInitializer() {
		return FirstOf(ArrayInitializer(), Expression());
	}

	Rule IdentifierSuffix() {
		return FirstOf(
				Sequence(LBRK,
						FirstOf(
								Sequence(RBRK, ZeroOrMore(Dim()), DOT, CLASS),
								Sequence(Expression(), RBRK)
								)
						),
				Arguments(),
				Sequence(
						DOT,
						FirstOf(
								CLASS,
								ExplicitGenericInvocation(),
								THIS,
								Sequence(SUPER, Arguments()),
								Sequence(NEW, Optional(NonWildcardTypeArguments()), InnerCreator())
								)
						)
				);
	}

	Rule Literal() {
		return Sequence(
				FirstOf(
						FloatLiteral(),
						IntegerLiteral(),
						CharLiteral(),
						StringLiteral(),
						Sequence("true", TestNot(LetterOrDigit())),
						Sequence("false", TestNot(LetterOrDigit())),
						Sequence("null", TestNot(LetterOrDigit()))
						),
				Spacing()
				);
	}

	@SuppressSubnodes
	Rule IntegerLiteral() {
		return Sequence(FirstOf(HexNumeral(), OctalNumeral(), DecimalNumeral()), Optional(AnyOf("lL")));
	}

	@SuppressSubnodes
	Rule DecimalNumeral() {
		return FirstOf('0', Sequence(CharOneToNine(), ZeroOrMore(Digit())));
	}

	Rule CharOneToNine() {
		return CharRange('1', '9');
	}

	@SuppressSubnodes

	@MemoMismatches
	Rule HexNumeral() {
		return Sequence('0', IgnoreCase('x'), OneOrMore(HexDigit()));
	}

	Rule HexDigit() {
		return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), Digit());
	}

	@SuppressSubnodes
	Rule OctalNumeral() {
		return Sequence('0', OneOrMore(CharRange('0', '7')));
	}

	Rule FloatLiteral() {
		return FirstOf(HexFloat(), DecimalFloat());
	}

	@SuppressSubnodes
	Rule DecimalFloat() {
		return FirstOf(
				Sequence(OneOrMore(Digit()), '.', ZeroOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
				Sequence('.', OneOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(Digit()), Exponent(), Optional(AnyOf("fFdD"))),
				Sequence(OneOrMore(Digit()), Optional(Exponent()), AnyOf("fFdD"))
				);
	}

	Rule Exponent() {
		return Sequence(AnyOf("eE"), Optional(AnyOf("+-")), OneOrMore(Digit()));
	}

	@SuppressSubnodes
	Rule HexFloat() {
		return Sequence(HexSignificant(), BinaryExponent(), Optional(AnyOf("fFdD")));
	}

	Rule HexSignificant() {
		return FirstOf(
				Sequence(FirstOf("0x", "0X"), ZeroOrMore(HexDigit()), '.', OneOrMore(HexDigit())),
				Sequence(HexNumeral(), Optional('.'))
				);
	}

	Rule BinaryExponent() {
		return Sequence(AnyOf("pP"), Optional(AnyOf("+-")), OneOrMore(Digit()));
	}

	Rule CharLiteral() {
		return Sequence(
				'\'',
				FirstOf(Escape(), Sequence(TestNot(AnyOf("'\\")), ANY)).suppressSubnodes(),
				'\''
				);
	}

	Rule StringLiteral() {
		return Sequence(
				'"',
				ZeroOrMore(
						FirstOf(
								Escape(),
								Sequence(TestNot(AnyOf("\r\n\"\\")), ANY)
								)
						).suppressSubnodes(),
				'"'
				);
	}

	Rule Escape() {
		return Sequence('\\', FirstOf(AnyOf("btnfr\"\'\\"), OctalEscape(), UnicodeEscape()));
	}

	Rule OctalEscape() {
		return FirstOf(
				Sequence(CharRange('0', '3'), CharRange('0', '7'), CharRange('0', '7')),
				Sequence(CharRange('0', '7'), CharRange('0', '7')),
				CharRange('0', '7')
				);
	}

	Rule UnicodeEscape() {
		return Sequence(OneOrMore('u'), HexDigit(), HexDigit(), HexDigit(), HexDigit());
	}

	Rule ParExpression() {
		return Sequence(LPAR, Expression(), RPAR);
	}

	Rule PrefixOp() {
		return FirstOf(INC, DEC, BANG, TILDA, PLUS, MINUS);
	}

	Rule PostFixOp() {
		return FirstOf(INC, DEC);
	}

	Rule Selector() {
		return FirstOf(
				Sequence(DOT, Identifier(), Optional(Arguments())),
				Sequence(DOT, ExplicitGenericInvocation()),
				Sequence(DOT, THIS),
				Sequence(DOT, SUPER, SuperSuffix()),
				Sequence(DOT, NEW, Optional(NonWildcardTypeArguments()), InnerCreator()),
				DimExpr()
				);
	}

	Rule DimExpr() {
		return Sequence(LBRK, Expression(), RBRK);
	}

	Rule ClassCreatorRest() {
		return Sequence(Arguments(), Optional(ClassBody()));
	}

	Rule ExplicitGenericInvocation() {
		return Sequence(NonWildcardTypeArguments(), ExplicitGenericInvocationSuffix());
	}

	Rule NonWildcardTypeArguments() {
		return Sequence(LPOINT, ReferenceType(), ZeroOrMore(COMMA, ReferenceType()), RPOINT);
	}

	Rule ExplicitGenericInvocationSuffix() {
		return FirstOf(
				Sequence(SUPER, SuperSuffix()),
				Sequence(Identifier(), Arguments())
				);
	}

	Rule SuperSuffix() {
		return FirstOf(Arguments(), Sequence(DOT, Identifier(), Optional(Arguments())));
	}

	Rule Arguments() {
		return Sequence(
				LPAR,
				Optional(Expression(), ZeroOrMore(COMMA, Expression())),
				RPAR
				);
	}


	Rule MethodReferenceExpression() {
		return Sequence(
				FirstOf(
						THIS,
						ClassType()),
				DOUBLECOLON,
				Identifier());
	}

	Rule LambdaExpression() {
		return Sequence(
				FormalParameterDeclsRest(),
				LAMBDA,
				Expression());
	}

	Rule Dim() {
		return Sequence(LBRK, RBRK);
	}

	Rule ClassType() {
		return Sequence(
				Identifier(), Optional(TypeArguments()),
				ZeroOrMore(DOT, Identifier(), Optional(TypeArguments()))
				);
	}

	Rule TypeArguments() {
		return Sequence(LPOINT, TypeArgument(), ZeroOrMore(COMMA, TypeArgument()), RPOINT);
	}

	Rule TypeArgument() {
		return FirstOf(
				ReferenceType(),
				Sequence(QUERY, Optional(FirstOf(EXTENDS, SUPER), ReferenceType()))
				);
	}

	Rule ReferenceType() {
		return FirstOf(
				Sequence(BasicType(), OneOrMore(Dim())),
				Sequence(ClassType(), ZeroOrMore(Dim()))
				);
	}

	Rule QualifiedIdentifier() {
		return Sequence(Identifier(), ZeroOrMore(DOT, Identifier()));
	}

	Rule Class() {
		return Sequence(CLASS, push("\npublic class"));
	}

	Rule ClassBody() {
		return Sequence(
				LWING,
				RWING,
				push(pop() + " {\n\n}\n")
				);
	}

	@SuppressSubnodes
	@MemoMismatches
	Rule Identifier() {
		return Sequence(
				Sequence(
						Letter(),
						ZeroOrMore(LetterOrDigit())),
				new Action<String>() {
					@Override
					public boolean run(org.parboiled.Context<String> context) {
						push(pop() + " " + context.getMatch());
						return true;
					};
				},
				Spacing()
				);
	}

	Rule Letter() {
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
	Rule Keyword(String keyword) {
		return Terminal(keyword, LetterOrDigit());
	}

	Rule CharUpperAToUpperZ() {
		return CharRange('A', 'Z');
	}

	Rule CharLowerAToLowerZ() {
		return CharRange('a', 'z');
	}

	@MemoMismatches
	Rule LetterOrDigit() {
		return FirstOf(CharLowerAToLowerZ(), CharUpperAToUpperZ(), Digit(), '_', '$');
	}

	Rule Digit() {
		return ranges.ZeroToNine();
	}

	@SuppressNode
	@DontLabel
	Rule Terminal(String string) {
		return Sequence(string, Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	@DontLabel
	Rule Terminal(String string, Rule mustNotFollow) {
		return Sequence(string, TestNot(mustNotFollow), Spacing()).label('\'' + string + '\'');
	}

	@SuppressNode
	Rule Spacing() {
		return ZeroOrMore(FirstOf(

				// whitespace
				OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

				// traditional comment
				Sequence("/*", ZeroOrMore(TestNot("*/"), ANY), "*/"),

				// end of line comment
				Sequence(
						"//",
						ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
						FirstOf("\r\n", '\r', '\n', EOI)
						)
				));
	}

}