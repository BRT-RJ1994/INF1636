package Jogo;

import Pe�a.*;

public interface Observado {
	
	public void add(Observador o);
	
	public void remove(Observador o);
	
	public int[][] getCasas();
	
	public Pe�a[][] getPe�as();
	
}
