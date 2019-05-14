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

    rule 4:
    based on user input, states which cells already have a number assigned to them

     */

    double rule_number_one_clauses = 0;
    double rule_number_two_clauses = 0;
    double rule_number_three_clauses = 0;

    HashMap< String,Integer > clause_dictionary = new HashMap< String, Integer>();
    HashMap< String,Integer > statement_dictionary = new HashMap< String, Integer>();

    //a running number for the clause index
    int clause_number = 0;
    int statement_number = 0;


    public void main(String[] args) {

        int size = 1;

        //get size as a Input from console

        size = inputSize();


    }


    public String[][]createRules(int sudoku_size, int [][] sudoku_to_solve) {

        //ArrayList rules [] = new ArrayList[3];
        String [][] rules  = new String [4][0];

        int size = sudoku_size;

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

        int implicator_value;
        int not_implicated_value;

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

        String [] rule_two = new String[(int )rule_number_two_clauses];
        String [] rule_two_cnf = new String[(int )rule_number_two_clauses];

        int insert_number = 0;

        for (int cell_row = 1; cell_row <= lenght; cell_row++) {

            for (int cell_column = 1; cell_column <= lenght; cell_column++) {

                //iterate every value in every cell with each other but just ONCE

                for (implicator_value = 1; implicator_value <= lenght; implicator_value++) {

                    int length_new = (int ) lenght - implicator_value;

                    // now for every cell fill in the clause all possible values like ( NOT c_1_1_v1 OR  NOT c_1_1_v2)
                    for (int number_values = 1; number_values <= length_new; number_values++) {

                        not_implicated_value = number_values + implicator_value;

                        String value_1 = "c_" + cell_row + "_" + cell_column + "_w" + implicator_value ;


                        String value_2 = "c_" + cell_row + "_" + cell_column + "_w" + not_implicated_value;


                        String value = "-" + value_1  + " v -" + value_2;

                        rule_two [insert_number] = value;

                        rule_two_cnf [insert_number] = "-"+Integer.toString(clause_dictionary.get(value_1) )+ " "
                                + "-" +Integer.toString(clause_dictionary.get(value_2) );



                        insert_number ++;

                        clause_number++;

                    }

                }

            }


        }

        rules[1] = rule_two_cnf;








        //************

        //create rule three

        System.out.println("rule 3 ");
        rule_number_three_clauses = binomi(lenght,two) * lenght * (lenght * 3); // the 3 is for row / coloumn / quadrant
        System.out.println(rule_number_three_clauses + "the core rules ");

        String [] rule_three = new String[(int )rule_number_three_clauses];
        String [] rule_three_cnf = new String[(int )rule_number_three_clauses];

        insert_number = 0;


        //column
        for (int cell_row = 1; cell_row <= lenght; cell_row++) {

            for (int cell_column = 1; cell_column <= lenght; cell_column++) {

                //iterate every value in every cell with each other but just ONCE

                for (implicator_value = 1; implicator_value <= lenght; implicator_value++) {

                    int length_new = (int ) lenght - implicator_value;

                    // now for every cell fill in the clause all possible values like ( NOT c_1_1_v1 OR  NOT c_1_1_v2)
                    for (int number_values = 1; number_values <= length_new; number_values++) {

                        //**************************************************** column is suplimented

                        not_implicated_value = number_values + implicator_value;

                        String value_1 = "c_" + cell_row + "_" + implicator_value + "_w" + cell_column ;


                        String value_2 = "c_" + cell_row + "_" + not_implicated_value + "_w" + cell_column;


                        String value = "-" + value_1  + " v -" + value_2;

                        rule_three [insert_number] = value;

                        rule_three_cnf [insert_number] = "-"+Integer.toString(clause_dictionary.get(value_1) )+ " "
                                + "-" + Integer.toString(clause_dictionary.get(value_2) );

                        //System.out.println(rule_two_cnf[insert_number]);
                        //System.out.println(rule_three [insert_number]);

                        insert_number ++;

                        clause_number++;

                    }

                }

            }


        }


        //row
        for (int cell_row = 1; cell_row <= lenght; cell_row++) {

            for (int cell_column = 1; cell_column <= lenght; cell_column++) {

                //iterate every value in every cell with each other but just ONCE

                for (implicator_value = 1; implicator_value <= lenght; implicator_value++) {

                    int length_new = (int ) lenght - implicator_value;

                    // now for every cell fill in the clause all possible values like ( NOT c_1_1_v1 OR  NOT c_1_1_v2)
                    for (int number_values = 1; number_values <= length_new; number_values++) {

                        //**************************************************** column is suplimented

                        not_implicated_value = number_values + implicator_value;

                        String value_1 = "c_" + implicator_value + "_" + cell_column + "_w" + cell_row ;


                        String value_2 = "c_" + not_implicated_value + "_" + cell_column + "_w" + cell_row;


                        String value = "-" + value_1  + " v -" + value_2;

                        rule_three [insert_number] = value;

                        rule_three_cnf [insert_number] = "-"+Integer.toString(clause_dictionary.get(value_1) )+ " "
                                + "-" +Integer.toString(clause_dictionary.get(value_2) );

                        //System.out.println(rule_two_cnf[insert_number]);
                        System.out.println(rule_three [insert_number]);

                        insert_number ++;

                        clause_number++;

                    }

                }

            }


        }


        //quardant
        /*
        for (int cell_row = 1; cell_row <= lenght; cell_row++) {

            for (int cell_column = 1; cell_column <= lenght; cell_column++) {

                //iterate every value in every cell with each other but just ONCE

                for (implicator_value = 1; implicator_value <= lenght; implicator_value++) {

                    int length_new = (int ) lenght - implicator_value;

                    // now for every cell fill in the clause all possible values like ( NOT c_1_1_v1 OR  NOT c_1_1_v2)
                    for (int number_values = 1; number_values <= length_new; number_values++) {

                        //**************************************************** column is suplimented

                        not_implicated_value = number_values + implicator_value;

                        String value_1 = "c_" + implicator_value + "_" + cell_column + "_w" + cell_row ;


                        String value_2 = "c_" + not_implicated_value + "_" + cell_column + "_w" + cell_row;


                        String value = "-" + value_1  + " v -" + value_2;

                        rule_three [insert_number] = value;

                        rule_three_cnf [insert_number] = "-"+Integer.toString(clause_dictionary.get(value_1) )+ " "
                                + "-" +Integer.toString(clause_dictionary.get(value_2) );

                        //System.out.println(rule_two_cnf[insert_number]);
                        System.out.println(rule_three [insert_number]);

                        insert_number ++;

                        clause_number++;

                    }

                }

            }


        }
        */


        int offset = 0;

        for (implicator_value = 1; implicator_value <= lenght; implicator_value ++) {

            int length_new = (int ) lenght - implicator_value;

         for (int  )
            for (int cell_row = 1; cell_row <= lenght; cell_row++) {

                for (int cell_column = 1; cell_column <= lenght; cell_column++) {


                        // now for every cell fill in the clause all possible values like ( NOT c_1_1_v1 OR  NOT c_1_1_v2)
                        for (int number_values = 1; number_values <= length_new; number_values++) {

                            //**************************************************** column is suplimented

                            not_implicated_value = number_values + implicator_value;

                            String value_1 = "c_" + cell_row + "_" + implicator_value + "_w" + cell_row ;


                            String value_2 = "c_" + cell_row + "_" + not_implicated_value + "_w" + cell_row;


                            String value = "-" + value_1  + " v -" + value_2;

                            rule_three [insert_number] = value;

                            rule_three_cnf [insert_number] = "-"+Integer.toString(clause_dictionary.get(value_1) )+ " "
                                    + "-" +Integer.toString(clause_dictionary.get(value_2) );


                            insert_number ++;

                            clause_number++;

                        }



                }


            }

            offset = offset + size;

        }




        rules[2] = rule_three_cnf;


        double sum_clauses = rule_number_two_clauses+rule_number_three_clauses+rule_number_one_clauses;

        System.out.println("All: ");
        System.out.println(sum_clauses);

        //consider user input
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < sudoku_to_solve.length; i++) {

            for (int j = 0; j < sudoku_to_solve[i].length; j++) {
                int value = sudoku_to_solve[i][j];
                if(value != 0){
                    int clause = (i * sudoku_size * sudoku_size) + (j * sudoku_size) + value;
                    list.add(Integer.toString(clause));
                }
            }
        }
        String[] rule_four = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            rule_four[i] = list.get(i);
        }
        rules[3] = rule_four;
        //end consider user input


        clause_number = clause_number + rule_four.length;

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





