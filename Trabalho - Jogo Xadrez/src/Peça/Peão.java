package Pe�a;

import java.io.File;

import javax.imageio.ImageIO;

public class Pe�o extends Pe�a{
	
	public Pe�o(int c) {
		
		this.cor = c;
		
		try {
			if(cor==0)
				this.img = ImageIO.read(new File("imagens/Pecas_2/CyanP.png"));
			else
				this.img = ImageIO.read(new File("imagens/Pecas_2/PurpleP.png"));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		this.tipo = tipoPe�a.Peao;
		
	}
	
	

}
