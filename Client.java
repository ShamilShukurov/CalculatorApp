import java.util.Scanner;
import java.util.Stack;



public class Client {
	private static Calculator calculator;
	private static Stack<String> history = new Stack<String>();
	public static void main ( String args []) {
		
	try {
		calculator = new ServerConnection("localhost", 1099).connect();
		System.out.println("Input expression you want to calculate");
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print(">");
			String expression = input.nextLine();
			if(expression.equalsIgnoreCase("history")) {
				System.out.println(getHistory());
				continue;
			}
			if(expression.equalsIgnoreCase("exit")) {
				break;
			}
			String res = calculator.calculate(expression);
			// Add successful calculation to the history
			addHistory(res);
			System.out.println(res);
			
		}

	} catch ( Exception e) {
		System.out.println (" Client exception : " +e);
		}
	}
	private static String getHistory() {
		if(history.isEmpty()) {
			return "Empty history";
		}
		else {
			return history.pop();
		}
	}
	
	private static void addHistory(String expression) {
		if(expression.contains("=")) {
			history.push(expression.split("=")[0]);
		}
	}
	public static Calculator getCalculator() {
		return calculator;
	}
	
}


