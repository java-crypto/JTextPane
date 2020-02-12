/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
 * getestet mit/tested with: Java Runtime Environment 11.0.4 x64
 * Datum/Date (dd.mm.jjjj): 12.02.2020
 * Funktion: TextPane Menue
 * Function: TextPane Menue
 *
 * Sicherheitshinweis/Security notice
 * Die Programmroutinen dienen nur der Darstellung und haben keinen Anspruch auf eine korrekte Funktion,
 * insbesondere mit Blick auf die Sicherheit !
 * Prüfen Sie die Sicherheit bevor das Programm in der echten Welt eingesetzt wird.
 * The program routines just show the function but please be aware of the security part -
 * check yourself before using in the real world !
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class Main {
    public static ResourceBundle resourceBundle;

    public static void main(String[] args) {

        JMenuBar menuBar;
        JMenu menuFile, menuEdit;
        JMenuItem menuItemExit, menuItemLoad, menuItemSave, menuItemCopy, menuItemPaste;
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        menuItemLoad = new JMenuItem("Load");
        menuFile.add(menuItemLoad);
        menuItemSave = new JMenuItem("Save");
        menuFile.add(menuItemSave);
        menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemExit);
        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);
        menuItemCopy = new JMenuItem("Copy");
        menuEdit.add(menuItemCopy);
        menuItemPaste = new JMenuItem("Paste");
        menuEdit.add(menuItemPaste);

        TextPane form = new TextPane();
        JFrame frame = new JFrame("TextPane Menü");
        frame.setContentPane(form.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // insert of the menue
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);

        menuItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 form.loadRtf("textpane_rtf.rtf");
            }
        });

        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.saveRtf("textpane_rtf.rtf");
            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}