
public class MainAnalisadorPDC {

	public static void main(String[] args) {
		Tabuleiro tab = new Tabuleiro(8, 8);		
		Analisador an = new Analisador();
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				try {
					tab.construir();
					an.analisar(tab, tab.getTabuleiro(i, j));
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e);
				}
				
			}
		}
		/*tab.construir();
		an.analisar(tab, tab.getTabuleiro(1, 0));*/
	}

}
