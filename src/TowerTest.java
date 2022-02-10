import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TowerTest {

    TowerPiece door322 = new TowerPiece("Door", 3, 2, 2);
    TowerPiece wall312 = new TowerPiece("Wall", 3, 1, 2);
    TowerPiece lookout224 = new TowerPiece("Lookout", 2, 2, 4);

    TowerPiece door537 = new TowerPiece("Door", 5, 3, 7);
    TowerPiece door981 = new TowerPiece("Door", 9, 8, 1);
    TowerPiece door288 = new TowerPiece("Door", 2, 8, 8);
    TowerPiece door436 = new TowerPiece("Door", 4, 3, 6);

    TowerPiece wall836 = new TowerPiece("Wall", 8, 3, 6);
    TowerPiece wall197 = new TowerPiece("Wall", 1, 9, 7);
    TowerPiece wall573 = new TowerPiece("Wall", 5, 7, 3);
    TowerPiece wall982 = new TowerPiece("Wall", 9, 8, 2);

    TowerPiece lookout624 = new TowerPiece("Lookout", 6, 2, 4);
    TowerPiece lookout167 = new TowerPiece("Lookout", 1, 6, 7);
    TowerPiece lookout248 = new TowerPiece("Lookout", 2, 4, 8);
    TowerPiece lookout234 = new TowerPiece("Lookout", 2, 3, 4);

    ArrayList<TowerPiece> allpieces = new ArrayList<>();


    @Test
    public void testEvalTower() {

//        allpieces.add(door537);
//        allpieces.add(door981);
//        allpieces.add(door288);
//        allpieces.add(door436);
//        allpieces.add(wall836);
//        allpieces.add(wall197);
//        allpieces.add(wall573);
//        allpieces.add(wall982);
//        allpieces.add(lookout624);
//        allpieces.add(lookout167);
//        allpieces.add(lookout248);
//        allpieces.add(lookout234);

        for (int i = 0; i < 50; i++) {
            TowerPiece piece = TowerBuilder.generateTowerPiece();
            allpieces.add(piece);
            System.out.println(piece.toString());
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

        System.out.println(allpieces.size());

        ArrayList<List<TowerPiece>> pop = TowerBuilder.generatePop(allpieces, 20, 4);
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

        System.out.println(nonZeroTowers + " valid towers");
    }
}
