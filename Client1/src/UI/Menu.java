package UI;

import Config.EntityInfo;
import Config.GameInfo;
import Config.LobbyInfo;
import Network.ChatThread;
import Network.InteractWithServer;
import Rendering.test.LocalGame;
import Rendering.test.Main;
import com.google.gwt.logging.client.DefaultLevel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.xpath.operations.Bool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import static Rendering.test.Main.gameInfo;
/**
 * Class {@code Menu} the Main Client of the Client
 *
 *
 * @author Joshi, Weizhi,JavaDoc by Weizhi
 * @since 05/02/2020
 */

public class Menu extends Application {

    private static Stage stage = new Stage();
    private static Pane pane = new Pane();
    private static Scene scene;
    private static Label id = new Label("Not logged in");

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final String FONT = "-fx-family: 'Britannic';";
    private static final String LABEL_SIZE = "-fx-font-size: 24;";
    private static final String BASE_COLOUR = "-fx-base: rgb(41,55,63);";
    private static final String DEFAULT_STYLE = "-fx-base: rgb(41,55,63); -fx-font-size: 24; -fx-text-fill: rgb(133,121,103);" + FONT;
    private static final String HOVER_STYLE = "-fx-base: rgb(41,55,63); -fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: rgb(182,175,164);" + FONT;
    private static final double MENU_BUTTON_WIDTH = 275;
    private static final double MENU_BUTTON_HEIGHT = 50;
    private static final double MENU_BUTTON_SPACE = MENU_BUTTON_HEIGHT + 15;
    private static final double MENU_START_X = 475;
    private static final double MENU_START_Y = 225;
    public static String curUsername;
    private static boolean isLogin = false;
    private static Thread updateThread;
    private static Vector<String> originalGameInfo;

    /**
     * get the main menu
     *
     * @return scene,the main menu scene
     * @author Joshi,JavaDoc by Weizhi
     * @version 1.0
     */
    public static Scene mainMenu() throws FileNotFoundException {

        //Creating Buttons for menu
        Button button1 = createButton("Quick Play", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE,HOVER_STYLE);

        Button button2 = createButton("Lobby", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        Button button3 = createButton("Register", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        Button button4 = createButton("History", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        Button button5 = createButton("Exit", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        //Quick play button - not yet
        button1.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    new LocalGame(1);
                    stage.setScene(scene);
                    stage.setResizable(false);
                });

        //Lobby Button
        button2.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        if (isLogin){
                            scene = lobby();
                            stage.setScene(scene);
                            stage.setResizable(false);
                        }
                    } catch (FileNotFoundException ex) {

                    }

                });

        //Register button - not yet
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        if (!isLogin){
                            scene = register();
                            stage.setScene(scene);
                            stage.setResizable(false);
                        }
                    } catch (FileNotFoundException ex) {

                    }

                });
        //History button - not yet
        button4.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        if (isLogin){
                            scene = history();
                            stage.setScene(scene);
                            stage.setResizable(false);
                        }
                    } catch (FileNotFoundException ex) {

                    }

                });
        //Exit Button
        button5.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> System.exit(0));


        // Login section
        GridPane loginPane = new GridPane();
        Label head1 = new Label("Username:");
        Label head2 = new Label("Password:");
        TextField username = new TextField();
        PasswordField password = new PasswordField();

        if (id.getText()!="Not logged in") {
            loginPane.setVisible(false);
        }

        //Login Button
        Button login = createButton("Login", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        login.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                id.setText( "Logged in as: " + login(username,password,login,loginPane));
            }
        });
//


        loginPane.setVgap(10);
        loginPane.setHgap(5);
        loginPane.add(head1,0,0);
        loginPane.add(head2,0,1);
        loginPane.add(username,1,0);
        loginPane.add(password,1,1);
        loginPane.add(login,0,2,2,1);


        //Creating pane
        pane = new Pane();
        //Adding all elements to pane
        ObservableList list = pane.getChildren();
        GridPane menuPane = createMenuPane(button1,button2,button3,button4,button5, loginPane);
        list.addAll(menuPane,id);

        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        //Creating scene
        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->{

            if ((key.getCode()==KeyCode.ENTER)&&(scene.focusOwnerProperty().get() == password )){
                id.setText( "Logged in as: " + login(username,password,login,loginPane));
            }

        });
        return scene;
    }
    /**
     * update the room info
     *
     * @param groupName the group name of the room
     * @param lobby the lobby pane of room
     * @param startButton the start button of the room
     * @author Weizhi
     * @version 1.0
     */
    public static void updateRoom(String groupName,GridPane lobby,Button startButton){
        try{
            originalGameInfo = InteractWithServer.getGroupMember(groupName);
            while (true){
                Thread.sleep(500);
                Vector<String> gameInfo = InteractWithServer.getGroupMember(groupName);
                if (gameInfo.equals(originalGameInfo)){

                }else {
                    //set start button
                    Boolean isAllReady = true;
                    for (String key:gameInfo){
                        if (!key.split(" [|] ")[2].equals("Ready")){
                            isAllReady = false;
                            break;
                        }
                    }
                    if (isAllReady&&gameInfo.size()>1){
                        startButton.setDisable(false);
                    }else {
                        startButton.setDisable(true);
                    }
                    //update entities
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lobby.getChildren().clear();
                            //更新JavaFX的主线程的代码放在此处
                        }
                    });
                    int i = 0;
                    for (String key:gameInfo){
                        Label label = new Label();
                        File f = new File("./res/icon/player.png");
                        String url = f.toURI().toString();
                        Image image = new Image(url);
                        label.setGraphic(new ImageView(image));
                        Label entity = new Label(key);
                        int finalI = i;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                lobby.add(label,0, finalI);
                                lobby.add(entity,1, finalI);
                                //更新JavaFX的主线程的代码放在此处
                            }
                        });
                        i++;
                    }
                    originalGameInfo = gameInfo;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * get game match history
     *
     * @return Scene, the history panel
     * @author Weizhi
     * @version 1.0
     */
    public static Scene history() throws FileNotFoundException{

        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set avalible group
        Vector<String> history = InteractWithServer.getMatchHistory(curUsername);
        GridPane lobby = new GridPane();
        for (int i = 0;i<history.size();i++) {
            Label label = new Label();
            File f = new File("./res/icon/player.png");
            String url = f.toURI().toString();
            Image image = new Image(url);
            label.setGraphic(new ImageView(image));
            Label matchInfo = new Label(history.get(i));
            lobby.add(label, 0, i);
            lobby.add(matchInfo, 1, i);
        }
        lobby.setVgap(15);
        lobby.setGridLinesVisible(true);

        GridPane lobbyPane = createMenuPane(new Label(), new Label(),new Label(), new Label(), button3, lobby);
        list.addAll(lobbyPane,id);
        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = mainMenu();
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });
        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        return scene;
    }
    /**
     * get the room scene
     *
     * @return scene,the room scene
     * @author Weizhi
     * @version 1.0
     */
    public static Scene room(String groupName) throws FileNotFoundException{
        System.out.println("Join room: "+groupName);
        pane = new Pane();

        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Label name = new Label(groupName);
        Button button1 = createButton("Start!", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        Button button2 = createButton("Unready", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        Button button4 = createButton("Config", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        button1.setDisable(true);
        //Set Entities
        Vector<String> gameInfo = InteractWithServer.getGroupMember(groupName);
        GridPane lobby = new GridPane();
        int i = 0;
        for (String key:gameInfo){
            Label label = new Label();
            File f = new File("./res/icon/player.png");
            String url = f.toURI().toString();
            Image image = new Image(url);
            label.setGraphic(new ImageView(image));
            Label entity = new Label(key);
            lobby.add(label,0,i);
            lobby.add(entity,1,i);
            i++;
        }
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateRoom(groupName,lobby,button1);
            }
        });
        updateThread.start();

        GridPane lobbyPane = createMenuPane(name,button1,button2, button4, button3, lobby);
        list.addAll(lobbyPane,id);
        //start button
        button1.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    InteractWithServer.startGame(groupName);
                });

        //prepared button
        button2.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    if (button2.getText().equals("Ready!")){
                        InteractWithServer.setUnready(curUsername);
                        button2.setText("Unready");
                        button3.setDisable(false);
                    }else {
                        InteractWithServer.setReady(curUsername);
                        button2.setText("Ready!");
                        button3.setDisable(true);
                    }
                });
        //Config button
        button4.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try{
                         scene = Config(groupName);
                         updateThread.stop();
                    }catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });
        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        if (button2.getText().equals("Unready")){
                            Boolean isSuccess = InteractWithServer.leaveGroup(curUsername);
                            if (isSuccess){
                                scene = lobby();
                                updateThread.stop();
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });



        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * get the lobby scene
     *
     * @return scene,the lobby scene
     * @author Joshi,JavaDoc by Weizhi
     * @version 1.0
     */
    public static Scene lobby() throws FileNotFoundException{

        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button1 = createButton("Host Game", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        Button button2 = createButton("Join Game", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set avalible group
        LobbyInfo lobbyInfo = InteractWithServer.getLobbyInfo();
        GridPane lobby = new GridPane();
        for (int i = 0;i<lobbyInfo.gameInfos.size();i++){
            Label label = new Label();
            File f = new File("./res/icon/player.png");
            String url = f.toURI().toString();
            Image image = new Image(url);
            label.setGraphic(new ImageView(image));
            Button group = createButton(lobbyInfo.gameInfos.get(i),MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);
            lobby.add(label,0,i);
            lobby.add(group,1,i);
            int finalI = i;
            group.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
                try {
                    if(InteractWithServer.isExistGroup(lobbyInfo.gameInfos.get(finalI).split(" [|]")[0])){
                        Boolean isSuccess = InteractWithServer.joinGroup(lobbyInfo.gameInfos.get(finalI).split(" [|]")[0],curUsername);
                        if (isSuccess){
                            scene = room(lobbyInfo.gameInfos.get(finalI).split(" [|]")[0]);
                        }else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.titleProperty().set("Join Fail!");
                            alert.headerTextProperty().set("");
                            alert.showAndWait();
                            scene = lobby();
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.titleProperty().set("Group missing!");
                        alert.headerTextProperty().set("Check again!");
                        alert.showAndWait();
                        scene = lobby();
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                stage.setScene(scene);
                stage.setResizable(false);
            });
        }

        lobby.setVgap(15);
        lobby.setGridLinesVisible(true);

        GridPane lobbyPane = createMenuPane(button1, button2,new Label(), new Label(), button3, lobby);
        list.addAll(lobbyPane,id);

        //Host game button
        button1.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = hostGame();

                    } catch (Throwable ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });

        //Join Game button
        button2.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> { try {
                    scene = joinGame();

                } catch (Throwable ex) {

                }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = mainMenu();
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * get the join game scene
     *
     * @return scene,the join game scene
     * @author Weizhi
     * @version 1.0
     */
    public static Scene joinGame() throws FileNotFoundException{
        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set Create groups
        GridPane loginPane = new GridPane();
        Label head1 = new Label("Group Name");
        TextField groupname = new TextField();
        //Login Button
        Button login = createButton("Join", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        login.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupname.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Input Empty!");
                    alert.headerTextProperty().set("Please input correct group name");
                    alert.showAndWait();
                }else if(!InteractWithServer.isExistGroup(groupname.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Group Not Exist!");
                    alert.headerTextProperty().set("Please input correct group name");
                    alert.showAndWait();
                }else{
                    Boolean isSuccess = InteractWithServer.joinGroup(groupname.getText(),curUsername);
                    if (isSuccess){
                        try {
                            scene = room(groupname.getText());
                        } catch (FileNotFoundException ex) {

                        }
                        stage.setScene(scene);
                        stage.setResizable(false);
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.titleProperty().set("Join Fail!");
                        alert.headerTextProperty().set("Please input correct group name");
                        alert.showAndWait();
                    }
                }
            }
        });
//
        loginPane.setVgap(10);
        loginPane.setHgap(5);
        loginPane.add(head1,0,0);
        loginPane.add(groupname,0,1);
        loginPane.add(login,0,2,2,1);


        GridPane lobbyPane = createMenuPane(new Label(),new Label(),new Label(), new Label(), button3, loginPane);
        list.addAll(lobbyPane,id);

        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = lobby();
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * get the register scene
     *
     * @return scene,register user
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static Scene register () throws FileNotFoundException{
        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set Create groups
        GridPane loginPane = new GridPane();
        Label head1 = new Label("Username:");
        Label head2 = new Label("Password:");
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        //Login Button
        Button login = createButton("Register", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        login.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (username.getText().isEmpty()||password.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Input Empty!");
                    alert.headerTextProperty().set("Please input correct name or password!");
                    alert.showAndWait();
                }else {
                    if (InteractWithServer.registerUser(username.getText(),password.getText())){
                        try {
                            scene = mainMenu();
                        } catch (FileNotFoundException ex) {

                        }
                        stage.setScene(scene);
                        stage.setResizable(false);
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.titleProperty().set("Register Fail!");
                        alert.headerTextProperty().set("Please try again!");
                        alert.showAndWait();
                    }
                }
            }
        });
//
        loginPane.setVgap(10);
        loginPane.setHgap(5);
        loginPane.add(head1,0,0);
        loginPane.add(head2,0,1);
        loginPane.add(username,1,0);
        loginPane.add(password,1,1);
        loginPane.add(login,0,2,2,1);


        GridPane lobbyPane = createMenuPane(new Label(),new Label(),new Label(), new Label(), button3, loginPane);
        list.addAll(lobbyPane,id);

        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = mainMenu();
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * get the Config scene
     *
     * @param groupName the target group
     * @return scene,register user
     * @author Weizhi
     * @version 1.0
     */
    public static Scene Config (String groupName) throws FileNotFoundException{
        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set Create groups
        GridPane loginPane = new GridPane();
        Label head2 = new Label("Choose Your Weapon:");
//        TextField username = new TextField();
//        PasswordField password = new PasswordField();
        //Set Choice box

        Vector<String> weapon = InteractWithServer.getWeaponList();
        ChoiceBox<Object> weaponChoiceBox = new ChoiceBox<>();
        weaponChoiceBox.setItems(FXCollections.observableArrayList(weapon));
        weaponChoiceBox.setTooltip(new Tooltip("Select your weapon"));

        //Login Button
        Button save = createButton("Save", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        save.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (InteractWithServer.setUserWeapon(curUsername,weaponChoiceBox.getValue().toString().split(" [|]")[0])){
                    try {
                        scene = room(groupName);
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Change Fail!");
                    alert.headerTextProperty().set("Please try again!");
                    alert.showAndWait();
                }
            }
        });
//
        loginPane.setVgap(10);
        loginPane.setHgap(5);
        loginPane.add(head2,0,0);
        loginPane.add(weaponChoiceBox,0,1);
        loginPane.add(save,0,2,2,1);


        GridPane lobbyPane = createMenuPane(new Label(),new Label(),new Label(), new Label(), button3, loginPane);
        list.addAll(lobbyPane,id);

        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = room(groupName);
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * get the host game scene
     *
     * @return scene,the host game scene
     * @author Weizhi
     * @version 1.0
     */
    public static Scene hostGame() throws FileNotFoundException {
        pane = new Pane();
        ObservableList list = pane.getChildren();
        //Setting background image
        Image background = new Image(new FileInputStream("res/background/menu.png"));
        pane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        Button button3 = createButton("Back", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);

        //Set Create groups
        GridPane loginPane = new GridPane();
        Label head1 = new Label("Group Name");
        TextField groupname = new TextField();
        Label map = new Label("Game Map");

        Vector<String> gameMap =  InteractWithServer.getMapList();
        ChoiceBox<Object> mapChoiceBox = new ChoiceBox<>();
        mapChoiceBox.getItems().addAll(gameMap);
        mapChoiceBox.getSelectionModel().selectFirst();
        mapChoiceBox.setTooltip(new Tooltip("Select Map"));

        //Host Button
        Button login = createButton("Create", MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, DEFAULT_STYLE, HOVER_STYLE);


        login.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (groupname.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Input Empty!");
                    alert.headerTextProperty().set("Please input correct group name");
                    alert.showAndWait();
                }else if(InteractWithServer.isExistGroup(groupname.getText())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("Group Exist!");
                    alert.headerTextProperty().set("Please input correct group name");
                    alert.showAndWait();
                }else{
                    Boolean isSuccess = InteractWithServer.createGroup(groupname.getText(),curUsername,(String)mapChoiceBox.getValue());
                    if (isSuccess){
                        try {
                            scene = room(groupname.getText());
                        } catch (FileNotFoundException ex) {

                        }
                        stage.setScene(scene);
                        stage.setResizable(false);
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.titleProperty().set("Create Fail!");
                        alert.headerTextProperty().set("Please input correct group name");
                        alert.showAndWait();
                    }
                }
            }
        });
//
        loginPane.setVgap(10);
        loginPane.setHgap(5);
        loginPane.add(head1,0,0);
        loginPane.add(groupname,0,1);
        loginPane.add(map,1,0);
        loginPane.add(mapChoiceBox,1,1);
        loginPane.add(login,0,2,2,1);


        GridPane lobbyPane = createMenuPane(new Label(),new Label(),new Label(), new Label(), button3, loginPane);
        list.addAll(lobbyPane,id);

        //back
        button3.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    try {
                        scene = lobby();
                    } catch (FileNotFoundException ex) {

                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                });


        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }
    /**
     * login precess
     *
     * @param login login button
     * @param loginPane login pane
     * @param username the input user name
     * @param password the input password
     * @return String,the join information
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static String login(TextField username, PasswordField password, Button login,GridPane loginPane) {//throws AuthenticationException {
        if (!isLogin) {
            loginPane.setVisible(false);
            boolean isSuccess = (Boolean) InteractWithServer.isLogin(username.getText(),password.getText());
            if (!isSuccess){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Login Fail!");
                alert.headerTextProperty().set("Please input correct username and password");
                alert.showAndWait();
                loginPane.setVisible(true);
            }else {
                isLogin = true;
                curUsername = username.getText();
                new Thread(new ChatThread(curUsername)).start();
            }
            //username.setVisible(true);
            //password.setVisible(true);
        }else{

        }
        return username.getText();
    }
    /**
     * Create menu pane
     *
     * @param node1 the first component
     * @param node2 the second component
     * @param node3 the third component
     * @param node4 the fourth component
     * @param node5 the fifth component
     * @return GridPane,the GridPane created
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static GridPane createMenuPane(Node node1 , Node node2, Node node3, Node node4, Node node5, GridPane pane){

        GridPane menuPane = new GridPane();
        Region rect = new Region();
        rect.setStyle("-fx-background-color: rgb(225,222,218); " +
                "-fx-border-style: solid; -fx-border-width: 1; -fx-border-color: rgb(41,55,63); " +
                "-fx-min-width: 450; -fx-min-height:325; -fx-max-width:450; -fx-max-height: 325;");
        menuPane.add(rect,0,0,1,5);
        menuPane.relocate(0,MENU_START_Y);
        menuPane.setPadding(new Insets(0,30,30,30));
        menuPane.setVgap(15);
        menuPane.setHgap(15);
        menuPane.setPrefWidth(MENU_BUTTON_WIDTH);
        menuPane.getRowConstraints().add(new RowConstraints(50));
        menuPane.getRowConstraints().add(new RowConstraints(50));
        menuPane.getRowConstraints().add(new RowConstraints(50));
        menuPane.getRowConstraints().add(new RowConstraints(50));
        menuPane.getRowConstraints().add(new RowConstraints(50));
        menuPane.getColumnConstraints().add(new ColumnConstraints(450));
        menuPane.getColumnConstraints().add(new ColumnConstraints(305));
        menuPane.add(node1,1,0);
        menuPane.add(node2,1,1);
        menuPane.add(node3,1,2);
        menuPane.add(node4,1,3);
        menuPane.add(node5,1,4);
        menuPane.add(pane,0,0,1,5);
        pane.setAlignment(Pos.CENTER);

        //menuPane.setGridLinesVisible(true);
        return menuPane;

    }

    /**
     * Create label
     *
     * @param text the text of label
     * @param width the width of label
     * @param height the height of label
     * @param x the x position of label
     * @param y the y position of label
     * @param style the style of the label
     * @return Label,the label created
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static Label createLabel(String text, double width, double height, double x, double y, String style){
        Label label = new Label(text);
        label.setPrefSize(width,height);
        label.relocate(x,y);
        label.setStyle(style);
        return label;
    }

    /**
     * Create button
     *
     * @param text the text of button
     * @param width the width of button
     * @param height the height of button
     * @param style the style of the button
     * @param hstyle the high style of the button
     * @return Button,the button created
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static Button createButton(String text, double width, double height, String style, String hstyle){
        Button button = new Button(text);
        button.setPrefSize(width,height);
        //button.relocate(x,y);
        button.setStyle(style);
        button.setFocusTraversable(false);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> button.setStyle(hstyle));

        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> button.setStyle(style));
        String click1 = "res/sounds/click-on.wav";
        String click2 = "res/sounds/click-off.wav";
        Media clickOn = new Media(new File(click1).toURI().toString());
        Media clickOff = new Media(new File(click2).toURI().toString());

        button.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    MediaPlayer mediaPlayer = new MediaPlayer(clickOn);
                    mediaPlayer.play();
                });
        button.addEventHandler(MouseEvent.MOUSE_RELEASED,
                e -> {
                    MediaPlayer mediaPlayer = new MediaPlayer(clickOff);
                    mediaPlayer.play();
                });

        return button;
    }
    /**
     * Auto generated start
     *
     *
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    @Override
    public void start(Stage pristage) throws Exception {

        //Setting the title to Stage.
        stage.setTitle("Battlezone");
        //Setting icon
        stage.getIcons().add(new Image(new FileInputStream("res/icon.png")));
        stage.setMaxHeight(WINDOW_HEIGHT);
        stage.setMaxWidth(WINDOW_WIDTH);
        //getting the menu scene
        scene = mainMenu();
        //Setting the stage
        stage.setScene(scene);
        stage.setResizable(false);
        //Displaying the stage
        stage.show();
        System.out.println(stage.getWidth()+ " + " + stage.getHeight());
    }
    /**
     * Start of the Cilent
     *
     * @author Joshi, JavaDoc by Weizhi
     * @version 1.0
     */
    public static void main(String args[]){
        launch(args);
    }


}
