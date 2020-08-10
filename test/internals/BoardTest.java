package internals;

import internals.meeple.Vizier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void testBoardCreation(){
        Board board = new Board();
        board.map[2][2].builder.addMember();
        board.printBoard();
        Board copyBoard = new Board(board);
        copyBoard.printBoard();
        board.map[2][2].vizier.addMember();
        assertNotEquals(board.map[2][2].vizier.getPopulation(),
                copyBoard.map[2][2].vizier.getPopulation());
    }

    @Test
    public void testPossibleMove(){
        Board board = new Board();
        board.clean();
        System.out.println();
        System.out.println();
        System.out.println();

        ArrayList<Board> testPosMove0 = board.getPossibleMove(0,0);
        assertTrue(testPosMove0.isEmpty());

        board.map[1][1].builder.addMember();
        board.map[1][1].elder.addMember();
        board.map[1][1].vizier.addMember();
        ArrayList<Board> testPosMove1 = board.getPossibleMove(1,1);
        assertTrue(testPosMove1.isEmpty());
        board.map[1][0].vizier.addMember();

        ArrayList<Board> testPosMove2 = board.getPossibleMove(1,1);
        assertNotEquals(true, testPosMove2.isEmpty());
        assertEquals(4, testPosMove2.size());
        for(Board posBoard : testPosMove2){
            assertTrue(posBoard.activeMeeple instanceof Vizier);
            assertEquals(2, posBoard.activeMeeple.getPopulation());
            assertEquals(1, posBoard.activeX);
            assertEquals(0, posBoard.activeY);
        }
    }

}