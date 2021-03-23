import java.rmi.Remote ;
import java.rmi.RemoteException ;

public interface Calculator extends Remote {
	public String calculate (String expression) throws RemoteException ;
}
