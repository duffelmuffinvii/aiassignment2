import java.util.ArrayList;

public class Bin {

    int number;
    ArrayList<Float> valuesStored;

    public Bin(int number,ArrayList<Float> valuesStored)
    {
        this.number=number;
        this.valuesStored= valuesStored;
    }

    public double binFinalScore(Bin bin1,Bin bin2,Bin bin3) {
        //BIN1
        double score1 = 1.0;
        for (int i = 0; i < 10; i++) {
            score1 *= bin1.valuesStored.get(i);
        }

        //Bin2
        double score2 = 0.0;
        for (int j = 0; j < 10; j++) {
            score2 += bin2.valuesStored.get(j);
        }

        //Bin3
        double score3 = 0.0;
        double max = bin3.valuesStored.get(0);
        double min = bin3.valuesStored.get(0);

        for (int k = 0; k < 10; k++) {
            if (max < bin3.valuesStored.get(k)) {
                max = bin3.valuesStored.get(k);
            }

            if (min > bin3.valuesStored.get(k)) {
                min = bin3.valuesStored.get(k);
            }
        }

        score3 = max - min;

        double finalScore = score1 + score2 + score3;
        return finalScore;
    }


}
