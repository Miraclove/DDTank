package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
/**
 * Class {@code AudioClip} Audio player in the game
 *
 * <p> For playing the audio in the game
 *
 * @author : Damon,JavaDoc edit by Weizhi
 * @since : 19/02/2020
 */
public class AudioClip {
    private File file;
    /**
     * Constructor for Audio
     *
     * <p> Input Audio from File to Audio Player to construct.
     *
     * @param path Audio file path
     * @author Damon,JavaDoc edit by Weizhi
     * @version 1.0
     */
    public AudioClip(String path) {
        file = new File(path);
        if (!file.exists()){
            System.out.println("Error >> AudioClip Not Found");
        }
    }

    /**
     * get Audio Stream
     *
     * <p> get Audio Stream
     *
     * @return AudioInputStream, Auto generated
     * @author Damon,JavaDoc edit by Weizhi
     * @version 1.0
     */
    public AudioInputStream getAudioStream() {
        try {
            return AudioSystem.getAudioInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
