import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry ;
import java.rmi.registry.Registry ;

public class ServerConnection {
	
	private Calculator calculator;
	private String machine;
	private int port;
	public ServerConnection(String machine, int port){
		super();
		this.machine = machine;
		this.port = port;
	}
	public Calculator connect() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry ( machine , port );
		this.calculator = (Calculator) registry.lookup ("Calculator");
		return this.calculator;
	}
}
