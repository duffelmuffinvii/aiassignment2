import java.util.ArrayList;

public class Puzzle1 {

    Bin bin1;
    Bin bin2;
    Bin bin3;
    Bin bin4;

    public Puzzle1(Bin bin1, Bin bin2, Bin bin3, Bin bin4) {
        this.bin1 = bin1;
        this.bin2 = bin2;
        this.bin3 = bin3;
        this.bin4 = bin4;
    }

    public Double binFinalScore() {
        //BIN1
        double score1 = 1.0;
        for (int i = 0; i < 10; i++) {
            score1 *= this.bin1.valuesStored.get(i);
        }

        //Bin2
        double score2 = 0.0;
        for (int j = 0; j < 10; j++) {
            score2 += this.bin2.valuesStored.get(j);
        }
        //Bin3
        double score3 = 0.0;
        double max = this.bin3.valuesStored.get(0);
        double min = this.bin3.valuesStored.get(0);

        for (int k = 0; k < 10; k++) {
            if (max < this.bin3.valuesStored.get(k)) {
                max = this.bin3.valuesStored.get(k);
            }

            if (min > this.bin3.valuesStored.get(k)) {
                min = this.bin3.valuesStored.get(k);
            }
        }

        score3 = max - min;

        double finalScore = score1 + score2 + score3;
        return finalScore;
    }

}
