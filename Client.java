import java.util.Scanner;
import java.util.Stack;



public class Client {
	
	public static void main ( String args []) {
		Stack<String> history = new Stack<String>();
	try {
		Calculator obj = new ServerConnection("localhost", 1099).connect();
		System.out.println("Input expression you want to calculate");
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print(">");
			String expression = input.nextLine();
			if(expression.equalsIgnoreCase("history")) {
				System.out.println(getHistory(history));
				continue;
			}
			if(expression.equalsIgnoreCase("exit")) {
				break;
			}
			String res = obj.calculate(expression);
			// Add successful calculation to the history
			if(res.contains("=")) {
				history.push(res);
			}
			System.out.println(res);
			
		}

	} catch ( Exception e) {
		System.out.println (" Client exception : " +e);
		}
	}
	private static String getHistory(Stack<String> history) {
		if(history.isEmpty()) {
			return "Empty history";
		}
		else {
			return history.pop();
		}
	}
	
}


