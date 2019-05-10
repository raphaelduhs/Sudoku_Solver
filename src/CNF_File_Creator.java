import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// creates a CNF File for a x*x Sudoku grid
public class CNF_File_Creator  {


    public static void main(String[] args)   {

        int size = 1;

    //get size as a Input from console
     try {
         size = inputSize();

     } catch (IOException e) {
         //no input
     }








    }



    public static int inputSize() throws IOException {

        System.out.println("Geben sie eine Zahl zwischen 2 und 6 ein");
        //read the size of the Sudoku
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
       int number = Integer.parseInt(reader.readLine());

       return number;


    }







}
