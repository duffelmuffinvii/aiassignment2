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
        r.setSeed(10);
        ArrayList<Float> input = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            input.add((20 * r.nextFloat())-10);
        }

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

}
