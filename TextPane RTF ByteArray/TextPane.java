/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 30.12.2019
 * Funktion: TextPane RTF ByteArray =
 *           laden und speichern einer RTF-Datei über eine ByteArray
 * Function: TextPane RTF ByteArray =
 *           load and save of a rtf-file via a ByteArray
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextPane {
    private JPanel panelMain;
    private JPanel panelButtons;
    private JButton buttonLoad;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JButton buttonSave;
    byte[] arrayUncrypt = null;

    public TextPane() {

        textPane.setContentType("text/rtf");
        textPane.setEditorKit(new RTFEditorKit());

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadRtfByteArray("textpane_rtf.rtf");
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveRtfByteArray("textpane_rtf.rtf");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane RTF ByteArray");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public void loadRtfByteArray(String filename) {
        arrayUncrypt = readBytesFromFileNio(filename);
        RTFEditorKit RTF_KIT = new RTFEditorKit();
        textPane.setContentType("text/rtf");
        InputStream inputStream;
        try {
            inputStream = new ByteArrayInputStream(arrayUncrypt);
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

    public void saveRtfByteArray(String filename) {
        // diese routine überträgt den inhalt eines JTextPane in ein byteArray
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        RTFEditorKit kit = (RTFEditorKit) textPane.getEditorKit();
        StyledDocument doc2 = textPane.getStyledDocument();
        int len = doc2.getLength();
        try {
            kit.write(out, doc2, 0, len);
            arrayUncrypt = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBytesToFileNio(arrayUncrypt, filename);
    }

    private static void writeBytesToFileNio(byte[] byteToFileByte, String filenameString) {
        try {
            Path path = Paths.get(filenameString);
            Files.write(path, byteToFileByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readBytesFromFileNio(String filenameString) {
        byte[] byteFromFileByte = null;
        try {
            //bFile = Files.readAllBytes(new File(filenameString).toPath());
            byteFromFileByte = Files.readAllBytes(Paths.get(filenameString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteFromFileByte;
    }

}
