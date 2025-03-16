import com.sun.jdi.event.ExceptionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main {

    static JFrame displayZoneFrame;
    JMenuBar displayZoneMenuBar;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    static Dimension dimension = new Dimension(845, 508);

    public Main() throws Exception {
        DynamicSprite.playerLifePoint = DynamicSprite.maxLifePoint;

        // --- Gère l'affichage d'une fenêtre avec JFrame ---
        displayZoneFrame = new JFrame("Dongeon Crawler");
        displayZoneFrame.setSize(dimension);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayZoneFrame.setLocationRelativeTo(null);
        displayZoneFrame.setResizable(false);

        // --- Gère l'affichage d'un menu dans le jeu => Intégration menu ---
        displayZoneMenuBar = new JMenuBar();
        displayZoneMenuBar.setBounds(0, 0, 100,20 );

        JMenu menuPartie = new JMenu("Partie");
        JMenu menuAPropos = new JMenu("À propos");

        // Codage des actions exécutées par les différents items
        JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle partie");
        itemNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomePage starterNewPage = new WelcomePage();
                displayZoneFrame.dispose();
            }
        });
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        itemQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        JMenuItem itemAPropos = new JMenuItem("À propos");
        itemAPropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(displayZoneFrame,"Ce jeu a été conçu par Hugo CARVALHO FONTES étudiant ENSEA promo 2026","À propos",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        displayZoneMenuBar.add(menuPartie);
        displayZoneMenuBar.add(menuAPropos);
        menuPartie.add(itemNouvellePartie);
        menuPartie.addSeparator();
        menuPartie.add(itemQuitter);
        menuAPropos.add(itemAPropos);
        displayZoneFrame.setJMenuBar(displayZoneMenuBar);

        // --- Test de l'animation d'un héro ---
        DynamicSprite hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),300,200,48,51);

        // --- Initialisation des différents Engines & Timers ---
        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Timer renderTimer = new Timer(50, (time)->renderEngine.update());
        renderTimer.start();
        Timer gameTimer = new Timer(50, (time)->gameEngine.update());
        gameTimer.start();
        Timer physicTimer = new Timer(50, (time)->physicEngine.update());
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setVisible(true);

        // --- Chargement du PlayGround ---
        PlayGround level = new PlayGround("./data/level1.txt");
        //SolidSprite testSprite = new SolidSprite(ImageIO.read(new File("./img/rock.png")),250,300,64,64);
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

    }

    // --- To GameOver menu... ---
    public static void isGameOver (double lifePoints){
        if (lifePoints==0){
            Main.displayZoneFrame.dispose();
            MainNextLevel.displayZoneFrameNewLevel.dispose();
            new GameOverPage();
        }
    }
}
