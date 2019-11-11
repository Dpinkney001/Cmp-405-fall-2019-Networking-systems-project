import java.awt.Window;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Test extends Socket {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public Test(int myPortNumber) {
		super(myPortNumber);
		// TODO Auto-generated constructor stub
	}



	private static Socket mySocket;
	private static Object otherAddress;
	
	
	
	public static <ClientGUI> void main(String[] args) {

		 //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
           	     //Socket = null;
                 Socket app = new Socket();
                 app.setVisible(true);
                 app.setSize(500, 500);
           	     
            } 
       });
        
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
		
		mySocket.close();
		
	}

}
