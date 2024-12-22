package com.neatwitstudios.terrajengine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {

    private static final URL[] soundURL = init();
    private static final Clip[] clips = new Clip[soundURL.length];

    private SoundManager() {
    }

    public static URL[] init() {
        URL[] soundURL = new URL[16];
        soundURL[0] = SoundManager.class.getResource("/resources/static/sound/music.wav");
        soundURL[1] = SoundManager.class.getResource("/resources/static/sound/sound.wav");
        soundURL[2] = SoundManager.class.getResource("/resources/static/sound/swing.wav");
        soundURL[3] = SoundManager.class.getResource("/resources/static/sound/Damage.wav");
        soundURL[4] = SoundManager.class.getResource("/resources/static/sound/Kick.wav");
        soundURL[5] = SoundManager.class.getResource("/resources/static/sound/Pistash.wav");
        soundURL[6] = SoundManager.class.getResource("/resources/static/sound/Porcupone1.wav");
        soundURL[7] = SoundManager.class.getResource("/resources/static/sound/Porcupone2.wav");
        return soundURL;
    }

    private static Clip loadClip(int i) {
        Clip clip = null;
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    public static void playMusic(int i) {
        if (clips[i] == null) {
            clips[i] = loadClip(i);
        }
        if (clips[i] != null) {
            clips[i].start();
            clips[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopMusic(int i) {
        if (clips[i] != null && clips[i].isRunning()) {
            clips[i].stop();
        }
    }

    public static void toggleMusic(int i) {
        if (clips[i] != null) {
            if (clips[i].isRunning()) {
                clips[i].stop();
            } else {
                clips[i].start();
                clips[i].loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            playMusic(i);
        }
    }

    public static void playSE(int i) {
        Clip clip = loadClip(i);
        if (clip != null) {
            clip.start();
        }
    }
}
