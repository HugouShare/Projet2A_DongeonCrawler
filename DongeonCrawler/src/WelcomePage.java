import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static sun.tools.jconsole.inspector.XDataViewer.dispose;

public class WelcomePage {

    JFrame welcomePage;
    protected Clip clip;

    public WelcomePage() {

        // --- Démarrage du jeu ---
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 18));
        playButton.setPreferredSize(new Dimension(100, 50));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main game = new Main();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                welcomePage.dispose();
                clip.stop();
            }
        });

        // --- Gère l'affichage d'un écran d'acceuil dans le jeu ---
        JLabel welcomePageLabel = new JLabel(new ImageIcon("./img/welcomePage.jpg"));
        welcomePage = new JFrame("Welcome Page");
        welcomePage.setSize(Main.dimension);
        welcomePage.setLocationRelativeTo(null);
        welcomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomePage.setVisible(true);
        welcomePage.add(welcomePageLabel);
        welcomePage.pack();
        welcomePage.add(playButton, BorderLayout.SOUTH);
        welcomePage.setSize(Main.dimension);


        // --- Gère la bande son d'un écran d'acceuil dans le jeu ---
        try{
            AudioInputStream welcomePageMusic = AudioSystem.getAudioInputStream(new File("./music/welcomePageMusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(welcomePageMusic);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
