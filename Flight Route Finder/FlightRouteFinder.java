import java.io.*;
import java.util.*;


public class FlightRouteFinder
{
    public static void main(String[] args) throws Exception
    {
        String imeDatoteke = "flights.txt";

        //1. branje datoteke .txt
        ArrayList<String> seznamLetalskihPovezav = datotekaPovezav(imeDatoteke);
        

        //2. 2 seznama možnih letališč - odhodi in prihodi
        ArrayList<String> odhodi = new ArrayList<>();
        ArrayList<String> prihodi = new ArrayList<>();

        seznamLetalisc(seznamLetalskihPovezav, odhodi, prihodi);


        //3. [0] - odhod, [1] - prihod
        ArrayList<String> vnosUporabnika = uporabnik(seznamLetalskihPovezav, imeDatoteke, odhodi, prihodi);

        //3.1 določimo začetno in končno letališče iz arraylista
        String zacetnoLetalisce = vnosUporabnika.get(0);
        String koncnoLetalisce = vnosUporabnika.get(1);
        

        //4. izpis možnih povezav
        izpisPovezav(zacetnoLetalisce, koncnoLetalisce, seznamLetalskihPovezav);
    }


    // 1. BRANJE DATOTEKE in shranjevanje v seznamLetalskihPovezav - Arraylist
    public static ArrayList<String> datotekaPovezav(String imeDatoteke) throws Exception
    {
        //prebere vsebino datoteke
        FileReader fr = new FileReader(imeDatoteke);
        BufferedReader dat = new BufferedReader(fr);

        String vrednost;
        ArrayList<String> seznamLetalskihPovezav = new ArrayList<>();

        //branje posameznih vrednosti
        while((vrednost = dat.readLine()) != null)
        {
            String[] povezava = vrednost.split(" ");

            for(String let : povezava)
            {
                seznamLetalskihPovezav.add(let);
            }
        }
        dat.close();
        System.out.println(); //prazna vrstica
        return seznamLetalskihPovezav;
    }


    // 2. Seznam vseh možnih odhodov in prihodov iz arraylista
    public static void seznamLetalisc(ArrayList<String> seznamLetalskihPovezav, ArrayList<String> odhodi, ArrayList<String> prihodi)
    {
        for(String povezava : seznamLetalskihPovezav)
        {
            String[] imenaLetalisc = povezava.split("-"); //razdeli "LJU-ZAG" na dva dela -> [0]-[1]
            String odhod = imenaLetalisc[0].trim();  //trim odstrani presledke
            String prihod = imenaLetalisc[1].trim();

            //preverja prvi del
            if(!odhodi.contains(odhod)) //če arraylist ne vsebuje tega letališča, ga doda na seznam
            {
                odhodi.add(odhod); //doda prvi del povezave na odhodi
            }

            //preverja drugi del
            if(!prihodi.contains(prihod))
            {
                prihodi.add(prihod); //doda drugi del na prihodi
            }
        }
    }


    // 3. VNOS UPORABNIKA
    public static ArrayList<String> uporabnik(ArrayList<String> seznamLetalskihPovezav, String imeDatoteke, ArrayList<String> odhodi, ArrayList<String> prihodi) throws Exception
    {
        InputStreamReader ist = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ist);


        boolean pravilenVnos = false;

        //1. VNOS IMENA DATOTEKE + izpis možnih letališč prihoda in odhoda
        do
        {
            System.out.println("Vnesite ime datoteke, ki vsebuje podatke o letalskih povezavah (s končnico .txt).");
            String vnosDatoteke = br.readLine().trim(); //s trim odstranimo presledke
            
            if((vnosDatoteke.equals(imeDatoteke)))
            {    
                pravilenVnos = true;
                System.out.println("Ime datoteke je pravilno.\n");
                System.out.println("Možna letališča odhoda: " + odhodi);
                System.out.println("Možna letališča prihoda: " + prihodi);
            }
            else
            {
                System.out.println("Ime datoteke je napačno. Poskusite ponovno.");
                pravilenVnos = false;
            }
        }
        while (!pravilenVnos);


        //2. ZAČETNO LETALIŠČE + preverjamo če obstaja

        String zacetnoLetalisce;
        String koncnoLetalisce;
        ArrayList<String> vnosUporabnika = new ArrayList<>();

        pravilenVnos = false;

        do
		{
            System.out.println("Prosim vnesite ime začetnega letališča (IATA oznaka).");
            zacetnoLetalisce = br.readLine().trim().toUpperCase();

            if (odhodi.contains(zacetnoLetalisce)) //če je začetno letališče med letališči odhoda
			{
				pravilenVnos = true;
                vnosUporabnika.add(zacetnoLetalisce);
                System.out.println("Vnešeno začetno letališče: " + zacetnoLetalisce);
                break;
			}
			else
			{
				System.out.println("Vnešeno letališče ne obstaja. Poskusite ponovno.");
			}
        }
		while (!pravilenVnos);


		System.out.println();


        //3. KONČNO LETALIŠČE + preverjamo če obstaja
        pravilenVnos = false;

        do
		{
            System.out.println("Prosim vnesite ime končnega letališča (IATA oznaka).");
            koncnoLetalisce = br.readLine().trim().toUpperCase();

            if (prihodi.contains(koncnoLetalisce)) //če je končno letališče med letališči prihoda
			{
				pravilenVnos = true;
                vnosUporabnika.add(koncnoLetalisce);
                System.out.println("Vnešeno končno letališče: " + koncnoLetalisce);
                break;
			}
			else
			{
				System.out.println("Vnešeno letališče ne obstaja. Poskusite ponovno.");
                pravilenVnos = false;
			}
		}
		while (!pravilenVnos); //dokler vnos ni pravilen

        System.out.println();

        return vnosUporabnika; //vrne arraylist
    }


    // 4. ISKANJE in IZPIS POVEZAV
    public static void izpisPovezav(String zacetnoLetalisce, String koncnoLetalisce, ArrayList<String> seznamLetalskihPovezav) throws Exception
    {
        //spremenljivke
        String direktnaPovezava = zacetnoLetalisce + "-" + koncnoLetalisce;
        ArrayList<String> moznePovezave = new ArrayList<>();

        //1. direktna povezava
        if (seznamLetalskihPovezav.contains(direktnaPovezava))
        {
            moznePovezave.add("Direktna povezava: " + direktnaPovezava); //direktno povezavo doda na seznam direktnePovezave
        }
        else
        {
            moznePovezave.add("Direktna povezava ne obstaja.");
        }


        //2. povezave z ENIM prestopom
        for (String povezava1 : seznamLetalskihPovezav) //povezava1
        {
            String[] prviLet = povezava1.split("-"); //razdeli "LJU-ZAG" na dva dela: "LJU" in "ZAG" -> prviLet[0] in prviLet[1]

            if (prviLet[0].equals(zacetnoLetalisce)) //preveri ali je odhod (prviDel[0]) isti kot kar uporabnik vpisal kot začetno letališče
            { 
                String prviPrestop = prviLet[1]; //če je true, si zapomne kam gre let (prviPrestop)


                for (String povezava2 : seznamLetalskihPovezav) //povezava2
                {
                    String[] drugiLet = povezava2.split("-"); //razdeli drugo povezavo na odhod in prihod

                    if (drugiLet[0].equals(prviPrestop) && drugiLet[1].equals(koncnoLetalisce)) //preverja ali je drugiLet[0] isti kot prviPrestop in hkrati drugiLet[1] isti kot koncnoLetalisce
                    {
                        moznePovezave.add("Let z 1 prestopom: " + povezava1 + " -> " + povezava2); //dodamo v Arraylist možne poti z 1 prestopom
                    }
                }
            }
        }


        //3. povezave z DVEMA prestopoma
        //povezava1
        for (String povezava1 : seznamLetalskihPovezav) 
        {
            //prvi let
            String[] prviLet = povezava1.split("-"); //razdeli "LJU-ZAG" na dva dela: prviLet[0] in prviLet[1]

            if (prviLet[0].equals(zacetnoLetalisce)) //preveri ali je odhod (prviDel[0]) isti kot kar uporabnik vpisal kot začetno letališče
            { 
                String prviPrestop = prviLet[1];

                //povezava2
                for (String povezava2 : seznamLetalskihPovezav)
                {
                    //drugi let
                    String[] drugiLet = povezava2.split("-");

                    if (drugiLet[0].equals(prviPrestop))
                    {
                        
                        //povezava3
                        for (String povezava3 : seznamLetalskihPovezav)
                        {
                            //tretji let
                            String drugiPrestop = drugiLet[1];
                            String[] tretjiLet = povezava3.split("-");

                            if (tretjiLet[0].equals(drugiPrestop) && tretjiLet[1].equals(koncnoLetalisce))
                            {
                                moznePovezave.add("Let z 2 prestopoma: " + povezava1 + " -> " + povezava2 + " -> " + povezava3);
                            }
                        }
                    }
                }
            }
        }


        //4. izpis vseh najdenih povezav
        System.out.println("Možne povezave med " + zacetnoLetalisce + " in " + koncnoLetalisce + ":");

        for (String pot : moznePovezave)
        {
            System.out.println(pot);
        }
    }
}