import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Socket extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private int myPortNumber;
	private InetAddress myAddress;
	private DatagramSocket mySocket;
	
	private Thread receiveThread;
	private boolean receiveThreadShouldKeepRunning = true;
	
	private ConcurrentLinkedQueue<DatagramPacket> messageQueue = 
			new ConcurrentLinkedQueue<DatagramPacket>();
	
	public Socket(int myPortNumber) {
		this.myPortNumber = myPortNumber;
		
		try {
			this.myAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("My IP Address  = " + this.myAddress.getHostAddress());
		System.out.println("My Port Number = " + this.myPortNumber);
		
		try {
			this.mySocket = new DatagramSocket(myPortNumber, myAddress);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(-1);
		}
		
		this.receiveThread = new Thread(
				new Runnable() {
					public void run() {
						receiveThreadMethod();
					}
				});
		this.receiveThread.setName("Receive Thread for Port Number - " + this.myPortNumber);
		this.receiveThread.start();
	}
	
	public void send(String message, 
					 InetAddress destinationAddress,
					 int destinationPort) {
		
		byte[] outBuffer;
		outBuffer = message.getBytes();
		
		DatagramPacket outPacket = new DatagramPacket(outBuffer, 
													  outBuffer.length,
													  destinationAddress,
													  destinationPort);
		
		try {
			this.mySocket.send(outPacket);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void receiveThreadMethod() {

		System.out.println("\nReceive Thread is Starting!!!!");
		
		try {
			this.mySocket.setSoTimeout(50);
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		do {
			byte[] inBuffer = new byte[1024];

			for ( int i = 0 ; i < inBuffer.length ; i++ ) {
				inBuffer[i] = ' ';
			}

			DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
			
			try {
				this.mySocket.receive(inPacket);
				this.messageQueue.add(inPacket);
			} catch (SocketTimeoutException ste) {
				// nothing to do
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(-1);
			}
			
		} while (receiveThreadShouldKeepRunning);
		
		System.out.println("Receive Thread is Exiting!!!!");
	}

	public DatagramPacket receive() {
		return this.messageQueue.poll();
	}
	
	public void close() {
		System.out.println("\nClosing Socket and Stopping Receive Thread");
		this.receiveThreadShouldKeepRunning = false;
		
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			System.exit(-1);
		}
		
		this.mySocket.close();
		System.out.println("Socket Closed");
	}


	public int getMyPortNumber() {
		return this.myPortNumber;
	}

	public InetAddress getMyAddress() {
		return this.myAddress;
	}
    
	public Socket() {
		JTextField textField = null;
		JTextArea textArea;
		//private JButton newSessionButton, sendButton, exitButton;
		//private JLabel newSessionLabel, sendLabel, exitLabel;
        JPanel contentPane = new JPanel(new BorderLayout());
        JLabel newSessionLabel = new JLabel();
		contentPane.add(newSessionLabel,BorderLayout.SOUTH);

		JLabel sendLabel = new JLabel();
		contentPane.add(sendLabel,BorderLayout.SOUTH);

		JLabel exitLabel = new JLabel();
		contentPane.add(exitLabel,BorderLayout.SOUTH);
		//Container contentPane = getContentPane();
		//contentPane.setLayout(new BorderLayout());


		JButton newSessionButton = new JButton("New Session");
		contentPane.add(newSessionButton, BorderLayout.SOUTH);


		


		JButton sendButton = new JButton("SEND");
		contentPane.add(sendButton,BorderLayout.SOUTH);

		JButton exitButton = new JButton("EXIT");
		contentPane.add(exitButton, BorderLayout.SOUTH);



		add( textField, BorderLayout.NORTH );

		textArea = new JTextArea( 4, 30 );
		add(new JScrollPane( textArea ), BorderLayout.CENTER );
		setSize( 400, 300 );
		setVisible( true );

		JTextField textFieldSouth = new JTextField();
		add(textFieldSouth, BorderLayout.SOUTH);
		setSize(400, 300);
		setVisible(true);
	}// end client gui
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
