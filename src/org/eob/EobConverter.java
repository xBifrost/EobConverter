package org.eob;

import org.eob.file.EobFiles;
import org.eob.file.inf.InfFile;
import org.eob.gui.EobConverterForm;
import org.eob.model.ItemType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bifrost
 * Date: 10/6/12
 * Time: 3:16 PM
 */
public class EobConverter {
    public final static String CONVERTER_VERSION = "0.6.0";
    private final static List<String> externalChangesList = new ArrayList<String>();
    private final static String ITEMS_FILE = "ITEM.DAT";

    private final static String LEVEL_MAZ_FILE = "LEVEL%d.MAZ";
    private final static String LEVEL_INF_FILE = "LEVEL%d.INF";

    private Settings settings = new Settings();
    private EobConverterForm eobConverterForm;

    private static void initExternalChanges() {
        externalChangesList.add("W eob_sewers_door_metal_1_2_3");
        externalChangesList.add("W eob_sewers_door_metal_1_17_23");
        externalChangesList.add("S eob_sewers_door_metal_1_16_11");
        externalChangesList.add("S eob_sewers_door_metal_1_19_18");

        externalChangesList.add("S eob_sewers_portcullis_throwable_2_7_4");
        externalChangesList.add("S eob_sewers_door_metal_2_28_5");
        externalChangesList.add("W eob_sewers_door_metal_2_20_18");
        externalChangesList.add("S eob_sewers_door_metal_2_19_21");
        externalChangesList.add("W eob_sewers_door_metal_2_21_23");
        externalChangesList.add("W eob_sewers_door_metal_2_5_20");
        externalChangesList.add("W eob_sewers_door_metal_2_5_22");
        externalChangesList.add("W eob_sewers_door_metal_2_5_24");
        externalChangesList.add("W eob_sewers_portcullis_throwable_2_23_16");

        externalChangesList.add("R eob_sewers_ladder_up_3_9_18_W");
        externalChangesList.add("S eob_sewers_door_metal_3_30_8");
        externalChangesList.add("W eob_sewers_door_metal_3_23_10");
        externalChangesList.add("S eob_sewers_door_metal_3_17_11");
        externalChangesList.add("W eob_sewers_door_metal_3_26_14");
        externalChangesList.add("S eob_sewers_door_metal_3_9_18");
        externalChangesList.add("S eob_sewers_door_metal_3_11_18");
        externalChangesList.add("S eob_sewers_door_metal_3_18_18");
        externalChangesList.add("W eob_sewers_door_metal_3_4_20");

        externalChangesList.add("W eob_ruins_door_stone_4_18_1");
        externalChangesList.add("S eob_ruins_net_4_30_4");
        externalChangesList.add("W eob_ruins_door_stone_4_2_7");
        externalChangesList.add("S eob_ruins_door_stone_4_3_8");
        externalChangesList.add("W eob_ruins_door_stone_4_20_6");
        externalChangesList.add("W eob_ruins_door_stone_4_20_8");
        externalChangesList.add("W eob_ruins_door_stone_4_20_10");
        externalChangesList.add("W eob_ruins_door_stone_4_20_12");
        externalChangesList.add("S eob_ruins_net_4_28_18");
        externalChangesList.add("S eob_ruins_door_stone_4_16_22");
        externalChangesList.add("W eob_ruins_net_4_17_28");
        externalChangesList.add("W eob_ruins_net_4_17_30");
        externalChangesList.add("S eob_ruins_door_stone_4_8_25");
        externalChangesList.add("W eob_ruins_net_4_21_24");
        externalChangesList.add("S eob_ruins_net_4_29_27");
        externalChangesList.add("W eob_ruins_door_stone_4_25_19");

        externalChangesList.add("W eob_ruins_door_stone_5_28_2");
        externalChangesList.add("S eob_ruins_door_stone_5_1_9");
        externalChangesList.add("S eob_ruins_door_stone_5_4_9");
        externalChangesList.add("S eob_ruins_door_stone_5_23_19");
        externalChangesList.add("S eob_ruins_door_stone_5_13_18");
        externalChangesList.add("S eob_ruins_door_stone_5_10_18");
        externalChangesList.add("S eob_ruins_door_stone_5_7_18");
        externalChangesList.add("W eob_ruins_door_stone_5_24_27");
        externalChangesList.add("W eob_ruins_door_stone_5_17_29");

        externalChangesList.add("W eob_ruins_door_stone_6_10_3");
        externalChangesList.add("S eob_ruins_door_stone_6_5_9");
        externalChangesList.add("S eob_ruins_door_stone_6_29_11");
        externalChangesList.add("S eob_ruins_door_stone_6_19_23");

        externalChangesList.add("W eob_drow_door_7_24_2");
        externalChangesList.add("W eob_drow_door_7_28_4");
        externalChangesList.add("W eob_drow_door_7_29_7");
        externalChangesList.add("W eob_drow_door_7_29_9");
        externalChangesList.add("W eob_drow_door_7_13_7");
        externalChangesList.add("W eob_drow_door_7_29_25");
        externalChangesList.add("W eob_drow_door_7_14_20");
        externalChangesList.add("W eob_drow_door_7_13_10");
        externalChangesList.add("S eob_drow_door_7_20_19");
        externalChangesList.add("S eob_drow_door_7_27_24");
        externalChangesList.add("S eob_drow_door_7_25_24");
        externalChangesList.add("S eob_drow_door_7_19_24");
        externalChangesList.add("S eob_drow_door_7_17_24");
        externalChangesList.add("S eob_drow_door_7_10_29");
        externalChangesList.add("S eob_drow_door_7_4_29");
        externalChangesList.add("S eob_drow_door_7_25_16");

        externalChangesList.add("S eob_drow_door_8_30_26");
        externalChangesList.add("S eob_drow_door_8_21_16");
        externalChangesList.add("S eob_drow_door_8_21_22");
        externalChangesList.add("W eob_drow_door_8_5_30");
        externalChangesList.add("W eob_drow_door_8_7_24");

        externalChangesList.add("R eob_drow_dart_firing_pad_9_7_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_11_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_6_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_7_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_11_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_12_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_7_14_N");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_14_N");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_14_N");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_14_N");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_11_14_N");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_6_13_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_7_13_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_13_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_13_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_13_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_8_13_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_9_13_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_10_13_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_11_13_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_12_13_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_14_8_S");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_13_9_E");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_15_9_W");
        externalChangesList.add("R eob_drow_dart_firing_pad_9_16_11_W");
        externalChangesList.add("F 9_16_11_W prison_secret_door"); // todo: add new wall type - Solid wall
        externalChangesList.add("S eob_drow_door_9_25_2");
        externalChangesList.add("S eob_drow_door_9_22_2");
        externalChangesList.add("S eob_drow_door_9_22_9");
        externalChangesList.add("S eob_drow_door_9_18_9");
        externalChangesList.add("S eob_drow_door_9_16_14");
        externalChangesList.add("S eob_drow_door_9_16_19");
        externalChangesList.add("W eob_drow_door_9_28_3");
        externalChangesList.add("W eob_drow_door_9_28_7");
        externalChangesList.add("W eob_drow_door_9_28_16");
        externalChangesList.add("W eob_drow_door_9_28_26");

        externalChangesList.add("W eob_hive_door_10_20_16");
        externalChangesList.add("S eob_hive_door_10_8_19");
        externalChangesList.add("S eob_hive_door_10_20_18");
        externalChangesList.add("S eob_hive_door_10_22_18");
        externalChangesList.add("S eob_hive_door_10_19_25");
        externalChangesList.add("S eob_hive_door_10_21_25");
        externalChangesList.add("S eob_hive_door_10_23_25");

        externalChangesList.add("W eob_hive_door_11_3_2");
        externalChangesList.add("W eob_hive_door_11_4_5");
        externalChangesList.add("W eob_hive_door_11_4_13");
        externalChangesList.add("W eob_hive_door_11_7_11");
        externalChangesList.add("W eob_hive_door_11_25_21");
        externalChangesList.add("W eob_hive_door_11_11_22");
        externalChangesList.add("W eob_hive_door_11_18_24");
        externalChangesList.add("W eob_hive_door_11_18_28");
        externalChangesList.add("S eob_hive_door_11_10_2");
        externalChangesList.add("S eob_hive_door_11_21_3");
        externalChangesList.add("S eob_hive_door_11_23_3");
        externalChangesList.add("S eob_hive_door_11_30_13");
        externalChangesList.add("S eob_hive_door_11_14_18");
        externalChangesList.add("S eob_hive_door_11_30_20");
        externalChangesList.add("S eob_hive_door_11_14_28");

        externalChangesList.add("W eob_sanctum_door_12_18_15");
        externalChangesList.add("W eob_sanctum_door_12_23_7");
        externalChangesList.add("W eob_sanctum_door_12_2_15");
        externalChangesList.add("W eob_sanctum_door_12_23_15");
        externalChangesList.add("W eob_sanctum_door_12_20_11");
        externalChangesList.add("S eob_sanctum_door_12_21_6");
        externalChangesList.add("S eob_sanctum_door_12_23_3");
    }

    public EobConverter(String[] args) {
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                int pos = arg.indexOf("=");
                String name = pos >= 0 ? arg.substring(0, pos) : arg;
                String value = pos >= 0 ? arg.substring(pos + 1) : "";
                try {
                    if (name.equals("--help")) {
                        EobLogger.println("usage: EobConverter.jar [-l|--levels=<from>;<to>] [-sp|--src-path=<value>] [-dp|--dst-path=<value>] [-c|--console] [-d|--debug] [-di|--debug-item=<value>] [-l1|--file-per-level] [-gs|--generate-default-structures]");
                        EobLogger.println("");
                        EobLogger.println("List of commands:");
                        EobLogger.println("   src-path                    Source path. (default=\".\")");
                        EobLogger.println("   dst-path                    Destination path. (default=\".\")");
                        EobLogger.println("   debug                       Show debug info.");
                        EobLogger.println("   debug-item                  Show debug info only for items contains <value> string. (default=\"\")");
                        EobLogger.println("   levels                      Convert all levels in range: <from,to>. (default=1;99)");
                        EobLogger.println("   file-per-level              Store each level in separate file.");
                        EobLogger.println("   generate-default-structures Generate default structures.");
                        EobLogger.println("");
                        return;
                    } else if (name.equals("--levels") || name.equals("-l")) {
                        String[] range = value.split(";");
                        settings.from = Integer.parseInt(range[0]);
                        settings.to = Integer.parseInt(range[1]);
                    } else if (name.equals("--debug") || name.equals("-d")) {
                        settings.debug = true;
                    } else if (name.equals("--console") || name.equals("-c")) {
                        settings.console = true;
                    } else if (name.equals("--src-path") || name.equals("-sp")) {
                        settings.srcPath = value;
                    } else if (name.equals("--dst-path") || name.equals("-dp")) {
                        settings.dstPath = value;
                    } else if (name.equals("--debug-item") || name.equals("-di")) {
                        settings.debugShowOnlyItemName = value;
                    } else if (name.equals("--file-per-level") || name.equals("-l1")) {
                        settings.createFilePerLevel = true;
                    } else if (name.equals("--generate-default-structures") || name.equals("-gs")) {
                        settings.generateDefaultStructures = true;
                    }
                } catch (IllegalArgumentException exception) {
                    EobLogger.println("Value " + value + " is not a number. Parameter " + name + " is ignored.");
                }
            }
        }
    }

    private void convert() {
        initExternalChanges();
        Eob1Settings.init();
        EobFiles eobFiles = new EobFiles(settings.srcPath);

        ItemParser itemParser = new ItemParser(eobFiles.getFile(ITEMS_FILE), settings.debug);
        itemParser.parseFile(settings.debugShowOnlyItemName);

        GrimrockExport grimrockExport = new GrimrockExport(settings.dstPath, externalChangesList, itemParser, settings.to, settings.generateDefaultStructures, settings.debug);

        for (int levelId = settings.from; levelId <= settings.to; levelId++) {
            byte[] levelMazFile = eobFiles.getFile(String.format(LEVEL_MAZ_FILE, levelId));
            if (levelMazFile != null) {
                LevelParser levelParser = new LevelParser(levelId, levelMazFile);
                levelParser.parse();
                grimrockExport.addLevel(levelParser);
            }

            byte[] levelInfFile = eobFiles.getFile(String.format(LEVEL_INF_FILE, levelId));
            if (levelInfFile != null) {
                InfFile infFile = new InfFile(levelId, levelInfFile, itemParser);
                grimrockExport.addLevelInfo(infFile);
            }
        }

        grimrockExport.exportIntoGrimrock(settings.createFilePerLevel);

        EobLogger.println("Summary:");
        EobLogger.println("Exported " + itemParser.getItemsCount() + " items of " + ItemType.getItemsCount() + " different types (" + ItemType.getUnknownItemsCount() + " are unknown)");
    }

    private void execute() {
        if (!settings.console) {
            settings.generateDefaultStructures = true;

            eobConverterForm = new EobConverterForm();
            final JFrame settingsFrame = new JFrame("Eye of Beholder Converter");
            settingsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            settingsFrame.add(eobConverterForm.getMainPanel());

            eobConverterForm.getSrcPathField().setText(settings.srcPath);
            eobConverterForm.getDstPathField().setText(settings.dstPath);
            eobConverterForm.getDebugModeCheckBox().setSelected(settings.debug);
            eobConverterForm.getItemNameField().setText(settings.debugShowOnlyItemName);
            eobConverterForm.getFromLevelField().setText(settings.from.toString());
            eobConverterForm.getToLevelField().setText(settings.to.toString());
            eobConverterForm.getGenerateDefaultStructuresCheckBox().setSelected(settings.generateDefaultStructures);
            eobConverterForm.getCreateLevelInSeparateCheckBox().setSelected(settings.createFilePerLevel);
            eobConverterForm.getConvertButton().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eobConverterForm.getOutput().setText("");
                    fillSettings();
                    convert();
                }
            });
            eobConverterForm.getSrcPathChooser().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser(eobConverterForm.getSrcPathField().getText());
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (fileChooser.showOpenDialog(settingsFrame) == JFileChooser.APPROVE_OPTION) {
                        eobConverterForm.getSrcPathField().setText(fileChooser.getSelectedFile().getPath());
                    }
                }
            });
            eobConverterForm.getDstPathChooser().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser(eobConverterForm.getDstPathField().getText());
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    if (fileChooser.showOpenDialog(settingsFrame) == JFileChooser.APPROVE_OPTION) {
                        eobConverterForm.getDstPathField().setText(fileChooser.getSelectedFile().getPath());
                    }
                }
            });
            EobLogger.output = eobConverterForm.getOutput();

            settingsFrame.setMinimumSize(new Dimension(600, 500));
            settingsFrame.pack();
            settingsFrame.setLocationRelativeTo(null);
            settingsFrame.setVisible(true);
        } else {
            convert();
        }
    }

    private void fillSettings() {
        settings.srcPath = eobConverterForm.getSrcPathField().getText();
        settings.dstPath = eobConverterForm.getDstPathField().getText();
        settings.debug = eobConverterForm.getDebugModeCheckBox().isSelected();
        settings.debugShowOnlyItemName = eobConverterForm.getItemNameField().getText();
        try {
            settings.from = Integer.parseInt(eobConverterForm.getFromLevelField().getText());
        } finally {
            settings.from = 1;
        }
        try {
            settings.to = Integer.parseInt(eobConverterForm.getToLevelField().getText());
        } finally {
            settings.to = 99;
        }
        settings.generateDefaultStructures = eobConverterForm.getGenerateDefaultStructuresCheckBox().isSelected();
        settings.createFilePerLevel = eobConverterForm.getCreateLevelInSeparateCheckBox().isSelected();
    }

    public static void main(String[] args) {
        EobConverter converter = new EobConverter(args);
        converter.execute();
    }

    private static class Settings {
        public String srcPath = ".";
        public String dstPath = ".";
        public Integer from = 1;
        public Integer to = 99;
        public String debugShowOnlyItemName = "";
        public boolean debug = false;
        public boolean createFilePerLevel = false;
        public boolean generateDefaultStructures = false;
        public boolean console = false;
    }
}
