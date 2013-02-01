package org.eob.gui;

import javax.swing.*;
import java.awt.*;

/**
 * User: Bifrost
 * Date: 11/8/12
 * Time: 12:38 AM
 */
public class EobConverterForm {
    private JPanel mainPanel;
    private JTextField srcPathField;
    private JButton srcPathChooser;
    private JTextField dstPathField;
    private JButton dstPathChooser;
    private JCheckBox debugModeCheckBox;
    private JTextField itemNameField;
    private JTextField fromLevelField;
    private JTextField toLevelField;
    private JCheckBox generateDefaultStructuresCheckBox;
    private JCheckBox createLevelInSeparateCheckBox;
    private JTextArea output;
    private JButton convertButton;
    private JCheckBox debugWallCheckBox;
    private JCheckBox scriptDebugModeCheckBox;
    private JCheckBox writeUnpackedInfFilesCheckBox;
    private JCheckBox exportEobScriptsCheckBox;
    private JCheckBox addEobScriptIntoLuaCheckBox;
    private JCheckBox printItemsCheckBox;
    private JCheckBox printItemTypesCheckBox;
    private JTextField extChangesPathField;
    private JButton extChangesPathChooser;
    private JCheckBox printLevelInfoCheckBox;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getSrcPathField() {
        return srcPathField;
    }

    public JButton getSrcPathChooser() {
        return srcPathChooser;
    }

    public JTextField getDstPathField() {
        return dstPathField;
    }

    public JButton getDstPathChooser() {
        return dstPathChooser;
    }

    public JTextField getExtChangesPathField() {
        return extChangesPathField;
    }

    public JButton getExtChangesPathChooser() {
        return extChangesPathChooser;
    }

    public JCheckBox getDebugModeCheckBox() {
        return debugModeCheckBox;
    }

    public JCheckBox getDebugWallCheckBox() {
        return debugWallCheckBox;
    }

    public JCheckBox getScriptDebugModeCheckBox() {
        return scriptDebugModeCheckBox;
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getFromLevelField() {
        return fromLevelField;
    }

    public JTextField getToLevelField() {
        return toLevelField;
    }

    public JCheckBox getGenerateDefaultStructuresCheckBox() {
        return generateDefaultStructuresCheckBox;
    }

    public JCheckBox getCreateLevelInSeparateCheckBox() {
        return createLevelInSeparateCheckBox;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

    public JCheckBox getWriteUnpackedInfFilesCheckBox() {
        return writeUnpackedInfFilesCheckBox;
    }

    public JCheckBox getExportEobScriptsCheckBox() {
        return exportEobScriptsCheckBox;
    }

    public JCheckBox getAddEobScriptIntoLuaCheckBox() {
        return addEobScriptIntoLuaCheckBox;
    }

    public JCheckBox getPrintItemsCheckBox() {
        return printItemsCheckBox;
    }

    public JCheckBox getPrintItemTypesCheckBox() {
        return printItemTypesCheckBox;
    }

    public JCheckBox getPrintLevelInfoCheckBox() {
        return printLevelInfoCheckBox;
    }

    public JTextArea getOutput() {
        return output;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 0, 3);
        mainPanel.add(panel1, gbc);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Debug"));
        final JLabel label1 = new JLabel();
        label1.setText("Item name contain: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        itemNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel1.add(itemNameField, gbc);
        debugWallCheckBox = new JCheckBox();
        debugWallCheckBox.setSelected(true);
        debugWallCheckBox.setText("Print Wall errors");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(debugWallCheckBox, gbc);
        addEobScriptIntoLuaCheckBox = new JCheckBox();
        addEobScriptIntoLuaCheckBox.setText("Add EoB script into the lua as comment (functions are added into the lua script at position [0,0])");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(addEobScriptIntoLuaCheckBox, gbc);
        printItemsCheckBox = new JCheckBox();
        printItemsCheckBox.setText("Print items");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(printItemsCheckBox, gbc);
        debugModeCheckBox = new JCheckBox();
        debugModeCheckBox.setText("Debug mode");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(debugModeCheckBox, gbc);
        printItemTypesCheckBox = new JCheckBox();
        printItemTypesCheckBox.setText("Print item types");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(printItemTypesCheckBox, gbc);
        scriptDebugModeCheckBox = new JCheckBox();
        scriptDebugModeCheckBox.setText("Script debug mode");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(scriptDebugModeCheckBox, gbc);
        printLevelInfoCheckBox = new JCheckBox();
        printLevelInfoCheckBox.setText("Print level info");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(printLevelInfoCheckBox, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 0, 3);
        mainPanel.add(panel2, gbc);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Paths"));
        srcPathField = new JTextField();
        srcPathField.setEditable(true);
        srcPathField.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel2.add(srcPathField, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Source path:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Destination path:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(label3, gbc);
        dstPathField = new JTextField();
        dstPathField.setEditable(true);
        dstPathField.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel2.add(dstPathField, gbc);
        srcPathChooser = new JButton();
        srcPathChooser.setText("...");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(srcPathChooser, gbc);
        dstPathChooser = new JButton();
        dstPathChooser.setText("...");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(dstPathChooser, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("External changes path:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(label4, gbc);
        extChangesPathField = new JTextField();
        extChangesPathField.setEditable(true);
        extChangesPathField.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel2.add(extChangesPathField, gbc);
        extChangesPathChooser = new JButton();
        extChangesPathChooser.setText("...");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel2.add(extChangesPathChooser, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        mainPanel.add(panel3, gbc);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Output"));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, BorderLayout.CENTER);
        output = new JTextArea();
        output.setEditable(false);
        scrollPane1.setViewportView(output);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 0, 3);
        mainPanel.add(panel4, gbc);
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Generate settings"));
        createLevelInSeparateCheckBox = new JCheckBox();
        createLevelInSeparateCheckBox.setText("Create file per level");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(createLevelInSeparateCheckBox, gbc);
        generateDefaultStructuresCheckBox = new JCheckBox();
        generateDefaultStructuresCheckBox.setText("Generate default structures");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(generateDefaultStructuresCheckBox, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("From level:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel4.add(label5, gbc);
        fromLevelField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel4.add(fromLevelField, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("To level:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 0);
        panel4.add(label6, gbc);
        toLevelField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 3, 0, 0);
        panel4.add(toLevelField, gbc);
        writeUnpackedInfFilesCheckBox = new JCheckBox();
        writeUnpackedInfFilesCheckBox.setText("Write unpacked Inf files");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(writeUnpackedInfFilesCheckBox, gbc);
        exportEobScriptsCheckBox = new JCheckBox();
        exportEobScriptsCheckBox.setText("Export EoB scripts into the files");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(exportEobScriptsCheckBox, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 0, 5);
        mainPanel.add(panel5, gbc);
        convertButton = new JButton();
        convertButton.setText("Convert");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(convertButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
