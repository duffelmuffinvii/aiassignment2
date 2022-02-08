import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Random r = new Random();
        r.setSeed(10);
        ArrayList<Double> input = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            input.add((20 * r.nextDouble())-10);
            System.out.println(input.get(i));
        }


    }


}
