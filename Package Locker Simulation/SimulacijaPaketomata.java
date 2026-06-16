import java.io.*;

public class SimulacijaPaketomata
{
    public static Paketomat paketomat;
    
    public static void main(String[] args) throws Exception
    {
        //1. vpis velikosti omaric - 10 omaric
        paketomat = new Paketomat(); //najprej začne dodajanje omaric

        //2. uporaba programa
        System.out.println("Dobrodošli v paketomatu.\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.println("Za uporabo paketomata vpišite številko:");
            System.out.println("1 - Pregled praznih omaric"); //izpis vseh proste omarice
            System.out.println("2 - Prevzem paketa"); //vzemiPaket
            System.out.println("3 - Oddaja paketa"); // odda paket v paketomat
            System.out.println("4 - Vse omarice"); //izpis vseh omaric
            System.out.println("5 - Izhod iz programa"); //izhod

            int vnosUporabnika = -1;

			try
			{
				vnosUporabnika = Integer.parseInt(br.readLine()); //preberemo vnos in spremenimo v število
			}
			catch(Exception e)
			{
				System.out.println("\nNapaka! Poskusi ponovno.\n");
				continue; //continue - gremo na začetek neskončne zanke
			}

            //gledamo kaj je vnesel uporabnik za uporabo
            switch (vnosUporabnika)
            {
                case 1: //izpis vseh praznih omaric
                    if (paketomat.aliSoVseOmariceZasedene() == false) //če vse omarice niso zasedene
                    {
                        paketomat.izpisVsehProstihOmaric();
                        break;
                    }
                    else
                    {
                        System.out.println("\nVse omarice so zasedene.\n");
                        break;
                    }

                case 2: //prevzem paketa
                    if (paketomat.aliSoVseOmaricePrazne()) //naprej pogleda ali so vse omarice prazne
                    {
                        System.out.println("\nNoben paket ni na voljo za prevzem. Vse omarice so prazne!\n");
                        break;
                    }
                    else
                    {
                        System.out.println("\nVnesite ID omarice:");
                        int IDomarice = Integer.parseInt(br.readLine()); //vnos spremeni v int

                        System.out.println("\nVnesite ime prejemnika:");
                        String imePrejemnikaPrevzem = br.readLine();
                        
                        Paket prevzetiPaket = paketomat.vzemiPaket(imePrejemnikaPrevzem, IDomarice);
                                
                        if (prevzetiPaket != null)
                        {
                            System.out.println("\nPaket je uspešno prevzet.\n");
                        }
                        break;
                    }

                case 3: //dodajanje paketa
                    if (paketomat.aliSoVseOmariceZasedene()) //naprej pogleda ali so vse omarice zasedene
                    {
                        System.out.println("\nVse omarice so zasedene!\n");
                        break;
                    }
                    //če niso zasedene gre na dodajanje
                    paketomat.dodajanjePaketa();
                    break;


                case 4: //izpis vseh omaric
                    paketomat.izpisVsehOmaric();
                    break;

                case 5: //izhod iz programa
                    System.out.println("Nasvidenje!");
                    return; //močnejši kot break - prekine izvajanje

                default:
                    System.out.println("\nNeveljavna izbira, poskusite ponovno!\n");
                    break;
            }
        }
    }
}