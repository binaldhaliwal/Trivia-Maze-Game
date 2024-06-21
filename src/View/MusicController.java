package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Controls the playback and management of background music in the game.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class MusicController {
    /**
     * The music clip that is played in the game.
     */
    private static Clip CLIP;
    /**
     * A constant representing the number one.
     */
    private static boolean MUTE = false;

    /**
     * Controls the playback and management of background music in the game.
     */
    public static void playMusic() {
        final String path = "src/Resources/game-music-loop-7-145285.wav";
        try {
           final File music = new File(path);
            if (music.exists()) {
                final AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                if (CLIP != null) {
                    CLIP.close();
                }
                CLIP = AudioSystem.getClip();
                CLIP.open(audioInput);
                CLIP.start();
                CLIP.loop(Clip.LOOP_CONTINUOUSLY);
                MUTE = false;
            } else {
                System.out.println("Error: Music file not found.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the music in the game.
     */
    public static void muteMusic() {
        if (CLIP != null) {
            CLIP.stop();
            MUTE = true;
        }
    }

    /**
     * Unmutes the music in the game.
     */
    public static void unmuteMusic() {
        if (CLIP != null && MUTE) {
            CLIP.start();
            CLIP.loop(Clip.LOOP_CONTINUOUSLY);
            MUTE = false;
        }
    }

    /**
     * Stops the music in the game.
     */
    public static void musicStop() {
        if (CLIP != null) {
            CLIP.stop();
            CLIP.close();
            CLIP = null;
            MUTE = true;
        }
    }

    /**
     * Plays the losing sound effect.
     */
    public static void playLossSound() {
        final String path = "src/Resources/LosingSound.wav";
        soundEffect(path);
    }

    /**
     * Plays the sound effects for winning and losing.
     * @param thePath the path to the sound effect
     */
    private static void soundEffect(final String thePath) {
        try {
            final Clip clip = AudioSystem.getClip();
            final AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(thePath));
            clip.open(audioInput);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Plays the winning sound effect.
     */
    public static void playWinSound() {
        final String path = "src/Resources/WinningSound.wav";
        soundEffect(path);
    }
}
