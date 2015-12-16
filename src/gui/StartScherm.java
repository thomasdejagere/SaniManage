/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.BarcodeController;
import domain.Product;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import validation.MyNumericNotEmptyVerifier;
import validation.MyNumericVerifier;
import validation.MyNumericVerifierAboveNull;
import validation.OnzeBarcodeVerifier;

/**
 *
 * @author Thomas
 */
public class StartScherm extends JFrame {

    private JPanel container;
    private JList onzeBarcodes;
    private JList omschrijving;
    private JList<Integer> aantal;
    private JList leveranciers;
    private BarcodeController controller;
    private JPanel bestelLeverancierSelecteren;
    private JPanel voegLeverancierMetBarcodeToe;
    private JPanel voegLeverancierToe;
    private JPanel verwijderLeverancier;
    private JPanel pasProductAan;
    private JPanel pasAantalAan;
    private JPanel voegBestaandProductToe;
    private JPanel voegNieuwProductToe;
    private JPanel verwijderBarcode;
    private JButton bestel;
    private JLabel status;
    private JLabel errorLabel;
    private JLabel succesLabel;
    //leverancierVerwijderen
    private JComboBox alleLeveranciers;
    //standaard leverancier kiezen
    private JComboBox leveranciersBox;
    //voeg nieuwe barcode toe
    private JComboBox leveranciersCBox;
    //pas product aan
    private JComboBox pasProductAanLeveranciers;
    //verwijderbarcode
    private JComboBox verwijderBarcodeLeveranciers;
    //Standaard leverancier kiezen
    private boolean standaardLeverancierKiezen;

    private int geselecteerdeItem;

    public StartScherm(BarcodeController controller) throws SQLException, IllegalStateException, ClassNotFoundException {
        super("Barcode systeem Sani Vervaeck");
        this.controller = controller;
        setLayout(new FlowLayout());

        //CONTAINER
        //container = new JPanel(new GridLayout(5, 2));
        container = new JPanel();
        BoxLayout boxLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(boxLayout);
        //BANNER
        JLabel banner = new JLabel();
        Font fontBanner = new Font("Arial", Font.BOLD, 35);
        banner.setFont(fontBanner);
        banner.setText("Sani Vervaeck Barcode Systeem");
        JPanel bannerPanel = new JPanel(new FlowLayout());
        bannerPanel.add(banner);
        container.add(bannerPanel);
//OPMAAK       
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        //KNOPPEN
        JPanel functionaliteitsknoppen = new JPanel(new FlowLayout());
        JButton toevoegenLeverancier = new JButton("Voeg leverancier toe");
//        JButton verwijderenLeverancier = new JButton("Verwijder leverancier");
        JButton voegNieuwProductButton = new JButton("Voeg een nieuw product toe");
        JButton voegBestaandProductToeButton = new JButton("Voeg een bestaand product toe");
        functionaliteitsknoppen.add(toevoegenLeverancier);
//        functionaliteitsknoppen.add(verwijderenLeverancier);
        functionaliteitsknoppen.add(voegNieuwProductButton);
        functionaliteitsknoppen.add(voegBestaandProductToeButton);
        container.add(functionaliteitsknoppen);

//OPMAAK
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        //Labels voor list panel
        JPanel containerLists = new JPanel();
        containerLists.setLayout(new BoxLayout(containerLists, BoxLayout.LINE_AXIS));
        JPanel labelLists = new JPanel(new FlowLayout());
        JLabel onzeBarcodeLabel = new JLabel("Onze barcodes");
        JLabel omschrijvingLabel = new JLabel("Omschrijving");
        JLabel aantalLabel = new JLabel("Aantal");
        JLabel leverancierListLabel = new JLabel("Leverancier");

        labelLists.add(onzeBarcodeLabel);
        labelLists.add(omschrijvingLabel);
        labelLists.add(aantalLabel);
        labelLists.add(leverancierListLabel);

        //LIST PANEL
        JPanel lists = new JPanel(new FlowLayout());

        //Standaardleverancier kiezen panel
        bestelLeverancierSelecteren = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel standaardLeverancierSelecterenComponents = new JPanel();
        standaardLeverancierSelecterenComponents.setLayout(new GridBagLayout());
        GridBagConstraints conE = new GridBagConstraints();
        conE.insets = new Insets(10, 0, 10, 10);
        JLabel label1 = new JLabel("Kies de leverancier");
        leveranciersBox = new JComboBox();
        JButton setStandaardLeverancier = new JButton("Kies");
        conE.fill = GridBagConstraints.HORIZONTAL;
        conE.gridx = 0;
        conE.gridy = 0;
        standaardLeverancierSelecterenComponents.add(label1, conE);
        conE.fill = GridBagConstraints.HORIZONTAL;
        conE.gridx = 1;
        conE.gridy = 0;
        standaardLeverancierSelecterenComponents.add(leveranciersBox, conE);
        conE.fill = GridBagConstraints.HORIZONTAL;
        conE.gridx = 1;
        conE.gridy = 1;
        standaardLeverancierSelecterenComponents.add(setStandaardLeverancier, conE);
        bestelLeverancierSelecteren.add(standaardLeverancierSelecterenComponents);

        //verwijder barcode
        verwijderBarcode = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel verwijderBarcodeComponents = new JPanel();
        verwijderBarcodeComponents.setLayout(new GridBagLayout());
        GridBagConstraints conZ = new GridBagConstraints();
        conZ.insets = new Insets(10, 0, 10, 10);
        JLabel verwijderLabel = new JLabel("Verwijder barcode");
        verwijderBarcodeLeveranciers = new JComboBox();
        JButton verwijderBarcodeButton = new JButton("Verwijder");
        conZ.fill = GridBagConstraints.HORIZONTAL;
        conZ.gridx = 0;
        conZ.gridy = 0;
        verwijderBarcodeComponents.add(verwijderLabel, conZ);
        conZ.gridx = 1;
        verwijderBarcodeComponents.add(verwijderBarcodeLeveranciers, conZ);
        conZ.gridy = 1;
        verwijderBarcodeComponents.add(verwijderBarcodeButton, conZ);
        verwijderBarcode.add(verwijderBarcodeComponents);

        //voeg leverancier toe met barcode
        voegLeverancierMetBarcodeToe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel voegLeverancierMetBarcodeToeComponents = new JPanel();
        voegLeverancierMetBarcodeToeComponents.setLayout(new GridBagLayout());
        GridBagConstraints conD = new GridBagConstraints();
        conD.insets = new Insets(10, 0, 10, 10);
        JLabel barcodeLabel = new JLabel("Geef een barcode in:");
        JTextField nieuweBarcode = new JTextField(20);
        nieuweBarcode.setInputVerifier(new MyNumericNotEmptyVerifier());
        JLabel leverancierLabel = new JLabel("Kies een leverancier:");
        leveranciersCBox = new JComboBox();
        JButton voegBarcodeToe = new JButton("Voeg toe");
        conD.fill = GridBagConstraints.HORIZONTAL;
        conD.gridx = 0;
        conD.gridy = 0;
        voegLeverancierMetBarcodeToeComponents.add(barcodeLabel, conD);
        conD.fill = GridBagConstraints.HORIZONTAL;
        conD.gridx = 1;
        conD.gridy = 0;
        voegLeverancierMetBarcodeToeComponents.add(nieuweBarcode, conD);
        conD.fill = GridBagConstraints.HORIZONTAL;
        conD.gridx = 0;
        conD.gridy = 1;
        voegLeverancierMetBarcodeToeComponents.add(leverancierLabel, conD);
        conD.fill = GridBagConstraints.HORIZONTAL;
        conD.gridx = 1;
        conD.gridy = 1;
        voegLeverancierMetBarcodeToeComponents.add(leveranciersCBox, conD);
        conD.fill = GridBagConstraints.HORIZONTAL;
        conD.gridx = 1;
        conD.gridy = 2;
        voegLeverancierMetBarcodeToeComponents.add(voegBarcodeToe, conD);
        voegLeverancierMetBarcodeToe.add(voegLeverancierMetBarcodeToeComponents);

        //voegLeverancier toe
        voegLeverancierToe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel voegLeverancierToeComponents = new JPanel();
        voegLeverancierToeComponents.setLayout(new GridBagLayout());
        GridBagConstraints conC = new GridBagConstraints();
        conC.insets = new Insets(10, 0, 10, 10);
        JLabel voegLeverancierToeLabel = new JLabel("Naam leverancier:");
        JTextField voegLeverancierToeTextField = new JTextField(20);
        JButton voegLeverancierToeButton = new JButton("Voeg toe");
        conC.fill = GridBagConstraints.HORIZONTAL;
        conC.gridx = 0;
        conC.gridy = 0;
        voegLeverancierToeComponents.add(voegLeverancierToeLabel, conC);
        conC.fill = GridBagConstraints.HORIZONTAL;
        conC.gridx = 1;
        conC.gridy = 0;
        voegLeverancierToeComponents.add(voegLeverancierToeTextField, conC);
        conC.fill = GridBagConstraints.HORIZONTAL;
        conC.gridx = 1;
        conC.gridy = 1;
        voegLeverancierToeComponents.add(voegLeverancierToeButton, conC);
        voegLeverancierToe.add(voegLeverancierToeComponents);
        //verwijderLeverancier
        verwijderLeverancier = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel verwijderLeverancierComponents = new JPanel();
        verwijderLeverancierComponents.setLayout(new GridBagLayout());
        GridBagConstraints conA = new GridBagConstraints();
        conA.insets = new Insets(10, 0, 10, 10);
        JLabel verwijderLeverancierLabel = new JLabel("Leverancier:");
        alleLeveranciers = new JComboBox();
        JButton verwijderLeverancierButton = new JButton("Verwijder");
        conA.fill = GridBagConstraints.HORIZONTAL;
        conA.gridx = 0;
        conA.gridy = 0;
        verwijderLeverancierComponents.add(verwijderLeverancierLabel, conA);
        conA.fill = GridBagConstraints.HORIZONTAL;
        conA.gridx = 1;
        conA.gridy = 0;
        verwijderLeverancierComponents.add(alleLeveranciers, conA);
        conA.fill = GridBagConstraints.NONE;
        conA.gridx = 1;
        conA.gridy = 1;
        verwijderLeverancierComponents.add(verwijderLeverancierButton, conA);
        verwijderLeverancier.add(verwijderLeverancierComponents);
        //Pas product aan
        //aanpassen van onze barcode, omschrijving en standaardleverancier
        pasProductAan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pasProductAanComponents = new JPanel();
        pasProductAanComponents.setLayout(new GridBagLayout());
        GridBagConstraints conB = new GridBagConstraints();
        conB.insets = new Insets(10, 0, 10, 10);
        JLabel pasOnzeBarcodeAanLabel = new JLabel("Onze barcode: ");
        JTextField pasOnzeBarcodeAan = new JTextField(10);
        pasOnzeBarcodeAan.setInputVerifier(new MyNumericVerifier());
        JLabel pasOmschrijvingAanLabel = new JLabel("Omschrijving: ");
        JTextField pasOmschrijvingAan = new JTextField(20);
        JLabel pasStandaardLeverancierAanLabel = new JLabel("Standaard leverancier:");
        pasProductAanLeveranciers = new JComboBox();
        JButton pasProductAanButton = new JButton("Pas aan");
        JButton verwijderProductUitLijst = new JButton("Verwijder product uit lijst");
        JButton voegLeverancierToeAanProduct = new JButton("Voeg barcode van een leverancier toe");
        JButton verwijderLeverancierVanProduct = new JButton("Verwijder barcode van leverancier");
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 0;
        pasProductAanComponents.add(pasOnzeBarcodeAanLabel, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 1;
        conB.gridy = 0;
        pasProductAanComponents.add(pasOnzeBarcodeAan, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 1;
        pasProductAanComponents.add(pasOmschrijvingAanLabel, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 1;
        conB.gridy = 1;
        pasProductAanComponents.add(pasOmschrijvingAan, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 2;
        pasProductAanComponents.add(pasStandaardLeverancierAanLabel, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 1;
        conB.gridy = 2;
        pasProductAanComponents.add(pasProductAanLeveranciers, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 1;
        conB.gridy = 3;
        pasProductAanComponents.add(pasProductAanButton, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 3;
        pasProductAanComponents.add(verwijderProductUitLijst, conB);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 4;
        pasProductAanComponents.add(voegLeverancierToeAanProduct);
        conB.fill = GridBagConstraints.HORIZONTAL;
        conB.gridx = 0;
        conB.gridy = 5;
        pasProductAanComponents.add(verwijderLeverancierVanProduct);
        pasProductAan.add(pasProductAanComponents);

        //pasAantalAan
        pasAantalAan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pasAantalAanComponents = new JPanel();
        pasAantalAanComponents.setLayout(new GridBagLayout());
        GridBagConstraints conF = new GridBagConstraints();
        conF.insets = new Insets(10, 0, 10, 10);
        JLabel pasAantalAanLabel = new JLabel("Aantal:");
        JTextField pasAantalAanTextField = new JTextField(20);
        pasAantalAanTextField.setInputVerifier(new MyNumericVerifier());
        JButton pasAantalAanButton = new JButton("Pas aantal aan");
        conF.fill = GridBagConstraints.HORIZONTAL;
        conF.gridx = 0;
        conF.gridy = 0;
        pasAantalAanComponents.add(pasAantalAanLabel, conF);
        conF.fill = GridBagConstraints.HORIZONTAL;
        conF.gridx = 1;
        conF.gridy = 0;
        pasAantalAanComponents.add(pasAantalAanTextField, conF);
        conF.fill = GridBagConstraints.HORIZONTAL;
        conF.gridx = 1;
        conF.gridy = 1;
        pasAantalAanComponents.add(pasAantalAanButton, conF);
        pasAantalAan.add(pasAantalAanComponents);

        //VoegBestaandProductToe
        voegBestaandProductToe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel voegBestaandProductToeComponents = new JPanel();
        voegBestaandProductToeComponents.setLayout(new GridBagLayout());
        GridBagConstraints conG = new GridBagConstraints();
        conG.insets = new Insets(10, 0, 10, 10);
        JLabel onzeBarcodeBestaandLabel = new JLabel("Voeg een bestaande barcode toe:");
        JTextField onzeBarcodeBestaandTextField = new JTextField(20);
        onzeBarcodeBestaandTextField.setInputVerifier(new MyNumericVerifier());
        JLabel aantalBestaandLabel = new JLabel("Geef een aantal te bestellen in:");
        JTextField aantalBestaandTextField = new JTextField(10);
        aantalBestaandTextField.setInputVerifier(new MyNumericVerifier());
        JButton voegBestaandToeButton = new JButton("Voeg toe");
        conG.fill = GridBagConstraints.HORIZONTAL;
        conG.gridx = 0;
        conG.gridy = 0;
        voegBestaandProductToeComponents.add(onzeBarcodeBestaandLabel, conG);
        conG.fill = GridBagConstraints.HORIZONTAL;
        conG.gridx = 1;
        conG.gridy = 0;
        voegBestaandProductToeComponents.add(onzeBarcodeBestaandTextField, conG);
        conG.fill = GridBagConstraints.HORIZONTAL;
        conG.gridx = 0;
        conG.gridy = 1;
        voegBestaandProductToeComponents.add(aantalBestaandLabel, conG);
        conG.fill = GridBagConstraints.HORIZONTAL;
        conG.gridx = 1;
        conG.gridy = 1;
        voegBestaandProductToeComponents.add(aantalBestaandTextField, conG);
        conG.fill = GridBagConstraints.HORIZONTAL;
        conG.gridx = 1;
        conG.gridy = 2;
        voegBestaandProductToeComponents.add(voegBestaandToeButton, conG);
        voegBestaandProductToe.add(voegBestaandProductToeComponents);

        //VoegNieuwProductToe
        voegNieuwProductToe = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel voegNieuwProductToeComponents = new JPanel();
        voegNieuwProductToeComponents.setLayout(new GridBagLayout());
        GridBagConstraints conH = new GridBagConstraints();
        conH.insets = new Insets(10, 0, 10, 10);
        JLabel onzeBarcodeNieuwProductLabel = new JLabel("Onze barcode: ");
        JTextField onzeBarcodeNieuwTextField = new JTextField(20);
        onzeBarcodeNieuwTextField.setInputVerifier(new MyNumericVerifier());
        JLabel omschrijvingNieuwLabel = new JLabel("Omschrijving: ");
        JTextField omschrijvingNieuwTextField = new JTextField(20);
        JLabel aantalNieuwLabel = new JLabel("Aantal te bestellen: ");
        JTextField aantalNieuwTextField = new JTextField(10);
        aantalNieuwTextField.setInputVerifier(new MyNumericVerifier());
        aantalNieuwTextField.setText("0");
        JButton voegNieuwProductToeButton = new JButton("Voeg toe");
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 0;
        conH.gridy = 0;
        voegNieuwProductToeComponents.add(onzeBarcodeNieuwProductLabel, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 1;
        conH.gridy = 0;
        voegNieuwProductToeComponents.add(onzeBarcodeNieuwTextField, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 0;
        conH.gridy = 1;
        voegNieuwProductToeComponents.add(omschrijvingNieuwLabel, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 1;
        conH.gridy = 1;
        voegNieuwProductToeComponents.add(omschrijvingNieuwTextField, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 0;
        conH.gridy = 2;
        voegNieuwProductToeComponents.add(aantalNieuwLabel, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 1;
        conH.gridx = 1;
        voegNieuwProductToeComponents.add(aantalNieuwTextField, conH);
        conH.fill = GridBagConstraints.HORIZONTAL;
        conH.gridx = 1;
        conH.gridy = 3;
        voegNieuwProductToeComponents.add(voegNieuwProductToeButton, conH);
        voegNieuwProductToe.add(voegNieuwProductToeComponents);

        //LISTS
        //ONZE BARCODES
        onzeBarcodes = new JList(controller.geefIngescandeBarcodes());
        onzeBarcodes.setFixedCellHeight(30);
        lists.add(onzeBarcodes);

        omschrijving = new JList(controller.geefOmschrijvingenBarcodes());
        omschrijving.setFixedCellHeight(30);
        lists.add(omschrijving);

        //AANTALLLEN
        DefaultListModel<Integer> dataModel = new DefaultListModel<>();
        List<Integer> aantallen = controller.geefAantallenPerProduct();
        for (Integer i : aantallen) {
            dataModel.addElement(i);
        }
        aantal = new JList<>(dataModel);
        aantal.setFixedCellHeight(30);

        lists.add(aantal);

        //LEVERANCIERS
        leveranciers = new JList(controller.geefStandaardLeverancierPerProduct());
        leveranciers.setFixedCellHeight(30);
        lists.add(leveranciers);
        JScrollPane scrollPane = new JScrollPane(lists, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        container.add(scrollPane);
//OPMAAK
        bestel = new JButton();
        bestel.setText("Bestel");
        bestel.setSize(50, 50);
        boolean result = controleerOfAlleProductenEenBestelLeverancierHebben();
        if (result) {
            bestel.setVisible(true);
        } else {
            bestel.setVisible(false);
        }
        container.add(bestel);
//OPMAAK
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        status = new JLabel();
        status.setText("");
        Font statusFont = new Font("Arial", Font.BOLD, 18);
        status.setFont(statusFont);
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(status);
        errorLabel = new JLabel();
        errorLabel.setText("");
        Font errorFont = new Font("Arial", Font.ITALIC, 12);
        errorLabel.setForeground(Color.red);
        errorLabel.setFont(errorFont);
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.add(errorLabel);
        succesLabel = new JLabel();
        succesLabel.setText("");
        Font succesFont = new Font("Arial", Font.BOLD, 12);
        succesLabel.setForeground(Color.green);
        succesLabel.setFont(succesFont);
        JPanel succesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        succesPanel.add(succesLabel);
        container.add(statusPanel);
        container.add(errorPanel);
        container.add(succesPanel);
        add(container);
        this.pack();
        onzeBarcodes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (onzeBarcodes.getSelectedValue() != null) {
                    verwijderAlleComponenten();
                    alleComboBoxenLeegmaken();
                    int index = onzeBarcodes.getSelectedIndex();
                    geselecteerdeItem = index;
                    omschrijving.clearSelection();
                    leveranciers.clearSelection();
                    aantal.clearSelection();
                    status.setText("Pas gegevens van het product aan:");
                    pasOnzeBarcodeAan.setText(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                    pasOmschrijvingAan.setText(controller.geefOmschrijving(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString()));
                    String[] overeenkomstigeLeveranciers = controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());

                    if (overeenkomstigeLeveranciers.length > 0) {
                        String standaardlev = controller.geefStandaardLeverancier(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (overeenkomstigeLeverancier.startsWith(standaardlev)) {
                                pasProductAanLeveranciers.addItem(overeenkomstigeLeverancier);
                            }
                        }
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (!(overeenkomstigeLeverancier.startsWith(standaardlev))) {
                                pasProductAanLeveranciers.addItem(overeenkomstigeLeverancier);
                            }
                        }

                    } else {
                        pasProductAanLeveranciers.addItem("Product heeft nog geen leveranciers");
                    }
                    if (controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString()).length > 0) {
                        verwijderLeverancierVanProduct.setVisible(true);
                    } else {
                        verwijderLeverancierVanProduct.setVisible(false);
                    }
                    pasProductAan.setVisible(true);
                    container.add(pasProductAan);
                    add(container);
                    repaint();
                    revalidate();
                }
            }
        });
        omschrijving.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (omschrijving.getSelectedValue() != null) {
                    verwijderAlleComponenten();
                    alleComboBoxenLeegmaken();
                    int index = omschrijving.getSelectedIndex();
                    geselecteerdeItem = index;
                    onzeBarcodes.clearSelection();
                    leveranciers.clearSelection();
                    aantal.clearSelection();
                    status.setText("Pas gegevens van het product aan:");
                    pasOnzeBarcodeAan.setText(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                    pasOmschrijvingAan.setText(controller.geefOmschrijving(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString()));
                    String[] overeenkomstigeLeveranciers = controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());

                    if (overeenkomstigeLeveranciers.length > 0) {
                        String standaardlev = controller.geefStandaardLeverancier(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (overeenkomstigeLeverancier.startsWith(standaardlev)) {
                                pasProductAanLeveranciers.addItem(overeenkomstigeLeverancier);
                            }
                        }
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (!(overeenkomstigeLeverancier.startsWith(standaardlev))) {
                                pasProductAanLeveranciers.addItem(overeenkomstigeLeverancier);
                            }
                        }

                    } else {
                        pasProductAanLeveranciers.addItem("Product heeft nog geen leveranciers");
                    }
                    if (controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString()).length > 0) {
                        verwijderLeverancierVanProduct.setVisible(true);
                    } else {
                        verwijderLeverancierVanProduct.setVisible(false);
                    }
                    pasProductAan.setVisible(true);
                    container.add(pasProductAan);
                    add(container);
                    repaint();
                    revalidate();
                }
            }
        });
        aantal.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (aantal.getSelectedValue() != null) {
                    pasAantalAan.setVisible(true);
                    verwijderAlleComponenten();
                    alleComboBoxenLeegmaken();
                    int index = aantal.getSelectedIndex();
                    geselecteerdeItem = index;
                    onzeBarcodes.clearSelection();
                    omschrijving.clearSelection();
                    leveranciers.clearSelection();
                    status.setText("Pas het aantal te bestellen artikelen aan:");
                    container.add(pasAantalAan);
                    add(container);
                    repaint();
                    revalidate();
                }
            }
        });
        leveranciers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (leveranciers.getSelectedValue() != null) {
                    bestelLeverancierSelecteren.setVisible(true);
                    voegLeverancierMetBarcodeToe.setVisible(true);
                    verwijderAlleComponenten();
                    alleComboBoxenLeegmaken();
                    int index = leveranciers.getSelectedIndex();
                    geselecteerdeItem = index;
                    onzeBarcodes.clearSelection();
                    omschrijving.clearSelection();
                    aantal.clearSelection();
                    if (leveranciers.getSelectedValue().toString().equals("Heeft geen leveranciers")) {
                        //controlleren of product barcodes heeft
                        if (controller.heeftBarcodes(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString())) {
                            kiesLeverancier(true, geselecteerdeItem);
                        } else {
                            voegBarcodeToe();
                        }
                    } else {
                        //Controleren of product andere leveranciers heeft buiten standaardleverancier
                        if (controller.heeftMeerdereBarcodes(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString())) {
                            kiesAndereLeverancier(geselecteerdeItem);
                        } else //optie aanbieden!
                        {
                            voegBarcodeToe();
                        }
                    }
                }
            }

        });

        setStandaardLeverancier.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = leveranciersBox.getSelectedItem().toString().indexOf(":");
                try {
                    standaardLeverancierKiezen = false;
                    controller.kiesGewensteLeverancier(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString(), leveranciersBox.getSelectedItem().toString().substring(0, index - 1), standaardLeverancierKiezen);
                } catch (SQLException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                }
                verwijderSelectieVanLijsten();
                bestelLeverancierSelecteren.setVisible(false);
                status.setText("");
                boolean result = controleerOfAlleProductenEenBestelLeverancierHebben();
                if (result) {
                    bestel.setVisible(true);
                } else {
                    bestel.setVisible(false);
                }
                repaint();
                revalidate();
            }

        });
        voegBarcodeToe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyNumericNotEmptyVerifier verifier = new MyNumericNotEmptyVerifier();
                if (verifier.shouldYieldFocus(nieuweBarcode)) {
                    try {
                        standaardLeverancierKiezen = false;
                        controller.voegBarcodeToe(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString(), nieuweBarcode.getText(), leveranciersCBox.getSelectedItem().toString(), standaardLeverancierKiezen);
                    } catch (SQLException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalStateException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean result = controleerOfAlleProductenEenBestelLeverancierHebben();
                    if (result) {
                        bestel.setVisible(true);
                    } else {
                        bestel.setVisible(false);
                    }
                    voegLeverancierMetBarcodeToe.setVisible(false);
                    status.setText("");
                    errorLabel.setText("");
                    nieuweBarcode.setText("");
                } else {
                    errorLabel.setText("* Je moet een getal invullen.");
                }
                boolean result = controleerOfAlleProductenEenBestelLeverancierHebben();
                if (result) {
                    bestel.setVisible(true);
                } else {
                    bestel.setVisible(false);
                }
                repaint();
                revalidate();
            }
        });
        verwijderBarcodeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verwijderBarcode(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString(), verwijderBarcodeLeveranciers.getSelectedItem().toString());
                } catch (SQLException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                }
                boolean result = controleerOfAlleProductenEenBestelLeverancierHebben();
                if (result) {
                    bestel.setVisible(true);
                } else {
                    bestel.setVisible(false);
                }
                verwijderBarcode.setVisible(false);
                status.setText("");
                errorLabel.setText("");
                repaint();
                revalidate();
            }

        });
        //AANTAL AANPASSEN
        pasAantalAanButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyNumericVerifier verifier = new MyNumericVerifier();
                if (verifier.shouldYieldFocus(pasAantalAanTextField)) {
                    controller.pasAantalAan(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString(), pasAantalAanTextField.getText());
                    DefaultListModel<Integer> dataModel = new DefaultListModel<>();
                    List<Integer> aantallen = controller.geefAantallenPerProduct();
                    for (Integer i : aantallen) {
                        dataModel.addElement(i);
                    }
                    aantal.setModel(dataModel);
                    pasAantalAanTextField.setText("");
                    status.setText("");
                    pasAantalAan.setVisible(false);
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("<html>* Je moet een getal invullen.<br>* Je mag het veld niet leeglaten.</html>");
                }
                repaint();
                revalidate();
            }

        });
        //product aanpassen
        pasProductAanButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                OnzeBarcodeVerifier verifier = new OnzeBarcodeVerifier();
                if (verifier.shouldYieldFocus(pasOnzeBarcodeAan)) {
                    try {
                        controller.pasProductAan(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString(), pasOnzeBarcodeAan.getText(), pasOmschrijvingAan.getText(), pasProductAanLeveranciers.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalStateException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    verwijderSelectieVanLijsten();
                    pasOnzeBarcodeAan.setText("");
                    pasOmschrijvingAan.setText("");
                    status.setText("");
                    pasProductAan.setVisible(false);
                    errorLabel.setText("");
//                if(pasProductAanLeveranciers.getSelectedIndex() != 0){
//                    DefaultListModel model = (DefaultListModel) leveranciers.getModel();
//                    
//                }
                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("<html>Fout: mogelijke foutoorzaken:");
                    builder.append("<br>*De barcode moet numeriek zijn.");
                    builder.append("<br>*De barcode moet beginnen met 540 en eindigen met 00.");
                    builder.append("<br>*Het 4de cijfer van de barcode moet een 0 of een 1 zijn.</html>");
                    errorLabel.setText(builder.toString());
                }
                repaint();
                revalidate();
            }

        });
        verwijderProductUitLijst.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.verwijderProductUitLijst(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                DefaultListModel<Integer> model = (DefaultListModel) aantal.getModel();
                model.removeElementAt(geselecteerdeItem);
                aantal.setModel(model);
                pasOnzeBarcodeAan.setText("");
                pasOmschrijvingAan.setText("");
                status.setText("");
                pasProductAan.setVisible(false);
            }

        });

        voegLeverancierToeAanProduct.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("");
                pasOnzeBarcodeAan.setText("");
                pasOmschrijvingAan.setText("");
                pasProductAan.setVisible(false);
                voegBarcodeToe();

            }

        });
        verwijderLeverancierVanProduct.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("");
                pasOnzeBarcodeAan.setText("");
                pasOmschrijvingAan.setText("");
                pasProductAan.setVisible(false);
                verwijderBarcode();
            }

        });
        //voeg leverancier toe button
        voegLeverancierToeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    controller.voegLeverancierToe(voegLeverancierToeTextField.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                }
                voegLeverancierToeTextField.setText("");
                voegLeverancierToe.setVisible(false);
//                leveranciers = new JList(controller.geefStandaardLeverancierPerProduct());
//                leveranciers.setFixedCellHeight(30);
//                lists.add(leveranciers);
//                container.add(lists);
                succesLabel.setText("De leverancier is succesvol opgeslaan in de databank.");
                status.setText("");
                repaint();
                revalidate();
            }

        });

//        verwijderLeverancierButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                try {
//                    controller.verwijderLeverancier(alleLeveranciers.getSelectedItem().toString());
//                } catch (SQLException ex) {
//                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                verwijderLeverancier.setVisible(false);
//                status.setText("");
//
//            }
//
//        });
        toevoegenLeverancier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verwijderSelectieVanLijsten();
                verwijderAlleComponenten();
                alleComboBoxenLeegmaken();
                container.add(voegLeverancierToe);
                status.setText("Voeg een leverancier toe:");
                add(container);
                repaint();
                revalidate();
            }

        });
//        verwijderenLeverancier.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                verwijderSelectieVanLijsten();
//                verwijderAlleComponenten();
//                alleComboBoxenLeegmaken();
//                String[] alleLev = controller.geefLeveranciers();
//                for (String lev : alleLev) {
//                    alleLeveranciers.addItem(lev);
//                }
//                verwijderLeverancier.setVisible(true);
//                container.add(verwijderLeverancier);
//                status.setText("Verwijder een leverancier:");
//                add(container);
//                repaint();
//                revalidate();
//            }
//
//        });
        voegNieuwProductButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                verwijderSelectieVanLijsten();
                verwijderAlleComponenten();
                alleComboBoxenLeegmaken();
                voegNieuwProductToe.setVisible(true);
                container.add(voegNieuwProductToe);
                status.setText("Voeg een nieuw product toe:");
                add(container);
                repaint();
                revalidate();
            }

        });
        voegBestaandProductToeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                verwijderSelectieVanLijsten();
                verwijderAlleComponenten();
                alleComboBoxenLeegmaken();
                voegBestaandProductToe.setVisible(true);
                container.add(voegBestaandProductToe);
                status.setText("Voeg een bestaand product toe:");
                add(container);
                repaint();
                revalidate();
            }

        });
        voegNieuwProductToeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyNumericVerifier verifier = new MyNumericVerifier();
                OnzeBarcodeVerifier verifier2 = new OnzeBarcodeVerifier();
                if (verifier2.shouldYieldFocus(onzeBarcodeNieuwTextField) && verifier.shouldYieldFocus(aantalNieuwTextField)) {
                    try {
                        if (controller.voegNieuwProductToe(onzeBarcodeNieuwTextField.getText(), omschrijvingNieuwTextField.getText(), aantalNieuwTextField.getText())) {
                            succesLabel.setText("Product is succesvol opgeslaan in de databank.");
                            voegNieuwProductToe.setVisible(false);
                            status.setText("");
                            DefaultListModel<Integer> dataModel = new DefaultListModel<>();
                            List<Integer> aantallen = controller.geefAantallenPerProduct();
                            for (Integer i : aantallen) {
                                dataModel.addElement(i);
                            }
                            aantal.setModel(dataModel);
                            errorLabel.setText("");
                            succesLabel.setText("Product is succesvol opgeslaan in de databank.");
                        } else {
                            errorLabel.setText("De barcode bestaat al.");
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("<html>Fout: mogelijke foutoorzaken:");
                    builder.append("<br>*De barcode moet numeriek zijn.");
                    builder.append("<br>*De barcode moet beginnen met 540 en eindigen met 00.");
                    builder.append("<br>*Het 4de cijfer van de barcode moet een 0 of een 1 zijn.");
                    builder.append("<br>*Het aantal moet numeriek zijn.</html>");
                    errorLabel.setText(builder.toString());
                    
                }
                repaint();
                revalidate();
            }

        });

        voegBestaandToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyNumericVerifierAboveNull verifier = new MyNumericVerifierAboveNull();
                OnzeBarcodeVerifier verifier2 = new OnzeBarcodeVerifier();
                if (verifier2.shouldYieldFocus(onzeBarcodeBestaandTextField) && verifier.shouldYieldFocus(aantalBestaandTextField)) {
                    if(controller.voegBestaandProductToe(onzeBarcodeBestaandTextField.getText(), aantalBestaandTextField.getText())){
                        DefaultListModel<Integer> dataModel = new DefaultListModel<>();
                    List<Integer> aantallen = controller.geefAantallenPerProduct();
                    for (Integer i : aantallen) {
                        dataModel.addElement(i);
                    }
                    aantal.setModel(dataModel);
                    voegBestaandProductToe.setVisible(false);
                    onzeBarcodeBestaandTextField.setText("");
                    aantalBestaandTextField.setText("");
                    status.setText("");
                    errorLabel.setText("");
                    }else{
                        errorLabel.setText("Fout: Het product bestaat niet.");
                    }
                    
                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("<html>Fout: mogelijke foutoorzaken:");
                    builder.append("<br>*De barcode moet numeriek zijn.");
                    builder.append("<br>*De barcode moet beginnen met 540 en eindigen met 00.");
                    builder.append("<br>*Het 4de cijfer van de barcode moet een 0 of een 1 zijn.");
                    builder.append("<br>*Het aantal moet numeriek zijn.");
                    builder.append("<br>*Het aantal moet boven nul zijn.</html>");
                    errorLabel.setText(builder.toString());
                }
            }
        });

        bestel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.voegAantallenAan();
                } catch (SQLException ex) {
                    Logger.getLogger(StartScherm.class.getName()).log(Level.SEVERE, null, ex);
                }
                Map<String, List<Product>> productenPerBestelLeverancier = controller.geefProductenPerBestelLeverancier();
                Set<String> leveranciers = productenPerBestelLeverancier.keySet();
                for (String l : leveranciers) {
                    BestelFrame b = new BestelFrame(l, productenPerBestelLeverancier.get(l));
                    b.setSize(600, 600);
                    b.setTitle("Bestelformulier voor " + l);
                    b.setVisible(true);
                }
            }
        });

    }

    private boolean controleerOfAlleProductenEenBestelLeverancierHebben() {
        for (int i = 0; i < leveranciers.getModel().getSize(); i++) {
            String lev = leveranciers.getModel().getElementAt(i).toString();
            if (lev.equals("Heeft geen leveranciers")) {
                return false;
            }
        }
        return true;
    }

    private void kiesAndereLeverancier(int index) {
        kiesLeverancier(false, index);
    }

    private void verwijderAlleComponenten() {
        container.remove(voegLeverancierMetBarcodeToe);
        container.remove(bestelLeverancierSelecteren);
        container.remove(pasAantalAan);
        container.remove(pasProductAan);
        container.remove(voegLeverancierToe);
        container.remove(verwijderLeverancier);
        container.remove(voegNieuwProductToe);
        container.remove(voegBestaandProductToe);
        container.remove(verwijderBarcode);
        errorLabel.setText("");
        succesLabel.setText("");
    }

    private void alleComboBoxenLeegmaken() {
        leveranciersBox.removeAllItems();
        leveranciersCBox.removeAllItems();
        pasProductAanLeveranciers.removeAllItems();
        alleLeveranciers.removeAllItems();
        verwijderBarcodeLeveranciers.removeAllItems();

    }

    private void verwijderBarcode() {
        verwijderBarcode.setVisible(true);
        status.setText("Verwijder een barcode van een leverancier");
        String[] overeenkomstigeLeveranciers = controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
            verwijderBarcodeLeveranciers.addItem(overeenkomstigeLeverancier);
        }
        container.add(verwijderBarcode, BorderLayout.SOUTH);
        add(container);
        repaint();
        revalidate();
    }

    private void voegBarcodeToe() {
        voegLeverancierMetBarcodeToe.setVisible(true);
        status.setText("Voeg een barcode van een leverancier toe voor artikel \" " + omschrijving.getModel().getElementAt(geselecteerdeItem).toString() + "\"");
        String[] alleLeveranciers = controller.geefLeveranciers();
        for (String alleLeverancier : alleLeveranciers) {
            leveranciersCBox.addItem(alleLeverancier);
        }
        container.add(voegLeverancierMetBarcodeToe, BorderLayout.SOUTH);
        add(container);
        repaint();
        revalidate();
    }

    private void kiesLeverancier(boolean standaardLeverancierKiezen, int index) {
        status.setText("Kies de bestelleverancier voor het artikel \"" + omschrijving.getModel().getElementAt(geselecteerdeItem).toString() + "\"");
        String[] overeenkomstigeLeveranciers = controller.geefLeveranciers(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
        String bestellev = controller.geefBestelLeverancier(onzeBarcodes.getModel().getElementAt(geselecteerdeItem).toString());
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (overeenkomstigeLeverancier.startsWith(bestellev)) {
                                leveranciersBox.addItem(overeenkomstigeLeverancier);
                            }
                        }
                        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
                            if (!(overeenkomstigeLeverancier.startsWith(bestellev))) {
                                leveranciersBox.addItem(overeenkomstigeLeverancier);
                            }
                        }
        
        
//        
//        
//        for (String overeenkomstigeLeverancier : overeenkomstigeLeveranciers) {
//            .addItem(overeenkomstigeLeverancier);
//        }
        container.add(bestelLeverancierSelecteren, BorderLayout.SOUTH);
        add(container);
        repaint();
        revalidate();
    }

    private void verwijderSelectieVanLijsten() {
        aantal.clearSelection();
        leveranciers.clearSelection();
        onzeBarcodes.clearSelection();
        omschrijving.clearSelection();
    }

}
