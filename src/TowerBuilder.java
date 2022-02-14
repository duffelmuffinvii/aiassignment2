import java.lang.reflect.Array;
import java.util.*;

public class TowerBuilder {

    public TowerBuilder() {
    }

    public static ArrayList<List<TowerPiece>> run(ArrayList<List<TowerPiece>> gen0, int secs) {
        ArrayList<List<TowerPiece>> currentGen = gen0;
        long time= System.currentTimeMillis();
        long end = time+(secs*1000);
        int i = 0;
        int bestScore = 0;
        int bestScoreGen = 0;
        while(System.currentTimeMillis() < end) {
            int x = 0;
            int score = getScore(getBestX(currentGen, 1).get(0));

            if (score > bestScore) {
                bestScore = score;
                bestScoreGen = i;
            }

            for (List<TowerPiece> t : currentGen) {
                if (t.size() == 0) x++;
            }

            currentGen = nextGen(currentGen);
            i++;
        }
        System.out.println("Ran for " + (i+1) + " generations");
        System.out.println(currentGen.size() + " towers");
        System.out.println("Median in final generation: " + getMedian(currentGen));
        System.out.println("Best result:");

        List<TowerPiece> best = getBestX(currentGen, 1).get(0);

        towerInfo(best);

        System.out.println("Score: " + getScore(best) + "\n");
        System.out.println("Best score obtained in generation " + (bestScoreGen+1));
        return currentGen;
    }

    public static double getMedian(ArrayList<List<TowerPiece>> towers) {
        ArrayList<Integer> scores = new ArrayList<>();
        for (List<TowerPiece> t : towers) scores.add(getScore(t));

        Collections.sort(scores);
        if (scores.size() % 2 == 0)
            return ((double)scores.get(scores.size()/2) + (double)scores.get(scores.size()/2 - 1))/2;
        else
            return (double)scores.get(scores.size()/2);
    }

    // generates initial population of towers given list of pieces
    public static ArrayList<List<TowerPiece>> generatePop(ArrayList<TowerPiece> pieces, int minValidTowers, int maxTowerSize) {
        ArrayList<List<TowerPiece>> population = new ArrayList<>();
        int validTowers = 0;
        while (validTowers < minValidTowers) {
            List<TowerPiece> t = randomTower(pieces, randInt(2, maxTowerSize));
            if (getScore(t) > 0) {
//                System.out.println("scored " + getScore(t));
//                System.out.println(t.toString());
                validTowers++;
            }
            population.add(t);
        }

        return population;
    }

    // currently just takes alternating pieces from each parent, likely will be changed
    public static List<TowerPiece> produceChild(List<TowerPiece> parentA, List<TowerPiece> parentB) {
        List<TowerPiece> child = new ArrayList<>();
        int childHeight = parentB.size();

        for (int i = 0; i < childHeight; i++) {
            if (i % 2 == 0 && i < parentA.size()) child.add(parentA.get(i));
            else child.add(parentB.get(i));
        }

        return child;
    }

    public static ArrayList<List<TowerPiece>> nextGen(ArrayList<List<TowerPiece>> prevGen) {
        ArrayList<List<TowerPiece>> next = getBestTwo(prevGen);
        int genSize = prevGen.size();
        ArrayList<List<TowerPiece>> culledPrev = cull(prevGen);

        ArrayList<Float> probabilities = new ArrayList<>();
        int sumScore = 0;

        //creates list of scores corresponding to list of towers
        for (List<TowerPiece> t : culledPrev) {
            float thisScore = getScore(t);
            probabilities.add(thisScore);
            sumScore += thisScore;
        }

        float cumProb = 0;

        //reiterates through list to scale scores and then convert it to cumulative probabilities, still corresponding to indices in list of towers
        for (int i = 0; i < probabilities.size(); i++) {
            float thisProb = probabilities.get(i)/sumScore;
            cumProb += thisProb;
            probabilities.set(i, cumProb);
        }

        Random r = new Random();

        while (next.size() < genSize) {
            // random floats for selecting parents
            float parentASelector = r.nextFloat();
            float parentBSelector = r.nextFloat();
            List<TowerPiece> parentA = new ArrayList<>();
            List<TowerPiece> parentB = new ArrayList<>();

            for (int i = 0; i < probabilities.size(); i++) {
                //checking selectors against probability distributiono list
                if (parentASelector < probabilities.get(i) && parentA.isEmpty()) {
                    parentA = culledPrev.get(i);
                }

                if (parentBSelector < probabilities.get(i) && parentB.isEmpty()) {
                    parentB = culledPrev.get(i);
                }
            }

            // add child to list
            next.add(produceChild(parentA, parentB));
        }

        int bestIndex = 0;
        int bestScore = 0;

        for (int i = 0; i < next.size(); i++) {

            List<TowerPiece> t  = next.get(i);

            int score = getScore(t);
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }


        //MUTATIONS

        ArrayList<Integer> mutations = new ArrayList<>();

        int amtMutate;

        if (next.size() > 1000) amtMutate = 200;
        else if (next.size() > 500) amtMutate = 100;
        else if (next.size() > 100) amtMutate = 20;
        else if (next.size() > 50) amtMutate = 10;
        else amtMutate = 2;

        for (int i = 0; i < amtMutate; i++) {
            int randInt = r.nextInt(next.size());
            if (randInt == bestIndex) {
                while (randInt == bestIndex) randInt = r.nextInt(next.size());
            }
            mutations.add(randInt);
        }

        for (int i = 0; i < mutations.size(); i += 2) {
            List<TowerPiece> tower1 = next.get(mutations.get(i));
            List<TowerPiece> tower2 = next.get(mutations.get(i+1));

            int size;

            if (tower1.size() < tower2.size()) size = tower1.size();
            else size = tower2.size();

            if (size == 0) continue;
            int rand = r.nextInt(size);

            TowerPiece temp = tower2.get(rand);
            tower2.set(rand, tower1.get(rand));
            tower1.set(rand, temp);

            next.set(mutations.get(i), tower1);
            next.set(mutations.get(i+1), tower2);
        }

        return next;
    }

    public static void towerInfo(List<TowerPiece> tower) {
        for (TowerPiece p : tower) {
            System.out.println(p.toString());
        }
    }

    public static ArrayList<List<TowerPiece>> getBestX(ArrayList<List<TowerPiece>> towers, int amount) {

        ArrayList<List<TowerPiece>> towersClone = new ArrayList<>(towers);

        ArrayList<List<TowerPiece>> bestx = new ArrayList<>();

        while (bestx.size() < amount) {
            int bestIndex = 0;
            int bestScore = 0;
            for (int i = 0; i < towersClone.size(); i++) {
                List<TowerPiece> t = towersClone.get(i);
                if (getScore(t) > bestScore) {
                    bestScore = getScore(t);
                    bestIndex = i;
                }
            }

            bestx.add(towersClone.get(bestIndex));
            towersClone.remove(bestIndex);
        }

        return bestx;

    }

    public static ArrayList<List<TowerPiece>> getBestTwo(ArrayList<List<TowerPiece>> towers) {
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<List<TowerPiece>> bestTowers = new ArrayList<>();
        int best = 0;
        int bestIndex = 0;
        int secondBest = 0;
        int secondBestIndex = 0;
        for (List<TowerPiece> tower : towers) {
            scores.add(TowerBuilder.getScore(tower));
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

        if (height == 0) return 0;

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

    public static float avgScore(ArrayList<List<TowerPiece>> towers) {
        float a = 0;

        for (List<TowerPiece> t : towers) {
            a += getScore(t);
        }

        return a/towers.size();
    }

    public static int randInt(int min, int max) {

        Random rand =  new Random();

        return rand.nextInt((max - min) + 1) + min;
    }
}
