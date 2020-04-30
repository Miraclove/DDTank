package Config;

import java.io.Serializable;
/**
 * Class {@code ChatVerify} Check whether username match password
 *
 * <p> use ObjectDataStream to transport Data
 *
 * @author : Weizhi
 * @since : 05/02/2020
 */
public final class ChatVerify implements Serializable {
    private String userId;
    private String userPassword;
    /**
     * Constructor for ChatVerify
     *
     * <p>
     *
     * @param userId the user name of the player
     * @param userPassword the user password of the player
     * @author Weizhi
     * @version 1.0
     */
    public ChatVerify(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    /**
     * get user name
     *
     * <p> get user name
     *
     * @return String, user name
     * @author Weizhi
     * @version 1.0
     */
    public String getUserId() {
        return userId;
    }
    /**
     * get user password
     *
     * <p> get user password
     *
     * @return String, user password
     * @author Weizhi
     * @version 1.0
     */
    public String getUserPassword() {
        return userPassword;
    }
}
