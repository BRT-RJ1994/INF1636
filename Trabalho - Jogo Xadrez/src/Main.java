import Interface.XadrezFrame;
import Jogo.*;
import Listeners.*;

public class Main {
	
	public static void main(String args[]) {
		
		Observador observador;
		Observado observado;
		Tabuleiro tab = Tabuleiro.getTabuleiro();
		XadrezFrame xadrez = new XadrezFrame("Xadrez", tab.getCasas(), tab.getPe�as());
		xadrez.addMouseListener(new TratadorClique(tab, xadrez));
		
		observado = tab;
		observador = xadrez;
		
		observado.add(observador);
		
		xadrez.setVisible(true);
		
	}
	
	
	
}
