import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sudoku_Rule_Creator {



     /*
    For a X times X Sudoku

    the single rules look like c_1_1_w1, c_1_2_w2, ..... c_X_X_wX
    the logical start is on the top left of the sudoku!

    rule 1:
    states that there is at least one value in each cell of the sudoku

    rule 2:
    states that there is not more than 1 value in each cell

    rule 3:
    states that the values 1 -- X are only allowed to appear once in every row/column/quadrant

     */

    int rule_number_one_clauses = 0;
    int rule_number_two_clauses = 0;
    int rule_number_three_clauses = 0;


    public void main(String[] args) {

        int size = 1;

        //get size as a Input from console

        size = inputSize();


    }


    public int inputSize()  {

        int number = -1;

        try {
            System.out.println("Geben sie eine Zahl zwischen 2 und 6 ein");
            //read the size of the Sudoku
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            number = Integer.parseInt(reader.readLine());

            return number;
        } catch (IOException e) {

            System.out.println("something went wrong");
            return number;
        }



    }


}



