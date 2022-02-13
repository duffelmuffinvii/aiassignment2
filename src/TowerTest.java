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
//                    System.out.println("DWL for " + i + ", " + j + ", " + k);
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


        ArrayList<List<TowerPiece>> pop = TowerBuilder.generatePop(pieces, 20, 10);
        int nonZeroTowers = 0;
        float totalScore = 0;
        for (List<TowerPiece> t : pop) {
            int score = TowerBuilder.getScore(t);
            totalScore += score;
//            System.out.println("Score: " + score);
            if (score > 0) nonZeroTowers++;
//            System.out.println();
        }

        System.out.println(pop.size() + " total towers");

        System.out.println(nonZeroTowers + " valid towers");

        System.out.println("Average score: " + totalScore/pop.size());

        ArrayList<List<TowerPiece>> best2 = TowerBuilder.getBestTwo(pop);

        System.out.println("Best score: " + TowerBuilder.getScore(best2.get(0)));

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

        ArrayList<List<TowerPiece>> gen1 = TowerBuilder.nextGen(pop);
        ArrayList<List<TowerPiece>> gen2 = TowerBuilder.nextGen(gen1);
//
        int sumScore2 = 0;
        int nonZero2 = 0;
//
        System.out.println("GEN 2");

        for (List<TowerPiece> t : gen2) {
            for (TowerPiece p : t) {
                System.out.println(p.toString());
            }
            int score = TowerBuilder.getScore(t);
            sumScore2 += score;
            System.out.println("Score: " + score);
            if (score > 0) nonZero2++;
            System.out.println();
        }
//
        System.out.println(gen2.size() + " towers");
        System.out.println(nonZero2 + " valid towers");
//
        System.out.println("Avg score: " + sumScore2/gen2.size());
        ArrayList<List<TowerPiece>> best2a = TowerBuilder.getBestTwo(gen2);
        for (List<TowerPiece> t : best2a) {
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
