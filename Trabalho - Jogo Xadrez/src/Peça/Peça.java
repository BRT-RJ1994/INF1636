package Pe�a;

import java.awt.*;
import java.io.*;
import javax.imageio.*;

public abstract class Pe�a{
	
	protected Image img;
	protected int cor;	// define cor da pe�a -> 0 para pe�as brancas, 1 para pe�as pretas
	
	public Pe�a() {
		
	}
	
	public int getCor() { return this.cor;}
	
	public Image getImg() {	return this.img;}
	
}
