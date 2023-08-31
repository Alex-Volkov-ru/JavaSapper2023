package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;

    private GameState state;
    public GameState getState() {
        return state;
    }



    public Game(int cols, int rows, int boombs){
        Ranges.setSize(new Coord(cols,rows));
        bomb = new Bomb(boombs);
        flag = new Flag();
    }

    public void start()
    {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox (Coord coord) {
        if(flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
        return flag.get(coord);
    }


    private void checkWinner(){
        if(state == GameState.PLAYED)
            if(flag.getCountofClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    public void pressLeftButton(Coord coord) {
        if(gameOver ()) return;
        openBox (coord);
        checkWinner();
    }
    private void openBox(Coord coord){
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO : openBoxAround (coord) ; return;
                    case BOMB : openBombs(coord); return;
                    default : flag.setOpenedToBox(coord); return;
                }
        }

    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
            if(bomb.get(coord) != Box.BOMB)
            if (flag.getCountofFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for(Coord around : Ranges.getCoordsAround(coord))
                    if(flag.get (around) == Box.CLOSED)
                        openBox(around);

    }

    private void openBombs(Coord bombed) {

        state = GameState.BOMBED;
        flag.setBobmbedToBox(bombed);
        for(Coord coord: Ranges.getAllCoords())
            if(bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBox(coord);
        else
            flag.setNobombToFlagedSafeBox(coord);
    }
    private void openBoxAround(Coord coord) {
        flag.setFlagedToBox(coord);
        for(Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }
    public void pressRightButton(Coord coord) {
        if(gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }
    private boolean gameOver(){
        if(state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
