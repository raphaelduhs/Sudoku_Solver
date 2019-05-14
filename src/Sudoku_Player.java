import java.util.Map;
import java.util.stream.Collectors;

public class Sudoku_Player {


    public static void main(String[] args) {




        Sudoku_Rule_Creator rule_creator = new Sudoku_Rule_Creator();
        CNF_File_Creator cnf = new CNF_File_Creator();
        Sudoku_Solver solver = new Sudoku_Solver();
        CNF_To_Sudoku sudoku_creator = new CNF_To_Sudoku();


        String[] rule_1 = rule_creator.createRules()[0];

        cnf.create_file(rule_1,rule_creator.clause_number,rule_creator.statement_number);

        int [] solved = solver.solve("./sudoku.cnf");


        // swap the value with the key to be able to translate the solution back
        Map<String, Integer> map = rule_creator.clause_dictionary;

        Map<Integer, String> swapped = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        // maybe have the user input here instead of the CNF_FILE_CREATOR class
        // for now hardcoded with size 2

        int sudoku_size = rule_creator.size;


        int [][] sudoku = sudoku_creator.print_sudoku(solved, sudoku_size);

        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }



    }


}
