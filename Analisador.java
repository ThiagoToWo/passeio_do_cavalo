import java.util.ArrayList;

import javax.swing.JButton;

public class Analisador {
	
	int[][] tabuleiroHeuristico = {{2, 3, 4, 4, 4, 4, 3, 2},
			   					   {3, 4, 6, 6, 6, 6, 4, 3},
			                       {4, 6, 8, 8, 8, 8, 6, 4},	
			                       {4, 6, 8, 8, 8, 8, 6, 4},
			                       {4, 6, 8, 8, 8, 8, 6, 4},
			                       {4, 6, 8, 8, 8, 8, 6, 4},
			                       {3, 4, 6, 6, 6, 6, 4, 3},
			                       {2, 3, 4, 4, 4, 4, 3, 2}};
	
	ArrayList<Integer> valorProximasCasas = new ArrayList<Integer>();

	public void analisar(Tabuleiro tabuleiro, JButton casaInicial) {

		tabuleiro.move(casaInicial); // se move para a casa
		tabuleiro.setCasaLegal(casaInicial); // define as casas legais para se ir no pr�ximo passo
		redefinirAcessibilidade(tabuleiro, casaInicial);
		int tamanhoCasasLegais = tabuleiro.getSizeCasasLegais(); // captura o tamanho da lista de casas legais
		
		//esvazia a lista com os valores de acessibilidade das pr�ximas casas
		valorProximasCasas.removeAll(valorProximasCasas); 
		
		for (int i = 0; i < tamanhoCasasLegais; i++) { // preenche a lista dos valores de acessibilidade das casas legais
			JButton casa = tabuleiro.getCasasLegais(i); // pega a i-�sima casa legal da lista
			int[] localCasaLegal = tabuleiro.getLocal(casa); // captura o endere�o dela
			
			// captura o valor de acessibilidade no mesmo endere�o no tabuleiro heur�stico
			int valorHeuristico = tabuleiroHeuristico[localCasaLegal[0]][localCasaLegal[1]]; 
			valorProximasCasas.add(valorHeuristico); // adiciona esse valor capturado na lista de valores de acessibilidade
		}
		
		// encontra o �ndice da pr�xima casa a ser movida na lista de casas legais 
		int indexProximaCasa = encontrarIndexProximaCasa(valorProximasCasas);
		JButton proximaCasa = tabuleiro.getCasasLegais(indexProximaCasa); // captura a casa desse �ndice

		analisar(tabuleiro, proximaCasa); // repete recursivamente
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
	
	public void redefinirAcessibilidade(Tabuleiro tabuleiro,JButton casaRecente) {
		
		int tamanhoCasasLegais = tabuleiro.getSizeCasasLegais(); // pega o tamanho da lista de casas legais
		
		int[] localCasaRecente = tabuleiro.getLocal(casaRecente); // paga o local da casa recente
		tabuleiroHeuristico[localCasaRecente[0]][localCasaRecente[1]] = 0; // configura esse local como 0 no tabuleiro heur�stico

		for (int i = 0; i < tamanhoCasasLegais; i++) { // configura novos valores para as casas legais da casa recente			
			JButton casa = tabuleiro.getCasasLegais(i); // pega uma casa legal
			int[] localCasaLegal = tabuleiro.getLocal(casa); // captura o endere�o dela
			
			tabuleiroHeuristico[localCasaLegal[0]][localCasaLegal[1]]--;// decrementa no mesmo local no tabuleiro heur�stico			
		}

	}
}
