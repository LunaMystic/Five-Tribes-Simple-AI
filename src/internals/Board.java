package internals;

import internals.meeple.Builder;
import internals.meeple.Elder;
import internals.meeple.MeepleGroup;
import internals.meeple.Vizier;

import java.util.ArrayList;
import java.util.Random;

// Index Example
// 00 01 02 03 04
// 10 11 12 13 14
// 20 21 22 23 24
// 30 31 32 33 34

/**
 * Board Class which store all information of a Board
 */
public class Board {
    public Tile[][] map;
    public static int[] valueArr = {15,8,4,12,6,5,8,4,10};
    public static int width = 3;
    public static int height = 3;

    // If the Board if currently on Pickup
    public MeepleGroup activeMeeple;
    public int activeX;
    public int activeY;
    /**
     * Default constructor of the Board
     */
    public Board(){
        Random random = new Random();
        map = new Tile[height][width];
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                map[i][j] = new Tile(valueArr[i*2+j]);
                for(int k=0; k<3; k++){
                    switch (random.nextInt(3)){
                        case 0:
                            map[i][j].builder.addMember();
                            break;
                        case 1:
                            map[i][j].elder.addMember();
                            break;
                        case 2:
                            map[i][j].vizier.addMember();
                            break;
                        default:
                            System.out.println("ERROR in random create tile");
                    }
                }
            }
        }
        // Placeholder
    }

    /**
     * Copy constructor of the board
     * @param b board copy from
     */
    public Board(Board b){
        map = new Tile[height][width];
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++) {
                Tile temp = b.map[i][j];
                map[i][j] = new Tile(temp.value);
                map[i][j].builder.setPopulation(temp.builder.getPopulation());
                map[i][j].vizier.setPopulation(temp.vizier.getPopulation());
                map[i][j].elder.setPopulation(temp.elder.getPopulation());
            }
        }
        this.activeX = b.activeX;
        this.activeY = b.activeY;
    }

    /**
     * Clean Method to do testing, Clean the board
     */
    public void clean(){
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++)
                map[i][j] = new Tile(valueArr[i*2+j]);
    }


    /**
     * Method to print a display of the board to console
     */
    public void printBoard(){
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                for(int k=0; k<15; k++){
                    System.out.print("-");
                }
                System.out.print("  ");
            }
            System.out.println();
            for(int j=0; j<width; j++){
                System.out.print("|");
                System.out.print(String.format("%-12s %s" , " Value: " + map[i][j].value, "|" ));
                System.out.print("  ");
            }
            System.out.println();
            for(int j=0; j<width; j++){
                tileInfoFormatter(" Viziers: ", map[i][j].vizier);
            }
            System.out.println();
            for(int j=0; j<width; j++){
                tileInfoFormatter(" Elder: ", map[i][j].elder);
            }
            System.out.println();
            for(int j=0; j<width; j++){
                tileInfoFormatter(" Builder: ", map[i][j].builder);
            }
            System.out.println();
        }
        for(int j=0; j<width; j++) {
            for (int k = 0; k < 15; k++) {
                System.out.print("-");
            }
            System.out.print("  ");
        }
        System.out.println();
    }

    /**
     * private helper to help print the certain field of a tile to console
     * @param s field name (Assassin, Elder, Builder, etc)
     * @param builder the meeple  corresponding to it
     */
    private void tileInfoFormatter(String s, MeepleGroup builder) {
        System.out.print("|");
        System.out.print(String.format("%-12s %s", s +
                builder.getPopulation(), "|"));
        System.out.print("  ");
    }

    public ArrayList<Board> getPossibleMove(int x, int y){
        ArrayList<Board> result = new ArrayList<>();
        int vizier = this.map[x][y].vizier.getPopulation();
        int elder = this.map[x][y].elder.getPopulation();
        int builder = this.map[x][y].builder.getPopulation();
        if(vizier + elder + builder == 0)
            return result;
        Board runner = new Board(this);
        runner.map[x][y] = new Tile(map[x][y].value);
        moveRecursionHelper(vizier, elder, builder, x, y, -1, -1, runner, result);
        return result;
    }

    // Index Example
    // 00 01 02 03 04
    // 10 11 12 13 14
    // 20 21 22 23 24
    // 30 31 32 33 34
    private static void moveLogicHelper(int vizier, int elder, int builder, int x, int y, int previousX, int previousY,
                            Board runner, ArrayList<Board> result){
        if(x >= height || x < 0)
            return;
        if(y >= width || y < 0)
            return;
        // Case When dropping the last Meeple
        if(vizier+elder+builder == 1){
            if(vizier == 1){
                if(runner.map[x][y].vizier.getPopulation() == 1){
                    runner.activeMeeple = new Vizier(runner.map[x][y].vizier.getPopulation() + 1);
                    runner.map[x][y].vizier.setPopulation(0);
                    runner.activeX = x;
                    runner.activeY = y;
                    runner.printBoard();
                    System.out.println("( " + runner.activeX + ", " + runner.activeY + ")\n\n");
                    result.add(runner);
                }
                return;
            }
            if(elder == 1){
                if(runner.map[x][y].elder.getPopulation() == 1){
                    runner.activeMeeple = new Elder(runner.map[x][y].elder.getPopulation() + 1);
                    runner.map[x][y].elder.setPopulation(0);
                    runner.activeX = x;
                    runner.activeY = y;
                    runner.printBoard();
                    System.out.println("( " + runner.activeX + ", " + runner.activeY + ")\n\n");
                    result.add(runner);
                }
                return;
            }
            if(builder == 1){
                if(runner.map[x][y].builder.getPopulation() == 1){
                    runner.activeMeeple = new Builder(runner.map[x][y].builder.getPopulation() + 1);
                    runner.map[x][y].builder.setPopulation(0);
                    runner.activeX = x;
                    runner.activeY = y;
                    runner.printBoard();
                    System.out.println("( " + runner.activeX + ", " + runner.activeY + ")\n\n");
                    result.add(runner);
                }
                return;
            }
        }
        //Otherwise Dropping each meeple and run recursion
        if(vizier != 0){
            Board nextRunner = new Board(runner);
            nextRunner.map[x][y].vizier.addMember();
            moveRecursionHelper(vizier - 1, elder, builder, x, y, previousX, previousY, nextRunner, result);
        }
        if(elder != 0){
            Board nextRunner = new Board(runner);
            nextRunner.map[x][y].elder.addMember();
            moveRecursionHelper(vizier, elder -1 , builder, x, y, previousX, previousY, nextRunner, result);
        }
        if(builder != 0){
            Board nextRunner = new Board(runner);
            nextRunner.map[x][y].builder.addMember();
            moveRecursionHelper(vizier, elder, builder - 1, x, y, previousX, previousY, nextRunner, result);
        }
    }

    private static void moveRecursionHelper(int vizier, int elder, int builder,
                                            int x, int y, int previousX, int previousY,
                                            Board nextRunner, ArrayList<Board> result) {
        if(x + 1 != previousX || y != previousY)
            moveLogicHelper(vizier, elder, builder, x + 1, y, x, y, nextRunner, result);
        if(x - 1 != previousX || y != previousY)
            moveLogicHelper(vizier, elder, builder, x - 1, y, x, y, nextRunner, result);
        if(x != previousX || y + 1 != previousY)
            moveLogicHelper(vizier, elder, builder, x, y + 1, x, y, nextRunner, result);
        if(x != previousX || y - 1 != previousY)
            moveLogicHelper(vizier, elder, builder, x, y - 1, x, y, nextRunner, result);
    }

}
