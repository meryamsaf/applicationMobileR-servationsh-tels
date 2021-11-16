package androidapp.meryam.bookingapplications4project;

public class Hotel {
   private String  hotelName;
    private String hotelLocation;
    private String hotelVille;
    private String hotelDetails;
    private String imageUrl;

    public Hotel() {
    }

    public Hotel(String hotelName, String hotelLocation, String hotelVille, String hotelDetails, String imageUrl) {
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.hotelVille = hotelVille;
        this.hotelDetails = hotelDetails;
        this.imageUrl = imageUrl;
    }

    public Hotel(String hotelName, String hotelVille, String imageUrl) {
        this.hotelName = hotelName;
        this.hotelVille = hotelVille;
        this.imageUrl = imageUrl;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelVille() {
        return hotelVille;
    }

    public void setHotelVille(String hotelVille) {
        this.hotelVille = hotelVille;
    }

    public String getHotelDetails() {
        return hotelDetails;
    }

    public void setHotelDetails(String hotelDetails) {
        this.hotelDetails = hotelDetails;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
