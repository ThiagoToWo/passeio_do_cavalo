import java.util.ArrayList;

import javax.swing.JButton;

public class Analisador {
	
	private int[][] tabuleiroHeuristico8x8 = {{2, 3, 4, 4, 4, 4, 3, 2},
			   					              {3, 4, 6, 6, 6, 6, 4, 3},
			   					              {4, 6, 8, 8, 8, 8, 6, 4},	
			   					              {4, 6, 8, 8, 8, 8, 6, 4},
			   					              {4, 6, 8, 8, 8, 8, 6, 4},
			   					              {4, 6, 8, 8, 8, 8, 6, 4},
			   					              {3, 4, 6, 6, 6, 6, 4, 3},
			   					              {2, 3, 4, 4, 4, 4, 3, 2}};
	
	private int[][] tabuleiroHeuristico3x4 = {{2, 3, 3, 2},
											  {2, 2, 2, 2},
											  {2, 3, 3, 2}};
	
	private int[][] tabuleiroHeuristico3x7 = {{2, 3, 4, 4, 4, 3, 2},
			                                  {2, 2, 4, 4, 4, 2, 2},
			                                  {2, 3, 4, 4, 4, 3, 2}};
	
	private int[][] tabuleiroHeuristico3x8 = {{2, 3, 4, 4, 4, 4, 3, 2},
				               			      {2, 2, 4, 4, 4, 4, 2, 2},
				               			      {2, 3, 4, 4, 4, 4, 3, 2}};
	
	private int[][] tabuleiroHeuristico3x9 = {{2, 3, 4, 4, 4, 4, 4, 3, 2},
            								  {2, 2, 4, 4, 4, 4, 4, 2, 2},
            								  {2, 3, 4, 4, 4, 4, 4, 3, 2}};
	
	private int[][] tabuleiroHeuristico3x10 = {{2, 3, 4, 4, 4, 4, 4, 4, 3, 2},
											   {2, 2, 4, 4, 4, 4, 4, 4, 2, 2},
											   {2, 3, 4, 4, 4, 4, 4, 4, 3, 2}};
	
	private int[][] tabuleiroHeuristico5x5 = {{2, 3, 4, 3, 2},
			  								  {3, 4, 6, 4, 3},
			  								  {4, 6, 8, 6, 4},
			  								  {3, 4, 6, 4, 3},
			  								  {2, 3, 4, 3, 2}};
	
	public int[][] getAcessibilidade8x8() {		
		return tabuleiroHeuristico8x8;
	}
           
	public int[][] getAcessibilidade3x4() {		
		return tabuleiroHeuristico3x4;
	}
	
	public int[][] getAcessibilidade3x7() {		
		return tabuleiroHeuristico3x7;
	}
	
	public int[][] getAcessibilidade3x8() {		
		return tabuleiroHeuristico3x8;
	}
	
	public int[][] getAcessibilidade3x9() {		
		return tabuleiroHeuristico3x9;
	}
	
	public int[][] getAcessibilidade3x10() {		
		return tabuleiroHeuristico3x10;
	}
	
	public int[][] getAcessibilidade5x5() {		
		return tabuleiroHeuristico5x5;
	}
	
	ArrayList<Integer> valorProximasCasas = new ArrayList<Integer>();

	public void analisar(Tabuleiro tabuleiro, JButton casaInicial, int[][] tabuleiroHeuristico) {

		tabuleiro.move(casaInicial); // se move para a casa
		tabuleiro.setCasaLegal(casaInicial); // define as casas legais para se ir no próximo passo
		redefinirAcessibilidade(tabuleiro, casaInicial, tabuleiroHeuristico);
		int tamanhoCasasLegais = tabuleiro.getSizeCasasLegais(); // captura o tamanho da lista de casas legais
		
		//esvazia a lista com os valores de acessibilidade das próximas casas
		valorProximasCasas.removeAll(valorProximasCasas); 
		
		for (int i = 0; i < tamanhoCasasLegais; i++) { // preenche a lista dos valores de acessibilidade das casas legais
			JButton casa = tabuleiro.getCasasLegais(i); // pega a i-ésima casa legal da lista
			int[] localCasaLegal = tabuleiro.getLocal(casa); // captura o endereço dela
			
			// captura o valor de acessibilidade no mesmo endereço no tabuleiro heurístico
			int valorHeuristico = tabuleiroHeuristico[localCasaLegal[0]][localCasaLegal[1]]; 
			valorProximasCasas.add(valorHeuristico); // adiciona esse valor capturado na lista de valores de acessibilidade
		}
		
		// encontra o índice da próxima casa a ser movida na lista de casas legais 
		int indexProximaCasa = encontrarIndexProximaCasa(valorProximasCasas);
		JButton proximaCasa = tabuleiro.getCasasLegais(indexProximaCasa); // captura a casa desse índice

		analisar(tabuleiro, proximaCasa, tabuleiroHeuristico); // repete recursivamente
	}

	private int encontrarIndexProximaCasa(ArrayList<Integer> valorDasJogadas) {
		int indiceDaProximaCasa = 0;
		int valorDaProximaCasa = valorDasJogadas.get(0);
		
		for (int i = 1; i < valorDasJogadas.size(); i++) {
			if (valorDasJogadas.get(i) < valorDaProximaCasa) {
				indiceDaProximaCasa = i;
				valorDaProximaCasa = valorDasJogadas.get(i);
			}
		}
		
		return indiceDaProximaCasa;		
	}
	
	private void redefinirAcessibilidade(Tabuleiro tabuleiro,JButton casaRecente, int[][] tabuleiroHeuristico) {
		
		int tamanhoCasasLegais = tabuleiro.getSizeCasasLegais(); // pega o tamanho da lista de casas legais
		
		int[] localCasaRecente = tabuleiro.getLocal(casaRecente); // paga o local da casa recente
		tabuleiroHeuristico[localCasaRecente[0]][localCasaRecente[1]] = 0; // configura esse local como 0 no tabuleiro heurístico

		for (int i = 0; i < tamanhoCasasLegais; i++) { // configura novos valores para as casas legais da casa recente			
			JButton casa = tabuleiro.getCasasLegais(i); // pega uma casa legal
			int[] localCasaLegal = tabuleiro.getLocal(casa); // captura o endereço dela
			
			tabuleiroHeuristico[localCasaLegal[0]][localCasaLegal[1]]--;// decrementa no mesmo local no tabuleiro heurístico			
		}

	}
	
}
