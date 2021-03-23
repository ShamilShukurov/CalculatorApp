import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class ArithmeticExpression {
	
//	Operators and their precedence
	private static Map<String, Integer> operators = new HashMap<String, Integer>() {
	{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
    }};

	
	
	private String expression;
	private String postfix;
	private String infix;

	public ArithmeticExpression(String expression) {
		this.expression = expression.trim();
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	/*	TESTS FOR THE VALIDATION OF THE EXPRESSION:
	 * 
	 * 	Test 1 : Check if expression contains only non-negative integer, supported
	 *  			operators and parentheses.
	 * 	Test 2 : Check if parentheses are not balanced
	 * 	Test 3 : Check if expression contains consecutive integers without operator
	 * 				between them
	 * 	Test 4 : Check if expression starts with operator
	 * 	Test 5 : Check if expression ends with operator
	 * 	Test 6 : Check if expression contains consecutive operators without operand
	 * 				between them
	 * 
	 */
	
/*TEST 1 : If expression contains only non-negative integer, supported
	  		operators and parentheses, returns true, otherwise returns false  */
	private static boolean checkSpelling(String expression) {
		for(int i=0;i<expression.length();i++) {
			char c= expression.charAt(i);
			
			boolean isDigit = Character.isDigit(c); 
			boolean isOperator = operators.containsKey(Character.toString(c));
			if(!(isDigit || c=='(' || c==')' || isOperator || c==' ') ) {
				return false;
			}
		}
		return true;
	}

//	TEST 2 :if parentheses in the expression is balanced returns true, false otherwise 
	private static boolean parenthesesMatching(String exp) {  
	    int count = 0;  
	    
	    for(int i = 0; i < exp.length(); i++){  
	        if (exp.charAt(i) == '(')  {  
	            count++;  
	        }else if(exp.charAt(i) == ')'){  	              
	            count--;  
	        }  
	        if (count < 0) {                 
	        	return false;
	        }  
	    }  
	    
	    if (count != 0){  
	        return false;  
	    } 
	    return true;
	}	

//	TEST 3 : If operand follows another operand without operator between them, returns false,
//			 otherwise true.
	private static boolean consecutiveIntegers(String expression) {
		for(int i=0;i<expression.length();i++) {
			char c= expression.charAt(i);
			if(Character.isDigit(c)) {
				if(i<expression.length()-1 && expression.charAt(i+1)==' ') {
					int k=i+1;
					while(k<expression.length()-1 && expression.charAt(k+1)==' ') {
						k++;
					}
					if(Character.isDigit(expression.charAt(k+1))) {
						return false;
					}			
				}
				
			}
		}
		return true;
	}
	
//	Combines first three tests, throw exception in case of malformed expression
	private static void validate1(String expression)throws ArithmeticExpressionException{
		String message,userMessage;
		if(!checkSpelling(expression)) {
    		message = "Expression contains non-integer operand or illegal operator";
			userMessage = "Make sure your expression contains only"
									+ " integers and supported operators";
    		throw new ArithmeticExpressionException(message, userMessage);	
		}
		
		if(!parenthesesMatching(expression)) {
			message = "Parentheses are not matching";
			userMessage = "Make sure parantheses are balanced";
			throw new ArithmeticExpressionException(message, userMessage);
		}
		
		if(!consecutiveIntegers(expression)) {
			message = "Consecutive Operands: Operand can not follow another operand";
			userMessage = "Make sure you don't forget to put an operator between operands";
			throw new ArithmeticExpressionException(message, userMessage);	
		}
	}
	
/* TEST 4,5,6: The function returns:
 * 				1 if expression starts with subtraction operator
 * 				2 if expression starts with other operator
 * 				3 if expression has two or more consecutive operators 
 *  			4 if expression ends with an operator
 *  			0 if everything is OK
 */
	private static int checkOperatorError(String expression) {
		int numOperators=0;
		int numOperands = 0;
		for(String token: expression.split("\\s")) {
			if(operators.containsKey(token)) {
				numOperators++;
				// Test 4
				if(numOperators>0 && numOperands==0) {
					if(token.equals("-")) {
						return 1;
					}
					return 2;
				}
			}
			else if(isNumeric(token)) {
				numOperands++;
			}
			if(numOperators>numOperands) {
				return 3;
			}
		}
		if(numOperators>=numOperands) {
			return 4;
		}
		return 0;
	}
	
//	Combines last three tests, throw exception in case of malformed expression
	private static void validate2(String expression)throws ArithmeticExpressionException{
		String message,userMessage;
		int res = checkOperatorError(expression);
		if(res == 1) {
    		message = "Negative Operand: Expression can contain only non-negative integers";
			userMessage = "Negative integers is not allowed";
    		throw new ArithmeticExpressionException(message, userMessage);	
		}
		else if(res == 2) {
    		message = "Operand Exception: Expression can not start with operator";
			userMessage = "Expression can not start with operator";
    		throw new ArithmeticExpressionException(message, userMessage);
		}
		else if(res == 3) {
    		message = "Consecutive Operator: Operator can not follow another operator";
			userMessage = "Expression contains consecutive operators";
    		throw new ArithmeticExpressionException(message, userMessage);
		}
		else if(res == 4) {
    		message = "Operand Exception: Expression can not end with operator";
			userMessage = "Expression can not end with operator";
    		throw new ArithmeticExpressionException(message, userMessage);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////
	


//	Checks for any syntax errors in the expression, puts white spaces between
//	operators,operands and parentheses: makes infix expression appropriate for finding 
//	matching postfix expression. 	
	private static String prepare(String expression) throws ArithmeticExpressionException {
		
		// Validate expression with TESTS 1-3
		validate1(expression);	
			
		// Put whitespace between operators, operands and parentheses	
		expression = expression.replaceAll("\\s+", "");
	    StringBuilder sb = new StringBuilder(expression);
	    int len = expression.length();
	    for(int i=0,p=0; i<expression.length() && p<len;i++,p++) {
	    	char c= expression.charAt(i);
	    	// Number
	    	if(Character.isDigit(c)) {
	    		if(i<expression.length()-1 && !Character.isDigit(expression.charAt(i+1))) {
	    			sb.insert(p+1, " ");
	    			p++;
	    			len++;
	    		} 		
	    	}
	    	// parentheses or operator
	    	else {
	    		sb.insert(p+1, " ");
	    		p++;
	    		len++;
	    	}
	    }
	    String preparedExpression = sb.toString();
	    
	    // Validate expression with TESTS 4-6
	    validate2(preparedExpression);
	    
		return preparedExpression; 
	}
	

	private static boolean isHigerPrec(String op, String peek)
    {
        return (operators.containsKey(peek) && operators.get(peek) >= operators.get(op));
    }

	private static String infixToPostfix(String expression) {
		StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();
        
        
        for(String token: expression.split("\\s")) {
        	// operator
            if (operators.containsKey(token)) {
                while ( ! stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);
            }
            
            // left parenthesis
            else if (token.equals("(")) {
                stack.push(token);
            }
            // right parenthesis
            else if (token.equals(")")) {
                while ( ! stack.peek().equals("("))
                    output.append(stack.pop()).append(' ');
                stack.pop();
            }
            // number
            else {
                output.append(token).append(' ');
            }
        }
        
        while ( ! stack.isEmpty()) {
        	output.append(stack.pop()).append(' ');
        }
        
		return output.toString();
	}

	private static boolean isNumeric(String str) { 
		  try {  
			    Integer.parseInt(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
	}
	
	private static int calcSimpleExpression(int operand1, int operand2, String operator) {
		
		switch(operator) {
			case "+":
				return operand1+operand2;
			case "-":
				return operand1-operand2;
			case "*":
				return operand1*operand2;
			case "/":
				return operand1/operand2;
		}
		
		return 0;
	}

	private static int calculatePostfix(String exp) {
		
		Stack<Integer> stack=new Stack<>(); 
		for(String token: exp.split("\\s")) {
			
			if(isNumeric(token)) {
				stack.push(Integer.parseInt(token));
			}
			else if(operators.containsKey(token)) {
				int operand2 = stack.pop();
				int operand1 = stack.pop();
				int res =calcSimpleExpression(operand1, operand2, token);
				stack.push(res);
			}
			else {
				
			}
			
		}
        return stack.pop();
		
	}
	
	public ArithmeticExpression build() throws ArithmeticExpressionException {
		this.infix = prepare(this.expression);
		this.postfix = infixToPostfix(this.infix);
		return this;
	}
	public int evaluate() throws ArithmeticExpressionException {
		if(this.postfix==null) {
			String message = "build() method should be called before"
					+ " evaluating an expression";
			String userMessage = "Calculation Error";
			throw new ArithmeticExpressionException(message,userMessage);
		}
		return calculatePostfix(this.postfix);
	}
	
	@Override
		public String toString() {
			if(this.infix != null) {
				return this.infix.replaceAll("\\s+", "");
			}
			return this.expression;
		}
	
}
