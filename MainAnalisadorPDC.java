
public class MainAnalisadorPDC {

	public static void main(String[] args) {
		Tabuleiro tab = new Tabuleiro(8, 8);		
		Analisador an = new Analisador();		
		tab.construir();
		an.analisar(tab, tab.getTabuleiro(3, 4));
	}

}
