import java.util.Map;
import java.util.stream.Collectors;

public class Sudoku_Player {


    public static void main(String[] args) {

        final int SUDOKU_SIZE = 2;

        final int [][] SUDOKU_TO_SOLVE = {
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        Sudoku_Rule_Creator rule_creator = new Sudoku_Rule_Creator();
        CNF_File_Creator cnf = new CNF_File_Creator();
        Sudoku_Solver solver = new Sudoku_Solver();
        CNF_To_Sudoku sudoku_creator = new CNF_To_Sudoku();


        String[][] rules = rule_creator.createRules();

        cnf.create_file(rules,rule_creator.clause_number,rule_creator.statement_number);

        // let the solver solve the sudoku and get a model in return
        int [] solved = solver.solve("./sudoku.cnf");

        /*
        // swap the value with the key to be able to translate the solution back
        Map<String, Integer> map = rule_creator.clause_dictionary;

        Map<Integer, String> swapped = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        */

        // create the solved sudoku based on the model of the solver
        int [][] sudoku = sudoku_creator.print_sudoku(solved, SUDOKU_SIZE);

        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }



    }


}
