package dev.hartcode.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AdventureGame {

    // Define the game locations and connections.
    private static final String GAME_LOCATIONS = """
    Forest, A wild and densely packed forest surrounds you, E: Mountain, S: Hill
    Mountain, A vast and ominous mountain towers before you, W: Forest, E: Lake, S: Road
    Lake, A serene and peaceful lake shimmers for as far as the eye can see, S: Well house, W: Mountain
    Hill, A blissfully green hobbit hill sprouts from the ground, N: Forest, E: Road, S: Cave
    Road, A long and tiresome road monotonously lies ahead, N: Mountain, E: Well house, S: Valley, W:Hill
    Well house, A creaky old well house wobbles from the gushing wind, N: Lake, S: Stream, W: Road
    Cave, A dark and mysterious cave omits a low rumbling growl, N: Hill, E: Valley
    Valley, A beautiful valley stretches all around you dazzling you, N: Road, E: Stream, W: Cave
    Stream, A gentle and zen stream trickles over your toes, N: Well house, W: Valley
    """;

    // Enum to represent compass directions (N, E, S, W).
    private enum Compass {
        N, E, S, W;

        private static final String[] s = { "North", "East", "South", "West" };

        public String getString() {
            return s[this.ordinal()];
        }
    }

    // Record to represent a location with its description and connections to other locations.
    private record Location(String description, HashMap<Compass, String> nextPlaces) {}

    // Variable to keep track of the last visited location.
    private String lastPlace;

    // Hash map to store locations and their details.
    private final HashMap<String, Location> adventureMap = new HashMap<>();

    // Default constructor to load the game with predefined locations.
    public AdventureGame() {
        this(null);
    }

    // Constructor to load the game with predefined locations and custom locations if provided.
    public AdventureGame(String customLocation) {
        loadLocations(GAME_LOCATIONS);
        if (customLocation != null) {
            loadLocations(customLocation);
        }
    }

    // Method to load locations and their connections from the provided data.
    private void loadLocations(String locationData) {
        for (String s : locationData.split("\\R")) {
            String[] parts = s.split(",", 3);
            Arrays.asList(parts).replaceAll(String::trim);
            HashMap<Compass, String> nextPlaces = loadNextPlaces(parts[2]);
            Location location = new Location(parts[1], nextPlaces);
            adventureMap.put(parts[0], location);
        }
    }

    // Method to load connections to other locations for a given location.
    private HashMap<Compass, String> loadNextPlaces(String parts) {
        HashMap<Compass, String> nextPlaces = new HashMap<>();
        List<String> nextSteps = Arrays.asList(parts.split(","));
        nextSteps.replaceAll(String::trim);
        for (String string : nextSteps) {
            String[] s = string.split(":");
            Compass compass = Compass.valueOf(s[0].trim());
            String destination = s[1].trim();
            nextPlaces.put(compass, destination);
        }
        return nextPlaces;
    }

    // Method to visit and display information about the current location.
    private void visit(Location location) {
        System.out.println(location.description);
        System.out.println("From here, you can see: ");
        location.nextPlaces.forEach((k, v) -> System.out.printf(
                "\t* A %s lies to the %s (%s)%n", v, k.getString(), k));
        System.out.println("Select your compass direction (Q - quit) >> ");
    }

    // Method to move to the next location based on the specified direction.
    public void move(String direction) {
        var nextPlaces = adventureMap.get(lastPlace).nextPlaces;
        String nextPlace;
        if ("NESW".contains(direction)) {
            nextPlace = nextPlaces.get(Compass.valueOf(direction));
            if (nextPlace != null) {
                play(nextPlace);
            }
        } else {
            System.out.println("Invalid direction!");
        }
    }

    // Method to play the game by moving to the specified location.
    public void play(String location) {
        if (adventureMap.containsKey(location)) {
            Location nextLocation = adventureMap.get(location);
            lastPlace = location;
            visit(nextLocation);
        } else {
            System.out.println(location + " is an invalid location");
        }
    }
}
