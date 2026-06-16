//Vsak paket je objekt, ki vsebuje ime prejemnika, ime posiljatelja, opis paketa in velikost paketa (majhna, srednja, velika).

public class Paket
{
    //lastnosti paketa
    private String prejemnik;
    private String posiljatelj;
    private String opisPaketa;
    private int velikostPaketa;
    private double odkupnina; //double

    //konstruktor
    public Paket(String prejemnik, String posiljatelj, String opisPaketa, int velikostPaketa, double odkupnina)
    {
        this.prejemnik = prejemnik;
        this.posiljatelj = posiljatelj;
        this.opisPaketa = opisPaketa;
        this.velikostPaketa = velikostPaketa;
        this.odkupnina = odkupnina;
    }

    //metode get
    public String getPrejemnik()
    {
        return this.prejemnik;
    }

    public String getPosiljatelj()
    {
        return this.posiljatelj;
    }

    public String getOpisPaketa()
    {
        return this.opisPaketa;
    }

    public int getVelikostPaketa()
    {
        return this.velikostPaketa;
    }

    public double getOdkupnina()
    {
        return this.odkupnina;
    }

    public void setOdkupnina(double odkupnina)
    {
        this.odkupnina = odkupnina;
    }

    //pretvorba velikosti paketa v int
    public static int pretvorbaVelikostPaketa(String velikost)
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
}