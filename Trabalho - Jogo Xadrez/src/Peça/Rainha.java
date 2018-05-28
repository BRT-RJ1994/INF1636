package Pe�a;

import java.io.File;
import javax.imageio.ImageIO;

public class Rainha extends Pe�a{

	public Rainha(int c) {
		
		this.cor = c;
		
		try {
			if(cor==0)
				this.img = ImageIO.read(new File("imagens/Pecas_2/CyanQ.png"));
			else
				this.img = ImageIO.read(new File("imagens/Pecas_2/PurpleQ.png"));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		this.tipo = tipoPe�a.Rainha;
		
	}
	
}