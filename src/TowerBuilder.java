import java.lang.reflect.Array;
import java.util.*;

public class TowerBuilder {

    public TowerBuilder() {
    }

    public static ArrayList<List<TowerPiece>> generatePop(ArrayList<TowerPiece> pieces, int size, int maxTowerSize) {
        ArrayList<List<TowerPiece>> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            population.add(randomTower(pieces, randInt(2, 5)));
        }

        return population;
    }

    public static List<TowerPiece> randomTower(ArrayList<TowerPiece> list, int towerSize) {
        list = new ArrayList<>(list);
        Collections.shuffle(list);
        return list.subList(0, towerSize);
    }

    public static TowerPiece generateTowerPiece() {
        Random r = new Random();

        int typeInt = randInt(1, 3);
        String type;

        switch (typeInt) {
            case 1:
                type = "Door";
                break;
            case 2:
                type = "Wall";
                break;
            case 3:
                type = "Lookout";
                break;
            default:
                System.out.println("what");
                return null;
        }

        return new TowerPiece(type, randInt(1, 10), randInt(1, 10), randInt(1, 10));
    }

    public static int getScore (List<TowerPiece> tower) {

        int height = tower.size();
        int cost = tower.get(0).cost;

        if (!tower.get(0).type.equals("Door")) {
            System.out.println("Bottom piece not door");
            return 0; // Bottom must be a door
        }
        if (tower.get(0).strength < height - 1) {
            System.out.println("insufficient strength");
            return 0; // Bottom must have strength to support rest of pieces
        }

        if (tower.size() > 2) {
            for (int i = 1; i < height - 1; i++) {
                if (!tower.get(i).type.equals("Wall")) {
                    System.out.println("non wall piece in between top and bottom") ;
                    return 0; // Every piece between top and bottom must be wall
                }
                if (((height - 1) - i) > tower.get(i).strength) {
                    System.out.println("insufficient strength");
                    return 0; // Piece must be strong enough to support all pieces above it
                }
                if (tower.get(i).width > tower.get(i-1).width) {
                    System.out.println("width conflict");
                    return 0; // Piece cannot be wider than the one beneath it
                }
                else cost += tower.get(i).cost;
            }
        }

        TowerPiece top = tower.get(tower.size()-1);

        if (!top.type.equals("Lookout") || tower.get(tower.size() - 2).width < top.width) {
            return 0;
        } // Top must be lookout and at most as wide as the piece below it
        else {
            cost += top.cost;
        }
        return 10 + (height * height) - cost;
    }

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random rand =  new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
