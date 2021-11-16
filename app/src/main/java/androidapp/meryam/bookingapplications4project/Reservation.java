package androidapp.meryam.bookingapplications4project;

import java.util.Date;

public class Reservation {
    private String NomHotel;
    private String DateReservation;
    private String  NombreJour;
    private String documentKey;

    public Reservation(String nomHotel, String dateReservation, String nombreJour) {
        NomHotel = nomHotel;
        DateReservation = dateReservation;
        NombreJour = nombreJour;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public Reservation() {
    }

    public String getNomHotel() {
        return NomHotel;
    }

    public void setNomHotel(String nomHotel) {
        NomHotel = nomHotel;
    }

    public String getDateReservation() {
        return DateReservation;
    }

    public void setDateReservation(String dateReservation) {
        DateReservation = dateReservation;
    }

    public String getNombreJour() {
        return NombreJour;
    }

    public void setNombreJour(String nombreJour) {
        NombreJour = nombreJour;
    }
}
