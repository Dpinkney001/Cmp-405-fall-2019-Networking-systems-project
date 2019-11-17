import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

//import javax.swing.JFrame;

public class SocketTest extends Socket {

	public SocketTest(int myPortNumber) {
		super(myPortNumber);
		// TODO Auto-generated constructor stub
	}



	private static Socket mySocket;
	private static String host;
	private static InetAddress destinationAddress;
	private static PrintStream s;
	
	public static void main(String[] args) {

		mySocket = new Socket(64000);
		
		mySocket.send("Hello Communication World!!!", 
					  mySocket.getMyAddress(), 
					  mySocket.getMyPortNumber());

		DatagramPacket inPacket = null;
		do {
			inPacket = mySocket.receive();
		} while (inPacket == null);
		
		byte[] inBuffer = inPacket.getData();
		String inMessage = new String(inBuffer);
		InetAddress senderAddress = inPacket.getAddress();
		int senderPort = inPacket.getPort();
		
		System.out.println();
		System.out.println("Received Message = " + inMessage);
		System.out.println("Sender Address   = " + senderAddress.getHostAddress());
		System.out.println("Sender Port      = " + senderPort);
		
		try {
			InetAddress[] destinationAddress = InetAddress.getAllByName(host);
			System.out.println(destinationAddress);
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace(s);
			System.exit(1);
		}
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		System.out.println();
		System.out.println("Enter message to send here: ");
		int counter = 0;
		do {
			String outMessage = keyboard.nextLine();
			
			System.out.println("Message "+ counter  +":"+ outMessage);
			mySocket.send(outMessage, destinationAddress , senderPort);
			counter++;
		} while(inPacket != null);
		
	    //JFrame frame = new JFrame();
	    
	    
	    
		mySocket.close();
		
	}

}
