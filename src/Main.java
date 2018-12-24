import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import eg.edu.alexu.csd.oop.game.Log;
import eg.edu.alexu.csd.oop.game.MakeSounds;
import eg.edu.alexu.csd.oop.game.Snapshot;
import eg.edu.alexu.csd.oop.game.object.Observable;
import eg.edu.alexu.csd.oop.game.world.InitialWorld;
import eg.edu.alexu.csd.oop.game.world.levels.EasyLevel;
import eg.edu.alexu.csd.oop.game.world.levels.HardLevel;
import eg.edu.alexu.csd.oop.game.world.levels.LevelDifficulty;
import eg.edu.alexu.csd.oop.game.world.levels.NormalLevel;

public class Main {


    public static void main(String[] args) {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem newMenuItem = new JMenuItem("New Game");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        final JMenuItem[] saveCheckPointMenuItem = {new JMenuItem("Save Check Point")};
        JMenuItem loadCheckPointMenuItem = new JMenuItem("Load Check Point");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.add(saveCheckPointMenuItem[0]);
        menu.add(loadCheckPointMenuItem);
        menuBar.add(menu);
        MakeSounds.play("src/Images/sound.wav");
        final Snapshot[] snapshot = {new Snapshot()};
        final boolean[] snapShotIsActive = {false};
        LevelDifficulty level = null;
                switch(args[0]) {
                	case "easy":
                		level = new EasyLevel();
                		break;
                	case "normal":
                		level = new NormalLevel();
                		break;
                	case "hard":
                		level = new HardLevel();
                		break;
                	default:
                		level = new EasyLevel();
                }
        
        
        InitialWorld initialWorld = new InitialWorld(InitialWorld.img.getWidth(), InitialWorld.img.getHeight(), level, new Observable());
        final GameController[] gameController = {GameEngine.start("Circus Of Plates", initialWorld, menuBar, Color.BLACK)};
        Log.getLoggeer().info("Start Game");
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController[0].pause();
                Log.getLoggeer().info("Pause Game");
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController[0].resume();
                Log.getLoggeer().info("Resume Game");
            }
        });
        saveCheckPointMenuItem[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snapshot[0] = initialWorld.getState();
                snapShotIsActive[0] = true;
                Log.getLoggeer().info("Check Point Saved");
            }
        });
        loadCheckPointMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snapShotIsActive[0]) {
                    initialWorld.setState(snapshot[0]);
                    Log.getLoggeer().info("Check Point Loaded");
                    snapShotIsActive[0] = false;
                } else {
                    Log.getLoggeer().info("Couldn't Load Check Point");
                }
            }
        });


    }

}