import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Puzzle1> population = new ArrayList<>();
        ArrayList<Double> scores = new ArrayList<>();

        if(Integer.parseInt(args[0]) == 1) {
            population = puzzle1Setup(args);
            fileRead(args);

            for(Puzzle1 p : population) {
                scores.add(p.binFinalScore());
            }


            GeneticAlgorithm ga = new GeneticAlgorithm(population,scores);

            //puzzle1 genetic algorithm with elitism keeping 3, 30% being culled, and pop size of 10
            ga.puzzle1GA(3,.30,10);
        } else {
            fileRead(args);
        }



    }

    public static void fileRead(String[] args) {

        ArrayList<Float> randArr = new ArrayList<>();

        if(Integer.parseInt(args[0]) == 1) {
            puzzle1Setup(args);

            try {
                File myObj = new File(args[1]);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    randArr.add(Float.parseFloat(data));
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            puzzle2Setup(args);
            try {
                File myObj = new File(args[1]);
                Scanner myReader = new Scanner(myObj);
                ArrayList<TowerPiece> pieces = new ArrayList<>();
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] preProcess = data.split(",\\s*");;
                    TowerPiece piece = new TowerPiece(preProcess[0], Integer.parseInt(preProcess[1]), Integer.parseInt(preProcess[2]), Integer.parseInt(preProcess[3]));
                    pieces.add(piece);

                }
                myReader.close();

                ArrayList<List<TowerPiece>> pop = TowerBuilder.generatePop(pieces, 50, 5);
                TowerBuilder.run(pop, Integer.parseInt(args[2]));

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Puzzle1> puzzle1Setup(String[] args) {
        Random r = new Random();
        ArrayList<Float> input = new ArrayList<>();

        ArrayList<Puzzle1> population = new ArrayList<>();
        Puzzle1 pop;

        //generate 40 random numbers between -10 and 10
        //these won't change
        for (int i = 0; i < 40; i++) {
            input.add((20 * r.nextFloat())-10);
        }

        System.out.println(input.size());
        for(int i = 0; i < 10; i++) {
            pop = assignRandomly(input);
            population.add(pop);
        }
        //pop = assignRandomly(input);
        //assignRandomly(input);
        try {
            File myObj = new File(args[1]);
            if (myObj.createNewFile()) {
                //create file and populate it with random numbers
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(args[1]);
                for(int i = 0; i < input.size(); i++) {
                    myWriter.write(String.valueOf(input.get(i)) + "\n");
                }
                myWriter.close();
            } else {
                //file already exists: add random numbers to file
                FileWriter myWriter = new FileWriter(args[1]);
                for(int i = 0; i < input.size(); i++) {
                    myWriter.write(String.valueOf(input.get(i))+"\n");
                }
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return population;
    }

    public static Puzzle1 assignRandomly(ArrayList<Float> input1) {
        ArrayList<Float> numbersinBin1 = new ArrayList<>();
        ArrayList<Float> numbersinBin2 = new ArrayList<>();
        ArrayList<Float> numbersinBin3 = new ArrayList<>();
        ArrayList<Float> numbersinBin4 = new ArrayList<>();
        int bound=40;
        int i = 0;
        ArrayList<Float> numbers = new ArrayList<>(input1);
        while (i < 10)
        {
            System.out.println(numbers.size());
            int randomNumber = new Random().nextInt(bound--);
            float choice = numbers.get(randomNumber);
            numbersinBin1.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);
            numbersinBin2.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);
            numbersinBin3.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);
            numbersinBin4.add(numbers.get(randomNumber));
           numbers.remove(choice);

            i++;
        }

        Bin bin1 = new Bin(1, numbersinBin1);
        Bin bin2 = new Bin(2,numbersinBin2);
        Bin bin3 = new Bin(3,numbersinBin3);
        Bin bin4 = new Bin(4,numbersinBin4);

        Puzzle1 generation = new Puzzle1(bin1,bin2,bin3,bin4);

        System.out.println("Numbers in bin1");
        for(int j=0;j<10;j++)
        {
            System.out.println(numbersinBin1.get(j));
        }
        System.out.println("Numbers in bin2");
        for(int j=0;j<10;j++)
        {
            System.out.println(numbersinBin2.get(j));
        }
        System.out.println("Numbers in bin3");
        for(int j=0;j<10;j++)
        {
            System.out.println(numbersinBin3.get(j));
        }
        System.out.println("Numbers in bin4");
        for(int j=0;j<10;j++)
        {
            System.out.println(numbersinBin4.get(j));
        }

        return generation;
    }

    public static void puzzle2Setup(String[] args) {
//        try {
//            File myObj = new File(args[1]);
//            if (myObj.createNewFile()) {
//                //create file and populate it with random numbers
//                System.out.println("File created: " + myObj.getName());
//                FileWriter myWriter = new FileWriter(args[1]);
//                for(int i = 0; i < input.size(); i++) {
//                    myWriter.write(String.valueOf(input.get(i)) + "\n");
//                }
//                myWriter.close();
//            } else {
//                //file already exists: add random numbers to file
//                FileWriter myWriter = new FileWriter(args[1]);
//                for(int i = 0; i < input.size(); i++) {
//                    myWriter.write(String.valueOf(input.get(i))+"\n");
//                }
//                myWriter.close();
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
    }


}
