public class Location {

    private double longitude;
    private double latitude;
    private String address;
    private String name;

    public Location(double longitude, double latitude, String address, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.name = name;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getAddress() {
        return this.address;
    }

    public String getName() {
        return this.name;
    }
}
