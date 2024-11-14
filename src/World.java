package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    protected List<Aeroport> list; // Liste de tous les aéroports

    // Constructeur pour charger les aéroports à partir d'un fichier CSV
    public World(String fileName) {
        this.list = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();

            while (s != null) {
                s = s.replaceAll("\"", ""); // Supprime les guillemets
                String fields[] = s.split(",");

                // Vérifie que la ligne contient suffisamment de champs
                if (fields.length > 11 && fields[1].equals("large_airport")) {
                    try {
                        String codeIATA = fields[9];
                        String nom = fields[2];

                        // Vérifie que les coordonnées contiennent bien latitude et longitude
                        double longitude = Double.parseDouble(fields[11]);
                        double latitude = Double.parseDouble(fields[12]);

                        // Ajoute l'aéroport à la liste
                        list.add(new Aeroport(nom, codeIATA, latitude, longitude));

                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de format dans la ligne : " + s);
                    }
                }
                s = buf.readLine(); // Lit la ligne suivante
            }
            buf.close();
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there?");
            if (!list.isEmpty()) {
                System.out.println(list.get(list.size() - 1));
            }
            e.printStackTrace();
        }
    }

    // Méthode pour charger les aéroports depuis un fichier CSV
    private void chargerAeroports(String cheminFichier) {
        try (BufferedReader buf = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = buf.readLine()) != null) {
                ligne = ligne.replaceAll("\"", ""); // Supprime les guillemets
                String[] champs = ligne.split(",");

                if (champs[1].equals("large_airport")) { // Filtre pour les grands aéroports
                    String codeIATA = champs[8];
                    String nom = champs[2];
                    double latitude = Double.parseDouble(champs[11].split(" ")[1]);
                    double longitude = Double.parseDouble(champs[12].split(" ")[0]);

                    list.add(new Aeroport(nom, codeIATA, latitude, longitude));
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Retourne la liste de tous les aéroports
    public List<Aeroport> getList() {
        return list;
    }

    // Trouve l'aéroport le plus proche des coordonnées GPS données
    public Aeroport findNearest(double longitude, double latitude) {
        Aeroport aeroportProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double distance = distance(longitude, latitude, aeroport.getLongitude(), aeroport.getLatitude());
            if (distance < distanceMin) {
                distanceMin = distance;
                aeroportProche = aeroport;
            }
        }
        return aeroportProche;
    }

    // Recherche un aéroport par son code IATA
    public Aeroport findByCode(String codeIATA) {
        for (Aeroport aeroport : list) {
            if (aeroport.getCodeIATA().equalsIgnoreCase(codeIATA)) {
                return aeroport;
            }
        }
        return null; // Aucun aéroport trouvé
    }

    // Calcule la distance entre deux points GPS
    public double distance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double theta1 = Math.toRadians(latitude1);
        double theta2 = Math.toRadians(latitude2);
        double phi1 = Math.toRadians(longitude1);
        double phi2 = Math.toRadians(longitude2);

        return Math.sqrt(
                Math.pow(theta2 - theta1, 2) + Math.pow((phi2 - phi1) * Math.cos((theta2 + theta1) / 2), 2)
        );
    }

}
