package application;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
public class Music {
       public void musicSound() {
              File bgm;
              AudioInputStream stream;
              AudioFormat format;
              DataLine.Info info;
              
              bgm = new File("/Bgm/MainBgm.wav"); // BgmÆú´õ
              
              Clip clip;
              
              try {
                     stream = AudioSystem.getAudioInputStream(bgm);
                     format = stream.getFormat();
                     info = new DataLine.Info(Clip.class, format);
                     clip = (Clip)AudioSystem.getLine(info);
                     clip.open(stream);
                     clip.start();
                     
              } catch (Exception e) {
                     System.out.println("err : " + e);
                     }
              
       }
       public static void main(String[] args) {
              Music m = new Music();
              while(true) {
                     try {
                           m.musicSound();
                        
                     } catch(Exception e) {
                           
                     }
              }
       }
}