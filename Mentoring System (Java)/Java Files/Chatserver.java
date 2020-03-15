import javax.swing.*;  
import java.awt.event.*;  
import java.io.*;
import java.net.*;

class Chatserver {

	public static void main(String[] args) {
		try{
			MyserverT myservert = new MyserverT();
			myservert.start();
		}
		catch(Exception e){
			
		}
	}
}

class MyserverT extends Thread{

	public JLabel l1,l2;
	public JTextArea area,space;
	public JButton b;
	public DataInputStream din;
	public DataOutputStream dout;
	public ServerSocket ss;
	public String msgin="",msgout="";
	public Socket s;
	MyserverT(){
		JFrame f = new JFrame("mentor");

		l1 = new JLabel();
		l1.setBounds(220,10,280,30);
		l1.setText("mentor");
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

		space = new JTextArea();
		space.setBounds(5,540,490,50);
		space.setFont(space.getFont().deriveFont(25.0f));
		f.add(space);

		f.setSize(500,700);
		f.setLayout(null);
		f.setVisible(true);
	}

	public void run(){
		try{
			ServerSocket ss = new ServerSocket(39521);
            Socket s = ss.accept();
            din = new DataInputStream(s.getInputStream());
	        dout = new DataOutputStream(s.getOutputStream());        
	        while(!msgin.equals("end")){
	               msgin = din.readUTF();
	               area.append("student: "+msgin+"\n");
	               b.addActionListener(new ActionListener(){
	               		public void actionPerformed(ActionEvent e){
	               			msgout = space.getText().toString();
	               			call(msgout,dout);
	               			//space.setText("");
	               			b = new JButton("Send");
	               		}
	               });
	        }
	    }
		catch(Exception e){
			space.setText("Oops...!!! Problem in starting Server Socket");
			space.setEditable(false);
			b.setEnabled(false);
		}
	}

	public void call(String a,DataOutputStream dout){
		try{
			area.append("you: "+a+"\n");
			dout.writeUTF(a);
			dout.flush();
			space.setText("");
		}
		catch(Exception e){
			space.setText("Error in sending message");
			space.setEditable(false);
			b.setEnabled(false);
		}
	}
}