import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
//import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Socket {
	
	private int myPortNumber;
	private InetAddress myAddress;
	private DatagramSocket mySocket;
	
	private Thread receiveThread;
	private boolean receiveThreadShouldKeepRunning = true;
	
	private ConcurrentLinkedQueue<DatagramPacket> messageQueue = 
			new ConcurrentLinkedQueue<DatagramPacket>();
	//private DatagramPacket outPackets;
	
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

	/*
	public void Broadcast(String outMessage, InetAddress[] destinationAddress, int destinationPort) {
		byte[] outBuffer;
		outBuffer = outMessage.getBytes();
		DatagramPacket[] outPackets = null;
		int j = 0;
		do {
			for(int i = 0; i <= destinationAddress.length; i++) {
			
			
			DatagramPacket[] outPackets1[i] = (DatagramPacket) new DatagramPacket(outBuffer,outBuffer.length,destinationAddress[i],destinationPort);
			outPackets1[i] += outPackets1[i];
			j++;
			}
		} while (j <= destinationAddress.length);
		try {
			((Object) this.mySocket).Broadcast(outPackets);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}
	}
	*/
}
