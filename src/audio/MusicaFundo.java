package audio;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicaFundo {
	private Clip clip;
	
	public void tocarMusica(String caminhoArquivo) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(caminhoArquivo));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
	}
	
	public void parar() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.close();
		}
	}
}
