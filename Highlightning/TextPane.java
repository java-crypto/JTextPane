/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 14.01.2020
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
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
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
    private JButton buttonHighlightning;
    private JTextField tfSearch;
    private JButton buttonHighlightningOff;

    public TextPane() {

        textPane.setContentType("text/rtf");
        textPane.setEditorKit(new RTFEditorKit());

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadRtf("textpane_rtf.rtf");
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveRtf("textpane_rtf.rtf");
            }
        });

        buttonHighlightning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                highlightningText();
            }
        });
        buttonHighlightningOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeHighlights(textPane);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane Highlightning");
        frame.setSize(550, 350);
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

    public void highlightningText() {
        textPane.getHighlighter().removeAllHighlights();
        Document document = textPane.getDocument();
        try {
            String find = tfSearch.getText(); // uses the same textfield as search
            for (int index = 0; index + find.length() < document.getLength(); index++) {
                String match = document.getText(index, find.length());
                if (find.equalsIgnoreCase(match)) { // non case sensitive
                    //if (find.equals(match)) { // case sensitive
                    javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter =
                            new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    textPane.getHighlighter().addHighlight(index, index + find.length(),
                            highlightPainter);
                }
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        textPane.requestFocus();
    }

    public void removeHighlights(JTextComponent textComp) {
        textPane.getHighlighter().removeAllHighlights();
        tfSearch.setText("");
        textPane.requestFocus();
    }

}
