package kalah.game;

public class StandardKalahGame extends KalahGame {
    public StandardKalahGame(int numberOfPlayers, int startingHouses, int startingSeeds) {
        super(numberOfPlayers, startingHouses, startingSeeds);
    }

    protected boolean extraTurnCondition(SowState state) {
        return state.seedsToSow == 0 && state.sowingHouseOwner == getPlayerTurn() && state.sowingHouseNumber == -1;
    }

    protected boolean captureCondition(SowState state) {
        return state.seedsToSow == 0 && state.sowingHouseOwner == getPlayerTurn() && state.seedsInHouse == 1 && state.seedsInOpposite > 0;
    }

    @Override
    protected boolean sowingStoreCondition(SowState state) {
        return state.sowingHouseOwner == getPlayerTurn();
    }

    @Override
    public TurnResult resolveTurnState(SowState sowState) {
        if (captureCondition(sowState)) {
            capture(sowState.sowingHouseOwner, sowState.sowingHouseNumber);
            return TurnResult.NEXT_TURN;
        }

        if (extraTurnCondition(sowState)) {
            return TurnResult.EXTRA_TURN;
        }
        return TurnResult.NEXT_TURN;
    }
}
