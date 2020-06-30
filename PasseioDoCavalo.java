import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PasseioDoCavalo extends JFrame{	
	
	private JButton[][] tabuleiro = new JButton[8][8];
	private Map<JButton, int[]> local = new HashMap<>();
	private ArrayList<JButton> locaisMarcados = new ArrayList<JButton>();
	
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
		
		GridLayout grid = new GridLayout(8, 8);
		setLayout(grid);
		
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
				add(casa);
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(700, 200);;
		setVisible(true);		
	}
	
	private void pintarCasaLegal(JButton casa) {
		
		int[] casaLocal = local.get(casa);
		int[] vertical = {-1, -2, -2, -1, 1, 2, 2, 1};
		int[] horizontal = {2, 1, -1, -2, -2, -1, 1, 2};	
		
		JButton moveLegal0 = tabuleiro[casaLocal[0] + vertical[0]][casaLocal[1] + horizontal[0]];
		JButton moveLegal1 = tabuleiro[casaLocal[0] + vertical[1]][casaLocal[1] + horizontal[1]];
		JButton moveLegal2 = tabuleiro[casaLocal[0] + vertical[2]][casaLocal[1] + horizontal[2]];
		JButton moveLegal3 = tabuleiro[casaLocal[0] + vertical[3]][casaLocal[1] + horizontal[3]];
		JButton moveLegal4 = tabuleiro[casaLocal[0] + vertical[4]][casaLocal[1] + horizontal[4]];
		JButton moveLegal5 = tabuleiro[casaLocal[0] + vertical[5]][casaLocal[1] + horizontal[5]];
		JButton moveLegal6 = tabuleiro[casaLocal[0] + vertical[6]][casaLocal[1] + horizontal[6]];
		JButton moveLegal7 = tabuleiro[casaLocal[0] + vertical[7]][casaLocal[1] + horizontal[7]];
		
		JButton[] casasLegais = {moveLegal0, moveLegal1, moveLegal2, moveLegal3, moveLegal4, moveLegal5, moveLegal6,
				moveLegal7};
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0 & !tabuleiro[i][j].isEnabled()) {
					tabuleiro[i][i].setBackground(Color.white);
				} else {
					tabuleiro[i][i].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		
		for (JButton cl : casasLegais) {
			cl.setBackground(Color.red);
		}
	}
	public class MoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {			
			JButton casaAtual = (JButton) e.getSource();
			casaAtual.setEnabled(false);
			casaAtual.setBackground(Color.CYAN);
			casaAtual.setText("" + jogada++);
			locaisMarcados.add(casaAtual);			
			pintarCasaLegal(casaAtual);
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
}
