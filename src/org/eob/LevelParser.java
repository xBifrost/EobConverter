package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.model.Square;
import org.eob.model.Wall;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * User: Bifrost
 * Date: 10/24/12
 * Time: 10:03 AM
 */
public class LevelParser {
    private final byte[] levelData;
    public final int width;
    public final int height;
    public final int levelId;
    public final Square[][] level;

    public LevelParser(String fileName) {
        levelId = getLevelId(fileName);
        levelData = FileUtility.readFile(fileName);
        width = ByteArrayUtility.getWord(levelData, 0);
        height = ByteArrayUtility.getWord(levelData, 2);
        level = new Square[width][height];
    }

    private int getLevelId(String fileName) {
        String levelIdStr = "";
        int pos = fileName.length() - 1;
        while (fileName.charAt(pos) < '0' || fileName.charAt(pos) > '9') {
            pos--;
        }
        while (fileName.charAt(pos) >= '0' && fileName.charAt(pos) <= '9') {
            levelIdStr = fileName.charAt(pos) + levelIdStr;
            pos--;
        }
        return Integer.parseInt(levelIdStr);
    }

    public void parse() {
        System.out.println("Parsing level " + levelId + "...");
        int width = ByteArrayUtility.getWord(levelData, 0);
        int height = ByteArrayUtility.getWord(levelData, 2);
        int sizePerSquare = ByteArrayUtility.getWord(levelData, 4);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                level[x][y] = new Square(
                        x, y,
                        levelData[6 + (y * width + x) * sizePerSquare],
                        levelData[6 + (y * width + x) * sizePerSquare + 1],
                        levelData[6 + (y * width + x) * sizePerSquare + 2],
                        levelData[6 + (y * width + x) * sizePerSquare + 3],
                        levelId);
                level[x][y].checkWalls();
            }
        }
    }
}
