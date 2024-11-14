package src;
public class Aeroport {
    // Attributs de l'aéroport
    private String nom;       // Nom de l'aéroport
    private double latitude;   // Latitude GPS
    private double longitude;  // Longitude GPS
    private String codeIATA;   // Code IATA de l'aéroport

    // Constructeur de la classe Aeroport
    public Aeroport(String nom, String codeIATA, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codeIATA = codeIATA;
    }

    // Méthode pour calculer la distance entre deux aéroports
    public double calculDistance(Aeroport autre) {
        double theta1 = Math.toRadians(this.latitude);
        double theta2 = Math.toRadians(autre.latitude);
        double phi1 = Math.toRadians(this.longitude);
        double phi2 = Math.toRadians(autre.longitude);

        return Math.sqrt(
                Math.pow(theta2 - theta1, 2) +
                        Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2)
        );
    }

    // Surcharge de la méthode toString pour affichage
    @Override
    public String toString() {
        return "Aeroport{" +
                "nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", codeIATA='" + codeIATA + '\'' +
                '}';
    }

    // Getters pour les attributs
    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCodeIATA() {
        return codeIATA;
    }
}

