package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.model.ItemType;
import org.eob.model.Square;
import org.eob.model.Wall;

import java.io.*;
import java.util.*;

/**
 * User: Bifrost
 * Date: 10/6/12
 * Time: 3:16 PM
 */
public class EobConverter {
    private final static List<String> externalChangesList = new ArrayList<String>();

    {
        externalChangesList.add("W door_1_2_3");
        externalChangesList.add("S door_1_16_11");
        externalChangesList.add("S door_1_19_18");

        externalChangesList.add("S portcullis_through_2_7_4");
        externalChangesList.add("S door_2_28_5");
        externalChangesList.add("W door_2_20_18");
        externalChangesList.add("S door_2_19_21");
        externalChangesList.add("W door_2_21_23");
        externalChangesList.add("W door_2_5_20");
        externalChangesList.add("W door_2_5_22");
        externalChangesList.add("W door_2_5_24");
        externalChangesList.add("W portcullis_through_2_23_16");

        externalChangesList.add("R stairs_up_3_9_18_W");
        externalChangesList.add("S door_3_30_8");
        externalChangesList.add("W door_3_23_10");
        externalChangesList.add("S door_3_17_11");
        externalChangesList.add("W door_3_26_14");
        externalChangesList.add("S door_3_9_18");
        externalChangesList.add("S door_3_11_18");
        externalChangesList.add("S door_3_18_18");
        externalChangesList.add("W door_3_4_20");

        externalChangesList.add("W door_4_18_1");
        externalChangesList.add("S net_4_30_4");
        externalChangesList.add("W door_4_2_7");
        externalChangesList.add("S door_4_3_8");
        externalChangesList.add("W door_4_20_6");
        externalChangesList.add("W door_4_20_8");
        externalChangesList.add("W door_4_20_10");
        externalChangesList.add("W door_4_20_12");
        externalChangesList.add("S net_4_28_18");
        externalChangesList.add("S door_4_16_22");
        externalChangesList.add("W net_4_17_28");
        externalChangesList.add("W net_4_17_30");
        externalChangesList.add("S door_4_8_25");
        externalChangesList.add("W net_4_21_24");
        externalChangesList.add("S net_4_29_27");
        externalChangesList.add("W door_4_25_19");

        externalChangesList.add("W door_5_28_2");
        externalChangesList.add("S door_5_1_9");
        externalChangesList.add("S door_5_4_9");
        externalChangesList.add("S door_5_23_19");
        externalChangesList.add("S door_5_13_18");
        externalChangesList.add("S door_5_10_18");
        externalChangesList.add("S door_5_7_18");
        externalChangesList.add("W door_5_24_27");
        externalChangesList.add("W door_5_17_29");

        externalChangesList.add("W door_6_10_3");
        externalChangesList.add("S door_6_5_9");
        externalChangesList.add("S door_6_29_11");
        externalChangesList.add("S door_6_19_23");

        externalChangesList.add("W door_7_24_2");
        externalChangesList.add("W door_7_28_4");
        externalChangesList.add("W door_7_29_7");
        externalChangesList.add("W door_7_29_9");
        externalChangesList.add("W door_7_13_7");
        externalChangesList.add("W door_7_29_25");
        externalChangesList.add("W door_7_14_20");
        externalChangesList.add("W door_7_13_10");
        externalChangesList.add("S door_7_20_19");
        externalChangesList.add("S door_7_27_24");
        externalChangesList.add("S door_7_25_24");
        externalChangesList.add("S door_7_19_24");
        externalChangesList.add("S door_7_17_24");
        externalChangesList.add("S door_7_10_29");
        externalChangesList.add("S door_7_4_29");
        externalChangesList.add("S door_7_25_16");

        externalChangesList.add("S door_8_30_26");
        externalChangesList.add("S door_8_21_16");
        externalChangesList.add("S door_8_21_22");
        externalChangesList.add("W door_8_5_30");
        externalChangesList.add("W door_8_7_24");

        externalChangesList.add("R firing_mechanism_9_7_8_S");
        externalChangesList.add("R firing_mechanism_9_8_8_S");
        externalChangesList.add("R firing_mechanism_9_9_8_S");
        externalChangesList.add("R firing_mechanism_9_10_8_S");
        externalChangesList.add("R firing_mechanism_9_11_8_S");
        externalChangesList.add("R firing_mechanism_9_6_9_E");
        externalChangesList.add("R firing_mechanism_9_7_9_E");
        externalChangesList.add("R firing_mechanism_9_8_9_E");
        externalChangesList.add("R firing_mechanism_9_9_9_E");
        externalChangesList.add("R firing_mechanism_9_10_9_E");
        externalChangesList.add("R firing_mechanism_9_8_9_W");
        externalChangesList.add("R firing_mechanism_9_9_9_W");
        externalChangesList.add("R firing_mechanism_9_10_9_W");
        externalChangesList.add("R firing_mechanism_9_11_9_W");
        externalChangesList.add("R firing_mechanism_9_12_9_W");
        externalChangesList.add("R firing_mechanism_9_7_14_N");
        externalChangesList.add("R firing_mechanism_9_8_14_N");
        externalChangesList.add("R firing_mechanism_9_9_14_N");
        externalChangesList.add("R firing_mechanism_9_10_14_N");
        externalChangesList.add("R firing_mechanism_9_11_14_N");
        externalChangesList.add("R firing_mechanism_9_6_13_E");
        externalChangesList.add("R firing_mechanism_9_7_13_E");
        externalChangesList.add("R firing_mechanism_9_8_13_E");
        externalChangesList.add("R firing_mechanism_9_9_13_E");
        externalChangesList.add("R firing_mechanism_9_10_13_E");
        externalChangesList.add("R firing_mechanism_9_8_13_W");
        externalChangesList.add("R firing_mechanism_9_9_13_W");
        externalChangesList.add("R firing_mechanism_9_10_13_W");
        externalChangesList.add("R firing_mechanism_9_11_13_W");
        externalChangesList.add("R firing_mechanism_9_12_13_W");
        externalChangesList.add("R firing_mechanism_9_14_8_S");
        externalChangesList.add("R firing_mechanism_9_13_9_E");
        externalChangesList.add("R firing_mechanism_9_15_9_W");
        externalChangesList.add("R firing_mechanism_9_16_11_W");
        externalChangesList.add("F 9_16_11_W prison_secret_door");
        externalChangesList.add("S door_9_25_2");
        externalChangesList.add("S door_9_22_2");
        externalChangesList.add("S door_9_22_9");
        externalChangesList.add("S door_9_18_9");
        externalChangesList.add("S door_9_16_14");
        externalChangesList.add("S door_9_16_19");
        externalChangesList.add("W door_9_28_3");
        externalChangesList.add("W door_9_28_7");
        externalChangesList.add("W door_9_28_16");
        externalChangesList.add("W door_9_28_26");

        externalChangesList.add("W door_10_20_16");
        externalChangesList.add("S door_10_8_19");
        externalChangesList.add("S door_10_20_18");
        externalChangesList.add("S door_10_22_18");
        externalChangesList.add("S door_10_19_25");
        externalChangesList.add("S door_10_21_25");
        externalChangesList.add("S door_10_23_25");

        externalChangesList.add("W door_11_3_2");
        externalChangesList.add("W door_11_4_5");
        externalChangesList.add("W door_11_4_13");
        externalChangesList.add("W door_11_7_11");
        externalChangesList.add("W door_11_25_21");
        externalChangesList.add("W door_11_11_22");
        externalChangesList.add("W door_11_18_24");
        externalChangesList.add("W door_11_18_28");
        externalChangesList.add("S door_11_10_2");
        externalChangesList.add("S door_11_21_3");
        externalChangesList.add("S door_11_23_3");
        externalChangesList.add("S door_11_30_13");
        externalChangesList.add("S door_11_14_18");
        externalChangesList.add("S door_11_30_20");
        externalChangesList.add("S door_11_14_28");

        externalChangesList.add("W door_12_18_15");
        externalChangesList.add("W door_12_23_7");
        externalChangesList.add("W door_12_2_15");
        externalChangesList.add("W door_12_23_15");
        externalChangesList.add("W door_12_20_11");
        externalChangesList.add("S door_12_21_6");
        externalChangesList.add("S door_12_23_3");
    }

    private static boolean DEBUG = true;

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        Eob1Settings.init();

        ItemParser itemParser = new ItemParser("ITEM.DAT", DEBUG);
        itemParser.parseFile("");

        GrimrockExport grimrockExport = new GrimrockExport(externalChangesList, itemParser, DEBUG);

        LevelParser levelParser = new LevelParser(args[0]);
        levelParser.parse();
        grimrockExport.addLevel(levelParser);


        grimrockExport.exportIntoGrimrock(true);

        System.out.println("Summary:");
        System.out.println("Exported " + itemParser.getItemsCount() + " items of " + ItemType.getItemsCount() + " different types (" + ItemType.getUnknownItemsCount() + " are unknown)");
    }
}
