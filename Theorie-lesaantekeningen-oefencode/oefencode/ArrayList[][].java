import java.util.ArrayList;

public class Sandbox {

    public static void main(String[] args) {
        ArrayList<String> houses = new ArrayList<String>();

        houses.add("House 1");
        houses.add("House 2");

        ArrayList<String>[] roomsPerHouse = new ArrayList[2];

        roomsPerHouse[0] = new ArrayList<String>();
        roomsPerHouse[0].add("Kamer Henk");
        roomsPerHouse[0].add("Kamer Jan");
        roomsPerHouse[0].add("Kamer Hendrik");

        roomsPerHouse[1] = new ArrayList<String>();
        roomsPerHouse[1].add("Kamer Jan");
        roomsPerHouse[1].add("Kamer Henk");
        roomsPerHouse[1].add("Kamer Hendrik");

        ArrayList<String>[][] furniturePerRoomPerHouse = new ArrayList[2][3];

        // voor huizen 1 en 2 (2 x array) 3 kamers aanmaken (ArrayList) die meubels
        // bevatten
        // (Strings)
        for (int i = 0; i < furniturePerRoomPerHouse.length; i++) {
            for (int j = 0; j < furniturePerRoomPerHouse[i].length; j++) {
                furniturePerRoomPerHouse[i][j] = new ArrayList<String>();
            }
        }

        furniturePerRoomPerHouse[0][0].add("zwart bureau");
        furniturePerRoomPerHouse[0][0].add("zwarte stoel");
        furniturePerRoomPerHouse[0][1].add("witte stoel");
        furniturePerRoomPerHouse[0][1].add("wit bureau");
        furniturePerRoomPerHouse[0][2].add("grijze stoel");
        furniturePerRoomPerHouse[0][2].add("grijs bureau");
        furniturePerRoomPerHouse[1][0].add("zwart bureau");
        furniturePerRoomPerHouse[1][0].add("zwarte stoel");
        furniturePerRoomPerHouse[1][1].add("witte stoel");
        furniturePerRoomPerHouse[1][1].add("wit bureau");
        furniturePerRoomPerHouse[1][2].add("grijze stoel");
        furniturePerRoomPerHouse[1][2].add("grijs bureau");
    }
}
