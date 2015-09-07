package cibertec.edu.socket;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class frmServer extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextArea txtMensajes;
	private JTextField txtMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmServer frame = new frmServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		txtMensajes = new JTextArea();
		txtMensajes.setBounds(10, 0, 451, 98);
		contentPane.add(txtMensajes);
		
		JLabel lblMensaje = new JLabel("Mensaje");
		lblMensaje.setBounds(10, 128, 46, 14);
		contentPane.add(lblMensaje);
		
		txtMensaje = new JTextField();
		txtMensaje.setBounds(76, 125, 277, 20);
		contentPane.add(txtMensaje);
		txtMensaje.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket cli = new Socket("127.0.1.1", 9091);					
					OutputStream out = cli.getOutputStream();					
					DataOutputStream flujo = new DataOutputStream(out);
					flujo.writeUTF(txtMensaje.getText().toString());
					cli.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnEnviar.setBounds(363, 120, 98, 30);
		contentPane.add(btnEnviar);
		
		Thread hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		try {
			ServerSocket serv = new ServerSocket(9090);
			Socket cli;
			while (true) {
				cli = serv.accept();
				InputStream in = cli.getInputStream();
				DataInputStream flujo = new DataInputStream(in);
				String msg = flujo.readUTF();

				txtMensajes.append(cli.getInetAddress() + msg + "\n");
				cli.close();
				if (msg.equalsIgnoreCase("fin")) {
					serv.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
