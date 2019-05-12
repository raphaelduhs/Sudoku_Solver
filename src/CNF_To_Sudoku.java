import java.util.Map;

// tranforms a Model to a Sudoku grid
public class CNF_To_Sudoku {

    public int[][] print_sudoku(int[] solved, int sudoku_size){
        //amount of cells per row or column
        sudoku_size *= sudoku_size;
        //sudoku solution matrix
        int [][] solution = new int [sudoku_size][sudoku_size];

        for(int i = 0; i < Math.pow(sudoku_size, 3); i = i+sudoku_size){

            for(int j = 0; j < sudoku_size; j++){

                if(solved[i+j] > 0){ // assign value to the cell
                    int row = (i / sudoku_size) / sudoku_size;
                    int column = ((i / sudoku_size) % sudoku_size) ;
                    solution[row][column] = j + 1;
                    break;
                }
            }
        }

        return solution;
    }
}
