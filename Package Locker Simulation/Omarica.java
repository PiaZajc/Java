//Vsaka omarica je objekt, ki ima svojo identifikacijsko stevilko (ID), velikost (majhna, srednja, velika - S,M,L) za razlicno velike pakete in paket.

public class Omarica
{
    //lastnosti omarice
    private int omaricaID;
    private int velikostOmarice;
    private Paket paket;

    //konstruktor
    public Omarica (int ID, int velikostOmarice)
    {
        this.omaricaID = ID;
        this.velikostOmarice = velikostOmarice;
        this.paket = null; //na začetku je prazna
    }

    //metode get
    public int getOmaricaID()
    {
        return this.omaricaID;
    }

    public int getVelikostOmarice()
    {
        return this.velikostOmarice;
    }

    public Paket getPaket()
    {
        return this.paket;
    }
    
    //set metoda
    public void setPaket(Paket paket)
    {
        this.paket = paket;
    }


    //pretvorba velikosti omaric v int
    public static int pretvorbaVelikostOmariceInt(String velikost)
    {
        switch (velikost)
        {
            case "S":
                return 1;
            case "M":
                return 2;
            case "L":
                return 3;
            default: 
                return -1;
        }
    }

    //pretvorba velikosti omaric v String iz Int
    public String pretvorbaVelikostOmariceSML()
    {
        switch (this.velikostOmarice)
        {
            case 1:
                return "S";
            case 2:
                return "M";
            case 3:
                return "L";
            default:
                return "";
        }
    }

    //metoda toString
    @Override public String toString()
    {
        String niz = "ID omarice: " + this.omaricaID + "\n";
        niz += "Velikost: " + this.pretvorbaVelikostOmariceSML() + "\n";
        niz += "Zasedena: " + (this.paket == null ? "Ne" : "Da") + "\n";
        return niz;
    }
}