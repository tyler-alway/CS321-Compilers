
//Tyler Alway
//----------------------------------------------------------------------
// miniJava (W15) lexer.
//
// (For CS321 Winter 2015)
//----------------------------------------------------------------------
//
import java.io.*;
import java.util.HashMap;

public class mjLexer implements mjTokenConstants {

	private static FileReader input; 
	//static FileInputStream input = null;
	private static int currLine = 1;
	private static int currColumn = 1;
	private static int c = 0; 
	private static boolean save = false; 

	
	
  static class LexError extends Exception {
    public LexError(int line, int column, String msg) { 
      super("at (" + line + "," + column + ") " + msg); 
    }
  }

  // Token object
  //
  static class Token {
    int code; 		// token code
    String lexeme;      // lexeme string
    int line;	   	// line number of token's first char
    int column;    	// column number of token's first char
    
    public Token(int code, String lexeme, int line, int column) {
      this.code=code; this.lexeme=lexeme;
      this.line=line; this.column=column; 
    }
    public String toString() {
      return "(" + line + "," + column + ") " + code + " " + lexeme;
    }
  }

  
  
  
  // The main method
  //
  public static void main(String [] args) {
	  
    
    
    try {
      if (args.length == 1) {
        Token tkn;
        
	int tknCnt = 0;
	input = new FileReader(args[0]);
        
    while ((tkn = nextToken()) != null) { 
      System.out.print("(" + tkn.line + "," + tkn.column + ")\t");
	  switch(tkn.code) {
	  case ID:     
	    System.out.println("ID(" + tkn.lexeme + ")"); 
	    break;
	  case INTLIT: 
		System.out.println("INTLIT(" + tkn.lexeme + ")"); 
		break;

	  case DBLLIT: 
		System.out.println("DBLLIT(" + tkn.lexeme + ")"); 
		break;

  	  case STRLIT: 
  	    System.out.println("STRLIT(" + tkn.lexeme + ")"); 
  	    break;
  	    
  	  default:     
	    System.out.println(tkn.lexeme);
	  } 
	  tknCnt++;
    }
    
    System.out.println("Total: " + tknCnt + " tokens");
    input.close();
    
    }
      
    else {
        System.err.println("Need a file name as command-line argument.");
    }
      
    } catch (LexError e) {
      System.err.println(e);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  
  
  
  // Return the next token
  //
  public static Token nextToken() throws Exception {
    
	String hold = new String(""); 
	int i = 0; 
	
	if(save == false) 
      c = nextChar();
	
	else
	  save = false;
    
    while(true){
      switch (c) {
      
      case -1:
        return null;
        
        
        
      // Skip whitespace
      case ' ':
          c = nextChar();
          currColumn++; 
          continue;
    	  
          
          
      case '\t':
          c = nextChar();
          currColumn++;
          continue;
    	  
          
      //also counts line number 
      case '\n':
    	currLine++;
    	currColumn = 1;
    	c = nextChar();
    	continue;
    	
    	
    	
      case '\r':
        c = nextChar();
        continue;
        

        
        
        
      //test for = or == tokens 
      case '=':
    	c=nextChar();
    	
    	//if the '=' is followed by whitespace
      	if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
      		save = true; 
      		column("=");
    		return new Token(61, "=", currLine, currColumn-1);
    	}
        
      	//if the '=' is followed by another '='
      	else if (c == '=') {
      	  column("==");
    	  return new Token(EQ, "==", currLine, currColumn-2); 
    	}
      	
      	//if the '=' if followed by any other char
      	//flag the current char to be saved and return the '=' token
      	else {
          save = true;
          column("=");
      	  return new Token(61, "=", currLine, currColumn-1);
      	}
      	
      	
      	
      //test for + 
      case '+':
    	column("+");
      	return new Token(43, "+", currLine, currColumn-1);

      	
      	
      //test for - 
      case '-':
    	column("-");
        return new Token(45, "-", currLine, currColumn-1);  
        
        
        
      //test for * 
      case '*':
    	column("*");
        return new Token(42, "*", currLine, currColumn-1);  
        
        
        
      //TEST FOR ! OR !=  
      case '!':  
    	c = nextChar();
    	
    	if (c == ' ' || c == '\t' || c == '\n' || c == '\r'){
    		save = true;
    		column("!");
    		return new Token(33, "!", currLine, currColumn-1);
    	}
    	
    	else if (c == '=') {
    		column("!=");
    		return new Token(NEQ , "!=", currLine, currColumn-2); 
    	}
    	
    	else {
          save = true;
          column("!");
          return new Token(33, "!", currLine, currColumn-1);
    	}
    	
    	
    	
      //test for < and <=	
      case '<':   
    	c = nextChar(); 
    	
    	if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
    	  save = true;
    	  column("<");
          return new Token(60, "<", currLine, currColumn-1);
    	}
    	
    	else if (c == '=') {
    		column("<=");
    		return new Token(LE , "<=", currLine, currColumn-2); 
    	}
    	
    	else {
          save = true;
          column("<");
          return new Token(60, "<", currLine, currColumn-1);
    	}
        
    	
    	
      //test for > and >=
      case'>':
        c = nextChar();  
        
        if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
          save = true;
          column(">");
          return new Token (62, ">", currLine, currColumn-1);
        }
        
    	else if (c == '=') {
    		column(">=");
    		return new Token(GE , ">=", currLine, currColumn-2); 
    	}
    	
    	else {
          save = true;
          column(">");
          return new Token(62, ">", currLine, currColumn-1);
    	}
        
        

      //test for / and comments
      case '/':
    	c = nextChar();

    	// for division
    	if ((c == ' ' || c == '\t' || c == '\n' || c == '\r') && c != '*' && c!= '/'){
    	  save = true;
    	  column("/");
          return new Token(47, "/", currLine, currColumn-1);  
    	}
    	
    	//this is a single line comment, ignore the comment 
    	else if (c == '/') {
          while(c != '\n' && c != -1) {
            c = nextChar();
            currColumn++;
          }
          //continue
          continue; 
          
    	}
    	
    	//if /* this is a multi line comment (no nesting) 
    	else if (c == '*') {
          int h = 0; //variable to help find the */
          int tempColumn = currColumn; 
          int tempLine = currLine;
          while (h != '*' || c != '/') {
        	c = nextChar();
        	currColumn++;
        	if (c == '\n')
        		currLine++;
        		
        	if (c == '*') {
        	  h = c; 
        	}
        	

        	else if (c == -1)
        	  throw new LexError(tempLine, tempColumn, "multi line comment never termenates");
        	
        	else if (c != '*' && c != '/')
        		h=0;
          }  
          //get next char and continue
          c = nextChar();
          continue;  
          
    	}
    	
    	
    	
    	//for division 
    	else {
    	  save = true;
    	  column("/");
    	  return new Token(47, "/", currLine, currColumn-1);
    	}
    	
    	
    	
      //test for ; 
      case ';':
    	column(";");
        return new Token(59, ";", currLine, currColumn-1);  
    	
        
        
      //test for '
      case ',':
    	column(",");
        return new Token(44, ",", currLine, currColumn-1);        
     

      //test for . or floats beginning with .
      case '.':
    	hold = hold + (char)c;
    	c = nextChar();
    	if (!isDigit(c)) {
    	  save = true; 
    	  column(".");
          return new Token(46, ".", currLine, currColumn-1);  
    	}
    	
    	else {
    		while (isDigit(c)) {
    			hold = hold + (char)c; 
    			c = nextChar();
    		}
      	    save = true; 
      	    column(hold);
          	hold = '0' + hold; 
    		return new Token(DBLLIT, hold, currLine, currColumn-hold.length()+1);
    	}
        
        
        //test for ( 
      case '(':
    	column("(");
        return new Token(40, "(", currLine, currColumn-1);  
        
        
        
        //test for ) 
      case ')':
    	column(")");
        return new Token(41, ")", currLine, currColumn-1);  
        
        
        
        //test for { 
      case '{':
    	column("{");
        return new Token(123, "{", currLine, currColumn-1);  
        
        
        
        //test for } 
      case '}':
    	column("}");
        return new Token(125, "}", currLine, currColumn-1);  
        
        
        
        //test for [
      case '[':
    	column("[");
        return new Token(91, "[", currLine, currColumn-1);  
        
        
        
        //test for ] 
      case ']':
    	column("]");
        return new Token(93, "]", currLine, currColumn-1);  
        
        
        
      // test for &&
      case '&':
    	c = nextChar();
    	if (c == '&') { 
      	  column("&&");
    	  return new Token (AND, "&&", currLine, currColumn-2);
    	}
    	
    	else { 
    	  throw new LexError(currLine, currColumn-1, "& is not a valid token");
    	}
    	  
    	
    	
      // test for ||
      case '|':
        c = nextChar();
        if (c == '|') {
      	  column("||");
      	  return new Token (OR, "||", currLine, currColumn-2);
        }
      	
      	else 
      	  throw new LexError(currLine, currColumn-2, "| is not a valid token");
        
        
        
      //test for keywords beginning with the letter B 
      case 'b':
        hold = getKeyword(hold, "boolean");
        
    	//return boolean
    	if (hold.equals("boolean")) {
    		save = true;
      	  column(hold);
          return new Token(BOOLEAN, "boolean", currLine, currColumn-7); 
    	} 
    	//return ID
        else { 
          hold = getID(hold);
    	  column(hold);
          save = true; 
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
    	
    	
    	
      //test for keywords beginning with the letter C 
      case 'c':
    	hold = getKeyword(hold, "class");
          
      	//return class
      	if (hold.equals("class")) {
      	  save = true;
      	  column(hold);
          return new Token(CLASS, "class", currLine, currColumn-hold.length()); 
      	}
        //return ID
        else { 
          hold = getID(hold);
    	  column(hold);
          save = true; 
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
      	
      	
      	
      //test for keywords beginning with the letter D 
      case 'd':
    	hold = getKeyword(hold, "double");
    	  
        //return double
      	if (hold.equals("double")) {
      		save = true;
      		column(hold);
            return new Token(DOUBLE, "double", currLine, currColumn-hold.length()); 
      	}
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
 
      	
      //test for keywords beginning with the letter E 
      case 'e':
    	hold = getKeyword(hold, "else", "extends");  
         
        //return else
      	if (hold.equals("else")) {
      		save = true;
      		column(hold);
            return new Token(ELSE, "else", currLine, currColumn-hold.length()); 
      	}
      	//return extends
      	else if (hold.equals("extends")) {
      		save = true;
      		column(hold);
      		return new Token(EXTENDS, "extends", currLine, currColumn-hold.length());	
      	}
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

     

      //test for keywords beginning with the letter F 
      case 'f':
    	hold = getKeyword(hold, "false");
           
        //return false
        if (hold.equals("false")) {
          save = true;
          column(hold);
          return new Token(FALSE, "false", currLine, currColumn-hold.length()); 
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }


      //test for keywords beginning with the letter I 
      case 'i':
    	hold = getKeyword(hold, "if", "int");
    	  
        //return if   
        if (hold.equals("if")) {
          save = true;
          column(hold);
          return new Token(IF, "if", currLine, currColumn-hold.length()); 
        }
        //return int	
        else if (hold.equals("int")){
          save = true;
          column(hold);
          return new Token(INT, "int", currLine, currColumn-hold.length());
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
      	

      	
      //test for keywords beginning with the letter M 
      case 'm':
    	hold = getKeyword(hold, "main");
          
        //return main 
        if (hold.equals("main")) {
          save = true;
          column(hold);
          return new Token(MAIN, "main", currLine, currColumn-hold.length()); 
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true;
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

      	
        
      //test for keywords beginning with the letter N 
      case 'n':
    	hold = getKeyword(hold, "new");
          
        //return new keyword
        if (hold.equals("new")) {
          save = true;
          column(hold);
          return new Token(NEW, "new", currLine, currColumn-hold.length()); 
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

      	
        
      //test for keywords beginning with the letter O 
      case 'o':
    	hold = getKeyword(hold, "out");
    	  
        //return out keyword
        if (hold.equals("out")) {
          save = true;
          column(hold);
          return new Token(OUT, "out", currLine, currColumn-hold.length()); 
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

 
        
      //test for keywords beginning with the letter p 
      case 'p':
    	hold = getKeyword(hold, "println", "public");
        
        //return println keyword
        if (hold.equals("println")) {
          save = true;
          column(hold);
          return new Token(PRINTLN, "println", currLine, currColumn-hold.length()); 
        }
        //return public keyword
        else if (hold.equals("public")){
          save = true;
          column(hold);
          return new Token(PUBLIC, "public", currLine, currColumn-hold.length());
        }
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

        
        
      //test for keywords beginning with the letter R 
      case 'r':
    	hold = getKeyword(hold, "return ");
    	  
        //return return 
        if (hold.equals("return")) {
          save = true;
          column(hold);
          return new Token(RETURN, "return", currLine, currColumn-hold.length()); 
        }
        
        //return ID
        else { 
          hold = getID(hold);
            
          save = true;
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

        
        
      	
      //test for keywords beginning with the letter s lower case only 
      case 's':
    	hold = getKeyword(hold, "static");
    	  
        //return static   
        if (hold.equals("static")) {
          save = true;
          column(hold);
          return new Token(STATIC, "static", currLine, currColumn-hold.length()); 
        }
        
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }

        
        
        
        
        
      //test for keywords beginning with the letter S upper case only 
      case 'S': 
    	hold = getKeyword(hold, "String", "System");
              
        if (hold.equals("String")) {
          save = true;
          column(hold);
          return new Token(STRING, "String", currLine, currColumn-hold.length()); 
        }
          	
        else if (hold.equals("System")){
          save = true;
          column(hold);
          return new Token(SYSTEM, "System", currLine, currColumn-hold.length());
        }
        
        //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
	

        
        
      //test for the letter T 
      case 't':
        hold = getKeyword(hold, "this", "true");
        //return true token
        if (hold.equals("this")) {
          save = true; 
          column(hold);
          return new Token(THIS, "this", currLine, currColumn-hold.length()); 
        }
        //return the true token
        else if (hold.equals("true")){
          save = true; 
          column(hold);
          return new Token(TRUE, "true", currLine, currColumn-hold.length());
        }
        
      //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
	
        
      //test for keywords beginning with the letter V 
      case 'v':
    	hold = getKeyword(hold, "void");
        
    	//return the void token   
        if (hold.equals("void")) {
          save = true;
          column(hold);
          return new Token(VOID, "void", currLine, currColumn-hold.length()); 
        }
        
      //return ID
        else { 
          hold = getID(hold);
            
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        }
        
	
        
      //test for keywords beginning with the letter W 
      case 'w':    	  
    	  hold = getKeyword(hold, "while");
    	  
          //return while token    
          if (hold.equals("while")) {
            save = true; 
            column(hold);
            return new Token(WHILE, "while", currLine, currColumn-hold.length()); 
          }
          
          //return ID
          else { 
            hold = getID(hold);
              
            save = true; 
            column(hold);
            return new Token(ID, hold, currLine, currColumn-hold.length());
          }

          
      default: 
        //get the ID   
        if (isLetter(c)) { 
          hold = getID(hold);  
          save = true; 
          column(hold);
          return new Token(ID, hold, currLine, currColumn-hold.length());
        } 

        
      	//INTLIT or DBLLIT 
        else if (isDigit(c)) {
        	hold = hold + (char)c;
        	c= nextChar();
        	//the int is 0
        	if(hold.toCharArray()[0] == '0' && c == '\t' || c == '\n' || c == '\r' || c == ' ') {
                save = true; 
                column(hold);
                return new Token(INTLIT, hold, currLine, currColumn-hold.length());
        	}
        	
            //this is a hex int 0x
        	else if (hold.toCharArray()[0] == '0' && (c == 'x' || c == 'X')) {
          	  hold = hold + (char)c;
          	  c= nextChar();
              if(!isHex(c)){
              	throw new LexError(currLine, currColumn -hold.length()+1, "incorrect format for Hexademimal");
              }
          	  while (isHex(c)) {
              	  hold = hold + (char)c;
              	  c= nextChar();
                }
          	  

              column(hold);
          	  int tempnum = hold.length(); 
          	  
          	  try {
                int temp = Integer.parseInt(hold.substring(2, hold.length()), 16);
                hold = String.valueOf(temp);
              }catch (NumberFormatException e){
          	    throw new LexError(currLine, currColumn -hold.length(), "Integer Overflow");
              }
          	  
          	  
              save = true; 
              return new Token(INTLIT, hold, currLine, currColumn-tempnum);
            }
            
            
            //this is an int = 0
            else if (hold.toCharArray()[0] == '0' && !isDigit(c) && !isLetter(c) && c!= '.') {
                save = true; 
                column(hold);
                try {
        		    Integer.parseInt(hold, 10);
                }catch (NumberFormatException e){
              	  throw new LexError(currLine, currColumn -hold.length(), "Integer Overflow");
                }
                return new Token(INTLIT, hold, currLine, currColumn-hold.length());
            }
        	
        	
        	
        	
        	
        	
        	
        	
      	  //this is an octal
            else if (hold.toCharArray()[0] == '0' && c != '.') {
              if(!isOctal(c)){
                throw new LexError(currLine, currColumn -hold.length()+1, "incorrect format for octaldecimal");
              } 
              while (isOctal(c)) {
                hold = hold + (char)c;
                c= nextChar();
              }
              int tempOctal = hold.length();
              column(hold);
              try {
      		    int temp = Integer.parseInt(hold, 8);
      		    hold = String.valueOf(temp);
        		  
        		save = true; 
                return new Token(INTLIT, hold, currLine, currColumn-tempOctal);
              }catch (NumberFormatException e){
            	  throw new LexError(currLine, currColumn -hold.length()+1, "Integer Overflow");
              }

      	  }
            
            
            
            
            
          //while loop to see if it is a normal int or double  
          while (isDigit(c)) {
        	  hold = hold + (char)c;
        	  c= nextChar();
          }
          
          //this is a double
          if (c == '.') {
        	  hold = hold + (char)c;
        	  c= nextChar();
              while (isDigit(c)) {
            	  hold = hold + (char)c;
            	  c= nextChar();
              }
              save = true; 
              column(hold);
              if(hold.toCharArray()[hold.length()-1] == '.') {
            	  hold = hold + '0'; 
            	  currColumn++; 
              }
              return new Token(DBLLIT, hold, currLine, currColumn-hold.length());
          }
          

          
          //this is an int
          else {
              save = true; 
              column(hold);
              try {
      		    Integer.parseInt(hold, 10);
              }catch (NumberFormatException e){
            	  throw new LexError(currLine, currColumn -hold.length(), "Integer Overflow");
              }
              return new Token(INTLIT, hold, currLine, currColumn-hold.length());
          }
          
          
          
        }
        
        
        
        
    	//if " is encountered this is a String 
    	else if (c == '"') {
    	  int tempcount = currColumn;
    	  hold = hold + (char)c;
    	  c = nextChar();
          while (c != '"') {
        	hold = hold + (char)c; 
        	c = nextChar();
        	if (c == (-1)){
              column(hold);
        	  throw new LexError(currLine, tempcount, "String never terminates");
        	} 
          }  
          hold = hold + (char)c;
          column(hold);
          return new Token(STRLIT, hold, currLine, currColumn-hold.length());
          
    	}
        
        
    	else {
    	  throw new LexError(currLine, currColumn -hold.length()+1, (char)c + " is not a legal character");
    	}

        
      }
    }
     
  }

  
  
  // Utility routines
  //
  private static boolean isLetter(int c) {
    return (('A' <= c) && (c <= 'Z')
	    || ('a' <= c) && (c <= 'z'));
  }

  private static boolean isDigit(int c) {
    return ('0' <= c) && (c <= '9');
  }

  private static boolean isOctal(int c) {
    return ('0' <= c) && (c <= '7');
  }

  private static boolean isHex(int c) {
    return ('0' <= c) && (c <= '9')
            || ('A' <= c) && (c <= 'F')
	    || ('a' <= c) && (c <= 'f');
  }
  
  
  // Read next char from input; book-keep line and column numbers.
  //
  private static int nextChar() throws Exception {
    int c = input.read();    
    return c;
  }
  
  
  
  //method to set the column number
  //add the size of the last token to the currColumn 
  private static void column(String hold) {
	  currColumn = currColumn + hold.length(); 
  }
  
  
  
  //method to get a keyword 
  //this takes in a String (hold) and trys to match it to the passed in keyword
  //this returns the new hold
  private static String getKeyword(String hold, String keyword) throws Exception {
	int i = 0; 
  	hold = hold + (char)c;
  	c = nextChar();
  	//while loop to find keyword
    while (hold.length() <= keyword.length() && hold.substring(i, i+1).equals(keyword.substring(i, i+1)) && (isDigit(c) || isLetter(c))) {
      hold = hold + (char)c;
      c = nextChar();  
      i++;
    }
	  
    return hold;
  }
  
  //method to get a keyword from two different options
  //this takes in a String (hold) and then adds to it to try and for one of the key words also passed in
  //this returns the new hold
  private static String getKeyword(String hold, String keyword1, String keyword2) throws Exception {
    int i = 0;
	hold = hold + (char)c;          
    c = nextChar();

    //if the second letter is the second letter in keyword1
    if (keyword1.substring(1, 2).equals(String.valueOf((char)c))) {
     while (hold.length() <= keyword1.length() &&  hold.substring(i, i+1).equals(keyword1.substring(i, i+1))  && (isDigit(c) || isLetter(c))) {
       hold = hold + (char)c;   
       c = nextChar(); 
       i++;
      }
    }
    //if the second letter is the second letter in keyword2
    else if (keyword2.substring(1, 2).equals(String.valueOf((char)c))) {
     while (hold.length() <= keyword2.length() && hold.substring(i, i+1).equals(keyword2.substring(i, i+1)) && (isDigit(c) || isLetter(c))) {
       hold = hold + (char)c;  
       c = nextChar(); 
       i++;
      }
    }
    return hold;
  }
  
  
  //method to get a ID
  //passes in a String to add to 
  //then returns the resulting String 
  private static String getID(String hold) throws Exception {
    while (isDigit(c) || isLetter(c)) {
      hold = hold + (char)c;
      c = nextChar();
    }
	return hold;
  }

  

}//endClass
