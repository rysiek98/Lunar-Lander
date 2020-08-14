import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/** Klasa odpowiadająca za muzyke w grze */

public class Sound {

    private static String musicPath = "2001.wav";
    static AudioInputStream audioInput;
    static Clip clip;
    private static boolean music;

    /** Metoda dodająca muzykę do gry
     *@param  play decyduje o stracie odtwarzania muzyki
     * */
    public static void playSound(boolean play) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        audioInput = AudioSystem.getAudioInputStream(new File(musicPath).getAbsoluteFile());

        clip = AudioSystem.getClip();

        clip.open(audioInput);
        if(play) {
            music = true;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }else{
            Control.changeMusicStatus(false);
            music = false;
        }
    }

    /** Metoda zatrzymująca muzykę w grze */
    public static void stopMusic(){
        clip.stop();
        music = false;
    }

    /** Metoda włączająca muzykę w grze */
    public static void startMusic(){
        clip.start();
        music = true;
    }
    /** Metoda informuje nas czy muzyka jest odtwarzana czy nie */
    public static boolean getMusic(){
        return music;
    }
}
