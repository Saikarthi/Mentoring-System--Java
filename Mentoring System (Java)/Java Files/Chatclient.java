import javax.swing.*;  
import java.awt.event.*; 
import java.io.*;
import java.net.*;

class Chatclient {

	public static void main(String[] args) {
		try{
			MyclientT myclientt = new MyclientT();
			myclientt.start();
		}
		catch(Exception e){
			
		}
	}
}

class MyclientT extends Thread{

	public JLabel l1,l2;
	public JTextArea area;
	public JTextField space;
	public JButton b;
	public DataInputStream din;
	public DataOutputStream dout;
	public String msgin="",msgout="";
	public Socket s;
	MyclientT(){
		JFrame f = new JFrame("Student");
		l1 = new JLabel();
		l1.setBounds(220,10,280,30);
		l1.setText("Student");
		f.add(l1);

		area = new JTextArea();
		area.setBounds(5,50,490,480);
		area.setVisible(true);
		area.setFont(area.getFont().deriveFont(20.0f));
		area.setEditable(false);
		f.add(area);	

		b = new JButton("Send");
		b.setBounds(200,600,100,40);
		f.add(b);

		space = new JTextField(20);
		space.setBounds(5,540,490,50);
		space.setFont(space.getFont().deriveFont(25.0f));
		f.add(space);

		f.setSize(500,700);
		f.setLayout(null);
		f.setVisible(true);
	}
	public void run(){
		try{
			Socket s = new Socket("localhost",39521);
			din = new DataInputStream(s.getInputStream());
        	dout = new DataOutputStream(s.getOutputStream());
	        while(!msgin.equals("end")){
	               b.addActionListener(new ActionListener(){
	               		public void actionPerformed(ActionEvent e){
	               			msgout = space.getText();
	               			call(msgout,dout);
	               			//space.setText("");
	               			b = new JButton("Send");
	               		}
	               });
	               msgin = din.readUTF();
	               area.append("mentor: "+msgin+"\n");
	           }
		}
		catch(Exception e){
			space.setText("Oops...!!! Problem in starting Client Socket");
			space.setEditable(false);
			b.setEnabled(false);
		}
	}

	public void call(String a,DataOutputStream dout){
		try{
			area.append("You: "+a+"\n"); 
		    dout.writeUTF(a);
		    space.setText("");
		}
		catch(Exception e){
			space.setText("Error in sending message");
			space.setEditable(false);
			b.setEnabled(false);
		}
	}
}