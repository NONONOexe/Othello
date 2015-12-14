package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MoveSound {
	Clip clip;
	AudioInputStream audioIn;

	public MoveSound() {
		start();
	}

	private void start() {
		try {
			audioIn = AudioSystem.getAudioInputStream(new File(
					"./sound/se_maoudamashii_se_pc01.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		clip.setFramePosition(0);
		clip.start();
	}
}
