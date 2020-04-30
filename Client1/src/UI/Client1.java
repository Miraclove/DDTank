package UI;

import Config.EntityInfo;
import Config.GameInfo;
import Network.ChatThread;
import Network.InteractWithServer;
import Rendering.test.Main;

public class Client1 {
    public static void main(String[] args){
        boolean isSuccess = (Boolean) InteractWithServer.isLogin("Jack","123456");
        EntityInfo player1 = new EntityInfo("Jack");
        EntityInfo player2 = new EntityInfo("Mike");
        GameInfo gameInfo1 = new GameInfo();
        gameInfo1.gameEntity.put(player1.getUserName(),player1);
        gameInfo1.gameEntity.put(player2.getUserName(),player2);
        gameInfo1.curUser = player1.getUserName();
        new Main(gameInfo1);
    }
}
