// This is supporting software for CS322 Compilers and Language Design II
// Copyright (c) Portland State University
// 
// Static analysis for miniJava (W15) ((A starter version.)
//  1. Type-checking
//  2. Detecting missing return statement
//  3. (Optional) Detecting uninitialized variables
//
// (For CS321 Winter 2015 - Jingke Li)
//

import java.util.*;
import java.io.*;
import ast.*;

public class Checker {

  static class TypeException extends Exception {
    public TypeException(String msg) { super(msg); }
  }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  //------------------------------------------------------------------------------
  // ClassInfo
  //----------
  // For easy access to class hierarchies (i.e. finding parent's info).
  //
  static class ClassInfo {
    Ast.ClassDecl cdecl; 	// classDecl AST
    ClassInfo parent; 		// pointer to parent

    ClassInfo(Ast.ClassDecl cdecl, ClassInfo parent) { 
      this.cdecl = cdecl; 
      this.parent = parent; 
    }      

    // Return the name of this class 
    //
    String className() { return cdecl.nm; }

    // Given a method name, return the method's declaration
    // - if the method is not found in the current class, recursively
    //   search ancestor classes; return null if all fail
    //
    Ast.MethodDecl findMethodDecl(String mname) {
      for (Ast.MethodDecl mdecl: cdecl.mthds)
	if (mdecl.nm.equals(mname))
	  return mdecl;
      if (parent != null)
        return parent.findMethodDecl(mname);
      return null;
    }

    // Given a field name, return the field's declaration
    // - if the field is not found in the current class, recursively
    //   search ancestor classes; return null if all fail
    //
    Ast.VarDecl findFieldDecl(String fname) {
      for (Ast.VarDecl fdecl: cdecl.flds) {
	if (fdecl.nm.equals(fname))
	  return fdecl;
      }
      if (parent != null)
        return parent.findFieldDecl(fname);
      return null;
    }
  }

  //------------------------------------------------------------------------------
  // Global Variables
  // ----------------
  // For type-checking:
  // classEnv - an environment (a className-classInfo mapping) for class declarations
  // typeEnv - an environment (a var-type mapping) for a method's params and local vars
  // thisCInfo - points to the current class's ClassInfo
  // thisMDecl - points to the current method's MethodDecl
  //
  // For other analyses:
  // (Define as you need.)
  //
  private static HashMap<String, ClassInfo> classEnv = new HashMap<String, ClassInfo>();
  private static HashMap<String, Ast.Type> typeEnv = new HashMap<String, Ast.Type>();
  private static ClassInfo thisCInfo = null;
  private static Ast.MethodDecl thisMDecl = null;

  //------------------------------------------------------------------------------
  // Type Compatibility Routines
  // ---------------------------

  // Returns true if tsrc is assignable to tdst.
  //
  // Pseudo code:
  //   if tdst==tsrc or both are the same basic type 
  //     return true
  //   else if both are ArrayType // structure equivalence
  //     return assignable result on their element types
  //   else if both are ObjType   // name equivalence 
  //     if (their class names match, or
  //         tdst's class name matches an tsrc ancestor's class name)
  //       return true
  //   else
  //     return false
  //
  private static boolean assignable(Ast.Type tdst, Ast.Type tsrc) throws Exception {

  
    if (tdst == tsrc
	|| (tdst instanceof Ast.IntType) && (tsrc instanceof Ast.IntType)
	|| (tdst instanceof Ast.BoolType) && (tsrc instanceof Ast.BoolType)) {
      return true;
    } 

    else if ((tdst instanceof Ast.ArrayType) && (tsrc instanceof Ast.ArrayType)) // structure equivalence
	{
	   Ast.ArrayType temp1 = (Ast.ArrayType)tdst;
	   Ast.ArrayType temp2 = (Ast.ArrayType)tsrc;
       return ((assignable(temp1.et, temp2.et)) ? true : false);
	}
	  
    else if ((tdst instanceof Ast.ObjType) && (tsrc instanceof Ast.ObjType))   // name equivalence 
	{
	   Ast.ObjType temp1 = (Ast.ObjType)tdst;
	   Ast.ObjType temp2 = (Ast.ObjType)tsrc;
      if (temp1.nm.equals(temp2.nm))
	  {
	  // need to do this later///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  //|| temp1.nm.equals(tsrc.parent.className())) 

        return true;
	  }
	  
	  return false; 
	}
	
    else
     return false;

  }
  
  // Returns true if t1 and t2 can be compared with "==" or "!=".
  //
  private static boolean comparable(Ast.Type t1, Ast.Type t2) throws Exception {
    return assignable(t1,t2) || assignable(t2,t1);
  }

  //------------------------------------------------------------------------------
  // The Main Routine
  //-----------------
  //
  public static void main(String [] args) throws Exception {
    try {
      if (args.length == 1) {
        FileInputStream stream = new FileInputStream(args[0]);
        Ast.Program p = new astParser(stream).Program();
        stream.close();
        check(p);
      } else {
	System.out.println("Need a file name as command-line argument.");
      } 
    } catch (TypeException e) {
      System.err.print(e + "\n");
    } catch (Exception e) {
      System.err.print(e + "\n");
    }
  }

  //------------------------------------------------------------------------------
  // Checker Routines for Individual AST Nodes
  //------------------------------------------

  // Program ---
  //  ClassDecl[] classes;
  //
  // 1. Sort ClassDecls, so parent will be visited before children.
  // 2. For each ClassDecl, create an ClassInfo (with link to parent if exists),
  //    and add to classEnv.
  // 3. Actual type-checking traversal over ClassDecls.
  //
  static void check(Ast.Program n) throws Exception {
    Ast.ClassDecl[] classes = topoSort(n.classes);
    for (Ast.ClassDecl c: classes) {
      ClassInfo pcinfo = (c.pnm == null) ? null : classEnv.get(c.pnm);
      classEnv.put(c.nm, new ClassInfo(c, pcinfo));
    }
    for (Ast.ClassDecl c: classes)
      check(c);
  }

  // Utility routine
  // - Sort ClassDecls based on parent-chidren relationship.
  //
  private static Ast.ClassDecl[] topoSort(Ast.ClassDecl[] classes) {
    List<Ast.ClassDecl> cl = new ArrayList<Ast.ClassDecl>();
    Vector<String> done = new Vector<String>();
    int cnt = classes.length;
    while (cnt > 0) {
      for (Ast.ClassDecl cd: classes)
	if (!done.contains(cd.nm)
	    && ((cd.pnm == null) || done.contains(cd.pnm))) {
	  cl.add(cd);
	  done.add(cd.nm);
	  cnt--;
	} 
    }
    return cl.toArray(new Ast.ClassDecl[0]);
  }

  // ClassDecl ---
  //  String nm, pnm;
  //  VarDecl[] flds;
  //  MethodDecl[] mthds;
  //
  //  1. Set thisCInfo pointer to this class's ClassInfo, and reset
  //     typeEnv to empty.
  //  2. Recursively check n.flds and n.mthds.
  //
  static void check(Ast.ClassDecl n) throws Exception {


    thisCInfo = new ClassInfo(n, thisCInfo);
    typeEnv.clear();

  
    for (Ast.VarDecl c: n.flds)
      check(c);
	  
	  
    for (Ast.MethodDecl c: n.mthds)
      check(c);
  
  }

  // MethodDecl ---
  //  Type t;
  //  String nm;
  //  Param[] params;
  //  VarDecl[] vars;
  //  Stmt[] stmts;
  //
  //  1. Set thisMDecl pointer and reset typeEnv to empty.
  //  2. Recursively check n.params, n.vars, and n.stmts.
  //  3. For each VarDecl, add a new name-type binding to typeEnv.
  //
  static void check(Ast.MethodDecl n) throws Exception {

    thisMDecl = n; 
    typeEnv.clear();
	
    for (Ast.Param c: n.params) {
      check(c);
	  typeEnv.put(c.nm, c.t);
	}
	    
    for (Ast.VarDecl c: n.vars){
      check(c);
	  typeEnv.put(c.nm, c.t); 
	}
	   
    for (Ast.Stmt c: n.stmts)
      check(c);

    typeEnv.put(n.nm, n.t);

  } 

  // Param ---
  //  Type t;
  //  String nm;
  //
  //  If n.t is ObjType, make sure its corresponding class exists.
  //
  static void check(Ast.Param n) throws Exception {

    if (n.t instanceof Ast.ObjType) {
	  if (!classEnv.containsKey(n.nm))
	    throw new TypeException("ObjType does not exist"); 
	}

  }

  // VarDecl ---
  //  Type t;
  //  String nm;
  //  Exp init;
  //
  //  1. If n.t is ObjType, make sure its corresponding class exists.
  //  2. If n.init exists, make sure it is assignable to the var.
  //
  static void check(Ast.VarDecl n) throws Exception {

    if (n.t instanceof Ast.ObjType) {
	  Ast.ObjType o = (Ast.ObjType)n.t;
	  if (!classEnv.containsKey(o.nm))
	    throw new TypeException("Class does not exist"); 
	}

	if (n.init != null)
     if (!assignable(n.t, check(n.init))) {
	   throw new TypeException("not assignable"); 
	 }
	
  }

  // STATEMENTS

  // Dispatch a generic check call to a specific check routine
  // 
  static void check(Ast.Stmt n) throws Exception {
    if (n instanceof Ast.Block) 	check((Ast.Block) n);
    else if (n instanceof Ast.Assign)   check((Ast.Assign) n);
    else if (n instanceof Ast.CallStmt) check((Ast.CallStmt) n);
    else if (n instanceof Ast.If) 	check((Ast.If) n);
    else if (n instanceof Ast.While)    check((Ast.While) n);
    else if (n instanceof Ast.Print)    check((Ast.Print) n);
    else if (n instanceof Ast.Return)   check((Ast.Return) n);
    else
      throw new TypeException("Illegal Ast Stmt: " + n);
  }

  // Block ---
  //  Stmt[] stmts;
  //
  static void check(Ast.Block n) throws Exception {

    for (Ast.Stmt c: n.stmts)
      check(c);

  }

  // Assign ---
  //  Exp lhs;
  //  Exp rhs;
  //
  //  Make sure n.rhs is assignable to n.lhs.
  //
  static void check(Ast.Assign n) throws Exception {
    assignable(check(n.lhs), check(n.rhs));
	

  }

  // CallStmt ---
  //  Exp obj; 
  //  String nm;
  //  Exp[] args;
  //
  //  1. Check that n.obj is ObjType and the corresponding class exists.
  //  2. Check that the method n.nm exists.
  //  3. Check that the count and types of the actual arguments match those of
  //     the formal parameters.
  //
  static void check(Ast.CallStmt n) throws Exception {

    if (check(n.obj) instanceof Ast.ObjType) {
	  boolean flag = false; 
	  Ast.MethodDecl temp = null;
	  
	  for (Ast.MethodDecl c: classEnv.get(((Ast.ObjType)check(n.obj)).nm).cdecl.mthds) {
	    if(c.nm.equals(n.nm)) {
		temp = c; 
		  flag = true;
		  break; 
		}
	  }
        
	  if (flag == false)
	    throw new TypeException("Method does not exist"); 
	}
	

	
    //  3. Check that the count and types of the actual arguments match those of
    //     the formal parameters.
	/*
	boolean flag = true; 
	if(Array.getLength(temp.params) != Array.getLength(n.args))// checks the number of parameters 
	  throw new TypeException("Incorrect number of params");
	  
	for (int i = 0; i < Array.getLength(temp.params) {
	    if(check(args[i]) instanceof Ast.IntType &&  temp.params[i].t instanceof Ast.IntType)  {
          
		}
		
	    else if(check(args[i]) instanceof Ast.BoolType && temp.params[i].t instanceof  Ast.BoolType)  {
          
		}
		
		
	    else if(check(args[i]) instanceof Ast.ArrayType && temp.params[i].t instanceof Ast.ArrayType)  {
          
		}
		
	    else if(check(args[i]) instanceof Ast.ObjType && temp.params[i].t instanceof Ast.ObjType)  {
          
		}
		
	  }
	*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  }

  // If ---
  //  Exp cond;
  //  Stmt s1, s2;
  //
  //  Make sure n.cond is boolean.
  //
  static void check(Ast.If n) throws Exception {

    if (!(check(n.cond) instanceof Ast.BoolType))
	  throw new TypeException("n.cond for if is not of type Boolean");

  }

  // While ---
  //  Exp cond;
  //  Stmt s;
  //
  //  Make sure n.cond is boolean.
  //
  static void check(Ast.While n) throws Exception {

    if (!(n.cond instanceof Ast.BoolLit))
	  throw new TypeException("n.cond for while is not of type Boolean");

  }
  
  // Print ---
  //  PrArg arg;
  //
  //  Make sure n.arg is integer, boolean, or string.
  //
  static void check(Ast.Print n) throws Exception {

    if (!((n.arg instanceof Ast.BoolLit) || (n.arg instanceof Ast.IntLit) || (n.arg instanceof Ast.StrLit) || n.arg == null)) {
	  for (Ast.VarDecl c: thisMDecl.vars) {
	    if (c.nm.equals(n.arg.toString())){
	      if (!((c.t == Ast.BoolType) || (c.t == Ast.IntType) || c.t == null))
	        throw new TypeException("n.cond for print is not of type Boolean, Integer, or String");
		  else
		    return;
	    }
	  }
	}
	  
   
   
   
   
    for (Ast.VarDecl c: thisMDecl.vars) {
	  if (c.nm.equals(n.arg.toString())){
	    if (!((c.t == Ast.BoolType) || (c.t == Ast.IntType) || c.t == null))
	      throw new TypeException("n.cond for print is not of type Boolean, Integer, or String");
	  }
	}
    
  
  
  


  }

  // Return ---  
  //  Exp val;
  //
  //  If n.val exists, make sure it matches the expected return type.
  //
  static void check(Ast.Return n) throws Exception { 

  
  if (check(n.val)  instanceof Ast.ArrayType){ 
  
    if (((Ast.ArrayType)check(n.val)).et instanceof Ast.IntType && thisMDecl.t instanceof Ast.IntType);
	
	else if (((Ast.ArrayType)check(n.val)).et instanceof Ast.BoolType && thisMDecl.t instanceof Ast.BoolType);
	
	//else if (check(n.val) instanceof Ast.StrLit && thisMDecl.t instanceof Ast.StrLit);
	
	else if (((Ast.ArrayType)check(n.val)).et instanceof Ast.ObjType && thisMDecl.t instanceof Ast.ObjType);
	
	else 
	  throw new TypeException("return value for array element is not of matching type");
  }

  else{ 
    
  
    if (check(n.val) instanceof Ast.IntType && thisMDecl.t instanceof Ast.IntType);
	
	else if (check(n.val) instanceof Ast.BoolType && thisMDecl.t instanceof Ast.BoolType);
	
	//else if (check(n.val) instanceof Ast.StrLit && thisMDecl.t instanceof Ast.StrLit);
	
	else if (check(n.val) instanceof Ast.ObjType && thisMDecl.t instanceof Ast.ObjType);
	
	else 
	  throw new TypeException("return value is not of matching type");
  }
  
  
  }

  // EXPRESSIONS

  // Dispatch a generic check call to a specific check routine
  //
  static Ast.Type check(Ast.Exp n) throws Exception {
    if (n instanceof Ast.Binop)    return check((Ast.Binop) n);
    if (n instanceof Ast.Unop)     return check((Ast.Unop) n);
    if (n instanceof Ast.Call)     return check((Ast.Call) n);
    if (n instanceof Ast.NewArray) return check((Ast.NewArray) n);
    if (n instanceof Ast.ArrayElm) return check((Ast.ArrayElm) n);
    if (n instanceof Ast.NewObj)   return check((Ast.NewObj) n);
    if (n instanceof Ast.Field)    return check((Ast.Field) n);
    if (n instanceof Ast.Id)	   return check((Ast.Id) n);
    if (n instanceof Ast.This)     return check((Ast.This) n);
    if (n instanceof Ast.IntLit)   return check((Ast.IntLit) n);
    if (n instanceof Ast.BoolLit)  return check((Ast.BoolLit) n);
    throw new TypeException("Exp node not recognized: " + n);
  }

  // Binop ---
  //  BOP op;
  //  Exp e1,e2;
  //
  //  Make sure n.e1's and n.e2's types are legal with respect to n.op.
  //
  static Ast.Type check(Ast.Binop n) throws Exception {

    if (check(n.e1) instanceof Ast.ArrayType && (check(n.e2) instanceof Ast.ArrayType)){ 
	    if (n.op == Ast.BOP.EQ || n.op == Ast.BOP.NE) {
	  
	    if (!((((Ast.ArrayType)check(n.e1)).et instanceof Ast.ArrayType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.ArrayType)
	       || ((Ast.ArrayType)check(n.e1)).et instanceof Ast.BoolType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.BoolType
	       || ((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType
	       || ((Ast.ArrayType)check(n.e1)).et instanceof Ast.ObjType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.ObjType)) {
	      throw new TypeException("This op requires same type");
        }
	  
	    return new Ast.BoolType();
      }
	  
	  
      if (n.op == Ast.BOP.ADD || n.op == Ast.BOP.SUB || n.op == Ast.BOP.MUL || n.op == Ast.BOP.DIV) {
  	  if (!( ((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType))  {
	      throw new TypeException("This op requires integers1");
	  }
	  
	    return new Ast.IntType();
	  } 
	  
  	  else if (n.op == Ast.BOP.AND || n.op == Ast.BOP.OR) {
  	    if (!( ((Ast.ArrayType)check(n.e1)).et instanceof Ast.BoolType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.BoolType))  
  	      throw new TypeException("This op requires booleans");
  		
  	    return new Ast.BoolType();
	  }

	
	  else if (n.op == Ast.BOP.LT || n.op == Ast.BOP.LE || n.op == Ast.BOP.GT || n.op == Ast.BOP.GE) {
	    if (!(((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType))  
	      throw new TypeException("This op requires Integers");
		
	    return new Ast.BoolType();
	  }

	  else
	    throw new TypeException("operand type does not match operator");
    }
  
    else if (!(check(n.e1) instanceof Ast.ArrayType) && (check(n.e2) instanceof Ast.ArrayType)) {
	    if (n.op == Ast.BOP.EQ || n.op == Ast.BOP.NE) {
	  
	    if (!((check(n.e1) instanceof Ast.ArrayType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.ArrayType)
	       || check(n.e1) instanceof Ast.BoolType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.BoolType
	       || check(n.e1) instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType
	       || check(n.e1) instanceof Ast.ObjType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.ObjType)) {
	      throw new TypeException("This op requires same type");
        }
	  
	    return new Ast.BoolType();
      }
	  
	  
      if (n.op == Ast.BOP.ADD || n.op == Ast.BOP.SUB || n.op == Ast.BOP.MUL || n.op == Ast.BOP.DIV) {
  	  if (!( check(n.e1) instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType)) { 
	      throw new TypeException("This op requires integers2");
	  }
	  
	    return new Ast.IntType();
	  } 
	  
  	  else if (n.op == Ast.BOP.AND || n.op == Ast.BOP.OR) {
  	    if (!( check(n.e1) instanceof Ast.BoolType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.BoolType))  
  	      throw new TypeException("This op requires booleans");
  		
  	    return new Ast.BoolType();
	  }
	

	
	  else if (n.op == Ast.BOP.LT || n.op == Ast.BOP.LE || n.op == Ast.BOP.GT || n.op == Ast.BOP.GE) {
	    if (!(check(n.e1) instanceof Ast.IntType && ((Ast.ArrayType)check(n.e2)).et instanceof Ast.IntType))  
	      throw new TypeException("This op requires Integers");
		
	    return new Ast.BoolType();
	  }

	  else
	    throw new TypeException("operand type does not match operator");
	}
	
	
	else if ((check(n.e1) instanceof Ast.ArrayType) && !(check(n.e2) instanceof Ast.ArrayType)) {
	    if (n.op == Ast.BOP.EQ || n.op == Ast.BOP.NE) {
	  
	    if (!((check(n.e2) instanceof Ast.ArrayType && ((Ast.ArrayType)check(n.e1)).et instanceof Ast.ArrayType)
	       || check(n.e2) instanceof Ast.BoolType && ((Ast.ArrayType)check(n.e1)).et instanceof Ast.BoolType
	       || check(n.e2) instanceof Ast.IntType && ((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType
	       || check(n.e2) instanceof Ast.ObjType && ((Ast.ArrayType)check(n.e1)).et instanceof Ast.ObjType)) {
	      throw new TypeException("This op requires same type");
        }
	  
	    return new Ast.BoolType();
      }
	  
	  
      if (n.op == Ast.BOP.ADD || n.op == Ast.BOP.SUB || n.op == Ast.BOP.MUL || n.op == Ast.BOP.DIV) {
  	  if (!( ((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType && check(n.e2) instanceof Ast.IntType)){  
	      throw new TypeException("This op requires integers3");
	  }
	  
	    return new Ast.IntType();
	  } 
	  
  	  else if (n.op == Ast.BOP.AND || n.op == Ast.BOP.OR) {
  	    if (!( ((Ast.ArrayType)check(n.e1)).et instanceof Ast.BoolType && check(n.e2) instanceof Ast.BoolType))  
  	      throw new TypeException("This op requires booleans");
  		
  	    return new Ast.BoolType();
	  }
	
	
	  else if (n.op == Ast.BOP.LT || n.op == Ast.BOP.LE || n.op == Ast.BOP.GT || n.op == Ast.BOP.GE) {
	    if (!(((Ast.ArrayType)check(n.e1)).et instanceof Ast.IntType && check(n.e2) instanceof Ast.IntType))  
	      throw new TypeException("This op requires Integers");
		
	    return new Ast.BoolType();
	  }

	  else
	    throw new TypeException("operand type does not match operator");
	}
  
    else{ 

	    if (n.op == Ast.BOP.EQ || n.op == Ast.BOP.NE) {
	  
	    if (!((check(n.e1) instanceof Ast.ArrayType && check(n.e2) instanceof Ast.ArrayType)
	       || check(n.e1) instanceof Ast.BoolType && check(n.e2) instanceof Ast.BoolType
	       || check(n.e1) instanceof Ast.IntType && check(n.e2) instanceof Ast.IntType
	       || check(n.e1) instanceof Ast.ObjType && check(n.e2) instanceof Ast.ObjType)) {
	      throw new TypeException("This op requires same type");
        }
	  
	    return new Ast.BoolType();
      }
	  
	  
      if (n.op == Ast.BOP.ADD || n.op == Ast.BOP.SUB || n.op == Ast.BOP.MUL || n.op == Ast.BOP.DIV) {
  	  if (!(check(n.e1) instanceof Ast.IntType) && (check(n.e2) instanceof Ast.IntType)) { 
	      throw new TypeException("This op requires integers4");
	  }
	  
	    return new Ast.IntType();
	  } 
	  
  	  else if (n.op == Ast.BOP.AND || n.op == Ast.BOP.OR) {
  	    if (!(check(n.e1) instanceof Ast.BoolType) && (check(n.e2) instanceof Ast.BoolType))  
  	      throw new TypeException("This op requires booleans");
  		
  	    return new Ast.BoolType();
	  }
	 
	
	  else if (n.op == Ast.BOP.LT || n.op == Ast.BOP.LE || n.op == Ast.BOP.GT || n.op == Ast.BOP.GE) {
	    if (!(check(n.e1) instanceof Ast.IntType) && (check(n.e1) instanceof Ast.IntType))  
	      throw new TypeException("This op requires Integers");
		
	    return new Ast.BoolType();
	  }

	  else
	    throw new TypeException("operand type does not match operator");
    }
  }
   
  // Unop ---
  //  UOP op;
  //  Exp e;
  //
  //  Make sure n.e's type is legal with respect to n.op.
  //
  static Ast.Type check(Ast.Unop n) throws Exception {
  
	if (n.op == Ast.UOP.NEG ) {
	  if (!(check(n.e) instanceof Ast.IntType))  
	    throw new TypeException("This op requires Integers");
		
	  return new Ast.IntType();
		
	}
	
	if (n.op == Ast.UOP.NOT ) {
	  if (!(check(n.e) instanceof Ast.BoolType))  
	    throw new TypeException("This op requires Integers");
		
	  return new Ast.BoolType();
		
	}
	else
	  throw new TypeException("operand type does not match operator");

  }
  
  // Call ---
  //  Exp obj; 
  //  String nm;
  //  Exp[] args;
  //
  //  (See the hints in CallStmt.) 
  
  //  1. Check that n.obj is ObjType and the corresponding class exists.
  //  2. Check that the method n.nm exists.
  //  3. Check that the count and types of the actual arguments match those of
  //     the formal parameters.
  
  
  //  In addition, this routine needs to return the method's return type.
  //  
  static Ast.Type check(Ast.Call n) throws Exception {

    Ast.Type returns = thisMDecl.t;; 
    if (check(n.obj) instanceof Ast.ObjType) {
	  boolean flag = false; 
	  for (Ast.MethodDecl c: classEnv.get(((Ast.ObjType)check(n.obj)).nm).cdecl.mthds) {
	    if(c.nm.equals(n.nm)) {
		  returns = c.t; 
		  flag = true;
		  break; 
		}
	  }
        
	  if (flag == false)
	    throw new TypeException("Method does not exist"); 
	}
	

	
	
	
    //  3. Check that the count and types of the actual arguments match those of
    //     the formal parameters.
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     if (returns instanceof Ast.IntType)
	   return new Ast.IntType();
	
	else if (returns instanceof Ast.BoolType)
	   return new Ast.BoolType();
	
	else if (returns instanceof Ast.ArrayType) {
	  
	  Ast.ArrayType a =  (Ast.ArrayType)returns;
	  
	  return new Ast.ArrayType(a.et);
	}
	
	else if (returns instanceof Ast.ObjType) {

	  Ast.ObjType o =  (Ast.ObjType)returns;
	  
	  return new Ast.ObjType(o.nm);
	}
	
	else 
	  throw new TypeException("Return value is not of matching type");


  }

  // NewArray ---
  //  Type et;
  //  int len;
  //
  //  1. Verify that n.et is either integer or boolean.
  //  2. Varify that n.len is non-negative. 
  //  (Note: While the AST representation allows these cases to happen, our 
  //  miniJava parser does not, so these checks are not very meaningful.)
  //
  static Ast.Type check(Ast.NewArray n) throws Exception {

    if(!(n.et instanceof Ast.IntType || n.et instanceof Ast.BoolType))
	  throw new TypeException("incorrect type");
	  
	if (n.len < 0)
      throw new TypeException("Cannot have negetive length");	
	  
	  
	  //Ast.ArrayType a =  (Ast.ArrayType)thisMDecl.t;
	  
	  return new Ast.ArrayType(n.et);
	  
  }

  // ArrayElm ---
  //  Exp ar, idx;
  //
  //  Varify that n.ar is array and n.idx is integer.
  //
  static Ast.Type check(Ast.ArrayElm n) throws Exception {

    if (check(n.ar) instanceof Ast.ArrayType && check(n.idx) instanceof Ast.IntType) {
      return check(n.ar); 
	}

	  
	throw new TypeException("invalid type");	
	  
  }

  // NewObj ---
  //  String nm;
  //
  //  Verify that the corresponding class exists.
  //
  static Ast.Type check(Ast.NewObj n) throws Exception {

    if (!classEnv.containsKey(n.nm))
	  throw new TypeException("incorrect type for new class");
	  
	//Ast.ObjType o =  (Ast.ObjType)thisMDecl.t;
    return new Ast.ObjType(n.nm);

  }
  
  // Field ---
  //  Exp obj; 
  //  String nm;
  //
  //  1. Verify that n.obj is ObjType, and its corresponding class exists.
  //  2. Verify that n.nm is a valid field in the object.
  //   private static HashMap<String, ClassInfo> classEnv = new HashMap<String, ClassInfo>();
  //
  static Ast.Type check(Ast.Field n) throws Exception {
 

 
    if(check(n.obj) instanceof Ast.ObjType) {
	  Ast.Type temp = check(n.obj);
	  if (!classEnv.containsKey(((Ast.ObjType)temp).nm))
	    throw new TypeException("class does not exist");


	for (Ast.VarDecl c: classEnv.get(((Ast.ObjType)temp).nm).cdecl.flds){
	  if (c.nm.equals(n.nm)){
	    return c.t; 
	  }
    }  
	}

 

	
	throw new TypeException("no matching field");
      	   
  }
  
  
  
  
  
  // Id ---
  //  String nm;
  //
  //  1. Check if n.nm is in typeEnv. If so, the Id is a param or a local var.
  //     Obtain and return its type.
  //  2. Otherwise, the Id is a field variable. Find and return its type (through
  //     the current ClassInfo).
  //
  static Ast.Type check(Ast.Id n) throws Exception {

    if (typeEnv.containsKey(n.nm)) {
	  return typeEnv.get(n.nm);
	}
	
	else {

	  Ast.VarDecl v =  thisCInfo.findFieldDecl(n.nm);
	    return v.t;
	  
	}

  }

  // This ---
  //
  //  Find and return an ObjType that corresponds to the current class
  //  (through the current ClassInfo).
  //
  static Ast.Type check(Ast.This n) { 
	return new Ast.ObjType(thisCInfo.cdecl.nm);
  }

  // Literals
  //
  public static Ast.Type check(Ast.IntLit n) { 
    return Ast.IntType; 
  }

  public static Ast.Type check(Ast.BoolLit n) { 
    return Ast.BoolType; 
  }

  public static void check(Ast.StrLit n) {
    // nothing to check or return
  }

}
