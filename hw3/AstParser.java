/* Generated By:JavaCC: Do not edit this line. AstParser.java */
import java.util.*;
import java.io.*;
import ast.*;

public class AstParser implements AstParserConstants {
  public static void main(String [] args) throws Exception {
    if (args.length == 1) {
      FileInputStream stream = new FileInputStream(args[0]);
      Ast.Program p = new AstParser(stream).Program();
      stream.close();
      System.out.print(p);
    } else {
      System.out.println("Need one file name as command-line argument.");
    }
  }

// Parsing routines needed ...





// Program -> {ClassDecl}
//
  static final public Ast.Program Program() throws ParseException {
 LinkedList<Ast.ClassDecl> cd = new LinkedList(); Ast.ClassDecl cl; Ast.Program p;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 28:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      cl = ClassDecl();
                     cd.add(cl);
    }
    jj_consume_token(0);
                                               p = new Ast.Program(cd);
    {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

//ClassDecl  -> "ClassDecl" <Id> [<Id>] {VarDecl} {MethodDecl}
  static final public Ast.ClassDecl ClassDecl() throws ParseException {
 Token tkn1; Token tkn2 = null; LinkedList<Ast.VarDecl> vd = new LinkedList(); Ast.VarDecl v; LinkedList<Ast.MethodDecl> ml = new LinkedList();
 Ast.MethodDecl m; Ast.ClassDecl cd; String s = null;
    jj_consume_token(28);
    tkn1 = jj_consume_token(Id);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Id:
      tkn2 = jj_consume_token(Id);
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 30:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      v = VarDecl();
                                                        vd.add(v);
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 29:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      m = MethodDecl();
                                                                                          ml.add(m);
    }
   if (tkn2 != null)
    s = tkn2.image;
  {if (true) return new Ast.ClassDecl(tkn1.image, s, vd, ml);}
    throw new Error("Missing return statement in function");
  }

//MethodDecl -> "MethodDecl" Type <Id> "(" {Param} ")" {VarDecl} {Stmt}
  static final public Ast.MethodDecl MethodDecl() throws ParseException {
 Ast.Type t; Token tkn; LinkedList<Ast.Param> pl = new LinkedList(); Ast.Param p; LinkedList<Ast.VarDecl> vd = new LinkedList(); Ast.VarDecl v; LinkedList<Ast.Stmt> sl = new LinkedList(); Ast.Stmt s;
    jj_consume_token(29);
    t = Type();
    tkn = jj_consume_token(Id);
    jj_consume_token(49);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 49:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_4;
      }
      p = Param();
                                                      pl.add(p);
    }
    jj_consume_token(50);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 30:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      v = VarDecl();
                                                                                         vd.add(v);
    }
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 17:
      case 19:
      case 20:
      case 27:
      case 33:
      case 34:
      case 51:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_6;
      }
      s = Stmt();
                                                                                                                     sl.add(s);
    }
   {if (true) return new Ast.MethodDecl(t, tkn.image, pl, vd, sl);}
    throw new Error("Missing return statement in function");
  }

//VarDecl    -> "VarDecl" Type <Id> Exp
  static final public Ast.VarDecl VarDecl() throws ParseException {
 Ast.Type t; Token tkn; Ast.Exp e;
    jj_consume_token(30);
    t = Type();
    tkn = jj_consume_token(Id);
    e = Exp();
  {if (true) return new Ast.VarDecl(t, tkn.image, e);}
    throw new Error("Missing return statement in function");
  }

//Param      -> "(" "Param" Type <Id> ")"
  static final public Ast.Param Param() throws ParseException {
 Ast.Type t; Token tkn;
    jj_consume_token(49);
    jj_consume_token(42);
    t = Type();
    tkn = jj_consume_token(Id);
    jj_consume_token(50);
  {if (true) return new Ast.Param(t, tkn.image);}
    throw new Error("Missing return statement in function");
  }

//Type   -> "void" 
//       | "IntType" 
//       | "BoolType" 
//       | "(" "ObjType" <Id> ")"
//       | "(" "ArrayType" Type ")"
  static final public Ast.Type Type() throws ParseException {
 Ast.Type t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 11:
      jj_consume_token(11);
         {if (true) return null;}
      break;
    case 12:
      jj_consume_token(12);
              {if (true) return new Ast.IntType();}
      break;
    case 14:
      jj_consume_token(14);
               {if (true) return new Ast.BoolType();}
      break;
    case 49:
      jj_consume_token(49);
      t = Type1();
                    {if (true) return t;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Ast.Type Type1() throws ParseException {
 Token tkn; Ast.Type t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 31:
      jj_consume_token(31);
      tkn = jj_consume_token(Id);
      jj_consume_token(50);
                            {if (true) return new Ast.ObjType(tkn.image);}
      break;
    case 32:
      jj_consume_token(32);
      t = Type();
      jj_consume_token(50);
                                {if (true) return new Ast.ArrayType(t);}
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//Stmt   -> "{" {Stmt} "}"
//       |  "Assign" Exp Exp
//       |  "CallStmt" Exp <Id> "(" {Exp} ")"
//       |  "If" Exp Stmt [ "Else" Stmt ]  
//       |  "While" Exp Stmt
//       |  "Print" (Exp | <StrLit>)
//       |  "Return" Exp
  static final public Ast.Stmt Stmt() throws ParseException {
 LinkedList<Ast.Stmt> sl = new LinkedList(); Ast.Stmt s, s1 = null; Ast.Exp e1, e2; LinkedList<Ast.Exp> el = new LinkedList(); Token tkn = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
        case 19:
        case 20:
        case 27:
        case 33:
        case 34:
        case 51:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_7;
        }
        s = Stmt();
                  sl.add(s);
      }
      jj_consume_token(52);
                                      {if (true) return new Ast.Block(sl);}
      break;
    case 33:
      jj_consume_token(33);
      e1 = Exp();
      e2 = Exp();
                                     {if (true) return new Ast.Assign(e1, e2);}
      break;
    case 34:
      jj_consume_token(34);
      e1 = Exp();
      tkn = jj_consume_token(Id);
      jj_consume_token(49);
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 16:
        case IntLit:
        case BoolLit:
        case Id:
        case 49:
          ;
          break;
        default:
          jj_la1[10] = jj_gen;
          break label_8;
        }
        e2 = Exp();
                                                      el.add(e2);
      }
      jj_consume_token(50);
                                                                            {if (true) return new Ast.CallStmt(e1, tkn.image, el);}
      break;
    case 17:
      jj_consume_token(17);
      e1 = Exp();
      s = Stmt();
      if (jj_2_1(2)) {
        jj_consume_token(18);
        s1 = Stmt();
      } else {
        ;
      }
                                                                     {if (true) return new Ast.If(e1, s, s1);}
      break;
    case 19:
      jj_consume_token(19);
      e1 = Exp();
      s = Stmt();
                                   {if (true) return new Ast.While(e1, s);}
      break;
    case 27:
      jj_consume_token(27);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case IntLit:
      case BoolLit:
      case Id:
      case 49:
        e1 = Exp();
                         {if (true) return new Ast.Print(e1);}
        break;
      case StrLit:
        tkn = jj_consume_token(StrLit);
                                                                       {if (true) return new Ast.Print(new Ast.StrLit(tkn.image.substring(1, tkn.image.length()-1)));}
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    case 20:
      jj_consume_token(20);
      e1 = Exp();
                          {if (true) return new Ast.Return(e1);}
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//Exp    -> "(" ")"
//       |  "(" "Binop" BOP Exp Exp ")"
//       |  "(" "Unop" UOP Exp ")"
//       |  "(" "Call" Exp <Id> "(" {Exp} ")" ")"
//       |  "(" "NewObj" <Id> ")"
//       |  "(" "Field" Exp <Id> ")"
//       |  "(" "NewArray" Type <IntLit> ")"
//       |  "(" "ArrayElm" Exp Exp ")"
//       |  "This"
//       |  <Id>
//       |  <IntLit>
//       |  <BoolLit>
  static final public Ast.Exp Exp() throws ParseException {
 Token tkn = null; boolean tf; Ast.Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 49:
      jj_consume_token(49);
      e = Exp1();
                 {if (true) return e;}
      break;
    case 16:
      jj_consume_token(16);
            {if (true) return new Ast.This();}
      break;
    case Id:
      tkn = jj_consume_token(Id);
                 {if (true) return new Ast.Id(tkn.image);}
      break;
    case IntLit:
      tkn = jj_consume_token(IntLit);
                     {if (true) return new Ast.IntLit(Integer.parseInt(tkn.image));}
      break;
    case BoolLit:
      tkn = jj_consume_token(BoolLit);
                      if(tkn.image.equals("true"))
                        tf = true;
                      else
                                            tf = false;
     {if (true) return new Ast.BoolLit(tf);}
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Ast.Exp Exp1() throws ParseException {
 Ast.BOP bop; Ast.Exp e1; Ast.Exp e2; LinkedList<Ast.Exp> el = new LinkedList(); Ast.UOP uop; Token tkn; Ast.Type t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 50:
      jj_consume_token(50);
       {if (true) return null;}
      break;
    case 35:
      jj_consume_token(35);
      bop = BOP();
      e1 = Exp();
      e2 = Exp();
      jj_consume_token(50);
                                                     {if (true) return new Ast.Binop(bop, e1, e2);}
      break;
    case 36:
      jj_consume_token(36);
      uop = UOP();
      e1 = Exp();
      jj_consume_token(50);
                                         {if (true) return new Ast.Unop(uop, e1);}
      break;
    case 37:
      jj_consume_token(37);
      e1 = Exp();
      tkn = jj_consume_token(Id);
      jj_consume_token(49);
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 16:
        case IntLit:
        case BoolLit:
        case Id:
        case 49:
          ;
          break;
        default:
          jj_la1[14] = jj_gen;
          break label_9;
        }
        e2 = Exp();
                                                   el.add(e2);
      }
      jj_consume_token(50);
      jj_consume_token(50);
                                                                             {if (true) return new Ast.Call(e1, tkn.image, el);}
      break;
    case 38:
      jj_consume_token(38);
      tkn = jj_consume_token(Id);
      jj_consume_token(50);
                               {if (true) return new Ast.NewObj(tkn.image);}
      break;
    case 39:
      jj_consume_token(39);
      e1 = Exp();
      tkn = jj_consume_token(Id);
      jj_consume_token(50);
                                         {if (true) return new Ast.Field(e1, tkn.image);}
      break;
    case 40:
      jj_consume_token(40);
      t = Type();
      tkn = jj_consume_token(IntLit);
      jj_consume_token(50);
                                                {if (true) return new Ast.NewArray(t, Integer.parseInt(tkn.image));}
      break;
    case 41:
      jj_consume_token(41);
      e1 = Exp();
      e2 = Exp();
      jj_consume_token(50);
                                            {if (true) return new Ast.ArrayElm(e1, e2);}
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//BOP    -> "+"  | "-"  | "*" | "/"  | "&&" | "||"
//       |  "==" | "!=" | "<" | "<=" | ">"  | ">=" 
  static final public Ast.BOP BOP() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 53:
      jj_consume_token(53);
       {if (true) return Ast.BOP.ADD;}
      break;
    case 54:
      jj_consume_token(54);
                                   {if (true) return Ast.BOP.SUB;}
      break;
    case 55:
      jj_consume_token(55);
                                                               {if (true) return Ast.BOP.MUL;}
      break;
    case 56:
      jj_consume_token(56);
                                                                                           {if (true) return Ast.BOP.DIV;}
      break;
    case 57:
      jj_consume_token(57);
         {if (true) return Ast.BOP.AND;}
      break;
    case 58:
      jj_consume_token(58);
                                      {if (true) return Ast.BOP.OR;}
      break;
    case 59:
      jj_consume_token(59);
                                                                   {if (true) return Ast.BOP.EQ;}
      break;
    case 60:
      jj_consume_token(60);
        {if (true) return Ast.BOP.NE;}
      break;
    case 61:
      jj_consume_token(61);
                                   {if (true) return Ast.BOP.LT;}
      break;
    case 62:
      jj_consume_token(62);
         {if (true) return Ast.BOP.LE;}
      break;
    case 63:
      jj_consume_token(63);
                                    {if (true) return Ast.BOP.GT;}
      break;
    case 64:
      jj_consume_token(64);
                                                                 {if (true) return Ast.BOP.GE;}
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//UOP    -> "-" | "!"
  static final public Ast.UOP UOP() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 54:
      jj_consume_token(54);
       {if (true) return Ast.UOP.NEG;}
      break;
    case 65:
      jj_consume_token(65);
                                   {if (true) return Ast.UOP.NOT;}
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3_1() {
    if (jj_scan_token(18)) return true;
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3R_17() {
    if (jj_scan_token(20)) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_scan_token(27)) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(19)) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    if (jj_scan_token(17)) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_scan_token(34)) return true;
    return false;
  }

  static private boolean jj_3R_12() {
    if (jj_scan_token(33)) return true;
    return false;
  }

  static private boolean jj_3R_10() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_11()) {
    jj_scanpos = xsp;
    if (jj_3R_12()) {
    jj_scanpos = xsp;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) {
    jj_scanpos = xsp;
    if (jj_3R_17()) return true;
    }
    }
    }
    }
    }
    }
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_scan_token(51)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public AstParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[18];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
      jj_la1_init_2();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x10000000,0x0,0x40000000,0x20000000,0x0,0x40000000,0x81a0000,0x5800,0x80000000,0x81a0000,0x10000,0x10000,0x81a0000,0x10000,0x10000,0x0,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x10000,0x0,0x0,0x20000,0x0,0x80006,0x20000,0x1,0x80006,0x31800,0x33800,0x80006,0x31800,0x31800,0x403f8,0xffe00000,0x400000,};
   }
   private static void jj_la1_init_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x2,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public AstParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AstParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new AstParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public AstParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AstParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public AstParser(AstParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(AstParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        exists = true;
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[66];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 18; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 66; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
