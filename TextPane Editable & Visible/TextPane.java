/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 15.01.2020
 * Funktion: TextPane Highlightning =
 *           markiert den Suchtext im Dokument in gelb
 * Function: TextPane Highlightning =
 *           highlights the search text in the document
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
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextPane extends JFrame {
    private JPanel panelMain;
    private JPanel panelButtons;
    private JButton buttonLoad;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JButton buttonSave;
    private JButton buttonEdit;
    private JButton buttonVisible;

    public TextPane() {

        textPane.setContentType("text/rtf");
        textPane.setEditorKit(new RTFEditorKit());
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadRtf("textpane_rtf.rtf");
                textPane.getCaret().setSelectionVisible(true);
                textPane.requestFocus();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveRtf("textpane_rtf.rtf");
                textPane.requestFocus();
            }
        });

        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toggleEditable();
            }
        });
        buttonVisible.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toggleVisible();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane Editable & Visible");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public void loadRtf(String filename) {
        RTFEditorKit RTF_KIT = new RTFEditorKit();
        textPane.setContentType("text/rtf");
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filename);
            final DefaultStyledDocument styledDocument = new DefaultStyledDocument(new StyleContext());
            RTF_KIT.read(inputStream, styledDocument, 0);
            textPane.setDocument(styledDocument);
            // delete added last line
            String content = textPane.getDocument().getText(0, textPane.getDocument().getLength());
            int lastLineBreak = content.lastIndexOf('\n');
            textPane.getDocument().remove(lastLineBreak, textPane.getDocument().getLength() - lastLineBreak);
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        textPane.setEditable(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
        textPane.setVisible(true);
        textPane.requestFocus();
    }

    public void saveRtf(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            RTFEditorKit kit = (RTFEditorKit) textPane.getEditorKit();
            StyledDocument doc = textPane.getStyledDocument();
            int len = doc.getLength();
            kit.write(fos, doc, 0, len);
            fos.close();
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        textPane.requestFocus();
    }

    private void toggleEditable(){
        textPane.setEditable(!textPane.isEditable());
        if (textPane.isEditable() == true) {
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } else {
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        textPane.requestFocus();
    }

    private void toggleVisible(){
        textPane.setVisible(!textPane.isVisible());
        textPane.requestFocus();
    }
}
