import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;

// creates a CNF File for a x*x Sudoku grid
public class CNF_File_Creator  {


    public void create_file (String[][] rules, int number_clauses , int number_statements) {


        String[] rule_one = rules[0];
        String[] rule_two = rules[1];


        try{
            FileWriter fw=new FileWriter("./sudoku.cnf");

            fw.write("p cnf " + number_statements + " " +  number_clauses ) ;
            fw.write('\n');


            for (String rule: rule_one
                 ) {

                fw.write(rule);
                fw.append(" 0");
                fw.write('\n');

            }


            for (String rule: rule_two
            ) {

                fw.write(rule);
                fw.append(" 0");
                fw.write('\n');

            }



            fw.close();
        }catch(Exception e){System.out.println(e);}
        System.out.println("Success...");
    }


}


