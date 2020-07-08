
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tabuleiro extends JFrame{	

	private Map<JButton, int[]> local = new HashMap<>();
	private ArrayList<JButton> locaisMarcados = new ArrayList<JButton>();
	private ArrayList<JButton> casasLegais = new ArrayList<JButton>();	
	private int linha;
	private int coluna;
	private JButton[][] tabuleiro;
	private String[] tabuleiros = {"opções de tabuleiros", "8 x 8", "3 x 4", "3 x 7", "3 x 8", "3 x 9", "3 x 10", "5 x 5"};
	private JComboBox<String> opcaoTabuleiro;
	private JButton casaAtual;
	private int jogada = 0;	
	private String autor = "Autor: Thiago de Oliveira Alves\ntowo497@gmail.com";
	private String versao = "Versão: 0.0 \n 03-07-2020\n\n";
	private String regras = "O problema do cavalo, ou passeio do cavalo, é um problema matemático envolvendo o movimento"
			+ " da peça do\n cavalo no tabuleiro de xadrez. O cavalo é colocado no tabuleiro vazio e, seguindo as regras do"
			+ " jogo, precisa\n passar por todas as casas exatamente uma vez em movimentos consecutivos. Existem diversas"
			+ " soluções\n para o problema, dentre elas 26.534.728.821.064 terminam numa casa onde ele ataca a casa na qual"
			+ " iniciou\n o seu movimento. Esses caminhos são chamados de fechados, pois com mais um movimento o cavalo"
			+ " volta\n para a posição inicial, formando assim um ciclo. Quando o cavalo termina em uma posição em que não"
			+ " é\n possível retornar à casa inicial o caminho é dito aberto. Uma determinada solução fechada pode ser"
			+ " realizada\n iniciando-se de qualquer casa do tabuleiro, o que não é o caso de uma solução aberta.\n\n" 
			+ " O problema aparece no quinto livro de Wilson, Lucas escrito por volta do Séc. XVI que contém uma seção sobre\n"
			+ " o Xadrez. Em manuscritos árabes antigos, o problema era restringido a metade do tabuleiro. Existem"
			+ " algumas\n soluções com um refinamento matemático no qual ao somar os algarismos das ordens dos movimentos"
			+ " nas\n colunas e fileiras o resultado é 260, sendo este tipo de solução proposto inicialmente por Carl"
			+ " Jaenisch em 1862.\n O exercício tem pouco a ver com o xadrez e existe a possibilidade do problema anteceder"
			+ " o jogo e o movimento\n do cavalo ter sido retirado do problema. Durante séculos muitas variações desse"
			+ " problema foram estudadas\n por matemáticos, incluindo Euler que em 1759 foi o primeiro a estudar"
			+ " cientificamente esse problema.\n\n Fonte: https://pt.wikipedia.org/wiki/Problema_do_cavalo";	

	public Tabuleiro(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public JButton getTabuleiro(int linha, int coluna) {
		return tabuleiro[linha][coluna];
	}

	public int[] getLocal(JButton casa) {
		return local.get(casa);
	}

	public JButton getLocaisMarcados(int i) {
		return locaisMarcados.get(i);
	}

	public JButton getCasasLegais(int i) {
		return casasLegais.get(i);
	}
	
	public int getSizeCasasLegais() {
		return casasLegais.size();
	}
	
	public void construir() {
		
		super.setTitle("O Passeio do Cavalo");
		
		tabuleiro = new JButton[linha][coluna];
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Comandos");
		JMenuItem desfazerItem = new JMenuItem("desfazer último movimento");
		desfazerItem.addActionListener(new DesfazListener());
		JMenuItem novoItem = new JMenuItem("novo jogo");
		novoItem.addActionListener(new NovoListener());
		JMenu menuSobre = new JMenu("Informações");
		JMenuItem autoria = new JMenuItem("Autor");
		autoria.addActionListener(new AutorListener());
		JMenuItem versao = new JMenuItem("Sobre o aplicativo");
		versao.addActionListener(new VersaoListener());
		JMenuItem regras = new JMenuItem("Sobre o jogo");
		regras.addActionListener(new RegrasListener());
		menuSobre.add(autoria);
		menuSobre.add(versao);
		menuSobre.add(regras);
		menu.add(novoItem);
		menu.add(desfazerItem);
		menuBar.add(menu);
		menuBar.add(menuSobre);
		setJMenuBar(menuBar);
		
		JPanel northPanel = new JPanel();
		opcaoTabuleiro = new JComboBox<String>(tabuleiros);
		opcaoTabuleiro.addActionListener(new TabuleiroListener());
		northPanel.add(opcaoTabuleiro);
		getContentPane().add(BorderLayout.NORTH, northPanel);
		
		JPanel centerPanel = new JPanel();
		GridLayout grid = new GridLayout(linha, coluna);
		centerPanel.setLayout(grid);
		add(centerPanel);
		
		MoveListener move = new MoveListener();
		
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
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
		
		JPanel southPanel = new JPanel();
		JButton botReturn = new JButton("<<");
		botReturn.addActionListener(new DesfazListener());
		JButton optionSeeMove = new JButton("Mostrar movimentos possíveis");
		optionSeeMove.addActionListener(new SeeMoveListener());
		southPanel.add(botReturn);
		southPanel.add(optionSeeMove);
		getContentPane().add(BorderLayout.SOUTH, southPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setLocation(500, 100);;
		setVisible(true);		
	}
	
	public void move(JButton casa) {
		paintTabuleiro();
		
		casaAtual = casa;
		
		if (locaisMarcados.isEmpty()) {
			casaAtual.setEnabled(false);
			casaAtual.setBackground(Color.CYAN);
			casaAtual.setText("" + ++jogada);
			locaisMarcados.add(casaAtual);
			
		} else {
			setCasaLegal(locaisMarcados.get(jogada - 1));
			
			if (isLegal(casaAtual)) {
				casaAtual.setEnabled(false);
				casaAtual.setBackground(Color.CYAN);
				casaAtual.setText("" + ++jogada);
				locaisMarcados.add(casaAtual);
			}
			
		}
	}
	
	public void setCasaLegal(JButton casa) {
		
		int[] casaLocal = local.get(casa);
		int[] vertical = {-1, -2, -2, -1, 1, 2, 2, 1};
		int[] horizontal = {2, 1, -1, -2, -2, -1, 1, 2};
		
		casasLegais.removeAll(casasLegais);
		
		for (int i = 0; i < 8; i++) {
			int linhaLegal = casaLocal[0] + vertical[i];
			int colunaLegal = casaLocal[1] + horizontal[i];
			
			boolean isLinhaLegal = (linhaLegal >= 0) && (linhaLegal < linha);
			boolean isColunaLegal = (colunaLegal >= 0) && (colunaLegal < coluna);
			
			if (isLinhaLegal && isColunaLegal && tabuleiro[linhaLegal][colunaLegal].isEnabled()) {
				casasLegais.add(tabuleiro[linhaLegal][colunaLegal]);
			}
		}		
	}
	
	private void paintTabuleiro() {

		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				
				if (tabuleiro[i][j].isEnabled()) {
					if ((i + j) % 2 == 0) {
						tabuleiro[i][j].setBackground(Color.white);
					} else {
						tabuleiro[i][j].setBackground(Color.LIGHT_GRAY);
					}
				}				
			}
		}
	}
	
	private void paintCasaLegal() {
		setCasaLegal(locaisMarcados.get(jogada - 1));
		
		for (JButton cl : casasLegais) {
			cl.setBackground(Color.red);
		}
	}
	
	public boolean isLegal(JButton casa) {
		boolean legalChek = false;
		
		for (int i = 0; i < casasLegais.size(); i++) {
			if (casa == casasLegais.get(i)) {
				legalChek = true;
			}
		}
		return legalChek;		
	}
	
	public class MoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {			
			JButton casaClicada = (JButton) e.getSource();
			
			move(casaClicada);
			
		}
	}	

	public class NovoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			dispose();
			new Tabuleiro(8, 8).construir();
		}

	}
	public class DesfazListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			paintTabuleiro();
			
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
	
	public class SeeMoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			paintCasaLegal();
		}

	}
	
	public class TabuleiroListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(opcaoTabuleiro.getSelectedIndex()) {
			case 1:
				dispose();
				new Tabuleiro(8, 8).construir();
				break;
				
			case 2:
				dispose();
				Tabuleiro tab3x4 = new Tabuleiro(3, 4);
				tab3x4.construir();
				tab3x4.setSize(400, 400);
				tab3x4.setLocation(700, 200);
				break;
				
			case 3:
				dispose();
				Tabuleiro tab3x7 = new Tabuleiro(3, 7);
				tab3x7.construir();
				tab3x7.setSize(700, 400);
				tab3x7.setLocation(600, 200);
				break;
				
			case 4:
				dispose();
				Tabuleiro tab3x8 = new Tabuleiro(3, 8);
				tab3x8.construir();
				tab3x8.setSize(800, 400);
				tab3x8.setLocation(600, 200);
				break;
				
			case 5:
				dispose();
				Tabuleiro tab3x9 = new Tabuleiro(3, 9);
				tab3x9.construir();
				tab3x9.setSize(900, 400);
				tab3x9.setLocation(500, 200);
				break;
				
			case 6:
				dispose();
				Tabuleiro tab3x10 = new Tabuleiro(3, 10);
				tab3x10.construir();
				tab3x10.setSize(1000, 400);
				tab3x10.setLocation(450, 200);
				break;
				
			case 7:
				dispose();
				Tabuleiro tab5x5 = new Tabuleiro(5, 5);
				tab5x5.construir();
				tab5x5.setSize(490, 520);
				tab5x5.setLocation(700, 200);
			}
		}
	}
	
	private class AutorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			JOptionPane.showMessageDialog(null, autor, "Sobre mim", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	private class VersaoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, versao, "Sobre este", JOptionPane.INFORMATION_MESSAGE);

		}

	}
	
	public class RegrasListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
				JOptionPane.showMessageDialog(null, regras, "Regras", JOptionPane.INFORMATION_MESSAGE);				
		}
	}
}