import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    // so I have 4 bins
    Bin bestBin;
    Bin secondBestBin;
    Bin bin1;
    Bin bin2;
    Bin bin3;
    Bin bin4;
    ArrayList<Bin> puzzleBins=new ArrayList<Bin>();

    public GeneticAlgorithm(Bin bin1,Bin bin2,Bin bin3, Bin bin4)
    {
        this.bin1=bin1;
        this.bin2=bin2;
        this.bin3=bin3;
        this.bin4=bin4;
    }

    public void elitism(int percentageElitism)
    {

        ArrayList<Bin> Bins = new ArrayList<>();
        Bins.add(bin1);
        Bins.add(bin2);
        Bins.add(bin3);
        Bins.add(bin4);

       if(percentageElitism < 50) {
           bestBin = multiplicationSelection(bin1, bin2, bin3, bin4);
       }
       else
       {
           bestBin = multiplicationSelection(bin1, bin2, bin3, bin4);
           secondBestBin=additionSelection(Bins);
       }

        puzzleBins.add(bestBin);
        puzzleBins.add(secondBestBin);
      // check if the score in the bin is the highest
        // multiplication give more preference
        // if multiplication is negative check addition
        //if third bin is greater then make that the elite

    }


    public Bin multiplicationSelection(Bin bin1,Bin bin2,Bin bin3, Bin bin4)
    {
        ArrayList<Bin> Bins = new ArrayList<>();
        Bins.add(bin1);
        Bins.add(bin2);
        Bins.add(bin3);
        Bins.add(bin4);

        double max = 0.0;
        int binNumber=0;
        double score = 1.0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<10;j++)
            {
                score=score*Bins.get(i).valuesStored.get(j);
            }
            if(max < score)
            {
                max=score;
                binNumber=i-1;
            }
        }

        if(max < 0)
        {
             return additionSelection(Bins);
        }
        return Bins.get(binNumber);
    }



    public Bin additionSelection(ArrayList<Bin> bins)
    {

        double max = 0.0;
        int binNumber=0;
        double score = 0.0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<10;j++)
            {
                score=score+bins.get(i).valuesStored.get(j);
            }
            if(max < score)
            {
                max=score;
                binNumber=i-1;
            }
        }

        if(max < 0)
        {
            thirdBinSelection(bins);
        }
        return bins.get(binNumber);
    }


    public Bin thirdBinSelection(ArrayList<Bin> bins)
    {

        double max = 0.0;
        double min= 0.0;

        double max_score=0.0;
        int binNumber=0;
        double score = 0.0;
        for(int i=0;i<4;i++)
        {
            max=bins.get(i).valuesStored.get(0);
            min=bins.get(i).valuesStored.get(0);
            for(int j=0;j<10;j++)
            {
                if(max > bins.get(i).valuesStored.get(0))
                {
                    max=bins.get(i).valuesStored.get(0);
                }
                if(min < bins.get(i).valuesStored.get(0)) {
                    min = bins.get(i).valuesStored.get(0);
                }
            }
            score = max-min;

            if(max_score < score)
            {
                max_score=score;
                binNumber=i-1;
            }
        }

        return bins.get(binNumber);
    }

    public void culling(int cullingPercentage,int populationSize)
    {
        //find the bin that produces the lowest multiplication result
        //find the bin that produces the lowest addition result
        //find the bin that produces the lowest third bin criteria

        // if the multiplication is the lowest then get rid of that
    }

    public void crossOver(int CrossOverSize,int populationSize)
    {

        ArrayList<Float> numbersinNewBin2= new ArrayList<>();
        ArrayList<Float> numbersinNewBin3= new ArrayList<>();
        Bin binoffspring1=new Bin(2,numbersinNewBin2);
        Bin binoffspring2=new Bin(3,numbersinNewBin3);
        //swapping first half of the bins

        //bin1 and bin2

        for(int i=0;i<5;i++)
        {
            for(int j=4;j<10;j++)
            {
// swapping for cross over
            }
        }

    }

        public void generateGeneration(ArrayList<Bin> puzzleBins)
        {
            Puzzle1 newPuzzle=new Puzzle1(puzzleBins.get(0),puzzleBins.get(1),puzzleBins.get(2),puzzleBins.get(3));
        }

        public void fitnessCheck(Puzzle1 puzzle)
        {
            //calculate the score and compare with the previous ones and set this to be the limit to which it is compare
            // then generate next generation

        }

    ArrayList<Puzzle1> gen0;
    ArrayList<Puzzle1> gen1 = new ArrayList<>();
    ArrayList<Double> gen0Scores;
    ArrayList<Double> gen1Scores = new ArrayList<>();

    public GeneticAlgorithm(ArrayList<Puzzle1> gen0, ArrayList<Double> gen0Scores) {
        this.gen0Scores = gen0Scores;
        this.gen0 = gen0;
    }

    public static Puzzle1 produceChild(Puzzle1 parentA, Puzzle1 parentB) {

        Puzzle1 child = new Puzzle1(parentA.bin1, parentA.bin2, parentB.bin3, parentB.bin4);
        return child;
    }
    public static Puzzle1 produceChild(Puzzle1 parentA) {

        Puzzle1 child = new Puzzle1(parentA.bin1, parentA.bin2, parentA.bin3, parentA.bin4);
        return child;
    }

//    public void elitism(int numKeep) {
//
//        double max = 0.0;
//        int maxIndex = -1;
//        int count = 0;
//
//        //temporary score array internal editing
//        ArrayList<Double> temp = gen0Scores;
//
//        //loop for number of parents we want to keep
//        while(count < numKeep) {
//            //for each score
//            for (int i = 0; i < temp.size(); i++) {
//                //if the score is greater than max set max = score
//                if (temp.get(i) > max) {
//                    max = temp.get(i);
//                    maxIndex = i;
//                }
//            }
//            //add organism to new generation
//            gen1.add(gen0.get(maxIndex));
//            gen1Scores.add(temp.get(maxIndex));
//            //remove the score from temp
//            temp.remove(maxIndex);
//            max = 0.0;
//            maxIndex = -1;
//            count++;
//        }
//
//    }

    //takes in the percent that we want to cull and removes that percentage from the initial population
    public void culling(double percentage, int populationSize) {

        double numCulled = this.gen0.size() * percentage;
        //Random r = new Random();
        int x = -1;
        int cull = 0;
        int count = 0;

        while(cull < numCulled) {

            int y = randInt(0,populationSize-count-1);
            //int y = r.nextInt(((populationSize-1) - 0) + 1) + 0;
            if(y != x) {
                this.gen0.remove(y);
                x = y;
                cull++;
            }
            count++;

        }

    }

    public void selection(int populationSize) {

        //dummy variables for initiating puzzles
        Bin bin1 = new Bin(1, new ArrayList<>());
        Bin bin2 = new Bin(2, new ArrayList<>());
        Bin bin3 = new Bin(3, new ArrayList<>());
        Bin bin4 = new Bin(4, new ArrayList<>());


        ArrayList<Double> cumulative = new ArrayList<>();
        ArrayList<Double> cumulativePercent = new ArrayList<>();
        double percent = 0.0;
        double score = 0.0;
        boolean flag = false;
        int count = 0;
        Puzzle1 parent1 = new Puzzle1(bin1,bin2,bin3,bin4);
        Puzzle1 parent2 = new Puzzle1(bin1,bin2,bin3,bin4);
        Puzzle1 child;

        for(int i = 0; i < this.gen0Scores.size(); i++) {
            score = score + this.gen0Scores.get(i);
            cumulative.add(score);
        }

        for(int i = 0; i < cumulative.size(); i++) {
            percent = cumulative.get(i)/score;
            cumulativePercent.add(percent);
        }

        while(gen1.size() < populationSize) {

            int r = randInt(0, 100);
            for (int i = 0; i < cumulativePercent.size(); i++) {
                if (cumulativePercent.get(i)*100 > r && flag == false) {
                    System.out.println("r" + r);

                    if (count == 0) {
                        System.out.println("here");
                        parent1 = gen0.get(i);
                    }

                    if (count == 1) {
                        System.out.println("here2");
                        parent2 = gen0.get(i);
                        flag = true;
                    }

                    count++;

                }

            }
            child = produceChild(parent1, parent2);
            gen1.add(child);
            gen1Scores.add(child.binFinalScore());
        }

    }

    public static int randInt(int min, int max) {

        Random rand =  new Random();

        return rand.nextInt((max - min) + 1) + min;
    }


    public ArrayList<Double> puzzle1GA(int numKeep,double percentage, int populationSize) {
        elitism(numKeep);
        culling(percentage, populationSize);
        selection(populationSize);
        return gen1Scores;
    }


}
