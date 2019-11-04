import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.*;
 

public class DuvallChatClient extends JFrame implements ActionListener {
	
	/**
	 * Author: Duvall Pinkney
	 * email: Duvall.Pinkney@gmail.com
	 * For this assignment you will be extending what you learned about socket programming
	 *  in Datagram Socket Demo we did in class.

You will be implementing a text messaging application. You will have a seperate window 
for each person you are communicating with. The window will have as a title the IP address 
and port number of the person you are messaging with.

You will have a main window that allows you to initiate messaging sessions with other
 people based on their IP address and port number. Once you initiate a new messaging 
 session, you will create a new window for that session. This window should also have
  a button to exit the program.

You will still have the receiving Thread which will handle all the incoming messages.
 However, your code will also keep track of the source IP addresses and port number 
 along with the window containing that messaging session. If the source IP address
and port number combination is a new one, you will have to open a new messaging window.
 Otherwise, the incoming message will be displayed in the existing window for that source
  IP address.

Each window will have the following components:

The title of the window should be the IP address and port number of the other side of
 the messaging session.
A section to display the messaging interaction. This window should contain the messages 
from both you and the person you are messaging with.
A section for you to type your reply.
A button to send your reply.
A button the close and end the messaging session.
	 */
	private static final long serialVersionUID = 2903920085975133607L;
	//JDesktopPane desktop;
	
	@SuppressWarnings("unused")
	private DatagramSocket hostSocket;
	private static int portNumber;
	
	private Object myAddress = null;
	
	@SuppressWarnings("unused")
	private boolean b;
	private Socket outSocket;
	
	public DuvallChatClient() {
		super("Duvall Chat Client:");
		
		InetAddress myAddress = null;
		try {
			myAddress = InetAddress.getLocalHost();
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		int portNumber = 64000;
		//myAddress = myAddress + ":" + portNumber;

		//try {
		    Socket inSocket = null;
			try {
				inSocket = new Socket(myAddress, portNumber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    PrintWriter out = null;
			try {
				out = new PrintWriter(outSocket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		        String messageFromServer = null;
				String messageFromUser = null;
				
				try {
					while ((messageFromServer = in.readLine()) != null) {
						System.out.println(inSocket + ":" + messageFromServer);
						if (messageFromServer.contentEquals("Bye."))
							break;
						
						
						messageFromUser = stdIn.readLine();
						if(messageFromUser != null) {
							System.out.println(myAddress + ":" + messageFromUser);
							System.out.println(messageFromUser);
							//PrintWriter out = new PrintWriter();
							out.println(messageFromUser);
							
						}
						JTextArea textArea = new JTextArea();
						try
						{
							
							textArea.append( messageFromUser );

							byte[] data = messageFromUser.getBytes();
							DatagramPacket sendPacket = new DatagramPacket( data, data.length,
									InetAddress.getLocalHost(), 64000);

							out.println( sendPacket );
							textArea.append( "\n" );
							textArea.setCaretPosition( textArea.getText().length() );

						} catch ( IOException ioException )
						{
							ioException.printStackTrace();
						}

						Container contentPane = getContentPane();
						Component textField = null;
						contentPane.add( textField, BorderLayout.NORTH );

						textArea = new JTextArea( 4, 30 );
						contentPane.add(new JScrollPane( textArea ), BorderLayout.CENTER );
						contentPane.setSize( 400, 300 );
						contentPane.setVisible( true );


						JButton newSessionButton = new JButton("New Session");
						contentPane.add(newSessionButton);

						JButton sendButton = new JButton("SEND");
						contentPane.add(sendButton);

						JButton exitButton = new JButton("EXIT");
						contentPane.add(exitButton);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}// end constructor
	
	public DuvallChatClient(String string) {
		InetAddress myAddress = null;
		try {
			myAddress = InetAddress.getLocalHost();
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int portNumber = 64000;
		//myAddress = myAddress + ":" + portNumber;

		
		    Socket inSocket = null;
			try {
				inSocket = new Socket(myAddress, portNumber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    PrintWriter out = null;
			try {
				out = new PrintWriter(outSocket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		        String messageFromServer = null;
				String messageFromUser = null;
				
				try {
					while ((messageFromServer = in.readLine()) != null) {
						System.out.println(inSocket + ":" + messageFromServer);
						if (messageFromServer.contentEquals("Bye."))
							break;
						
						
						messageFromUser = stdIn.readLine();
						if(messageFromUser != null) {
							System.out.println(myAddress + ":" + messageFromUser);
							System.out.println(messageFromUser);
							out.println(messageFromUser);
						}
						JTextArea textArea = new JTextArea();
						try
						{
							
							textArea.append( messageFromUser );

							byte[] data = messageFromUser.getBytes();
							DatagramPacket sendPacket = new DatagramPacket( data, data.length,
									InetAddress.getLocalHost(), 64000);

							out.println( sendPacket );
							textArea.append( "\n" );
							textArea.setCaretPosition( textArea.getText().length() );

						} catch ( IOException ioException )
						{
							ioException.printStackTrace();
						}
						

						Container contentPane = getContentPane();
						Component textField = null;
						contentPane.add( textField, BorderLayout.NORTH );

						textArea = new JTextArea( 4, 30 );
						contentPane.add(new JScrollPane( textArea ), BorderLayout.CENTER );
						contentPane.setSize( 400, 300 );
						contentPane.setVisible( true );


						JButton newSessionButton = new JButton("New Session");
						contentPane.add(newSessionButton);

						JButton sendButton = new JButton("SEND");
						contentPane.add(sendButton);

						JButton exitButton = new JButton("EXIT");
						contentPane.add(exitButton);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					}// end constructor with string title

	/*
	public void DuvallChatClientWindow() {
		
		
		int inset = 50;
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset,
				screensize.width = inset*2,
				screensize.height - inset*2);
		
		desktop = new JDesktopPane();
		try {
			createFrame();
		} catch (PropertyVetoException e) {
			
			e.printStackTrace();
		}
		setContentPane(desktop);
		setJMenuBar(createMenuBar());
		
		desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
	}
		protected JMenuBar createMenuBar() {
			JMenuBar menuBar = new JMenuBar();

			JMenu menu = new JMenu("Menu");
			menuBar.add(menu);

			JMenuItem menuItem = new JMenuItem("New Chat Window");
			menuItem.setMnemonic(KeyEvent.VK_N);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
			menuItem.setActionCommand("new chat window");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			JMenuItem menuItem2 = new JMenuItem("Quit");
			menuItem2.setMnemonic(KeyEvent.VK_Q);
			menuItem2.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_Q, ActionEvent.ALT_MASK));
			menuItem2.setActionCommand("quit");
			menuItem2.addActionListener(this);
			menu.add(menuItem2);
			
			JMenuItem menuItem3 = new JMenuItem("Broadcast");
			menuItem3.setMnemonic(KeyEvent.VK_N);
			menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
			menuItem3.setActionCommand("new chat window");
			menuItem3.addActionListener(this);
			menu.add(menuItem3);
			
			JMenuItem menuItem4 = new JMenuItem("No Broadcast");
			menuItem4.setMnemonic(KeyEvent.VK_N);
			menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
			menuItem4.setActionCommand("new chat window");
			menuItem4.addActionListener(this);
			menu.add(menuItem4);

			return menuBar;

		}
		*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("new chat window".contentEquals(e.getActionCommand())) {
			//createFrame();
			
			DuvallChatClient frame = null;
			try {
				frame = new DuvallChatClient(InetAddress.getLocalHost().toString() + portNumber);
			} catch (HeadlessException | java.net.UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.setVisible(true);
		}else {
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if ("Broadcast".equals(e.getActionCommand())) {
			try {
				this.hostSocket = new DatagramSocket(portNumber);
			}catch (SocketException se) {
				se.printStackTrace();
				System.exit(-1);
			}
		}else if("NoBroadcast".contentEquals(e.getActionCommand())){
				try {
					try {
						this.hostSocket = new DatagramSocket(portNumber, InetAddress.getLocalHost());
						JFrame frame = new JFrame(InetAddress.getLocalHost().toString() + portNumber);
						frame.setVisible(true);
						
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (java.net.UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}// end if
	}
		
	/*
	protected void createFrame() throws java.beans.PropertyVetoException {
		DuvallChatClient frame = new DuvallChatClient();
		frame.setVisible(true);
		desktop.add(frame);
		frame.setSelected(true);
	}
	
	/*
	private boolean setSelected(boolean b) {
		this.b = b;
		b = false;
		return b;
	}
	protected void quit() {
        System.exit(0);
    }
	*/
	
	private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);
 
        //Create and set up the window.
        DuvallChatClient frame = null;
		try {
			frame = new DuvallChatClient(InetAddress.getLocalHost().toString() + portNumber);
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Display the window.
        frame.setVisible(true);
        frame.setSize(500,500);
    }

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public Object getMyAddress() {
		return myAddress;
	}

	public void setMyAddress(Object myAddress) {
		this.myAddress = myAddress;
		try {
			myAddress = InetAddress.getLocalHost();
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
