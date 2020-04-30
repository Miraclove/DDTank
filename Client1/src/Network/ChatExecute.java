package Network;


import Config.EntityInfo;
import Config.GameInfo;
import Config.GuiInfo;
import Rendering.engine.Timer;
import Rendering.entity.Transform;
import Rendering.entity.Weapon;
import Rendering.gui.GUI;
import Rendering.test.Main;
import Rendering.world.World;
import UI.Menu;
import org.joml.Vector2f;

/**
 * Class {@code ChatExecute} Process message from the server
 *
 * <p>
 *
 * @author Weizhi
 * @since 05/02/2020
 */

public final class ChatExecute {
    //     message : 接收到的消息内容
    private static String message;
    //     fromId : 发送该消息的用户ID，用作显示对方信息
    private static String positionY;
    //     toId : 发送目标方ID，主要在群聊天中有效，用作消息应该展示在哪个群里面
    private static String positionX;
    //     接收消息内容的标识信息
    private static String userName;
    //    开始处理服务端发送已封装的消息内容
    /**
     * process info from the server
     *
     * @param scMessage the message from the server
     * @author Weizhi
     * @version 1.0
     */
    public static void execute(String scMessage) {
        // 对接收到的消息内容进行解码
        String res[] = scMessage.split("```", 2);
        // 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
        String type = res[0];
        String message = res[1];
        if(res.length == 2){
            if (type.equals("updatePlayer")){
                String temp[] = message.split("```",4);
                userName = temp[0];
                positionX = temp[1];
                positionY = temp[2];
                message = temp[3];
                World.entityList.get(userName).setPosition(new Vector2f(Float.parseFloat(positionX),Float.parseFloat(positionY)));
                Main.guiInfo.setTime(message);

            } else if (type.equals("updateWeapon")){
                String temp[] = message.split("```",5);
                String time = temp[0];
                String angle = temp[1];
                String percent = temp[2];
                String windForce = temp[3];
                //init the motion
//                WeaponController.setStart(World.entityList.get("weapon"),Float.parseFloat(time),Float.parseFloat(angle),Float.parseFloat(percent),Float.parseFloat(windForce));
//                WeaponController.isLaunched = true;

            }else if (type.equals("changeCameraTarget")){
                String temp[] = message.split("```",2);
                String target = temp[0];
                Main.curTurnPlayer = target;
                Main.isChangeCameraTarget = true;
            }else if (type.equals("changeWind")){
                String temp[] = message.split("```",2);
                String wind = temp[0];
                Main.guiInfo.setWind(wind);
//                GUI.setWindForce(Float.parseFloat(wind));
            }else if (type.equals("changeTimer")){
                String temp[] = message.split("```",2);
                String time = temp[0];
                Main.guiInfo.setTime(time);
                if (time.equals("0")){
                    System.out.println("Send GameInfo From "+ Main.curTurnPlayer);
                    InteractWithServer.sendGuiInfo(Main.gui.getAngle()+"",Main.gui.getPercent()+"","");
                }
//                GUI.setWindForce(Float.parseFloat(wind));
            }else if (type.equals("startGame")){
                String temp[] = message.split("```");
                GameInfo gameInfo = InteractWithServer.getInitGameInfo(temp[0]);
                gameInfo.curUser = Menu.curUsername;
                new Main(gameInfo);
            }
        }

//            GUI.setRemainTime(message);
//            // 以ID为键，对应聊天面板为值的哈希映射
//            HashMap<String, ChatWithFriends> fModel;
//            HashMap<String, GroupMember> gModel;
//
//            // 接收到的消息是从好友发送来的
//            if (type.equals("toFriend")) {
//
//                fModel = UI_MainInterface.getFriendChat();
//                System.out.println(scMessage);
//
//                // 展示在对应好友聊天面板中
//                // 若打开了聊天窗口
//                if (fModel.containsKey(fromId)) {
//                    fModel.get(fromId).addMessage(Tools.base64StringToImage(InteractWithServer.getFriendInfo(fromId).getUserAvatar()),UI_MainInterface.getFriend().get(fromId).getfName(), res[0], message,
//                            0);
//                }else {
//                    UI_MainInterface.friend.get(fromId).setBackground(new Color(146,199,241));
//                }
//            }
            // 接收到的消息是从某个群发送来的
//            else if (type.equals("toGroup")) {

//                gModel = World.entityList;
//                // 若打开了聊天窗口
//                if (gModel.containsKey(toId)) {
//                    // 聊天面板显示用户昵称
//                    String fromString = UI_MainInterface.getFriend().containsKey(fromId)
//                            ? UI_MainInterface.getFriend().get(fromId).getfName() : ("陌生人:" + fromId);
//                    gModel.get(toId).addMessage(Tools.base64StringToImage(InteractWithServer.getFriendInfo(fromId).getUserAvatar()),fromString, res[0], message, 0);
//                }else {
//                    UI_MainInterface.group.get(toId).setBackground(new Color(146,199,241));
//                }
//            }
//        } // 接收的内容是为了改变用户状态（在线/离线）
//        else if (res.length == 3) {
////             res[0]:验证标识、res[1]:状态信息、res[2]:好友ID
//            if (res[0].equals("OnlineSituation")) {
//                if (UI_MainInterface.getFriend().containsKey(res[2])) {
//                    System.out.println(res[0]);
//                    System.out.println(UI_MainInterface.getFriend().get(res[2]).getfName());
//                    UI_MainInterface.getFriend().get(res[2]).setfOnline(res[1]);
//                }
//            }

    }
}
