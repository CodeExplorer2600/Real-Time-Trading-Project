import java.util.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public class CoinDCXWebSocketClient {
    public CoinDCXWebSocketClient(java.net.URI serverURI) {
        super(serverURI);
    }


    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to CoinDCX WebSocket API");
        send("{\"type\":\"subscribe\",\"symbols\":[\"BTC/USDT\"]}");
    }


    public void onMessage(String message) {
        System.out.println("Received message: " + message);

        JSONObject json = new JSONObject(message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from CoinDCX WebSocket API");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error occurred: " + ex.getMessage());
    }
}

public class OrderPayload {
    private String type;
    private String symbol;
    private double price;
    private double quantity;

    public OrderPayload(String type, String symbol, double price, double quantity) {
        this.type = type;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("symbol", symbol);
        json.put("price", price);
        json.put("quantity", quantity);
        return json.toString();
    }
}

public class OrderManager {
    public OrderPayload prepareBuyOrder(String symbol, double triggerPrice) {

        OrderPayload payload = new OrderPayload("buy", symbol, triggerPrice, 1.0);
        return payload;
    }

    public OrderPayload prepareSellOrder(String symbol, double triggerPrice) {

        OrderPayload payload = new OrderPayload("sell", symbol, triggerPrice, 1.0);
        return payload;
    }

    public void simulateCancelOrder(OrderPayload payload) {

        System.out.println("Simulating cancellation of order: " + payload.toJson());
    }
}

import java.util.Scanner;

public class CommandLineInterface {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter trigger price: ");
        double triggerPrice = scanner.nextDouble();
        OrderManager orderManager = new OrderManager();
        OrderPayload buyPayload = orderManager.prepareBuyOrder("BTC/USDT", triggerPrice);
        OrderPayload sellPayload = orderManager.prepareSellOrder("BTC/USDT", triggerPrice);

        System.out.println("Buy payload: " + buyPayload.toJson());
        System.out.println("Sell payload: " + sellPayload.toJson());

        orderManager.simulateCancelOrder(buyPayload);
        orderManager.simulateCancelOrder(sellPayload);
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }
}