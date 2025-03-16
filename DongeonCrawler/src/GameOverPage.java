import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static sun.tools.jconsole.inspector.XDataViewer.dispose;

public class GameOverPage {

    JFrame gameOverPage;
    protected Clip clip;

    public GameOverPage() {

        // --- Redémarrage du jeu ---
        JButton playNewGameButton = new JButton("Play a new game");
        playNewGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        playNewGameButton.setPreferredSize(new Dimension(100, 50));
        playNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Main();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                gameOverPage.dispose();
                clip.stop();
            }
        });

        // --- Gère l'affichage d'un écran GameOver dans le jeu ---
        JLabel gameOverPageLabel = new JLabel(new ImageIcon("./img/gameOverPage.jpg"));
        gameOverPage = new JFrame();
        gameOverPage.setSize(Main.dimension);
        gameOverPage.setLocationRelativeTo(null);
        gameOverPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverPage.setVisible(true);
        gameOverPage.add(gameOverPageLabel);
        gameOverPage.pack();
        gameOverPage.add(playNewGameButton, BorderLayout.SOUTH);
        gameOverPage.setSize(Main.dimension);


        // --- Gère la bande son d'un écran GameOver dans le jeu ---
        try{
            AudioInputStream welcomePageMusic = AudioSystem.getAudioInputStream(new File("./music/gameOverPageMusic.wav"));
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