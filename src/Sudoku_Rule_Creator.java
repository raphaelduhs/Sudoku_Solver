import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Sudoku_Rule_Creator {



     /*
    For a X times X Sudoku

    the single rules look like c_1_1_v1, c_1_2_v2, ..... c_X_X_vX
    the logical start is on the top left of the sudoku!

    rule 1:
    states that there is at least one value in each cell of the sudoku

    rule 2:
    states that there is not more than 1 value in each cell

    rule 3:
    states that the values 1 -- X are only allowed to appear once in every row/column/quadrant

     */

    double rule_number_one_clauses = 0;
    double rule_number_two_clauses = 0;
    double rule_number_three_clauses = 0;
    int size;

    HashMap< String,Integer > clause_dictionary = new HashMap< String, Integer>();

    //a running number for the clause index
    int clause_number = 0;
    int statement_number = 0;


    public void main(String[] args) {

        int size = 1;

        //get size as a Input from console

        size = inputSize();


    }


    public String[][]createRules() {

        //ArrayList rules [] = new ArrayList[3];
        String [][] rules  = new String [3][0];

        size = inputSize();

        //lenght of one row/coloumn/quardant
        double lenght = size*size;
        double two = 2;
        int clauses = 0;


        //***************


        //create rule one - this is equivalent to the number of cells
        System.out.println("rule 1 ");
        rule_number_one_clauses = Math.pow(lenght,two);
        System.out.println(rule_number_one_clauses);

        String [] rule_one = new String[(int )rule_number_one_clauses];
        String [] rule_one_cnf = new String[(int )rule_number_one_clauses];

        for (int cell_row = 1; cell_row <= lenght; cell_row++) {

            for (int cell_column = 1; cell_column <= lenght; cell_column++) {

                // now for every cell fill in the clause all possible values like c_1_1_v1 OR c_1_1_v2 OR .....
                for (int number_values = 1; number_values <= lenght ; number_values ++) {

                    String value = "c_"+cell_row + "_" +cell_column+ "_w"+number_values;

                    if (rule_one[clause_number] != null) {
                        rule_one[clause_number] = rule_one[clause_number] + " v " + value;
                    }else {
                        rule_one[clause_number] = value;
                    }


                    // fill the dictionary to be later able to translate the solution
                    statement_number++;
                    clause_dictionary.put(value, statement_number );

                    //for the CNF file
                    if (rule_one_cnf[clause_number] != null) {
                        rule_one_cnf[clause_number] = rule_one_cnf[clause_number] + " " + clause_dictionary.get(value);
                    }else {
                        rule_one_cnf[clause_number] = Integer.toString(clause_dictionary.get(value));
                    }




                }

                //System.out.println(rule_one[clause_number]);
                //System.out.println(rule_one_cnf[clause_number]);

                clause_number++;

            }


        }



        rules[0] = rule_one_cnf;





        //************

        //create rule two
        System.out.println("rule 2 ");
        rule_number_two_clauses = binomi(lenght, two);
        System.out.println(rule_number_two_clauses + " per cell");

        // number of clauses times number of cells
        rule_number_two_clauses = rule_number_two_clauses * rule_number_one_clauses;
        System.out.println(rule_number_two_clauses + " for all ");

        //************

        //create rule three

        System.out.println("rule 3 ");
        rule_number_three_clauses = binomi(lenght,two) * lenght * (lenght * 3); // the 3 is for row / coloumn / quadrant
        System.out.println(rule_number_three_clauses + "the core rules ");


        double sum_clauses = rule_number_two_clauses+rule_number_three_clauses+rule_number_one_clauses;

        System.out.println("All: ");
        System.out.println(sum_clauses);


        return rules;


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



    double binomi ( double n, double k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }



}





