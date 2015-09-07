package cibertec.edu.pe;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class frmcliente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtmensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmcliente frame = new frmcliente();
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
	public frmcliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIngreseMensaje = new JLabel("INGRESE MENSAJE");
		lblIngreseMensaje.setBounds(10, 11, 108, 14);
		contentPane.add(lblIngreseMensaje);

		txtmensaje = new JTextField();
		txtmensaje.setBounds(116, 11, 139, 20);
		contentPane.add(txtmensaje);
		txtmensaje.setColumns(10);

		JButton btnEnviar = new JButton("ENVIAR");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					Socket cli = new Socket("127.0.0.1", 9091);
					OutputStream out = cli.getOutputStream();
					DataOutputStream flujo = new DataOutputStream(out);
					flujo.writeUTF(txtmensaje.getText().toString());
					cli.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnEnviar.setBounds(254, 10, 81, 23);
		contentPane.add(btnEnviar);
	}
}
