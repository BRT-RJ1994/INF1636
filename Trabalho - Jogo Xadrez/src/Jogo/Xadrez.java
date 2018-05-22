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
		
		if(posicoes[i][j] != null)		// pe�a foi selecionada para come�ar uma jogada
			Pe�a_Selecionada(i,j);
		else if(posicoes[i][j] == null && selecao==1)	// casa vazia foi selecionada ap�s a sele��o de uma pe�a
			Casa_Selecionada(i,j);
		else											// Nenhum dos casos acima, ignora o clique
			return;	
			
	}
	
	public void Pe�a_Selecionada(int i, int j) {
		
		if(selecao == 0 || pe�a.getCor() == posicoes[i][j].getCor()) { // primeira sele��o ou outra pe�a da mesma cor foi selecionada -> Reinicia a jogada com a nova pe�a
			iOrigem = i;
			jOrigem = j;
			pe�a = posicoes[i][j];
			selecao = 1;
			novaMovimentacao(i,j);
		}
		else {								// Pe�a da outra cor foi selecionada. Verifica se o movimento � vi�vel...
			if(posicoes[i][j] == pe�a)	// pe�a clicada � a mesma anterior  ->aborta
				return;
			return;	// falta implementar captura de outra pe�a
		}
		
	}

	public void Casa_Selecionada(int i, int j) {
		
		if(casas[i][j] == 1) {	// casa � v�lida -> efetua a movimenta��o
			posicoes[iOrigem][jOrigem] = null;
			posicoes[i][j] = pe�a;
			tabuleiro.repaint();
		}
		zeraCasas();
		selecao = 0;
	}
	
	public void novaMovimentacao (int i, int j) {
		
		if (posicoes[i][j].getTipo() == tipoPe�a.Torre) movimentaTorre(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Bispo) movimentaBispo(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Cavalo) movimentaCavalo(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Rainha) movimentaRainha(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Peao) movimentaPeao(i,j);
		if (posicoes[i][j].getTipo() == tipoPe�a.Rei) movimentaRei(i,j);
		
	}
	
	public void movimentaTorre (int i, int j){
		
		int auxi, auxj;
		
		for (auxi = i; auxi < 8; auxi++) {
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) {
				casas[auxi][j] = 1;   // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][j].getCor()) {
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][j] = 1;  // � um movimento poss�vel
		}
		
		for (auxi = i; auxi >= 0; auxi--) {
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor()) {
				casas[auxi][j] = 1;   // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][j].getCor()){
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][j] = 1;  // � um movimento poss�vel
		}
		for (auxj = j; auxj < 8; auxj++) {
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) {
				casas[i][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[i][auxj].getCor()) {
				break;  // n�o � um movimento poss�vel
			}
			casas[i][auxj] = 1;  // � um movimento poss�vel
		}
		for (auxj = j; auxj >= 0; auxj--) {
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor()) {
				casas[i][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[i][auxj].getCor()) {
				break;  // n�o � um movimento poss�vel
			}
			casas[i][auxj] = 1;  // � um movimento poss�vel
		}
	}
	
	public void movimentaBispo (int i, int j) {
		
		int auxi, auxj;
		
		for (auxi = i, auxj = j; auxi < 8 && auxj < 8; auxi++, auxj++) {
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][auxj].getCor()){
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][auxj] = 1;  // � um movimento poss�vel
		}
		
		for (auxi = i, auxj = j; auxi >= 0 && auxj >= 0; auxi--, auxj--) {
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][auxj].getCor()){
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][auxj] = 1;  // � um movimento poss�vel
		}
		
		for (auxi = i, auxj = j; auxi >= 0 && auxj < 8; auxi--, auxj++) {
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][auxj].getCor()){
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][auxj] = 1;  // � um movimento poss�vel
		}
		
		for (auxi = i, auxj = j; auxi < 8 && auxj >= 0; auxi++, auxj--) {
			if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
				casas[auxi][auxj] = 1;  // � um movimento poss�vel
				break;
			}
			else if (posicoes[i][j].getCor() == posicoes[auxi][auxj].getCor()){
				break;  // n�o � um movimento poss�vel
			}
			casas[auxi][auxj] = 1;  // � um movimento poss�vel
		}
	}
	
	public void movimentaCavalo (int i, int j) {
		
		int auxi, auxj;
		
		if ((auxi = i - 1) >= 0) {
			if ((auxj = j - 2) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
			if ((auxj = j + 2) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
		}
		
		if ((auxi = i - 2) >= 0) {
			if ((auxj = j - 1) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
		}
		
		if ((auxi = i + 1) < 8) {
			if ((auxj = j - 2) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
			if ((auxj = j + 2) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
		}
		
		if ((auxi = i + 2) < 8) {
			if ((auxj = j - 1) >= 0) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[auxi][auxj] == null || posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor()) {
					casas[auxi][auxj] = 1;  // � um movimento poss�vel
				}
			}
		}
		
	}

	public void movimentaRainha (int i, int j) {
		movimentaTorre(i,j);
		movimentaBispo(i,j);
	}
	
	public void movimentaPeao (int i, int j) {
		
	}
	
	public void movimentaRei (int i, int j) {
		
		int auxi, auxj;
		
		if ((auxi = i + 1) < 8) {
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor() || posicoes[auxi][j] == null) {
				casas[auxi][j] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor() || posicoes[auxi][auxj] == null) {
					casas[auxi][auxj] = 1;
				}
			}
			if ((auxj = j - 1) >= 0) {
				if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor() || posicoes[auxi][auxj] == null) {
					casas[auxi][auxj] = 1;
				}
			}
		}
		
		if ((auxi = i - 1) >= 0) {
			if (posicoes[i][j].getCor() != posicoes[auxi][j].getCor() || posicoes[auxi][j] == null) {
				casas[auxi][j] = 1;
			}
			if ((auxj = j + 1) < 8) {
				if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor() || posicoes[auxi][auxj] == null) {
					casas[auxi][auxj] = 1;
				}
			}
			if ((auxj = j - 1) >= 0) {
				if (posicoes[i][j].getCor() != posicoes[auxi][auxj].getCor() || posicoes[auxi][auxj] == null) {
					casas[auxi][auxj] = 1;
				}
			}
		}
		
		if ((auxj = j - 1) >= 0) {
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor() || posicoes[i][auxj] == null) {
				casas[i][auxj] = 1;
			}
		}
		
		if ((auxj = j + 1) < 8) {
			if (posicoes[i][j].getCor() != posicoes[i][auxj].getCor() || posicoes[i][auxj] == null) {
				casas[i][auxj] = 1;
			}
		}
	}
}
