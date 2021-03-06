/* Generated By:JavaCC: Do not edit this line. mjLLGrammar.java */
import java.io.*;

public class mjLLGrammar implements mjLLGrammarConstants {
  public static void main(String [] args) {
    try {
      if (args.length == 1) {
        FileInputStream stream = new FileInputStream(args[0]);
        new mjLLGrammar(stream).Program();
        stream.close();
      } else {
        System.out.println("Need a file name as command-line argument.");
      }
    } catch (TokenMgrError e) {
      System.err.println(e);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

//
// PARSER SECTION ---------------------------------------------------------------
//

// Program -> {ClassDecl}
//
  static final public void Program() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 7:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      ClassDecl();
    }
    jj_consume_token(0);
  }

// ClassDecl -> "class" <ID> ["extends" <ID>] "{" {VarDecl} {MethodDecl} "}"
//
  static final public void ClassDecl() throws ParseException {
    jj_consume_token(7);
    jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 8:
      jj_consume_token(8);
      jj_consume_token(ID);
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    jj_consume_token(55);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 12:
      case 13:
      case 14:
      case ID:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      VarDecl();
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 10:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      MethodDecl();
    }
    jj_consume_token(56);
  }

//MethodDecl -> "public" MethodDecl MethodDecl2	
//				
//MethodDecl1 -> ExtType <ID> "(" [Param {"," Param}] ")" 
//           |   "static" "void" "main" "(" "String" "[" "]" <ID> ")"
//
//MethodDecl2 -> "{" {VarDecl} {Stmt} "}"
//
//
  static final public void MethodDecl() throws ParseException {
    jj_consume_token(10);
    MethodDecl1();
    MethodDecl2();
  }

  static final public void MethodDecl1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 11:
    case 12:
    case 13:
    case 14:
    case ID:
      ExtType();
      jj_consume_token(ID);
      jj_consume_token(51);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 12:
      case 13:
      case 14:
      case ID:
        Param();
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
          jj_consume_token(49);
          Param();
        }
        break;
      default:
        jj_la1[5] = jj_gen;
        ;
      }
      jj_consume_token(52);
      break;
    case 57:
      jj_consume_token(57);
      jj_consume_token(11);
      jj_consume_token(21);
      jj_consume_token(51);
      jj_consume_token(24);
      jj_consume_token(53);
      jj_consume_token(54);
      jj_consume_token(ID);
      jj_consume_token(52);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void MethodDecl2() throws ParseException {
    jj_consume_token(55);
    label_5:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_5;
      }
      VarDecl();
    }
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case 17:
      case 19:
      case 20:
      case 25:
      case ID:
      case 55:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_6;
      }
      Stmt();
    }
    jj_consume_token(56);
  }

// Param -> Type <ID> 
//
  static final public void Param() throws ParseException {
    Type();
    jj_consume_token(ID);
  }

// VarDecl -> Type <ID> ["=" InitExpr] ";"
//  
  static final public void VarDecl() throws ParseException {
    Type();
    jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 47:
      jj_consume_token(47);
      InitExpr();
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    jj_consume_token(48);
  }

// ExtType -> Type | "void"
//
  static final public void ExtType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
    case 13:
    case 14:
    case ID:
      Type();
      break;
    case 11:
      jj_consume_token(11);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

//Type       -> BasicType Type1                            
//           |  <ID>                          // object type
//
//Type1       -> ["[" "]"]                      // array type
//
  static final public void Type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
    case 13:
    case 14:
      BasicType();
      Type1();
      break;
    case ID:
      jj_consume_token(ID);
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Type1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 53:
      jj_consume_token(53);
      jj_consume_token(54);
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
  }

// BasicType -> "int" | "double" | "boolean"
//
  static final public void BasicType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
      jj_consume_token(12);
      break;
    case 13:
      jj_consume_token(13);
      break;
    case 14:
      jj_consume_token(14);
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

//Stmt     ->   "{" {Stmt} "}"                         // stmt block
//           |  ExtId Stmt1
//           |  "if" "(" Expr ")" Stmt ["else" Stmt]    
//           |  "while" "(" Expr ")" Stmt               
//           |  "System" "." "out" "." "println" 
//                "(" [PrArg] ")" ";"                
//           |  "return" [Expr] ";"                     
//
//		   
//Stmt1    -> "(" [Args] ")" ";"            // call stmt
//           | Lvalue1  "=" InitExpr ";"    // assignment
//
  static final public void Stmt() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 55:
      jj_consume_token(55);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 16:
        case 17:
        case 19:
        case 20:
        case 25:
        case ID:
        case 55:
          ;
          break;
        default:
          jj_la1[13] = jj_gen;
          break label_7;
        }
        Stmt();
      }
      jj_consume_token(56);
      break;
    case 16:
    case ID:
      ExtId();
      Stmt1();
      break;
    case 17:
      jj_consume_token(17);
      jj_consume_token(51);
      Expr();
      jj_consume_token(52);
      Stmt();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 18:
        jj_consume_token(18);
        Stmt();
        break;
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      break;
    case 19:
      jj_consume_token(19);
      jj_consume_token(51);
      Expr();
      jj_consume_token(52);
      Stmt();
      break;
    case 25:
      jj_consume_token(25);
      jj_consume_token(50);
      jj_consume_token(26);
      jj_consume_token(50);
      jj_consume_token(27);
      jj_consume_token(51);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case 22:
      case 23:
      case INTLIT:
      case DBLLIT:
      case STRLIT:
      case ID:
      case 35:
      case 40:
      case 51:
        PrArg();
        break;
      default:
        jj_la1[15] = jj_gen;
        ;
      }
      jj_consume_token(52);
      jj_consume_token(48);
      break;
    case 20:
      jj_consume_token(20);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case 22:
      case 23:
      case INTLIT:
      case DBLLIT:
      case ID:
      case 35:
      case 40:
      case 51:
        Expr();
        break;
      default:
        jj_la1[16] = jj_gen;
        ;
      }
      jj_consume_token(48);
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Stmt1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case 22:
      case 23:
      case INTLIT:
      case DBLLIT:
      case ID:
      case 35:
      case 40:
      case 51:
        Args();
        break;
      default:
        jj_la1[18] = jj_gen;
        ;
      }
      jj_consume_token(52);
      jj_consume_token(48);
      break;
    case 47:
    case 53:
      Lvalue1();
      jj_consume_token(47);
      InitExpr();
      jj_consume_token(48);
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

// Args -> Expr {"," Expr}
//
  static final public void Args() throws ParseException {
    Expr();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 49:
        ;
        break;
      default:
        jj_la1[20] = jj_gen;
        break label_8;
      }
      jj_consume_token(49);
      Expr();
    }
  }

// PrArg -> Expr | <STRLIT>
//
  static final public void PrArg() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 16:
    case 22:
    case 23:
    case INTLIT:
    case DBLLIT:
    case ID:
    case 35:
    case 40:
    case 51:
      Expr();
      break;
    case STRLIT:
      jj_consume_token(STRLIT);
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

//InitExpr   -> "new" InitExpr1
//           |  Expr 
//
//InitExpr1   -> BasicType "[" <INTLIT> "]"    // new array
//           |  <ID> "(" ")"                  // new object 
//
  static final public void InitExpr() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 15:
      jj_consume_token(15);
      InitExpr1();
      break;
    case 16:
    case 22:
    case 23:
    case INTLIT:
    case DBLLIT:
    case ID:
    case 35:
    case 40:
    case 51:
      Expr();
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void InitExpr1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
    case 13:
    case 14:
      BasicType();
      jj_consume_token(53);
      jj_consume_token(INTLIT);
      jj_consume_token(54);
      break;
    case ID:
      jj_consume_token(ID);
      jj_consume_token(51);
      jj_consume_token(52);
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

//Expr        -> Or()
//					
//Term        -> Literal()
//            | ExtId() Term1()	
//
//Term1       -> "(" [Args()] ")"          // method call 			
//            |  Lvalue1()
//
//paren       -> paren1() Term()
//
//
//paren1      -> ["(" Expr() ")"  ]
//
//Neg         -> ["-"] paren()
//
//Not         -> ["!"] Neg()
//			
//Mult        -> Not() Mult1() 
//
//Mult1       -> ["*" Not() Mult1()] 
//
//                      
//Div         -> Mult() Div1()
//
//            	
//Div1        -> ["/" Mult() Div1()]
//
//            					
//Plus        -> Div() Plus1()
//
//					
//Plus1       -> ["+" Div() Plus1()]
//
//	
//Minus       -> Plus() Minus1()
//
//          
//Minus1      -> ["-" Plus() Minus1()]
//
//		
//Equal       -> Minus() Equal1()
//
//			
//Equal1      -> ["==" Minus() Equal1()]
//
//  
//NotEqual    -> Equal() NotEqual1()
//
//		
//NotEqual1   -> ["!=" Equal() NotEqual1()]
//
//
//Less        -> NotEqual() Less1()
//	
//
//Less1       -> ["<" NotEqual() Less1()]
//
//
//LessOrEqual -> Less() LessOrEqual1()
//
//		
//LessOrEqual1 -> ["<=" Less() LessOrEqual1()]
//
//
//Greater     -> LessOrEqual() Greater1()
//
//			
//Greater1    -> [">" LessOrEqual() Greater1()]
//
//            
//GreaterOrEqual  -> Greater() GreaterOrEqual1()
//
//
//GreaterOrEqual1 -> [">=" Greater() GreaterOrEqual1()]
//
//                
//And()       ->GreaterOrEqual And1
//
//		
//And1        ->["&&" GreaterOrEqual() And1()]
//
//Or          ->And() Or1()
//
//Or1         ->["||" And() Or1()] 	
  static final public void Expr() throws ParseException {
    Or();
  }

  static final public void Term() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 22:
    case 23:
    case INTLIT:
    case DBLLIT:
      Literal();
      break;
    case 16:
    case ID:
      ExtId();
      Term1();
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Term1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
      case 22:
      case 23:
      case INTLIT:
      case DBLLIT:
      case ID:
      case 35:
      case 40:
      case 51:
        Args();
        break;
      default:
        jj_la1[25] = jj_gen;
        ;
      }
      jj_consume_token(52);
      break;
    default:
      jj_la1[26] = jj_gen;
      Lvalue1();
    }
  }

  static final public void paren() throws ParseException {
    paren1();
    Term();
  }

  static final public void paren1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
      Expr();
      jj_consume_token(52);
      break;
    default:
      jj_la1[27] = jj_gen;
      ;
    }
  }

  static final public void Neg() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 35:
      jj_consume_token(35);
      break;
    default:
      jj_la1[28] = jj_gen;
      ;
    }
    paren();
  }

  static final public void Not() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 40:
      jj_consume_token(40);
      break;
    default:
      jj_la1[29] = jj_gen;
      ;
    }
    Neg();
  }

  static final public void Mult() throws ParseException {
    Not();
    Mult1();
  }

  static final public void Mult1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 36:
      jj_consume_token(36);
      Not();
      Mult1();
      break;
    default:
      jj_la1[30] = jj_gen;
      ;
    }
  }

  static final public void Div() throws ParseException {
    Mult();
    Div1();
  }

  static final public void Div1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 37:
      jj_consume_token(37);
      Mult();
      Div1();
      break;
    default:
      jj_la1[31] = jj_gen;
      ;
    }
  }

  static final public void Plus() throws ParseException {
    Div();
    Plus1();
  }

  static final public void Plus1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 34:
      jj_consume_token(34);
      Div();
      Plus1();
      break;
    default:
      jj_la1[32] = jj_gen;
      ;
    }
  }

  static final public void Minus() throws ParseException {
    Plus();
    Minus1();
  }

  static final public void Minus1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 35:
      jj_consume_token(35);
      Plus();
      Minus1();
      break;
    default:
      jj_la1[33] = jj_gen;
      ;
    }
  }

  static final public void Equal() throws ParseException {
    Minus();
    Equal1();
  }

  static final public void Equal1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 41:
      jj_consume_token(41);
      Minus();
      Equal1();
      break;
    default:
      jj_la1[34] = jj_gen;
      ;
    }
  }

  static final public void NotEqual() throws ParseException {
    Equal();
    NotEqual1();
  }

  static final public void NotEqual1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 42:
      jj_consume_token(42);
      Equal();
      NotEqual1();
      break;
    default:
      jj_la1[35] = jj_gen;
      ;
    }
  }

  static final public void Less() throws ParseException {
    NotEqual();
    Less1();
  }

  static final public void Less1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 43:
      jj_consume_token(43);
      NotEqual();
      Less1();
      break;
    default:
      jj_la1[36] = jj_gen;
      ;
    }
  }

  static final public void LessOrEqual() throws ParseException {
    Less();
    LessOrEqual1();
  }

  static final public void LessOrEqual1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 44:
      jj_consume_token(44);
      Less();
      LessOrEqual1();
      break;
    default:
      jj_la1[37] = jj_gen;
      ;
    }
  }

  static final public void Greater() throws ParseException {
    LessOrEqual();
    Greater1();
  }

  static final public void Greater1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 45:
      jj_consume_token(45);
      LessOrEqual();
      Greater1();
      break;
    default:
      jj_la1[38] = jj_gen;
      ;
    }
  }

  static final public void GreaterOrEqual() throws ParseException {
    Greater();
    GreaterOrEqual1();
  }

  static final public void GreaterOrEqual1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 46:
      jj_consume_token(46);
      Greater();
      GreaterOrEqual1();
      break;
    default:
      jj_la1[39] = jj_gen;
      ;
    }
  }

  static final public void And() throws ParseException {
    GreaterOrEqual();
    And1();
  }

  static final public void And1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 38:
      jj_consume_token(38);
      GreaterOrEqual();
      And1();
      break;
    default:
      jj_la1[40] = jj_gen;
      ;
    }
  }

  static final public void Or() throws ParseException {
    And();
    Or1();
  }

  static final public void Or1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 39:
      jj_consume_token(39);
      And();
      Or1();
      break;
    default:
      jj_la1[41] = jj_gen;
      ;
    }
  }

//Lvalue     -> ExtId Lvalue1
//		   
//Lvalue1     -> ["[" Expr "]"]            // array element
//
  static final public void Lvalue() throws ParseException {
    ExtId();
    Lvalue1();
  }

  static final public void Lvalue1() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 53:
      jj_consume_token(53);
      Expr();
      jj_consume_token(54);
      break;
    default:
      jj_la1[42] = jj_gen;
      ;
    }
  }

// ExtId -> ["this" "."] <ID> {"." <ID>} 
//
  static final public void ExtId() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 16:
      jj_consume_token(16);
      jj_consume_token(50);
      break;
    default:
      jj_la1[43] = jj_gen;
      ;
    }
    jj_consume_token(ID);
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 50:
        ;
        break;
      default:
        jj_la1[44] = jj_gen;
        break label_9;
      }
      jj_consume_token(50);
      jj_consume_token(ID);
    }
  }

// Literal -> <INTLIT> | <DBLLIT> | "true" | "false"
//
  static final public void Literal() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTLIT:
      jj_consume_token(INTLIT);
      break;
    case DBLLIT:
      jj_consume_token(DBLLIT);
      break;
    case 22:
      jj_consume_token(22);
      break;
    case 23:
      jj_consume_token(23);
      break;
    default:
      jj_la1[45] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

// BinOp -> "+" | "-" | "*" | "/" | "&&" | "||" 
//       |  "==" | "!=" | "<" | "<=" | ">" | ">=" 
//
  static final public void BinOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 34:
      jj_consume_token(34);
      break;
    case 35:
      jj_consume_token(35);
      break;
    case 36:
      jj_consume_token(36);
      break;
    case 37:
      jj_consume_token(37);
      break;
    case 38:
      jj_consume_token(38);
      break;
    case 39:
      jj_consume_token(39);
      break;
    case 41:
      jj_consume_token(41);
      break;
    case 42:
      jj_consume_token(42);
      break;
    case 43:
      jj_consume_token(43);
      break;
    case 44:
      jj_consume_token(44);
      break;
    case 45:
      jj_consume_token(45);
      break;
    case 46:
      jj_consume_token(46);
      break;
    default:
      jj_la1[46] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

// UnOp -> "-" | "!"
//
  static final public void UnOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 35:
      jj_consume_token(35);
      break;
    case 40:
      jj_consume_token(40);
      break;
    default:
      jj_la1[47] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(53)) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_15()) jj_scanpos = xsp;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(12)) {
    jj_scanpos = xsp;
    if (jj_scan_token(13)) {
    jj_scanpos = xsp;
    if (jj_scan_token(14)) return true;
    }
    }
    return false;
  }

  static private boolean jj_3R_12() {
    if (jj_3R_13()) return true;
    if (jj_3R_14()) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_12()) {
    jj_scanpos = xsp;
    if (jj_scan_token(33)) return true;
    }
    return false;
  }

  static private boolean jj_3R_10() {
    if (jj_3R_11()) return true;
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public mjLLGrammarTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[48];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80,0x100,0x7000,0x400,0x0,0x7000,0x7800,0x21b0000,0x0,0x7800,0x7000,0x0,0x7000,0x21b0000,0x40000,0xc0c10000,0xc0c10000,0x21b0000,0xc0c10000,0x0,0x0,0xc0c10000,0xc0c18000,0x7000,0xc0c10000,0xc0c10000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x10000,0x0,0xc0c00000,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x2,0x0,0x20000,0x2,0x2000002,0x800002,0x8000,0x2,0x2,0x200000,0x0,0x800002,0x0,0x8010b,0x8010a,0x800002,0x8010a,0x288000,0x20000,0x8010b,0x8010a,0x2,0x2,0x8010a,0x80000,0x80000,0x8,0x100,0x10,0x20,0x4,0x8,0x200,0x400,0x800,0x1000,0x2000,0x4000,0x40,0x80,0x200000,0x0,0x40000,0x0,0x7efc,0x108,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public mjLLGrammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public mjLLGrammar(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new mjLLGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public mjLLGrammar(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new mjLLGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public mjLLGrammar(mjLLGrammarTokenManager tm) {
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
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(mjLLGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 48; i++) jj_la1[i] = -1;
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
    boolean[] la1tokens = new boolean[58];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 48; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 58; i++) {
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
