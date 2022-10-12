import java.util.ArrayList;  

public class MoveChooser {
  
    public static Move chooseMove(BoardState boardState){

	int searchDepth = Othello.searchDepth;

        ArrayList<Move> moves= boardState.getLegalMoves();
        if (moves.isEmpty()){
            return null;
        }
        
        Move bestMove = null;
        int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;

        for (int i = 0; i < moves.size(); i++){
            Move move = moves.get(i);

            BoardState boardCopy = boardState.deepCopy();
            boardCopy.makeLegalMove(move.x, move.y);

            int childScore = minimaxValue(boardCopy, -1, searchDepth - 1, alpha, beta);
            if (bestMove == null || childScore > alpha){
                alpha = childScore;
                bestMove = move;
            }
        }

        return bestMove;
	   
    }

    private static int minimaxValue(BoardState board, int nodeType, int searchDepth, int alpha, int beta) {
        ArrayList<Move> moves = board.getLegalMoves();
        if (searchDepth <= 0 || moves.isEmpty()){
            return evaluate(board);
        }

        if (nodeType == 1) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++){
                Move move = moves.get(i);
                BoardState boardCopy = board.deepCopy();
                boardCopy.makeLegalMove(move.x, move.y);

                int childScore = minimaxValue(boardCopy, -nodeType, searchDepth - 1, alpha, beta);
                if (childScore > maxScore){
                    maxScore = childScore;
                    alpha = maxScore;
                }
                if (alpha >= beta)
                    break;
            }    

            return maxScore;

        }else{
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++){
                Move move = moves.get(i);
                BoardState boardCopy = board.deepCopy();
                boardCopy.makeLegalMove(move.x, move.y);

                int childScore = minimaxValue(boardCopy, -nodeType, searchDepth - 1, alpha, beta);
                if (childScore < minScore){
                    minScore = childScore;
                    beta = minScore;
                }

                if (alpha >= beta)
                    break;
            }    

            return minScore;

        }

    }

    static final int[][] boardValues = {
        { 120, -20, 20, 5, 5, 20, -20, 120 }, 
            { -20, -40, -5, -5, -5, -5, -40, -20 },
            { 20, -5, 15, 3, 3, 15, -5, 20 }, 
            { 5, -5, 3, 3, 3, 3, -5, 5 }, 
            { 5, -5, 3, 3, 3, 3, -5, 5 },
            { 20, -5, 15, 3, 3, 15, -5, 20 }, 
            { -20, -40, -5, -5, -5, -5, -40, -20 },
            { 120, -20, 20, 5, 5, 20, -20, 120 } };

    private static int evaluate(BoardState board){
        int score = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                int colour = board.getContents(i, j);
                if (colour == 1)
                    score += boardValues[i][j];
                else if (colour == -1)
                    score -= boardValues[i][j];

            }
        }

        return score;
    }

}
