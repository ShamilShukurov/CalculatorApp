import java.rmi.RemoteException;

public class CalculatorImpl implements Calculator {

	public CalculatorImpl () { 
		super () ; 
	}
	@Override
	public String calculate(String expression) throws RemoteException {
		ArithmeticExpression exp = new ArithmeticExpression(expression);
		
		try {
			exp = exp.build();
			int result = exp.evaluate();
			
			return exp.toString() + "="+result;
		} catch (ArithmeticExpressionException e) {
			return e.getUserMessage();
		}catch (ArithmeticException e) {
			return "Division by 0 is undefined";
		}catch(Exception e) {
			return "Malformed Expression";
		}
		
		
		
	}
	
}
