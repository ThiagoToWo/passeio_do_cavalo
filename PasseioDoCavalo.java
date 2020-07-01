import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PasseioDoCavalo extends JFrame{	
	
	private JButton[][] tabuleiro = new JButton[8][8];
	private Map<JButton, int[]> local = new HashMap<>();
	private ArrayList<JButton> locaisMarcados = new ArrayList<JButton>();
	private JButton[] casasLegais = new JButton[8];
	private boolean isShow = false;
	
	private int jogada = 0;
	
	public static void main(String[] args) {
		new PasseioDoCavalo().construir();
	}	
	
	public void construir() {
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Comandos");
		JMenuItem desfazerItem = new JMenuItem("desfazer");
		desfazerItem.addActionListener(new DesfazListener());
		JMenuItem novoItem = new JMenuItem("novo");
		novoItem.addActionListener(new NovoListener());
		menu.add(novoItem);
		menu.add(desfazerItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		JPanel northPanel = new JPanel();
		JButton botReturn = new JButton("<<");
		botReturn.addActionListener(new DesfazListener());
		JCheckBox optionSeeMove = new JCheckBox("Mostrar movimentos legais");
		optionSeeMove.addItemListener(new SeeMoveListener());
		northPanel.add(botReturn);
		northPanel.add(optionSeeMove);
		getContentPane().add(BorderLayout.NORTH, northPanel);
		
		JPanel centerPanel = new JPanel();
		GridLayout grid = new GridLayout(8, 8);
		centerPanel.setLayout(grid);
		add(centerPanel);
		
		MoveListener move = new MoveListener();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				JButton casa = new JButton();
				if ((i + j) % 2 == 0) {
					casa.setBackground(Color.white);
				} else {
					casa.setBackground(Color.LIGHT_GRAY);
				}
				tabuleiro[i][j] = casa;
				local.put(tabuleiro[i][j], new int[]{i, j});
				casa.addActionListener(move);
				centerPanel.add(casa);
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(700, 200);;
		setVisible(true);		
	}
	
	private void setCasaLegal(JButton casa) {
		
		int[] casaLocal = local.get(casa);
		int[] vertical = {-1, -2, -2, -1, 1, 2, 2, 1};
		int[] horizontal = {2, 1, -1, -2, -2, -1, 1, 2};	
		
		for (int i = 0; i < 8; i++) {
			try {
				casasLegais[i] = tabuleiro[casaLocal[0] + vertical[i]][casaLocal[1] + horizontal[i]];
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e);
			}
		}		
	}
	
	private void paintCasaLegal() {

		for (int i = 0; i < 8; i++) {
			try {
				casasLegais[i].setBackground(Color.red);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e);
			}
		}
	}
	
	public boolean isLegal(JButton casa) {
		boolean legalChek = false;
		
		for (int i = 0; i < 8; i++) {
			if (casa == casasLegais[i]) {
				legalChek = true;
			}
		}
		return legalChek;		
	}
	
	public class MoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton casaAtual = (JButton) e.getSource();
			
			if (locaisMarcados.isEmpty()) {
				casaAtual.setEnabled(false);
				casaAtual.setBackground(Color.CYAN);
				casaAtual.setText("" + jogada++);
				locaisMarcados.add(casaAtual);
				
			} else {
				setCasaLegal(locaisMarcados.get(jogada - 1));
				
				if (isLegal(casaAtual)) {
					casaAtual.setEnabled(false);
					casaAtual.setBackground(Color.CYAN);
					casaAtual.setText("" + jogada++);
					locaisMarcados.add(casaAtual);
				}
				
			}
		}
	}	

	public class NovoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			dispose();
			new PasseioDoCavalo().construir();
		}

	}
	public class DesfazListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton casaAnterior = locaisMarcados.get(--jogada);
			casaAnterior.setEnabled(true);
			casaAnterior.setText(null);
			int[] localAnterior = local.get(casaAnterior);
			
			if ((localAnterior[0] + localAnterior[1]) % 2 == 0) {
				casaAnterior.setBackground(Color.white);
			} else {
				casaAnterior.setBackground(Color.lightGray);
			}
			locaisMarcados.remove(casaAnterior);
		}

	}
	
	public class SeeMoveListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			JCheckBox cb = (JCheckBox) arg0.getItem();
			if (cb.isSelected()) {
				isShow = true;
			} else {
				isShow = false;
			}
		}
	}	
}
