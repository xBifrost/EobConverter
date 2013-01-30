package org.eob;

import org.eob.external.ExternalChangesParser;
import org.eob.file.EobFiles;
import org.eob.file.dat.ItemTypeDatFile;
import org.eob.file.inf.InfFile;
import org.eob.gui.EobConverterForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * User: Bifrost
 * Date: 10/6/12
 * Time: 3:16 PM
 */
public class EobConverter {
    public final static String CONVERTER_VERSION = "0.9.8";
    private final static String ITEMS_FILE = "ITEM.DAT";
    private final static String ITEM_TYPE_FILE = "ITEMTYPE.DAT";
    private final static String LEVEL_MAZ_FILE = "LEVEL%d.MAZ";
    private final static String LEVEL_INF_FILE = "LEVEL%d.INF";
    public final static String LEVEL_INF_UNPACKED = "LEVEL%d.INF_UNPACKED";
    public final static String LEVEL_SCRIPT_FILE = "LEVEL%d.SCRIPT";

    private Settings settings = new Settings();
    private EobConverterForm eobConverterForm;

    public EobConverter(String[] args) {
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                int pos = arg.indexOf("=");
                String name = pos >= 0 ? arg.substring(0, pos) : arg;
                String value = pos >= 0 ? arg.substring(pos + 1) : "";
                try {
                    if (name.equals("--help")) {
                        EobLogger.println("usage: EobConverter.jar [-l|--levels=<from>;<to>] [-sp|--src-path=<value>] [-dp|--dst-path=<value>] [-ep|--ext-path=<value>]");
                        EobLogger.println("                        [-c|--console] [-d|--debug] [-ds|debug-script] [-dt|--debug-show-item-type]");
                        EobLogger.println("                        [-di|--debug-show-item] [-din|--debug-item-name=<value>]");
                        EobLogger.println("                        [-l1|--file-per-level] [-gs|--generate-default-structures] [-sw|--skip-wall-errors]");
                        EobLogger.println("                        [-inf|--write_unpacked-inf] [-es|--export-scripts] [-as|--add-scripts]");
                        EobLogger.println("");
                        EobLogger.println("List of commands:");
                        EobLogger.println("   src-path                    Source path. (default=\".\")");
                        EobLogger.println("   dst-path                    Destination path. (default=\".\")");
                        EobLogger.println("   ext-path                    External changes path. (default=\"\")");
                        EobLogger.println("   debug                       Show debug info.");
                        EobLogger.println("   debug-script                Show script debug info.");
                        EobLogger.println("   debug-show-item-type        Show item types.");
                        EobLogger.println("   debug-show-item             Show items");
                        EobLogger.println("   debug-item-name             Show debug info only for items contains <value> string. (default=\"\")");
                        EobLogger.println("   skip-wall-errors            Skip showing wall errors (default=show)");
                        EobLogger.println("   levels                      Convert all levels in range: <from,to>. (default=1;99)");
                        EobLogger.println("   file-per-level              Store each level in separate file.");
                        EobLogger.println("   generate-default-structures Generate default structures.");
                        EobLogger.println("   write_unpacked-inf          Write unpacked inf files.");
                        EobLogger.println("   export-scripts              Export EoB scripts into the files.");
                        EobLogger.println("   add-scripts                 Add EoB script into the lua as comment.");
                        EobLogger.println("                               Functions are added into the lua script at position [0,0].");
                        EobLogger.println("");
                        return;
                    } else if (name.equals("--levels") || name.equals("-l")) {
                        String[] range = value.split(";");
                        settings.from = Math.max(1, Math.min(99, Integer.parseInt(range[0])));
                        settings.to = Math.max(settings.from, Math.min(99, Integer.parseInt(range[1])));
                    } else if (name.equals("--debug") || name.equals("-d")) {
                        settings.debug = true;
                    } else if (name.equals("--debug-script") || name.equals("-ds")) {
                        settings.scriptDebug = true;
                    } else if (name.equals("--console") || name.equals("-c")) {
                        settings.console = true;
                    } else if (name.equals("--src-path") || name.equals("-sp")) {
                        settings.srcPath = value;
                    } else if (name.equals("--dst-path") || name.equals("-dp")) {
                        settings.dstPath = value;
                    } else if (name.equals("--ext-path") || name.equals("-ep")) {
                        settings.extChangesPath = value;
                    } else if (name.equals("--debug-show-item-type") || name.equals("-dt")) {
                        settings.showItemTypes = true;
                    } else if (name.equals("--debug-show-item") || name.equals("-di")) {
                        settings.showItems = true;
                    } else if (name.equals("--debug-item-name") || name.equals("-din")) {
                        settings.showItems = true;
                        settings.showOnlyItemName = value;
                    } else if (name.equals("--file-per-level") || name.equals("-l1")) {
                        settings.createFilePerLevel = true;
                    } else if (name.equals("--generate-default-structures") || name.equals("-gs")) {
                        settings.generateDefaultStructures = true;
                    } else if (name.equals("--skip-wall-errors") || name.equals("-sw")) {
                        settings.debugWalls = false;
                    } else if (name.equals("--write_unpacked-inf") || name.equals("-inf")) {
                        settings.writeUnpackedInf = true;
                    } else if (name.equals("--export-scripts") || name.equals("-es")) {
                        settings.exportEobScripts = true;
                    } else if (name.equals("--add-scripts") || name.equals("-as")) {
                        settings.addEobScriptIntoLua = true;
                    }
                } catch (IllegalArgumentException exception) {
                    EobLogger.println("Value " + value + " is not a number. Parameter " + name + " is ignored.");
                }
            }
        }
    }

    private void convert() {
        ExternalChangesParser externalChangesParser = new ExternalChangesParser();
        EobGlobalData eobGlobalData = new EobGlobalData();

        InputStream eob1RepairStream = ExternalChangesParser.class.getClassLoader().getResourceAsStream("org/eob/repairEob1.dat");
        InputStream externalChangesStream = null;
        try {
            externalChangesStream = settings.extChangesPath.trim().length() == 0 ?
                    ExternalChangesParser.class.getClassLoader().getResourceAsStream("org/eob/externalChangesEob1.dat") :
                    new FileInputStream(settings.extChangesPath);
        } catch (FileNotFoundException e) {
            EobLogger.println("File '" + settings.extChangesPath + "'was not found!");
        }

        eobGlobalData.externalChangeCommands = externalChangesParser.parseFile(eob1RepairStream);
        if (externalChangesStream != null) {
            eobGlobalData.externalChangeCommands.addAll(externalChangesParser.parseFile(externalChangesStream));
        }
        EobFiles eobFiles = new EobFiles(settings.srcPath);
        eobGlobalData.itemTypeDatFile = new ItemTypeDatFile(eobFiles.getFile(ITEM_TYPE_FILE));
        Eob1Settings.init(eobGlobalData);

        if (settings.showItemTypes) {
            eobGlobalData.itemTypeDatFile.printItemTypes();
        }

        eobGlobalData.itemParser = new ItemParser(eobFiles.getFile(ITEMS_FILE), eobGlobalData, settings);
        eobGlobalData.itemParser.parseFile();

        GrimrockExport grimrockExport = new GrimrockExport(eobGlobalData.externalChangeCommands, settings, eobGlobalData);

        for (int levelId = settings.from; levelId <= settings.to; levelId++) {
            byte[] levelMazFile = eobFiles.getFile(String.format(LEVEL_MAZ_FILE, levelId));
            if (levelMazFile != null) {
                LevelParser levelParser = new LevelParser(levelId, levelMazFile, eobGlobalData, settings);
                levelParser.parse();
                grimrockExport.addLevel(levelParser);
            }

            byte[] levelInfFile = eobFiles.getFile(String.format(LEVEL_INF_FILE, levelId));
            if (levelInfFile != null) {
                InfFile infFile = new InfFile(levelId, levelInfFile, eobGlobalData, settings.writeUnpackedInf);
                grimrockExport.addLevelInfo(infFile);
            }
        }

        grimrockExport.exportIntoGrimrock(settings.createFilePerLevel);

        EobLogger.println("Summary:");
        EobLogger.println("Exported " + eobGlobalData.itemParser.getItemsCount() + " items of " +
                eobGlobalData.itemTypeDatFile.itemTypeList.size() + " different types (" + eobGlobalData.itemTypeDatFile.getUnknownItemsCount() + " are unknown)");
    }

    private void execute() {
        if (!settings.console) {
            settings.generateDefaultStructures = true;

            eobConverterForm = new EobConverterForm();
            final JFrame settingsFrame = new JFrame("Eye of the Beholder Converter " + CONVERTER_VERSION);
            settingsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            settingsFrame.add(eobConverterForm.getMainPanel());

            eobConverterForm.getSrcPathField().setText(settings.srcPath);
            eobConverterForm.getDstPathField().setText(settings.dstPath);
            eobConverterForm.getExtChangesPathField().setText(settings.extChangesPath);
            eobConverterForm.getDebugModeCheckBox().setSelected(settings.debug);
            eobConverterForm.getScriptDebugModeCheckBox().setSelected(settings.scriptDebug);
            eobConverterForm.getDebugWallCheckBox().setSelected(settings.debugWalls);
            eobConverterForm.getItemNameField().setText(settings.showOnlyItemName);
            eobConverterForm.getFromLevelField().setText(settings.from.toString());
            eobConverterForm.getToLevelField().setText(settings.to.toString());
            eobConverterForm.getGenerateDefaultStructuresCheckBox().setSelected(settings.generateDefaultStructures);
            eobConverterForm.getCreateLevelInSeparateCheckBox().setSelected(settings.createFilePerLevel);
            eobConverterForm.getWriteUnpackedInfFilesCheckBox().setSelected(settings.writeUnpackedInf);
            eobConverterForm.getExportEobScriptsCheckBox().setSelected(settings.exportEobScripts);
            eobConverterForm.getAddEobScriptIntoLuaCheckBox().setSelected(settings.addEobScriptIntoLua);
            eobConverterForm.getPrintItemTypesCheckBox().setSelected(settings.showItemTypes);
            eobConverterForm.getPrintItemsCheckBox().setSelected(settings.showItems);
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
            eobConverterForm.getExtChangesPathChooser().addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser(eobConverterForm.getExtChangesPathField().getText());
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    if (fileChooser.showOpenDialog(settingsFrame) == JFileChooser.APPROVE_OPTION) {
                        eobConverterForm.getExtChangesPathField().setText(fileChooser.getSelectedFile().getPath());
                    }
                }
            });
            EobLogger.output = eobConverterForm.getOutput();

            settingsFrame.setMinimumSize(new Dimension(600, 600));
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
        settings.extChangesPath = eobConverterForm.getExtChangesPathField().getText();
        settings.debug = eobConverterForm.getDebugModeCheckBox().isSelected();
        settings.scriptDebug = eobConverterForm.getScriptDebugModeCheckBox().isSelected();
        settings.debugWalls = eobConverterForm.getDebugWallCheckBox().isSelected();
        settings.showOnlyItemName = eobConverterForm.getItemNameField().getText();
        try {
            settings.from = Math.max(1, Math.min(99, Integer.parseInt(eobConverterForm.getFromLevelField().getText())));
        } catch (NumberFormatException e) {
            settings.from = 1;
        }
        try {
            settings.to = Math.max(settings.from, Math.min(99, Integer.parseInt(eobConverterForm.getToLevelField().getText())));
        } catch (NumberFormatException e) {
            settings.to = 99;
        }
        settings.generateDefaultStructures = eobConverterForm.getGenerateDefaultStructuresCheckBox().isSelected();
        settings.createFilePerLevel = eobConverterForm.getCreateLevelInSeparateCheckBox().isSelected();
        settings.writeUnpackedInf = eobConverterForm.getWriteUnpackedInfFilesCheckBox().isSelected();
        settings.exportEobScripts = eobConverterForm.getExportEobScriptsCheckBox().isSelected();
        settings.addEobScriptIntoLua = eobConverterForm.getAddEobScriptIntoLuaCheckBox().isSelected();
        settings.showItemTypes = eobConverterForm.getPrintItemTypesCheckBox().isSelected();
        settings.showItems = eobConverterForm.getPrintItemsCheckBox().isSelected();
    }

    public static void main(String[] args) {
        EobConverter converter = new EobConverter(args);
        converter.execute();
    }
}
