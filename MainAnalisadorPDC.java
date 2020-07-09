
public class MainAnalisadorPDC {

	public static void main(String[] args) {
		Tabuleiro tab = new Tabuleiro(5, 5);		
		Analisador an = new Analisador();		
		tab.construir();
		an.analisar(tab, tab.getTabuleiro(0, 0), an.getAcessibilidade5x5());
	}

}
