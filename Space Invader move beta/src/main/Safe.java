package main;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Safe extends JFrame {

	private static final long serialVersionUID = 75467456546541L;
	private JPanel contentPane;
	private JTextField txtLvl;
	private Safe safe;

	public Safe(ArrayList<String> lvl) {
		this.safe = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setTitle("SAFE");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 11, 404, 229);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 384, 41);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblWollenSieDas = new JLabel("Wollen sie das Level sichern ?");
		lblWollenSieDas.setFont(new Font("Arial Black", Font.BOLD, 21));
		lblWollenSieDas.setHorizontalAlignment(SwingConstants.CENTER);
		lblWollenSieDas.setBounds(0, 0, 384, 41);
		panel_2.add(lblWollenSieDas);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 63, 384, 41);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel = new JLabel("SAFE");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 89));
		lblNewLabel.setBackground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 115, 384, 103);

		txtLvl = new JTextField();
		txtLvl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					safe.setVisible(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (!lvl.isEmpty()) {
						new DateiWriter(Game.ort + "/lvl/" + txtLvl.getText() + ".txt", lvl.toString(), true);
						System.out.println("Level: " + txtLvl.getText() + " wurde erfolgreich gesichert");
						panel_1.setBackground(Color.GREEN);
						panel_1.add(lblNewLabel);
						safe.setVisible(true);
						new Delay(2000);
						safe.setVisible(false);
					} else {
						System.out.println("Datei wurde nicht erstellt, da die Arraylist 'lvl' leer ist.");
					}
				}
			}
		});
		txtLvl.setText("lvl");
		txtLvl.setHorizontalAlignment(SwingConstants.CENTER);
		txtLvl.setFont(new Font("Arial Black", Font.BOLD, 21));
		txtLvl.setBounds(0, 0, 384, 41);
		panel_3.add(txtLvl);
		txtLvl.setColumns(10);

		setVisible(true);
	}
}
