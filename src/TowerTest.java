import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
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
            if (score > 0) nonZeroTowers++;
        }

        System.out.println("Initial population average score: " + totalScore/pop.size());
        ArrayList<List<TowerPiece>> initialBest = TowerBuilder.getBestTwo(pop);

        System.out.println("Initial best:");
        for (TowerPiece p : initialBest.get(0)) {
            System.out.println(p.toString());
        }

        ArrayList<List<TowerPiece>> gen1 = TowerBuilder.nextGen(pop);

        ArrayList<List<TowerPiece>> gen2 = TowerBuilder.nextGen(gen1);

        ArrayList<List<TowerPiece>> gen3 = TowerBuilder.nextGen(gen2);

        ArrayList<List<TowerPiece>> bestSolutions = TowerBuilder.getBestTwo(gen3);

        System.out.println("Best after 3 generations:");

        TowerBuilder.towerInfo(bestSolutions.get(0));
    }
}
