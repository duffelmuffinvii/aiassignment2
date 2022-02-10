import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    ArrayList<Float> gen0;
    ArrayList<Float> gen1 = new ArrayList<>();

    public GeneticAlgorithm(ArrayList<Float> gen0) {
        this.gen0 = gen0;
    }

    //takes in initial population scores
    //updates gen1 with top scoring organisms from gen0
    public void elitism(ArrayList<Float> gen0) {
        ArrayList<Float> temp = gen0;
        float max = 0;
        int maxIndex = -1;
        int count = 0;
        //may want to increase how many parents we take to the next gen
        while(count < 2) {
            for (int i = 0; i < gen0.size(); i++) {
                if (gen0.get(i) > max) {
                    max = gen0.get(i);
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

}
