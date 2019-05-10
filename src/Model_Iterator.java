import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Model_Iterator{

    public static void main(String[] args){

        ISolver solver = SolverFactory.newDefault();
        ModelIterator mi = new ModelIterator(solver);
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new InstanceReader(mi);

        // filename is given on the command line
        try {
            boolean unsat = true;
            IProblem problem = reader.parseInstance("./test.cnf");
            while(problem.isSatisfiable()){
                unsat = false ;
                int [] model = problem.model();
                System.out.println(Arrays.toString(model));
            }
            if (unsat){
                System.out.println("Unsatisfiable!");
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch ( ParseFormatException e) {
            e.printStackTrace();
        } catch ( IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable(trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout,sorry!");
        }
    }
}
