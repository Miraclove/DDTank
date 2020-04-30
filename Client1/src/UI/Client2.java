package UI;

import Config.EntityInfo;
import Config.GameInfo;
import Network.ChatThread;
import Network.InteractWithServer;
import Rendering.test.Main;

public class Client2 {
    public static void main(String[] args){
        boolean isSuccess = (Boolean) InteractWithServer.isLogin("Mike","123456");
        EntityInfo player1 = new EntityInfo("Jack");
        EntityInfo player2 = new EntityInfo("Mike");
        GameInfo gameInfo2 = new GameInfo();
        gameInfo2.gameEntity.put(player1.getUserName(),player1);
        gameInfo2.gameEntity.put(player2.getUserName(),player2);
        gameInfo2.curUser = player2.getUserName();
        new Main(gameInfo2);
    }
}
