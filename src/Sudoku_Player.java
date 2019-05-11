import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sudoku_Player {


    public static void main(String[] args) {

        Sudoku_Rule_Creator rule_creator = new Sudoku_Rule_Creator();
        CNF_File_Creator cnf = new CNF_File_Creator();
        Sudoku_Solver solver = new Sudoku_Solver();



        String[] rule_1 = rule_creator.createRules()[0];

        cnf.create_file(rule_1,rule_creator.clause_number,rule_creator.statement_number);

        solver.solve("./sudoku.cnf");


        // swap the value with the key to be able to translate the solution back
        Map<String, Integer> map = rule_creator.clause_dictionary;

        Map<Integer, String> swapped = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        System.out.println(swapped.get(1));







    }


}
