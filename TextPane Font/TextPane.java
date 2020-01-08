/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 11.0.5 x64
 * verwendete IDE/used IDE: intelliJ IDEA 2019.3.1
 * Datum/Date (dd.mm.jjjj): 08.01.2020
 * Funktion: TextPane Font =
 *           formatiert den selektierten Text in der Schrift
 * Function: TextPane Font =
 *           format of selected text in font
 *
 * Hinweis/Notice
 * Sie benoetigen intelliJ um das Programm uebersetzen und ausfuehren zu koennen
 * You need intelliJ to build and run the program
 */

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
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
    private JButton buttonFont;
    private JComboBox comboBoxFont;
    private int selectionStart, selectionEnd;
    private AttributeSet selectedAttribute;

    public TextPane() {

        textPane.setContentType("text/rtf");
        textPane.setEditorKit(new RTFEditorKit());

        // font color
        // get fonts dynamically
        GraphicsEnvironment gen = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = gen.getAvailableFontFamilyNames();
        for (String font : fonts) {
            comboBoxFont.addItem(font);
        }


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

        buttonFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeFont();
            }
        });

        // notwendig für selections
        textPane.addCaretListener(new CaretListener() { // achtet auf selektion
            @Override
            public void caretUpdate(CaretEvent caretEvent) {
                selectionStart = textPane.getSelectionStart();
                selectionEnd = textPane.getSelectionEnd();
                StyledDocument doc = textPane.getStyledDocument();
                selectedAttribute = doc.getCharacterElement(selectionStart).getAttributes();
                selectionStart = textPane.getSelectionStart();
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new TextPane().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("TextPane Font");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    public void loadRtf(String filename){
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

    public void saveRtf(String filename){
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

    // AttributeSet
    public MutableAttributeSet setStyles(MutableAttributeSet attr) {
        String font = StyleConstants.getFontFamily(selectedAttribute);
        StyleConstants.setFontFamily(attr, font);
        int size = StyleConstants.getFontSize(selectedAttribute);
        StyleConstants.setFontSize(attr, size);
        Color c = StyleConstants.getForeground(selectedAttribute);
        StyleConstants.setForeground(attr, c);
        Boolean bold = StyleConstants.isBold(selectedAttribute);
        StyleConstants.setBold(attr, bold);
        Boolean underline = StyleConstants.isUnderline(selectedAttribute);
        StyleConstants.setItalic(attr, underline);
        Boolean italic = StyleConstants.isItalic(selectedAttribute);
        StyleConstants.setItalic(attr, italic);
        return attr;
    }

    private void changeStyleFont(String font) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        attr = setStyles(attr);
        StyleConstants.setFontFamily(attr, font);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setCharacterAttributes(selectionStart, selectionEnd - selectionStart, attr, true);
        textPane.select(selectionStart, selectionEnd);
    }

    private void changeFont(){
        // check für gemachte selektion
        if (selectionEnd-selectionStart > 0) {
            String font = String.valueOf(comboBoxFont.getSelectedItem());
            changeStyleFont(font);
        }
        textPane.requestFocus();
    }
}
