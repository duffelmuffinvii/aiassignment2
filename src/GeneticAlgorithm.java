import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    //TODO: is this array list of gen0 an array of all the scores?
    //TODO: I treated it as so but I think it would have to be which bin each number is in? Not sure
    ArrayList<Float> gen0;
    ArrayList<Float> gen1 = new ArrayList<>();

    public GeneticAlgorithm(ArrayList<Float> gen0) {
        this.gen0 = gen0;
    }

    //updates gen1 with top scoring organisms from gen0
    public void elitism() {
        ArrayList<Float> temp = this.gen0;
        float max = 0;
        int maxIndex = -1;
        int count = 0;
        //may want to increase how many parents we take to the next gen
        while(count < 2) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i) > max) {
                    max = temp.get(i);
                    maxIndex = i;
                }
            }
            gen1.add(max);
            temp.remove(maxIndex);
            maxIndex = -1;
            count++;
        }

    }

    //takes in the percent that we want to cull and removes that percentage from the initial population
    public void culling(double percentage) {

        double numCulled = this.gen0.size() * percentage;
        Random r = new Random();
        int x = -1;
        int i = 0;

        while(i < numCulled) {

            int y = r.nextInt((9 - 0) + 1) + 0;
            if(y != x) {
                this.gen0.remove(x);
                x = y;
                i++;
            }

        }

    }

    public void selection() {

        Float sum = 0f;
        Float chance;
        Float cumulativeChance = 0f;
        ArrayList<Float> chancesOfSelection = new ArrayList<>();
        ArrayList<Float> cumulativeProb = new ArrayList<>();
        Random r = new Random();

        //calculate the sum
        for(Float score: this.gen0) {
            sum = sum+score;
        }

        //divide each score by the calculated sum
        for(Float score: this.gen0) {
            chance = score/sum;
            chancesOfSelection.add(chance);
            cumulativeChance = cumulativeChance + chance;
            cumulativeProb.add(cumulativeChance);
        }

        while(this.gen0.size() != this.gen1.size()) {

            int x = r.nextInt((100 - 0) + 1) + 0;
            int y = r.nextInt((100 - 0) + 1) + 0;
            if(x != y) {
                Float parent1 = gen0.get(x);
                Float parent2 = gen0.get(y);
                //TODO: cutpoint doesn't work if gen0 is a list of scores
                //TODO not entirely sure how to proceed

            }
        }


    }

}
