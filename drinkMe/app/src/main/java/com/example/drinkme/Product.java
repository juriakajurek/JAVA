package com.example.drinkme;

public class Product {

    private String Id;
    private String Nazwa;
    private String Liczba;
    private String Opis;
    private String Cena;

    public Product (String id, String naz, String lic, String opi, String cen) {
        Id = id;
        Nazwa = naz;
        Liczba = lic;
        Opis = opi;
        Cena = cen;
    }

    public String getIdNum() { return Id; }

    public String getNazwa() {
        return Nazwa;
    }

    public String getLiczba() {
        return Liczba;
    }

    public String getOpis() {
        return Opis;
    }

    public String getCena() {
        return Cena;
    }
}
