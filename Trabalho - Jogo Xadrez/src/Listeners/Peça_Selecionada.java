package Listeners;

import java.awt.*;
import java.awt.event.*;

public class Pe�a_Selecionada extends MouseAdapter implements MouseListener{
	
	Component comp;
	
	public Pe�a_Selecionada(Component c) {
		comp = c;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		System.out.println("Clicou em uma pe�a");
	}

}
