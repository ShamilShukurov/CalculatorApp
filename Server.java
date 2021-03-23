import java.rmi.registry.LocateRegistry ;
import java.rmi.registry.Registry ;
import java.rmi.server.UnicastRemoteObject ;
import java.util.Arrays ;


public class Server {
	
	public static void main ( String [ ] args ) {
		try {
			
				int port = 1099;
				Calculator skeleton = (Calculator) UnicastRemoteObject.exportObject(new CalculatorImpl () , 0);
				System.out.println ( "Server is ready to serve" ) ;
				Registry registry = LocateRegistry.getRegistry (port);
				System.out.println ( " Service Message enregistre " ) ;
				if (! Arrays.asList(registry.list()).contains ("Calculator"))
					registry.bind("Calculator", skeleton );
				else
					registry.rebind ("Calculator", skeleton );
		} catch ( Exception ex) {
			ex.printStackTrace ();
			}
	}	  
}
