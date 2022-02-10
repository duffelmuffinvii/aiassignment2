import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TowerTest {

    TowerPiece door322 = new TowerPiece("Door", 3, 2, 2);
    TowerPiece wall312 = new TowerPiece("Wall", 3, 1, 2);
    TowerPiece lookout224 = new TowerPiece("Lookout", 2, 2, 4);

    ArrayList<TowerPiece> pieces = new ArrayList<>();


    @Test
    public void testEvalTower() {

        for (int i = 1; i < 6; i ++) {
            for (int j = 1; j < 6; j++) {
                for (int k = 1; k < 6; k++) {
                    pieces.add(new TowerPiece("Door", i, j, k));
                    pieces.add(new TowerPiece("Wall", i, j, k));
                    pieces.add(new TowerPiece("Lookout", i, j, k));
                    System.out.println("DWL for " + i + ", " + j + ", " + k);
                }
            }
        }

        ArrayList<TowerPiece> testTower1 = new ArrayList<>();
        testTower1.add(door322);
        testTower1.add(wall312);
        testTower1.add(lookout224);

        ArrayList<TowerPiece> testTowerInvalid1 = new ArrayList<>();
        testTowerInvalid1.add(wall312);
        testTowerInvalid1.add(door322);
        testTowerInvalid1.add(lookout224);

        ArrayList<TowerPiece> testTowerShort = new ArrayList<>();
        testTowerShort.add(door322);
        testTowerShort.add(lookout224);

        assertEquals(TowerBuilder.getScore(testTower1), 11);
        assertEquals(TowerBuilder.getScore(testTowerInvalid1), 0);
        assertEquals(TowerBuilder.getScore(testTowerShort), 8);


        ArrayList<List<TowerPiece>> pop = TowerBuilder.generatePop(pieces, 10, 5);
        int nonZeroTowers = 0;
        for (List<TowerPiece> t : pop) {
            for (TowerPiece p : t) {
                System.out.println(p.toString());
            }
            int score = TowerBuilder.getScore(t);
            System.out.println("Score: " + score);
            if (score > 0) nonZeroTowers++;
            System.out.println();
        }

        System.out.println(pop.size() + " total towers");

        System.out.println(nonZeroTowers + " valid towers");

        ArrayList<List<TowerPiece>> best2 = TowerBuilder.getBestTwo(pop);

        System.out.println("BEST TWO");

        for (List<TowerPiece> t : best2) {
            for (TowerPiece p : t) {
                System.out.println(p.toString());
            }
            int score = TowerBuilder.getScore(t);
            System.out.println("Score: " + score);
            if (score > 0) nonZeroTowers++;
            System.out.println();
        }
    }
}
