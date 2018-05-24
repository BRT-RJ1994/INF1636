package Jogo;

import javax.swing.*;

import Listeners.TratadorClique;
import Pe�a.Pe�a;
import Pe�a.tipoPe�a;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Xadrez extends JFrame {

	public Tabuleiro tabuleiro;
	private int[][] casas = new int[8][8];
	private Pe�a[][] posicoes = new Pe�a[8][8];
	private int selecao = 0; // selecao = 0 -> nada selecionado,  selecao = 1 -> pe�a selecionada
	private Pe�a pe�a;
	private int iOrigem, jOrigem;	// indices de origem da pe�a a ser movimentada
	private int eMovRei = 0;   // indica se esta verificando a movimentacao do rei   (0 -> n�o, 1-> sim)
	private int vez = 0;  // 0 -> brancos, 1-> pretos  (brancos come�am)
	
	public Xadrez(String titulo) {
		
		super(titulo);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension ss = tk.getScreenSize();
		
		criaPe�as();
		zeraCasas();
		
		tabuleiro = new Tabuleiro(posicoes, casas);
		
		tabuleiro.addMouseListener(new TratadorClique(this, tabuleiro));
		this.getContentPane().setBackground(Color.gray);
		this.setLocation(ss.width/4, (2*ss.height-ss.width)/4);
		this.setSize(ss.width/2, ss.width/2);
		
		this.getContentPane().add(tabuleiro);
		
		
	}

	private void zeraCasas () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j< 8; j++) {
				this.casas[i][j] = 0;
			}
		}
	}
	
	private void criaPe�as() {

		for(int i=0; i<8; i++) {
			posicoes[i] = new Pe�a[8];
			for(int j=0; j<8; j++)
				if(i>=2 && i<=5)
					posicoes[i][j] = null;
				else
					posicoes[i][j] = new Pe�a();
		}
		
	}
	
	public void Recebe_Clique(int i, int j) {
		
		if(posicoes[i][j] != null && (posicoes[i][j].getCor() == vez || selecao == 1)) {		// pe�a foi selecionada para come�ar uma jogada
			Pe�a_Selecionada(i,j);
		}
		else if(posicoes[i][j] == null && selecao==1)	// casa vazia foi selecionada ap�s a sele��o de uma pe�a
			Casa_Selecionada(i,j);
		// Nenhum dos casos acima, ignora o clique
		
		tabuleiro.repaint();
		return;	
			
	}
	
	public void Pe�a_Selecionada(int i, int j) {
		
		if(selecao == 0 || pe�a.getCor() == posicoes[i][j].getCor()) { // primeira sele��o ou outra pe�a da mesma cor foi selecionada -> Reinicia a jogada com a nova pe�a
			zeraCasas();
			iOrigem = i;
			jOrigem = j;
			pe�a = posicoes[i][j];
			selecao = 1;
			novaMovimentacao(i,j);
		}
		else {								// Pe�a da outra cor foi selecionada. Verifica se a captura � vi�vel...
			if(casas[i][j] == 1) {
				posicoes[iOrigem][jOrigem] = null;
				posicoes[i][j] = pe�a;
				if (vez == 0) vez = 1;
				else if (vez == 1) vez = 0;
			}
			zeraCasas();
			selecao = 0;
		}
	}

	public void Casa_Selecionada(int i, int j) {
		
		if(casas[i][j] == 1) {	// casa � v�lida -> efetua a movimenta��o
			posicoes[iOrigem][jOrigem] = null;
			posicoes[i][j] = pe�a;
			if (vez == 0) vez = 1;
			else if (vez == 1) vez = 0;
		}
		zeraCasas();
		selecao = 0;
	}
	
	public void novaMovimentacao (int i, int j) {
		
		if (eMovRei == 1) {
			if (posicoes[i][j].getTipo() == tipoPe�a.Peao) movimentaPeaoCaptura(i,j);
		}
		
		else {
			if (posicoes[i][j].getTipo() == tipoPe�a.Peao) movimentaPeao(i,j);
			if (posicoes[i][j].getTipo() == tipoPe�a.Rei) movimentaRei(i,j);
		}
		
		if (posicoes[i][j].getTipo() == tipoPe�a.Torre) movimentaTorre(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Bispo) movimentaBispo(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Cavalo) movimentaCavalo(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Rainha) movimentaRainha(i,j);
		
	}
	
	public void movimentaTorre (int i, int j){
		
		int auxi, auxj;
		
		for (auxi = i+1; auxi < 8; auxi++) {
			if (posicoes[auxi][j]==null) {
				casas[auxi][j] = 1;   // � um movimento poss�vel
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) {
				casas[auxi][j] = 1;   // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][j] = 1;
				break;
			}
		}
		
		for (auxi = i-1; auxi >= 0; auxi--) {
			if (posicoes[auxi][j]==null) {
				casas[auxi][j] = 1;
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) {
				casas[auxi][j] = 1;   // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][j] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
		
		for (auxj = j+1; auxj < 8; auxj++) {
			if(posicoes[i][auxj]==null) {
				casas[i][auxj] = 1;
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) {
				casas[i][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[i][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
		
		for (auxj = j-1; auxj >= 0; auxj--) {
			if(posicoes[i][auxj]==null) {
				casas[i][auxj] = 1;
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) {
				casas[i][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[i][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
	}
	
	public void movimentaBispo (int i, int j) {
		
		int auxi, auxj;
		
		for (auxi = i+1, auxj = j+1; auxi < 8 && auxj < 8; auxi++, auxj++) {
			if (posicoes[auxi][auxj] == null) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
		
		for (auxi = i-1, auxj = j-1; auxi >= 0 && auxj >= 0; auxi--, auxj--) {
			if (posicoes[auxi][auxj] == null) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
		
		for (auxi = i-1, auxj = j+1; auxi >= 0 && auxj < 8; auxi--, auxj++) {
			if (posicoes[auxi][auxj] == null) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
		
		for (auxi = i+1, auxj = j-1; auxi < 8 && auxj >= 0; auxi++, auxj--) {
			if (posicoes[auxi][auxj] == null) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				continue;
			}
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else {
				if (eMovRei == 1) casas[auxi][auxj] = 1;
				break;  // n�o � um movimento poss�vel
			}
		}
	}
	
	public void movimentaCavalo (int i, int j) {
		
		int auxi, auxj;
		
		if ((auxi = i - 1) >= 0) {
			if ((auxj = j - 2) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
			if ((auxj = j + 2) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
		}
		
		if ((auxi = i - 2) >= 0) {
			if ((auxj = j - 1) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
		}
		
		if ((auxi = i + 1) < 8) {
			if ((auxj = j - 2) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
			if ((auxj = j + 2) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
		}
		
		if ((auxi = i + 2) < 8) {
			if ((auxj = j - 1) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
				if (eMovRei == 1) casas[auxi][auxj] = 1;
			}
		}
		
	}

	public void movimentaRainha (int i, int j) {
		movimentaTorre(i,j);
		movimentaBispo(i,j);
	}
	
	public void movimentaPeao (int i, int j) {
		
		if (i == 6 && posicoes[i][j].getCor() == 0 && posicoes[i-2][j] == null && posicoes[i-1][j] == null) casas[i-2][j] = 1;
		if (i == 1 && posicoes[i][j].getCor() == 1 && posicoes[i+2][j] == null && posicoes[i+1][j] == null) casas[i+2][j] = 1;
		if (posicoes[i][j].getCor() == 0 && posicoes[i-1][j] == null && (i - 1) >= 0) casas[i-1][j] = 1;
		if (posicoes[i][j].getCor() == 1 && posicoes[i+1][j] == null && (i + 1) < 8) casas[i+1][j] = 1;
		if (posicoes[i][j].getCor() == 0 && j>0 && posicoes[i-1][j-1]!=null && posicoes[i-1][j-1].getCor() == 1) casas[i-1][j-1] = 1;
		if (posicoes[i][j].getCor() == 0 && j<7 && posicoes[i-1][j+1]!=null && posicoes[i-1][j+1].getCor() == 1) casas[i-1][j+1] = 1;
		if (posicoes[i][j].getCor() == 1 && j>0 && posicoes[i+1][j-1]!=null && posicoes[i+1][j-1].getCor() == 0) casas[i+1][j-1] = 1;
		if (posicoes[i][j].getCor() == 1 && j<7 && posicoes[i+1][j+1]!=null && posicoes[i+1][j+1].getCor() == 0) casas[i+1][j+1] = 1;
		
	}
	
	public void movimentaPeaoCaptura (int i, int j) {
		if (posicoes[i][j].getCor() == 0 && j>0) casas[i-1][j-1] = 1;
		if (posicoes[i][j].getCor() == 0 && j<7) casas[i-1][j+1] = 1;
		if (posicoes[i][j].getCor() == 1 && j>0) casas[i+1][j-1] = 1;
		if (posicoes[i][j].getCor() == 1 && j<7) casas[i+1][j+1] = 1;
	}
	
	public void movimentaRei (int i, int j) {
		
		int auxi, auxj;
		int[][] auxRei = new int[8][8];
		
		eMovRei = 1;
		
		for (auxi = 0; auxi < 8; auxi++) {
			for (auxj = 0; auxj < 8; auxj++) {
				if (posicoes[auxi][auxj] != null && posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					novaMovimentacao(auxi,auxj);
				}
			}
		}
		
		eMovRei = 0;
		
		for (auxi = 0; auxi < 8; auxi++) {
			for (auxj = 0; auxj < 8; auxj++) {
				if (casas[auxi][auxj] == 1) {
					auxRei[auxi][auxj] = 1;
				}
			}
		}
		
		zeraCasas();
		
		if ((auxi = i + 1) < 8) {
			if ((posicoes[auxi][j] == null || posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) && auxRei[auxi][j] != 1) {
				casas[auxi][j] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if ((posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) && auxRei[auxi][auxj] != 1) {
					casas[auxi][auxj] = 1;
				}
			}
			if ((auxj = j - 1) >= 0) {
				if ((posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) && auxRei[auxi][auxj] != 1) {
					casas[auxi][auxj] = 1;
				}
			}
		}
		
		if ((auxi = i - 1) >= 0) {
			if ((posicoes[auxi][j] == null || posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) && auxRei[auxi][j] != 1) {
				casas[auxi][j] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if ((posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) && auxRei[auxi][auxj] != 1) {
					casas[auxi][auxj] = 1;
				}
			}
			if ((auxj = j - 1) >= 0) {
				if ((posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) && auxRei[auxi][auxj] != 1) {
					casas[auxi][auxj] = 1;
				}
			}
		}
		
		if ((auxj = j - 1) >= 0) {
			if ((posicoes[i][auxj] == null || posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) && auxRei[i][auxj] != 1 ) {
				casas[i][auxj] = 1;
			}
		}
		
		if ((auxj = j + 1) < 8) {
			if ((posicoes[i][auxj] == null || posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) && auxRei[i][auxj] != 1) {
				casas[i][auxj] = 1;
			}
		}
	}
}
