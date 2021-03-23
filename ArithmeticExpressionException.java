public class ArithmeticExpressionException extends Exception {
	private String userMessage;
	public ArithmeticExpressionException(String message, String userMessage) {
		super(message);
		this.userMessage = userMessage;
		
	}
	public String getUserMessage() {
		return userMessage;
	}
	
}
