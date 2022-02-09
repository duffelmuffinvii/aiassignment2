import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class TowerBuilder {

    public TowerBuilder() {
    }

    public ArrayList<TowerPiece> buildRandomTower(ArrayList<TowerPiece> allPieces) {

        int min = 2;
        int max = 10;

        Random r = new Random();
        r.setSeed(5);
        int length = r.nextInt((max - min) + 1) + min;

        ArrayList<TowerPiece> tower = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int pieceToUse = r.nextInt((length - min) + 1) + min;
            tower.add(allPieces.get(pieceToUse));
            allPieces.remove(pieceToUse);
        }

        return tower;

    }

    public static TowerPiece generateTowerPiece() {
        Random r = new Random();

        int typeInt = r.nextInt(4) + 1;
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

        return new TowerPiece(type, r.nextInt(10)+1, r.nextInt(10)+1, r.nextInt(10)+1);
    }

    public static int getScore (ArrayList<TowerPiece> tower) {

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
}
