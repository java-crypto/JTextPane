/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 08.01.2020
 * Funktion: TextPane HTML = laden und speichern einer HTML-Datei
 * Function: TextPane HTML = load and save of a html-file
 *
 * Hinweis/Notice
 * Sie benoetigen intelliJ um das Programm uebersetzen und ausfuehren zu koennen
 * You need intelliJ to build and run the program
 */

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;

public class TextPane {
    private JPanel panelMain;
    private JPanel panelButtons;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JButton buttonLoadHtml;
    private JButton buttonSaveHtml;

    public TextPane() {

        textPane.setContentType("text/html");
        textPane.setEditorKit(new HTMLEditorKit());

        buttonLoadHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    loadHtml("textpane_html.html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonSaveHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {saveHtml("textpane_html.html");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane HTML");
        frame.setSize(500, 350);
        frame.setVisible(true);
    }

   public void saveHtml(String filename){
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            HTMLEditorKit kit = (HTMLEditorKit) textPane.getEditorKit();
            StyledDocument doc = textPane.getStyledDocument();
            int len = doc.getLength();
            kit.write(fos, doc, 0, len);
            fos.close();
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        textPane.requestFocus();
    }

    public void loadHtml(String filename) throws IOException {
        File file = new File(filename);
        textPane.setPage(file.toURI().toURL());
        textPane.requestFocus();
    }
}
