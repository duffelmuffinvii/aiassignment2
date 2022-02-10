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

    // disgusting but its probably fine
    public static ArrayList<List<TowerPiece>> getBestTwo(ArrayList<List<TowerPiece>> towers) {

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<List<TowerPiece>> bestTowers = new ArrayList<>();

        int best = 0;
        int bestIndex = 0;
        int secondBest = 0;
        int secondBestIndex = 0;

        for (int i = 0; i < towers.size(); i++) {
            scores.add(TowerBuilder.getScore(towers.get(i)));
        }

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > best) {
                best = scores.get(i);
                bestIndex = i;
            }
        }

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > secondBest && scores.get(i) != best) {
                secondBest = scores.get(i);
                secondBestIndex = i;
            }
        }

        bestTowers.add(towers.get(bestIndex));
        bestTowers.add(towers.get(secondBestIndex));

        return bestTowers;
    }

    // removes the lowest 30% of towers (theoretically, havent tested)
    public static ArrayList<List<TowerPiece>> cull(ArrayList<List<TowerPiece>> towers) {
        int towersToCull = (int)Math.floor(towers.size() * 0.3);
        ArrayList<Integer> scores = new ArrayList<>();

        for (List<TowerPiece> tower : towers) {
            scores.add(getScore(tower));
        }

        int removed = 0;

        while (removed < towersToCull) {
            int min = scores.get(0);
            int minIndex = 0;

            for (int i = 0; i < towers.size(); i++) {
                if (scores.get(i) < min) {
                    min = scores.get(i);
                    minIndex = i;
                }
            }

            towers.remove(minIndex);
            removed++;
        }

        return towers;

    }

    public static List<TowerPiece> randomTower(ArrayList<TowerPiece> availablePieces, int towerSize) {
        availablePieces = new ArrayList<>(availablePieces);
        Collections.shuffle(availablePieces);
        return availablePieces.subList(0, towerSize);
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
            //System.out.println("Bottom piece not door");
            return 0; // Bottom must be a door
        }
        if (tower.get(0).strength < height - 1) {
            //System.out.println("insufficient strength");
            return 0; // Bottom must have strength to support rest of pieces
        }

        if (tower.size() > 2) {
            for (int i = 1; i < height - 1; i++) {
                if (!tower.get(i).type.equals("Wall")) {
                    //System.out.println("non wall piece in between top and bottom") ;
                    return 0; // Every piece between top and bottom must be wall
                }
                if (((height - 1) - i) > tower.get(i).strength) {
                    //System.out.println("insufficient strength");
                    return 0; // Piece must be strong enough to support all pieces above it
                }
                if (tower.get(i).width > tower.get(i-1).width) {
                    //System.out.println("width conflict");
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

        Random rand =  new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
