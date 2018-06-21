package me.pumpking.battleships;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class BattleshipsServer extends WebSocketServer {

    BattleshipsServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onStart() {
        System.out.println("Server started");
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("New connection!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Connection closed");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        System.out.println("New message");
        System.out.println(message);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("An error has occurred!");
        e.printStackTrace();
    }

}
