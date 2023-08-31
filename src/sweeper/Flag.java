package sweeper;

import java.nio.charset.CoderResult;

class Flag {
    private  Matrix flagMap;
    private  int countofClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countofClosedBoxes = Ranges.getSize().x*Ranges.getSize().y;
    }

    Box get (Coord coord) {
        return  flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countofClosedBoxes--;
    }
    void toggleFlagedToBox(Coord coord) {

        switch (flagMap.get(coord)) {
            case FLAGED : setClosedToBox (coord); break;
            case CLOSED : setFlagedToBox (coord); break;

        }

    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    int getCountofClosedBoxes() {
        return countofClosedBoxes;
    }

    void setBobmbedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBox(Coord coord) {
        if(flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }
    }

    void setNobombToFlagedSafeBox(Coord coord) {
        if(flagMap.get(coord) == Box.FLAGED){
            flagMap.set(coord, Box.NOBOMB);
        }
    }

    int getCountofFlagedBoxesAround(Coord coord) {
        int count = 0;
        for(Coord around : Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }

}
