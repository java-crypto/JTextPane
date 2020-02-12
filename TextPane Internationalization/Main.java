/*
 * Herkunft/Origin: http://java-crypto.bplaced.net/
 * Programmierer/Programmer: Michael Fehr
 * Copyright/Copyright: frei verwendbares Programm (Public Domain)
 * Copyright: This is free and unencumbered software released into the public domain.
 * Lizenttext/Licence: <http://unlicense.org>
 * getestet mit/tested with: Java Runtime Environment 8 Update 191 x64
 * getestet mit/tested with: Java Runtime Environment 11.0.4 x64
 * Datum/Date (dd.mm.jjjj): 11.02.2020
 * Funktion: TextPane Internationalisierung
 * Function: TextPane Internationalisation
 *
 * Sicherheitshinweis/Security notice
 * Die Programmroutinen dienen nur der Darstellung und haben keinen Anspruch auf eine korrekte Funktion,
 * insbesondere mit Blick auf die Sicherheit !
 * Prüfen Sie die Sicherheit bevor das Programm in der echten Welt eingesetzt wird.
 * The program routines just show the function but please be aware of the security part -
 * check yourself before using in the real world !
 */

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static ResourceBundle resourceBundle;

    public static void main(String[] args) {
        // als vorgabe: spanisch // as default spanic
        Locale.setDefault(new Locale("es"));

        JMenuBar menuBar;
        JMenu menuFile, menuLanguage;
        JMenuItem menuItemExit, menuItemLoad, menuItemSave, menuItemEn, menuItemDe;
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        menuItemSave = new JMenuItem("Save");
        menuFile.add(menuItemSave);
        menuItemLoad = new JMenuItem("Load");
        menuFile.add(menuItemLoad);
        menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemExit);
        menuLanguage = new JMenu("Language");
        menuBar.add(menuLanguage);
        menuItemEn = new JMenuItem("English");
        menuLanguage.add(menuItemEn);
        menuItemDe = new JMenuItem("German");
        menuLanguage.add(menuItemDe);

        TextPane form = new TextPane();
        JFrame frame = new JFrame("TextPane Internationalization and Localisation");
        frame.setContentPane(form.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setSize(500, 350);
        frame.setVisible(true);

        menuItemEn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locale locale = Locale.US; // vorgabe
                //Locale.setDefault(new Locale("es"));
                Locale.setDefault(new Locale("en"));
                UIManager.getDefaults().setDefaultLocale(locale);
                resourceBundle = ResourceBundle.getBundle("Bundle", locale);
                frame.setTitle(resourceBundle.getString("application.title"));
                form.setButtonLoad(resourceBundle.getString("mainButtonLoad"));
                menuItemLoad.setText(resourceBundle.getString("mainButtonLoad"));
                form.setButtonSave(resourceBundle.getString("mainButtonSave"));
                menuItemSave.setText(resourceBundle.getString("mainButtonSave"));
                form.setButtonBackgroundColorChooser(resourceBundle.getString("mainButtonBackgroundColorChooser"));
                menuFile.setText(resourceBundle.getString("menuFile"));
                menuItemExit.setText(resourceBundle.getString("menuItemExit"));
                menuLanguage.setText(resourceBundle.getString("menuLanguage"));
                menuItemEn.setText(resourceBundle.getString("menuItemEnglish"));
                menuItemDe.setText(resourceBundle.getString("menuItemGerman"));
                String line = resourceBundle.getString("mainOutputData") + "\n";
                try {
                    line = line + createLocaleLine(locale);
                    form.textPaneNewLine(line);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuItemDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locale locale = Locale.GERMANY;
                resourceBundle = ResourceBundle.getBundle("Bundle", locale);
                frame.setTitle(resourceBundle.getString("application.title"));
                form.setButtonLoad(resourceBundle.getString("mainButtonLoad"));
                menuItemLoad.setText(resourceBundle.getString("mainButtonLoad"));
                form.setButtonSave(resourceBundle.getString("mainButtonSave"));
                menuItemSave.setText(resourceBundle.getString("mainButtonSave"));
                form.setButtonBackgroundColorChooser(resourceBundle.getString("mainButtonBackgroundColorChooser"));
                menuFile.setText(resourceBundle.getString("menuFile"));
                menuItemExit.setText(resourceBundle.getString("menuItemExit"));
                menuLanguage.setText(resourceBundle.getString("menuLanguage"));
                menuItemEn.setText(resourceBundle.getString("menuItemEnglish"));
                menuItemDe.setText(resourceBundle.getString("menuItemGerman"));
                String line = resourceBundle.getString("mainOutputData") + "\n";
                try {
                    line = line + createLocaleLine(locale);
                    form.textPaneNewLine(line);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.loadRtf("textpane_rtf.rtf");
            }
        });
    }

    public static String createLocaleLine(Locale locale) {
        String line = "";
        // date
        DateFormat dateFormat = DateFormat.getDateInstance(
                DateFormat.DEFAULT, locale);
        String date = dateFormat.format(new Date());
        line = line + date + "\n";
        // time
        DateFormat timeFormat = DateFormat.getTimeInstance(
                DateFormat.DEFAULT, locale);
        String time = timeFormat.format(new Date());
        line = line + time + "\n";
        // currency
        Double currency = 52560010d;
        Currency currentCurrency = Currency.getInstance(locale);
        NumberFormat currencyFormatter =
                NumberFormat.getCurrencyInstance(locale);
        line = line + currentCurrency.getDisplayName() + ": " +
                currencyFormatter.format(currency) + "\n";
        // numbers
        Double num = 525949.2;
        NumberFormat numberFormatter;
        String numOut;
        numberFormatter = NumberFormat.getNumberInstance(locale);
        numOut = numberFormatter.format(num);
        line = line + numOut + "  " + "\n";
        return line;
    }

}
