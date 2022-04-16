



public class Main {

    public static void main(String[] args) {

     final int board_size = 9;//ROW      COLUMN
     int[][] board = new int[board_size][board_size];

     //int[][] options = new int[board_size][board_size];


       generateBoard(board);





     //   options = findCell(copyBoard(board), 1);
      //  insertNumber(1, board, options);
      //  printBoard(board);




    }






    static void generateBoard(int[][] board){

        int number = 1;

            for(int j = 0; j < 9; j ++){

                int[][] options = copyBoard(board);
                options = findCell(board, number);
                insertNumber(number, board, options);
                printBoard(board);


            }



            number++;

        for(int j = 0; j < 9; j ++){

            int[][] options = copyBoard(board);
            options = findCell(board, number);
            insertNumber(number, board, options);
            printBoard(board);


        }












    }






    static boolean insertNumber(int number, int[][] board, int[][] options){

        assert(board != null);
        assert(options != null);

        boolean result = false;

        int max = 8;
        int min = 0;
        int range = max - min + 1;

        int row = (int)(Math.random() * range) + min;
        int column = (int)(Math.random() * range) + min;

        int i = 0;
        while(!result){

            //If the cell is valid
            if(options[row][column] == -1){

                //Insert the number
                board[row][column] = number;
                result = true;
            }
            else
            {

                if(column < max){
                    column++;
                }
                else{
                    column = 0;
                    if(row < max){
                        row++;
                    }
                    else
                    {
                        row = 0;
                    }

                }
            }



            System.out.println("Row: " + row + "  Column: " + column);


            i++;
        }




        return result;
    }





    /*
    Checks if a given number is in a particular blockNumber
        Example)
            Block 1 | Block 2 | Block 3
            ---------------------------
            Block 4 | Block 5 | Block 6
            ---------------------------
            Block 7 | Block 8 | Block 9
     */

    static boolean inBlock(int[][] board, int blockNumber, int number) {

        assert(blockNumber >= 0 && blockNumber <= 9);
        assert(board != null);

        boolean result = false;
        int rowStart = 0;
        int columnStart = 0;
        if(blockNumber == 1){
            rowStart = 0;
            columnStart = 0;
        }
        else if(blockNumber == 2){
            rowStart = 0;
            columnStart = 3;
        }
        else if(blockNumber == 3){
            rowStart = 0;
            columnStart = 6;
        }
        else if(blockNumber == 4){
            rowStart = 3;
            columnStart = 0;
        }
        else if(blockNumber == 5){
            rowStart = 3;
            columnStart = 3;
        }
        else if(blockNumber == 6){
            rowStart = 3;
            columnStart = 6;
        }
        else if(blockNumber == 7){
            rowStart = 6;
            columnStart = 0;
        }
        else if(blockNumber == 8){
            rowStart = 6;
            columnStart = 3;
        }
        else if(blockNumber == 9){
            rowStart = 6;
            columnStart = 6;
        }

        for(int i = rowStart; i < (rowStart + 3) && !result; i++){
            for(int j = columnStart; j < (columnStart + 3) && !result; j++) {

                if(board[i][j] == number){
                    result = true;
                }
            }
        }

        return result;
    }


    // Marks block as invalid
    static void invalidateBlock(int[][] board, int blockNumber){
        assert(blockNumber >= 0 && blockNumber <= 9);
        assert(board != null);

        int rowStart = 0;
        int columnStart = 0;
        if(blockNumber == 1){
            rowStart = 0;
            columnStart = 0;
        }
        else if(blockNumber == 2){
            rowStart = 0;
            columnStart = 3;
        }
        else if(blockNumber == 3){
            rowStart = 0;
            columnStart = 6;
        }
        else if(blockNumber == 4){
            rowStart = 3;
            columnStart = 0;
        }
        else if(blockNumber == 5){
            rowStart = 3;
            columnStart = 3;
        }
        else if(blockNumber == 6){
            rowStart = 3;
            columnStart = 6;
        }
        else if(blockNumber == 7){
            rowStart = 6;
            columnStart = 0;
        }
        else if(blockNumber == 8){
            rowStart = 6;
            columnStart = 3;
        }
        else if(blockNumber == 9){
            rowStart = 6;
            columnStart = 6;
        }

        for(int i = rowStart; i < (rowStart + 3); i++){
            for(int j = columnStart; j < (columnStart + 3); j++) {

                if(board[i][j] == -1){
                    board[i][j] = 0;
                }
            }
        }
    }


        // Need to add 3 X 3 box check
    // Valid Cells are marked as -1. Empty cells are marked as 0
    static int[][] findCell(int[][] board, int number){

        assert (board != null);

        boolean validCell = true;
        int[][] validCells = copyBoard(board);

        // For each cell...
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board.length; column++){

                //If the Cell is empty...
                if(board[row][column] == 0){
                    validCell = true;

                    for(int i = 0; i < board.length; i++ ){    // Add dynamic programming to check if row or column previously contained number

                        //Check horizontally and vertically
                        if(board[row][i] == 1 || board[i][column] != 0){
                            validCell = false;
                        }
                    }
                }
                if(validCell && board[row][column] != number){
                    validCells[row][column] = -1;
                }
            }
        }

        // Mark any Blocks containing the given number as invalid
        for(int i = 0; i < board.length; i++){
            if(inBlock(validCells, i, number)){
                invalidateBlock(validCells,i);
            }
        }

        return validCells;
    }


    static int[][] copyBoard(int[][] board){

        assert (board != null);

        int[][] newBoard = new int[board.length][board.length];

        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board.length; column++){
                newBoard[row][column] = board[row][column];
            }
        }
        return newBoard;
    }

    static void printBoard(int[][] board){
        assert(board != null);
        System.out.println();
        //For each row
        for(int i = 0; i < board.length; i++){

            //And for each column
            for(int j = 0; j < board.length; j++){

                //Handle printing pipes to console
                if(((j + 1) % 3 == 0) && (j != board.length - 1)){
                    if(board[i][j] >= 0){
                        System.out.print("  " + board[i][j] + " |");
                    }
                    else{
                        System.out.print(" " + board[i][j] + " |");
                    }
                }
                else{
                    if(board[i][j] >= 0){
                        System.out.print("  " + board[i][j] + " ");
                    }
                    else {
                        System.out.print(" " + board[i][j] + " ");
                    }
                }
            }

            //Handle printing horizontal lines
            if(((i + 1) % 3 == 0) && (i != board.length -1)){
                System.out.println();
                System.out.print(" ");
                for(int k = 0; k < board.length*4; k++){
                    System.out.print("-");
                }
                System.out.println();
            }
            else {
                System.out.println();
            }
        }
    }
}
