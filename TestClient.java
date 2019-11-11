import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TestClient extends Socket {

	public TestClient(int myPortNumber) {
		super(myPortNumber);
		/**
		 * 
		 */
		
		
	}

	// end constructor testClient
	/*
	@Override
	public void actionPerformed(ActionEvent event) {
		
		JTextField textField = new JTextField();
		textField.addActionListener(
new ActionListener()
{

	try
	{
		String message = event.getActionCommand();
		textArea.append( message );

		byte[] data = message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket( data, data.length, InetAddress.getLocalHost(), 64000);

		socket.send( sendPacket );
		textArea.append( "\n" );
		textArea.setCaretPosition( textArea.getText().length() );

	} catch ( IOException ioException )
	{
		ioException.printStackTrace();
	}


}//end action performed
	});
	*/	
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
           	 
                 TestClient app = new TestClient(myPortNumber);
                 app.setVisible(true);
                 app.setSize(500, 500);
            } 
       });
        
        
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
