package me.pumpking.battleships;

public class Main {

    public static void main(String[] args) {
        BattleshipsServer server = new BattleshipsServer(25565);
        server.start();

        System.out.println("Server running on " + server.getAddress());
    }

}
