package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Som {
	public static void tocarSom(String caminhoArquivo) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(caminhoArquivo));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
