import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSPSimulatedAnnealing {
    public static void main(String[] args) {
        // Número de cidades
        int numCities = 10;

        // Gere aleatoriamente as coordenadas das cidades
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            cities.add(new City(i, Math.random() * 100, Math.random() * 100));
        }

        // Parâmetros do Simulated Annealing
        double initialTemperature = 1000;
        double coolingRate = 0.003;

        // Execute o Simulated Annealing
        ArrayList<City> bestRoute = simulatedAnnealing(cities, initialTemperature, coolingRate);

        // Exiba a melhor rota encontrada
        System.out.println("Melhor Rota Encontrada:");
        for (City city : bestRoute) {
            System.out.println(city);
        }
        System.out.println("Distância Total: " + calculateTotalDistance(bestRoute));
    }

    // Função para calcular a distância entre duas cidades
    public static double calculateDistance(City city1, City city2) {
        double dx = city1.getX() - city2.getX();
        double dy = city1.getY() - city2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Função para calcular a distância total de uma rota
    public static double calculateTotalDistance(ArrayList<City> route) {
        double totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalDistance += calculateDistance(route.get(i), route.get(i + 1));
        }
        totalDistance += calculateDistance(route.get(route.size() - 1), route.get(0)); // Volte para a cidade inicial
        return totalDistance;
    }

    // Implementação do Simulated Annealing
    public static ArrayList<City> simulatedAnnealing(ArrayList<City> cities, double initialTemperature, double coolingRate) {
        ArrayList<City> currentRoute = new ArrayList<>(cities);
        Collections.shuffle(currentRoute, new Random());

        ArrayList<City> bestRoute = new ArrayList<>(currentRoute);
        double bestDistance = calculateTotalDistance(currentRoute);

        double temperature = initialTemperature;

        while (temperature > 1) {
            ArrayList<City> newRoute = new ArrayList<>(currentRoute);

            int randomIndex1 = (int) (Math.random() * currentRoute.size());
            int randomIndex2 = (int) (Math.random() * currentRoute.size());

            City city1 = currentRoute.get(randomIndex1);
            City city2 = currentRoute.get(randomIndex2);

            newRoute.set(randomIndex1, city2);
            newRoute.set(randomIndex2, city1);

            double newDistance = calculateTotalDistance(newRoute);

            if (newDistance < bestDistance || Math.random() < Math.exp((bestDistance - newDistance) / temperature)) {
                currentRoute = newRoute;
                bestRoute = new ArrayList<>(currentRoute);
                bestDistance = newDistance;
            }

            temperature *= 1 - coolingRate;
        }

        return bestRoute;
    }
}

class City {
    private int id;
    private double x;
    private double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cidade " + id + " (" + x + ", " + y + ")";
    }
}
