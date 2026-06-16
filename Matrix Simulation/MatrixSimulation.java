import java.io.*;
import java.util.*; 

public class MatrixSimulation
{
	public static void main (String[] args) throws Exception
	{

        int n = Integer.parseInt(args[0]); 
        int m = Integer.parseInt(args[1]); 

		int[][] tabela = ustvariTabelo(n, m);

		System.out.println("Originalna tabela:");
        izpisiTabelo(tabela);

		prestejSosede(tabela);

	}
	
	public static int[][] ustvariTabelo(int n, int m) throws Exception
	{
		//spremenljivke
		double vnosVerjetnost = 0.0; 
		boolean pravilenVnos = false;

		System.out.println("Vnesite število med 0 in 1 za verjetnost, da bo v matriki v določenem polju napisana 1 ali 0: ");
       	
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

		do
		{
			vnosVerjetnost = Double.parseDouble(br.readLine());

			if (vnosVerjetnost < 0 || vnosVerjetnost > 1)
			{
				System.out.println("Vnešena verjenost je napačna. Vnesite število med 0 in 1.");
				pravilenVnos = false;
			}
			else
			{
				pravilenVnos = true;
			}	
		}
		while (pravilenVnos == false);
		System.out.println("Vnešena verjetnost: " + vnosVerjetnost);


		int[][] tabela = new int[n][m];

		Random random = new Random();
		
		for (int i = 0; i < n; i++)
		{  
            for (int j = 0; j < m; j++)
			{  
				double randomSt = random.nextDouble(1); //vsakic zgeneriramo random stevilo med 0 in 1 (1 ni vkljucena)
				tabela[i][j] = (randomSt < vnosVerjetnost ? 1 : 0);
			}
			System.out.println();
        }
		return tabela;
	}	


	public static void izpisiTabelo(int[][] tabela)
	{
    	for (int i = 0; i < tabela.length; i++) 
		{
        	for (int j = 0; j < tabela[i].length; j++)
			{
            	System.out.print(tabela[i][j] + " ");
        	}
            System.out.println();
        }
		System.out.println();
    }


	public static void prestejSosede(int[][] tabela) throws Exception
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		System.out.println("Vnesite željeno število korakov oziroma ponovitev:");
		int vnosKorak = Integer.parseInt(br.readLine());
		System.out.println("Vnešeno število korakov: " + vnosKorak);

		System.out.println();

		int n = tabela.length; 
		int m = tabela[0].length;

		System.out.println();

		//koordinate x in y za preverjanje sosedov
		int[] xOs = {-1, -1, -1, 0, 0, 1, 1, 1}; //tabela za x os
        int[] yOs = {-1, 0, 1, -1, 1, -1, 0, 1}; //tabela za y os

		/*			  	 xOs  yOs
		Levo zgoraj		(-1,  -1)
		Levo sredina 	(-1,   0)
		Levo spodaj 	(-1,   1)
		Sredina zgoraj 	(0,   -1)
		SREDINA SREDINA (0,    0) - nasa pozicija
		Sredina spodaj 	(0,    1)
		Desno zgoraj 	(1,   -1)
		Desno sredina 	(1,    0)
		Desno spodaj 	(1,    1)
		*/

		for (int k = 0; k < vnosKorak; k++)
		{ 
            int[][] novaTabela = new int[n][m];

			for (int i = 0; i < n; i++) 
			{
            	for (int j = 0; j < m; j++)
				{
					//seštevek sosednjih enk:
                	int steviloEnk = 0;
                	
                	for (int sosed = 0; sosed < 8; sosed++)
					{
                    	int sosedXos = i + xOs[sosed];
                    	int sosedYos = j + yOs[sosed];

                    	if (sosedXos >= 0 && sosedXos < n && sosedYos >= 0 && sosedYos < m && tabela[sosedXos][sosedYos] == 1)
						{
                    		steviloEnk++;
                    	}
                	}

                	if (tabela[i][j] == 1)
					{
                    	novaTabela[i][j] = (steviloEnk == 2 || steviloEnk == 3 ? 1 : 0);

                	} 
					else 
					{
                    	novaTabela[i][j] = (steviloEnk == 3) ? 1 : 0;
                	}
            	}
        	}
			tabela = novaTabela;
        	System.out.println("Tabela po " + (k + 1) + ". koraku: ");
        	izpisiTabelo(novaTabela);
		}
	}
}