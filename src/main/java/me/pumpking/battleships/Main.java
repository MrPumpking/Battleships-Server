package me.pumpking.battleships;

import me.pumpking.battleships.exceptions.IllegalShipPositionException;
import me.pumpking.battleships.models.Board;
import me.pumpking.battleships.models.Orientation;
import me.pumpking.battleships.models.ShipType;

public class Main {

    public static void main(String[] args) throws IllegalShipPositionException {
        // BattleshipsServer server = new BattleshipsServer(25565);
        // server.start();
        Board board = new Board(10, 10);

//        board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.SUBMARINE);
//        board.placeShip(4, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);
//        board.placeShip(8, 2, Orientation.HORIZONTAL, ShipType.BATTLESHIP);
        // board.placeShip(9, 3, Orientation.VERTICAL, ShipType.CARRIER);

        board.placeShip(9, 0, Orientation.HORIZONTAL, ShipType.DESTROYER);

        board.print();
    }

}
