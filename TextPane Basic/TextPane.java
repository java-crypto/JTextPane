/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 30.12.2019
 * Funktion: TextPane Basic = laden und speichern einer Textdatei
 * Function: TextPane Basic = load and save a textfile
 *
 * Hinweis/Notice
 * Sie benoetigen intelliJ um das Programm uebersetzen und ausfuehren zu koennen
 * You need intelliJ to build and run the program
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextPane {
    private JPanel panelMain;
    private JPanel panelButtons;
    private JButton buttonLoad;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JButton buttonSave;

    public TextPane() {

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileReader reader = null;
                try {
                    reader = new FileReader("textpane_basic.txt");
                    textPane.read(reader, "textpane_basic.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileWriter writer = null;
                try {
                    writer = new FileWriter("textpane_basic.txt");
                    textPane.write(writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane Basic");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
