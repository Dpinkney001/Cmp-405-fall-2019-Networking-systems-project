import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

import javax.swing.JFrame;

public class SocketTest extends Socket {

	public SocketTest(int myPortNumber) {
		super(myPortNumber);
		// TODO Auto-generated constructor stub
	}



	private static Socket mySocket;
	
	
	
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
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println();
		System.out.println("Enter message to send here: ");
		int counter = 0;
		do {
			String outMessage = keyboard.nextLine();
			
			System.out.println("Message "+ counter  +":"+ outMessage);
			counter++;
		} while(inPacket != null);
		
	    JFrame frame = new JFrame();
	    
	    
	    
		mySocket.close();
		
	}

}
