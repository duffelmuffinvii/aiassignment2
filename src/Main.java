import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
        }

    }

    public static void puzzle1Setup(String[] args) {
        Random r = new Random();
        //random seed will have to be deleted before submission
        //r.setSeed(10);
        ArrayList<Float> input = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            input.add((20 * r.nextFloat())-10);
        }
assignRandomly(input);
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
    }

    public static void assignRandomly(ArrayList<Float> numbers) {
        ArrayList<Float> numbersinBin1 = new ArrayList<>();
        ArrayList<Float> numbersinBin2 = new ArrayList<>();
        ArrayList<Float> numbersinBin3 = new ArrayList<>();
        ArrayList<Float> numbersinBin4 = new ArrayList<>();
        Bin bin1 = new Bin(1, numbersinBin1);
        Bin bin2 =new Bin(2,numbersinBin2);
        Bin bin3 =new Bin(3,numbersinBin3);
        Bin bin4 =new Bin(4,numbersinBin4);
        int bound=40;
        int i = 0;
        while (i < 10)
        {
        int randomNumber = new Random().nextInt(bound--);
        float choice = numbers.get(randomNumber);
            numbersinBin1.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);
            numbersinBin2.add(numbers.get(randomNumber));
            numbers.remove(choice);
            numbersinBin3.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);
            numbersinBin4.add(numbers.get(randomNumber));
            numbers.remove(choice);
            randomNumber= new Random().nextInt(bound--);
            choice = numbers.get(randomNumber);

        i++;
      }
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
    }
}
