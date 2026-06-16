import java.io.*;
import java.util.*;

public class Paketomat 
{
    private ArrayList<Omarica> omarice; //shranjuje tabelo posameznih omaric, v katere lahko vstavimo paket

    //konstruktor
    public Paketomat() throws Exception
    {
        //za vnos uporabnika
        InputStreamReader ist = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ist);

        System.out.println("Kreairanje omaric v paketomatu:");
        System.out.println("Vpišite velikosti omaric (S - majhna, M - srednja, L - velika)\n");
        this.omarice = new ArrayList<>(); //nov seznam za omarice

        //10 omaric
        for (int i = 0; i < 10; i++)
        {
            try
            {
                dodajanjeOmaric(br);
            }
            catch (Exception e)
            {
                System.out.println("Prišlo je do napake pri vnosu omarice.\n");
            }
        }
        System.out.println("Dodanih je 10 omaric.\n");

        //dodajanje dodatnih omaric
        while (true)
        {
            try 
            {
                System.out.println("Želite dodati še kakšno omarico? (da/ne):");
                String vnos = br.readLine().trim().toLowerCase();

                if (vnos.equals("da"))
                {
                    dodajanjeOmaric(br);
                    System.out.println();

                }
                else if (vnos.equals("ne"))
                {
                    System.out.println();
                    break;
                }
                else
                {
                    System.out.println("Vnesite 'da' ali 'ne'.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Prišlo je do napake pri vnosu.\n");
                break;
            }
        }
    }

    //dodajanje omaric ob zagonu programa
    private void dodajanjeOmaric(BufferedReader br) throws Exception
    {
        int velikostOmarice = -1;
        boolean pravilenVnos = false;
        String vnosVelikostOmarice; //prazno

        while (!pravilenVnos) //neskončna zanka, dokler ne vpiše pravilno velikost
        {
            System.out.println("Vnesite velikost omarice (S, M, L):");
            vnosVelikostOmarice = br.readLine().toUpperCase();

            velikostOmarice = Omarica.pretvorbaVelikostOmariceInt(vnosVelikostOmarice); //pretvori SML v 1,2,3

            if (velikostOmarice == 1 || velikostOmarice == 2 || velikostOmarice == 3)
            {
                pravilenVnos = true;
                System.out.println("Omarica velikosti " + vnosVelikostOmarice + " uspešno dodana.\n");
            } 
            else
            {
                System.out.println("Napaka: vpisana napačna velikost. Dovoljene velikosti so S, M in L.\n");
            }
        }

        Omarica novaOmarica = new Omarica(unikatnaOmaricaID(), velikostOmarice);
        omarice.add(novaOmarica); //dodamo omarico v seznam
    }

    //seznam prostih omaric
    public ArrayList<Omarica> prosteOmarice()
    {
        ArrayList<Omarica> proste = new ArrayList<>();

        for (Omarica o : omarice) //za vsako omarico v seznamu omarice
        {
            if (o.getPaket() == null)
            {
                proste.add(o); //dodan na seznam prostih omaric
            }
        }
        return proste; //vrne proste omarice
    }


    //METODE za case 1-4
    //Case 1: izpis vseh praznih omaric
    public void izpisVsehProstihOmaric()
    {
        System.out.println("\n** Seznam prostih omaric **\n");
        int stevec = 1;

        for (Omarica o : this.prosteOmarice()) //za vsako prosto omarico izpiši:
        {
            System.out.println(stevec + ". prosta omarica:");
            System.out.println(o.toString()); //kliče metodo toString (izpis)
            System.out.println();
            stevec++;
        }
    }

    //Case 2: prevzem paketa
    public Paket vzemiPaket(String imePrejemnikaPrevzem, int IDomarice) throws IOException
    {
        for (Omarica o : omarice) //gre čez vse omarice
        {
            Paket p = o.getPaket();
            
            //najprej pogledamo če je vnos ID omarice pravilen in ime prejemnika 
            if (o.getOmaricaID() == IDomarice) //če se ID-ja omarice ujemata
            {
                if (p == null) //če paketa ni v omarici
                {
                    System.out.println("\nTa omarica je prazna.\n");
                    return null;
                }

                if (p.getPrejemnik().equals(imePrejemnikaPrevzem)) //pogledamo če se ime prejemnika ujema
                {
                    
                    //2. preverjanje odkupnine
                    //najprej preverimo, če ima paket odkupnino
                    if(p.getOdkupnina() > 0) //če ima paket odkupnino -> večje od 0
                    {
                        boolean pravilenVnos = false;

                        while (!pravilenVnos)
                        {
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                            System.out.println("Paket ima odkupnino v znesku: " + p.getOdkupnina() + ". Vnesite znesek plačila.");
                            Double vnosOdkupnina = Double.valueOf(br.readLine());
                            
                            if(vnosOdkupnina > p.getOdkupnina()) //če je vnešena odkupnina večja od dejanske
                            {
                                System.out.println("Vračilo: " + (vnosOdkupnina - p.getOdkupnina()));
                                
                                pravilenVnos = true;
                                
                                o.setPaket(null); //sprazni omarico
                                
                                return p; //vrne paket
                            }
                            else if(vnosOdkupnina < p.getOdkupnina()) //če je znesek manjši
                            {
                                System.out.println("Vnešen znesek je premajhen. Poskusite ponovno.");
                                pravilenVnos = false;
                            }
                        }
                    }
                    else //če ni odkupnine
                    {
                        o.setPaket(null); //sprazni omarico
                        System.out.println("");
                    }
                }
                else //če se ime prejemnika ne ujema
                {
                    System.out.println("\nIme prejemnika se ne ujema.\n"); //če se ime ne ujema
                    return null;
                }
            }
            
        }
        //če se ID-ja ne ujemata (napišemo tukaj, drugače pogleda samo prvi ID)
        System.out.println();
        System.out.println("Omarica z ID " + IDomarice + " ne obstaja! Poskusite ponovno.\n");
        return null;
    }    
    
    //Case 3: metoda za dodajanje oz oddajo paketa:
    public void dodajanjePaketa() throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int velikostPaketa = -1;
        boolean pravilenVnos = false;

        while (pravilenVnos == false) //branje velikosti paketa + preverjanje ali je pravilno vnešeno
        {
            System.out.println("Vnesite velikost paketa (S, M, L):");
            String vnosVelikostPaketa = br.readLine().toUpperCase(); //da se bo ujemal
            velikostPaketa = Paket.pretvorbaVelikostPaketa(vnosVelikostPaketa); //pretvori vnos v int
                    
            if (velikostPaketa == 1 || velikostPaketa == 2 || velikostPaketa == 3)
            {
                pravilenVnos = true;
            }
            else
            {
                System.out.println("Napaka: dovoljen vnos je S, M ali L.");
            }
        }

        //branje ostalih obveznih stvari
        System.out.println("Vnesite ime prejemnika:");
        String imePrejemnikaOddaja = br.readLine();

        System.out.println("Vnesite ime pošiljatelja:");
        String imePosiljateljaOddaja = br.readLine();

        System.out.println("Vnesite opis:");
        String opisPaketa = br.readLine();

        System.out.println("Vnesite znesek odkupnine:");
        Double odkupnina = Double.valueOf(br.readLine());

        Paket novPaket = new Paket(imePrejemnikaOddaja, imePosiljateljaOddaja, opisPaketa, velikostPaketa, odkupnina);
        int rezultat = this.dostaviPaket(novPaket);

        if (rezultat != -1)
        {
            System.out.println("\nPaket uspešno oddan v omarico: ID " + rezultat + "\n");
        }
        else
        {
            System.out.println("Napaka pri oddaji paketa.\n");
        }
    }

    //Dostava paketa - prejme paket in ga vstavi v eno od omaric paketomata, če je to možno
    public int dostaviPaket(Paket paket) // išče najmanjšo možno omarico in vrne ID omarice (int)
    {
        Omarica prostaOmarica = null;

        for (Omarica o : this.prosteOmarice())
        {
            //preveri da je omarica prazna in je velikost omarice večja ali enaka od velikosti paketa
            if (o.getPaket() == null && o.getVelikostOmarice() >= paket.getVelikostPaketa())
            {
                //če je prosta omarica še vedno prazna ali je obstoječa prosta omarica večja od trenutne omarice v loopu izbereš to ker je manjša in še vedno dovolj velika za paket
                if (prostaOmarica == null || prostaOmarica.getVelikostOmarice() > o.getVelikostOmarice())
                {
                    prostaOmarica = o;
                }
            }
        }

        if (prostaOmarica != null)
        {
            prostaOmarica.setPaket(paket);
            return prostaOmarica.getOmaricaID();
        }
        return -1; //ni takšne omarice oz ni prosta
    }

    //Case 4: izpis vseh omaric:
    public void izpisVsehOmaric()
    {
        System.out.println("\n** Seznam vseh omaric **\n");
        int stevec = 1;
            
        for (Omarica o : this.getOmarice()) //za vsako omarico izpiši:
        {
            System.out.println(stevec + ". omarica:");
            System.out.println(o.toString()); //kliče metodo toString
            System.out.println();
            stevec++;
        }
    }


    //OSTALE METODE
    //ali so omarice prazne
    public Boolean aliSoVseOmaricePrazne()
    {
        for (Omarica o : this.getOmarice())
        {
            if (o.getPaket() != null) //če je zasedena
            {
                return false; //vse omarice so zasedene
            }
        }
        return true; //so prazne
    }
    
    //ali so omarice zasedene
    public Boolean aliSoVseOmariceZasedene()
    {
        for (Omarica o : this.getOmarice())
        {
            if (o.getPaket() == null)
            {
                return false;
            }
        }
        return true;
    }

    //vrne vse omarice
    public ArrayList<Omarica> getOmarice()
    {
        return this.omarice; //vrne seznam omaric
    }

    //ali lahko omarica sprejme paket - true/false
    public boolean omaricaSprejmePaket(int pretvorbaVelikostOmariceInt, int pretvorbaVelikostPaketa) //obe sta int
    {
        //S = 1, M = 2, L = 3
        //če je velikost omarice večja ali enaka velikosti paketa - vrne true
        return pretvorbaVelikostOmariceInt >= pretvorbaVelikostPaketa;
    }

    //kreiranje ID omarice + ali je unikaten (se ne ponavlja)
    public int unikatnaOmaricaID()
    {
        Random random = new Random();
        
        while (true)
        {
            int ID = random.nextInt(1000); //med 0 in 999
            boolean obstaja = false;

            //preverimo vse omarice!!
            for (Omarica o : this.omarice)
            {
                if (o.getOmaricaID() == ID)
                {
                    obstaja = true;
                    break; //prekine for zanko ker ID že obstaja
                }
            }

            //šele ko pregleda vse ID-je
            if (obstaja == false)
            {
                return ID; //vrne ID, ker še ne obstaja med vsemi obstoječimi
            }
        }
    }
}