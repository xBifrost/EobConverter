package org.eob;

import org.eob.enums.GameSupportType;
import org.eob.enums.InSquarePositionType;
import org.eob.enums.ItemSlotType;
import org.eob.enums.WallType;
import org.eob.export.MonsterGroup;
import org.eob.file.inf.EobCommand;
import org.eob.file.inf.EobScriptFunction;
import org.eob.file.inf.EobTrigger;
import org.eob.file.inf.InfFile;
import org.eob.model.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * User: Bifrost
 * Date: 10/25/12
 * Time: 6:19 PM
 */
@SuppressWarnings("FieldCanBeLocal")
public class GrimrockExport {
    private final static String DEFAULT_BASE_OBJECT = "rock";

    private Map<Long, LevelParser> levels = new TreeMap<Long, LevelParser>();
    private Map<Long, InfFile> levelsInfo = new TreeMap<Long, InfFile>();

    private static String namePrefix = "eob_";

    private static String DUNGEON_FILE = "dungeon.lua";
    private static String ITEM_FILE = "items.lua";
    private static String MONSTER_FILE = "monsters.lua";
    private static String OBJECTS_FILE = "objects.lua";
    private static String LEVEL_FILE = "level%d.lua";

    private final Settings settings;
    private final EobGlobalData eobGlobalData;

    private final Map<String, String> externalChanges = new LinkedHashMap<String, String>();
    private final Map<String, String> externalElementName = new LinkedHashMap<String, String>();
    private final Set<String> unusedExternalChanges = new LinkedHashSet<String>();

    // Map of the already defined monster groups
    private final Map<String, String> monstersByPosition = new HashMap<String, String>();

    // Indexes
    private final Map<String, Long> itemNameNextIndex = new HashMap<String, Long>();
    private final Map<String, Long> monsterNameNextIndex = new HashMap<String, Long>();
    private final Map<String, Long> monsterGroupNextIndex = new HashMap<String, Long>();

    private final List<GrimrockLevelSettings> levelsSettings = Arrays.asList(
            new GrimrockLevelSettings(1L, "Upper Sewer Level", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(2L, "Middle Sewer Level", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(3L, "Lower Sewer Level", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(4L, "Upper Level Dwarven Ruins", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(5L, "Dwarven Ruins and Camp", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(6L, "Botton Level of Dwarven Ruins", "temple", "assets/samples/music/temple_ambient.ogg"),
            new GrimrockLevelSettings(7L, "Upper Reaches of The Drow", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(8L, "Drow Outcasts", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(9L, "Lower Reaches of The Drow", "prison", "assets/samples/music/prison_ambient.ogg"),
            new GrimrockLevelSettings(10L, "Xanthar's Outer Sanctum, Mantis Hive", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(11L, "Xanthar's Outer Sanctum, Lower Reaches", "dungeon", "assets/samples/music/dungeon_ambient.ogg"),
            new GrimrockLevelSettings(12L, "Xanthar's Inner Sanctum", "temple", "assets/samples/music/temple_ambient.ogg")
    );

    Map<String, String> defaultMonsters = new LinkedHashMap<String, String>(); // Eob monsters by Grimrock monsters
    Map<String, String> defaultItems = new LinkedHashMap<String, String>(); // Eob monsters by Grimrock items
    Map<String, GrimrockWall> grimrockWalls = new LinkedHashMap<String, GrimrockWall>();

    public GrimrockExport(List<String> externalChangesList, Settings settings, EobGlobalData eobGlobalData) {
        this.settings = settings;
        this.eobGlobalData = eobGlobalData;
        prepareExternalChanges(externalChangesList);
        generateDefaultStructuresFromGrimrock();
    }

    private void generateDefaultStructuresFromGrimrock() {
        defaultMonsters.put("eob_kobold", "skeleton_warrior");
        defaultMonsters.put("eob_leech", "snail");
        defaultMonsters.put("eob_zombie", "skeleton_warrior");
        defaultMonsters.put("eob_skeleton", "skeleton_warrior");
        defaultMonsters.put("eob_kuotoa", "herder_big");
        defaultMonsters.put("eob_flind", "uggardian");
        defaultMonsters.put("eob_spider", "spider");
        defaultMonsters.put("eob_dwarf", "skeleton_warrior");
        defaultMonsters.put("eob_kenku", "skeleton_archer");
        defaultMonsters.put("eob_mage", "uggardian");
        defaultMonsters.put("eob_drowelf", "uggardian");
        defaultMonsters.put("eob_skelwar", "skeleton_warrior");
        defaultMonsters.put("eob_drider", "crab");
        defaultMonsters.put("eob_hellhnd", "shrakk_torr");
        defaultMonsters.put("eob_rust", "crab");
        defaultMonsters.put("eob_disbeast", "ice_lizard");
        defaultMonsters.put("eob_shindia", "skeleton_warrior");
        defaultMonsters.put("eob_mantis", "ogre");
        defaultMonsters.put("eob_xorn", "crab");
        defaultMonsters.put("eob_mflayer", "goromorg");
        defaultMonsters.put("eob_xanath", "cube");
        defaultMonsters.put("eob_golem", "warden");

        defaultItems.put("axe", "hand_axe");
        defaultItems.put("long_sword", "long_sword");
        defaultItems.put("short_sword", "cutlass");
        defaultItems.put("orb_of_power", "magic_orb");
        defaultItems.put("dart", "shuriken");
        defaultItems.put("dagger", "dagger");
        defaultItems.put("dwarven_potion", "potion_healing");
        defaultItems.put("bow", "short_bow");
        defaultItems.put("spear", "legionary_spear");
        defaultItems.put("halberd", "legionary_spear"); // there is no other long weapon in Grimrock, so spear will have to do
        defaultItems.put("mace", "knoffer");
        defaultItems.put("flail", "flail");
        defaultItems.put("staff", "whitewood_wand"); // Not present in the dungeon, but defined as 4th item (offset=0x2c)
        defaultItems.put("sling", "sling");
        defaultItems.put("dart", "shuriken");
        defaultItems.put("arrow", "arrow");
        defaultItems.put("rock", "rock");
        defaultItems.put("banded_armor", "ring_mail");
        defaultItems.put("chain_mail", "ring_mail");
        defaultItems.put("dwarven_helmet", "full_helmet");
        defaultItems.put("leather_armor", "leather_brigandine");
        defaultItems.put("plate_mail", "plate_cuirass");
        defaultItems.put("scale_mail", "ring_mail");
        defaultItems.put("shield", "round_shield");
        defaultItems.put("lock_picks", "machine_part_south");
        defaultItems.put("spell_book", "tome_wisdom");
        defaultItems.put("holy_symbol", "golden_chalice"); // there's nothing that looks like ankh symbol
        defaultItems.put("rations", "pitroot_bread");
        defaultItems.put("leather_boots", "leather_boots");
        defaultItems.put("bones", "remains_of_toorum");
        defaultItems.put("mage_scroll", "scroll");
        defaultItems.put("cleric_scroll", "scroll");
        defaultItems.put("scroll", "scroll");
        defaultItems.put("stone", "rock");
        defaultItems.put("key", "brass_key"); // Different keys
        defaultItems.put("potion", "potion_healing");
        defaultItems.put("gem", "blue_gem"); // Gems of different colors (red and blue)
        defaultItems.put("robe", "peasant_tunic");
        defaultItems.put("medallion_of_adornment", "spirit_mirror_pendant");
        defaultItems.put("ring", "hardstone_bracelet"); // There are no rings in Grimrock, we need to replace them with bracelets
        defaultItems.put("bracers", "bracelet_tirin");
        defaultItems.put("medallion", "frostbite_necklace"); // Actual name Luck Stone Medallion
        defaultItems.put("ring2", "bracelet_tirin"); // This one has no power (found in lv 4, x=11,y=29)
        defaultItems.put("wand", "whitewood_wand");
        defaultItems.put("egg", "slime_bell"); // There is really nothing in Grimrock that even vaguely resembles an egg. Slime bell is at least round :)

        GrimrockWall.addWall(grimrockWalls, "All", "Blockages", "emptyMonsterBlock", "blocker", "eob_blocker", "");
        GrimrockWall.addWall(grimrockWalls, "All", "Teleporters", "teleporter", "teleporter", "eob_teleporter", "");

        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorWithButton", "dungeon_door_metal", "eob_sewers_door_metal", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoor", "dungeon_door_metal", "eob_sewers_door_metal", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorOpened", "dungeon_door_metal", "eob_sewers_door_metal", "\t:setDoorState(\"open\")");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorStacked", "dungeon_door_metal", "eob_sewers_door_metal_stacked", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorPortcullisWithButton", "dungeon_door_portcullis", "eob_sewers_door_portcullis", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorPortcullisStacked", "dungeon_door_portcullis", "eob_sewers_door_portcullis_stacked", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Doors", "sewersDoorPortcullisThrowableThrough", "dungeon_ivy_1", "eob_sewers_portcullis_throwable", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Floor", "sewersEmptyPit", "dungeon_pit", "eob_sewers_pit", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Floor", "sewersPressurePlate", "dungeon_pressure_plate", "eob_sewers_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Ceiling", "sewersEmptyCeilingShaft", "dungeon_ceiling_shaft", "eob_sewers_ceiling_shaft", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Walls", "sewersWallIllusion", "dungeon_secret_door", "eob_sewers_illusion_wall_rune", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Walls", "sewersWallIllusionWithRune", "dungeon_secret_door", "eob_sewers_illusion_wall_rune", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Wall Texts", "sewersWallWithText", "dungeon_wall_text_long", "eob_sewers_wall_text_long", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Wall Texts", "sewersWallWithText2", "dungeon_wall_text_long", "eob_sewers_wall_text_rats", ""); // Read able text (Rats ->)
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Wall Texts", "sewersWallWithRune", "dungeon_wall_text", "eob_sewers_wall_text_rune1", ""); // Rune on the wall
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Decorations", "sewersWallWithDrainage", "dungeon_wall_drainage", "eob_sewers_wall_drainage", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Decorations", "sewersWallWithDrainageBent1", "dungeon_wall_drainage", "eob_sewers_wall_drainage_bent", ""); // Is it the same as 62???
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Decorations", "sewersWallWithDrainageBent2", "dungeon_wall_drainage", "eob_sewers_wall_drainage_bent", ""); // Is it the same as 44???
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Decorations", "sewersWallWithPipe", "receptor", "eob_sewers_wall_pipe", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Stairs / Ladders", "sewersLevelUp", "lever", "eob_sewers_ladder_up", ""); // Ladder
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Stairs / Ladders", "sewersLevelDown", "lever", "eob_sewers_ladder_down", ""); // Ladder
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Alcoves", "sewersWallWithAlcove", "dungeon_alcove", "eob_sewers_alcove", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithKeyHoleButton", "lock", "eob_sewers_lock_iron", ""); // Key hole -> button
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithSilverKeyHole", "lock_round", "eob_sewers_lock_silver", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithGoldKeyHole", "lock_golden", "eob_sewers_lock_golden", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithEyeKeyHole", "lock_round", "eob_sewers_lock_eye", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithJewelKeyHole", "lock_gear", "eob_sewers_lock_gem", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Locks", "sewersWallWithDaggerKeyHole", "dungeon_alcove", "eob_sewers_alcove_dagger", ""); // Dagger is used as key
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Buttons / Levers", "sewersWallWithLever", "lever", "eob_sewers_lever", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Buttons / Levers", "sewersWallWithSecretButtonLarge", "dungeon_secret_button_large", "eob_sewers_secret_button_large", ""); // Brick
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Buttons / Levers", "sewersWallWithButtonSmall", "dungeon_secret_button_small", "eob_sewers_secret_button_small", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Buttons / Levers", "sewersWallWithButton", "wall_button", "eob_sewers_button", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Buttons / Levers", "sewersWallWithButtonPressed", "lever", "eob_sewers_switch", "\t:setLeverState(\"activated\")");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Blockages", "sewersCaveIn", "dungeon_cave_in", "eob_sewers_cave_in", "");
        GrimrockWall.addWall(grimrockWalls, "Sewers", "Portals", "sewersWallPortalInactive", "temple_glass_wall_2", "eob_sewers_portal_inactive", ""); // Level 2 - debug area left by developers

        GrimrockWall.addWall(grimrockWalls, "Ruins", "Doors", "ruinsDoorWithButton", "temple_door_metal", "eob_ruins_door_stone", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Doors", "ruinsDoor", "temple_door_metal", "eob_ruins_door_stone", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Doors", "ruinsDoorStacked", "temple_door_metal", "eob_ruins_door_stone_stacked", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Doors", "ruinsNet", "temple_door_portcullis", "eob_ruins_net", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Doors", "ruinsNetTorn", "temple_door_portcullis", "eob_ruins_net_torn", ""); // Torn Net (4 level)
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Floor", "ruinsEmptyPit", "temple_pit", "eob_ruins_pit", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Floor", "ruinsPressurePlate", "temple_pressure_plate", "eob_ruins_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Ceiling", "ruinsEmptyCeilingShaft", "temple_ceiling_shaft", "eob_ruins_ceiling_shaft", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Walls", "ruinsWallIllusion", "temple_secret_door", "eob_ruins_illusion_wall", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Walls", "ruinsWallIllusionWithStatue", "temple_secret_door", "eob_ruins_illusion_wall_statue", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Walls", "ruinsWallIllusionWithRune", "temple_secret_door", "eob_ruins_illusion_wall_rune", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Wall Texts", "ruinsWallWithText", "temple_wall_text_long", "eob_ruins_wall_text", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Decorations", "ruinsWallWithSmallStatue", "temple_mosaic_wall_1", "eob_ruins_wall_small_statue", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Stairs / Ladders", "ruinsLevelUp", "temple_stairs_up", "eob_ruins_stairs_up", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Stairs / Ladders", "ruinsLevelDown", "temple_stairs_down", "eob_ruins_stairs_down", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Alcoves", "ruinsWallWithAlcove", "temple_alcove", "eob_ruins_alcove", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Locks", "ruinsWallWithKeyHole", "lock", "eob_ruins_statue_lock", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Locks", "ruinsWallWithKeyHole2", "lock_ornate", "eob_ruins_ornate_lock", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Buttons / Levers", "ruinsWallWithLever", "lever", "eob_ruins_lever", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Buttons / Levers", "ruinsWallWithStatueLever", "lever", "eob_ruins_statue_lever", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Buttons / Levers", "ruinsWallWithChain", "lever", "eob_ruins_chain_lever", ""); // Chain on the wall -> Type of the lever
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Buttons / Levers", "ruinsWallWithSecretButtonTiny", "temple_secret_button_tiny", "eob_ruins_secret_button_tiny", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Buttons / Levers", "ruinsWallWithButton", "wall_button", "eob_ruins_button", "");
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Firing mechanism", "ruinsWallWithFiringMechanism", "daemon_head", "eob_ruins_dart_firing_pad", ""); // Level 6: Darts
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Portals", "ruinsWallPortalLevelNecklace", "temple_glass_wall_2", "eob_ruins_portal_necklace", ""); // Level 5 - Stone Necklace
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Portals", "ruinsWallPortalLevelScepter", "temple_glass_wall_2", "eob_ruins_portal_scepter", ""); // Level 6 - Stone Scepter
        GrimrockWall.addWall(grimrockWalls, "Ruins", "Portals", "ruinsWallPortalLevelAmulet", "temple_glass_wall_2", "eob_ruins_portal_amulet", ""); // Level 4 - Stone Amulet

        GrimrockWall.addWall(grimrockWalls, "Drow", "Doors", "drowDoorWithButton", "prison_door_metal", "eob_drow_door", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Doors", "drowDoor", "prison_door_metal", "eob_drow_door", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Floor", "drowEmptyPit", "prison_pit", "eob_drow_pit", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Floor", "drowPressurePlate", "prison_pressure_plate", "eob_drow_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Ceiling", "drowEmptyCeilingShaft", "prison_ceiling_shaft", "eob_drow_ceiling_shaft", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Walls", "drowWallIllusionWithSpider", "temple_secret_door", "eob_drow_wall_illusion_with_spider", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Walls", "drowWallIllusion", "prison_secret_door", "eob_drow_wall_illusion", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Walls", "drowWallThrowable", "dungeon_ivy_1", "eob_drow_wall_throwable", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Wall Texts", "drowWallWithText", "prison_wall_text_long", "eob_drow_wall_text", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Wall Texts", "drowWallWithText2", "dungeon_wall_text_long", "eob_drow_wall_text", ""); // It is written, the key lies on the other side.
        GrimrockWall.addWall(grimrockWalls, "Drow", "Stairs / Ladders", "drowLevelUp", "prison_stairs_up", "eob_drow_stairs_up", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Stairs / Ladders", "drowLevelDown", "prison_stairs_down", "eob_drow_stairs_down", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Alcoves", "drowWallWithAlcove", "prison_alcove", "eob_drow_alcove", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Locks", "drowWallWithGemKeyHole", "lock_prison", "eob_drow_lock_gem", ""); // Gem is used as key
        GrimrockWall.addWall(grimrockWalls, "Drow", "Locks", "drowWallWithKeyHole", "lock", "eob_drow_lock_spider", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Locks", "drowWallWithKeyHole2", "lock_ornate", "eob_drow_lock_ornate", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Buttons", "drowWallWithSecretButtonTiny", "prison_secret_button_small", "eob_drow_secret_button_tiny", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Buttons / Levers", "drowWallWithLever", "lever", "eob_drow_lever", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Buttons / Levers", "drowWallWithButton", "wall_button", "eob_drow_button", "");
        GrimrockWall.addWall(grimrockWalls, "Drow", "Firing mechanism", "drowWallWithFiringMechanismFireball", "daemon_head", "eob_drow_fireball_firing_pad", ""); // Level 7: Fireball
        GrimrockWall.addWall(grimrockWalls, "Drow", "Firing mechanism", "drowWallWithFiringMechanismFireball2", "daemon_head", "eob_drow_fireball_firing_pad", ""); // Level 7: Fireball
        GrimrockWall.addWall(grimrockWalls, "Drow", "Firing mechanism", "drowWallWithFiringMechanismDartsL9", "daemon_head", "eob_drow_dart_firing_pad", ""); // Level 9: Darts
        GrimrockWall.addWall(grimrockWalls, "Drow", "Firing mechanism", "drowWallWithFiringMechanismDartsL8", "daemon_head", "eob_drow_dart_firing_pad", ""); // Level 7: Darts
        GrimrockWall.addWall(grimrockWalls, "Drow", "Firing mechanism", "drowWallWithFiringMechanismMagicMissile", "daemon_head", "eob_drow_magic_missile_firing_pad", ""); // Level 9: MagicMissile
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelCross", "temple_glass_wall_2", "eob_drow_portal_cross", ""); // Level 7 -  Stone Cross
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelNecklace", "temple_glass_wall_2", "eob_drow_portal_necklace", ""); // Level 7 -  Stone Necklace
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelDaggerL7", "temple_glass_wall_2", "eob_drow_portal_dagger", ""); // Level 7 -  Stone Dagger
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelDaggerL9", "temple_glass_wall_2", "eob_drow_portal_dagger", ""); // Level 9 -  Stone Dagger
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelAmulet", "temple_glass_wall_2", "eob_drow_portal_amulet", ""); // Level 7 -  Stone Amulet
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelScepter", "temple_glass_wall_2", "eob_drow_portal_scepter", ""); // Level 8 -  Stone Scepter
        GrimrockWall.addWall(grimrockWalls, "Drow", "Portals", "drowWallPortalLevelGem", "temple_glass_wall_2", "eob_drow_portal_gem", ""); // Level 7 -  Gem

        // Hive levels
        GrimrockWall.addWall(grimrockWalls, "Hive", "Doors", "hiveDoorWithButton", "dungeon_door_metal", "eob_hive_door", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Doors", "hiveDoor", "prison_door_metal", "eob_hive_door", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Floor", "hiveEmptyPit", "dungeon_pit", "eob_hive_pit", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Floor", "hivePressurePlate", "dungeon_pressure_plate", "eob_hive_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Ceiling", "hiveEmptyCeilingShaft", "dungeon_ceiling_shaft", "eob_hive_ceiling_shaft", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Wall Texts", "hiveWallWithText", "dungeon_wall_text_long", "eob_hive_wall_text", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Decorations", "hiveWallWithManacles", "prison_bench", "eob_hive_prison_manacles", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Decorations", "hiveWallWithRift", "dungeon_ivy_1", "eob_hive_wall_rift", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Decorations", "hiveWallWithStar", "daemon_head", "eob_hive_wall_star", ""); // Level 11: Celestial star
        GrimrockWall.addWall(grimrockWalls, "Hive", "Stairs / Ladders", "hiveLevelUp", "dungeon_stairs_up", "eob_hive_stairs_up", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Stairs / Ladders", "hiveLevelDown", "dungeon_stairs_down", "eob_hive_stairs_down", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Alcoves", "hiveWallWithAlcove", "dungeon_alcove", "eob_hive_alcove", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Locks", "hiveWallWithKeyHole", "lock", "eob_hive_lock", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithLeverUp", "lever", "eob_hive_lever", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithLeverDown", "lever", "eob_hive_lever", "\t:setLeverState(\"activated\")");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithSwitch", "lever", "eob_hive_switch", ""); // Can be pressed or released
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithSecretButtonSmall", "dungeon_secret_button_small", "eob_hive_secret_button_small", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithSecretButtonTiny", "dungeon_secret_button_small", "eob_hive_secret_button_tiny", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithButton", "wall_button", "eob_hive_button", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Buttons / Levers", "hiveWallWithButton2", "wall_button", "eob_hive_button2", "");
        GrimrockWall.addWall(grimrockWalls, "Hive", "Portals", "hiveWallPortalLevelCross", "temple_glass_wall_2", "eob_hive_portal_cross", ""); // Level 11 - Stone Cross
        GrimrockWall.addWall(grimrockWalls, "Hive", "Portals", "hiveWallPortalLevelOrb", "temple_glass_wall_2", "eob_hive_portal_orb", ""); // Level 11 - Stone Orb
        GrimrockWall.addWall(grimrockWalls, "Hive", "Portals", "hiveWallPortalLevelScepter", "temple_glass_wall_2", "eob_hive_portal_scepter", ""); // Level 10 - Stone Scepter
        GrimrockWall.addWall(grimrockWalls, "Hive", "Portals", "hiveWallPortalLevelRing", "temple_glass_wall_2", "eob_hive_portal_ring", ""); // Level 10 - Stone Ring

        // Sanctum levels
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Doors", "sanctumDoorWithButton", "temple_door_metal", "eob_sanctum_door", "\t:addPullChain()");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Doors", "sanctumDoor", "temple_door_metal", "eob_sanctum_door", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Floor", "sanctumPressurePlate", "temple_pressure_plate", "eob_sanctum_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Floor", "sanctumSpikeTrap", "temple_floor_drainage", "eob_sanctum_spike_trap", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Decorations", "sanctumPedestal", "altar", "eob_sanctum_pedestal", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Decorations", "sanctumPedestalWithEye", "altar", "eob_sanctum_pedestal_eye", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Decorations", "sanctumPedestalWithEyeDetector", "altar", "eob_sanctum_pedestal_eye", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Decorations", "sanctumWallWithLamp", "torch_holder", "eob_sanctum_wall_lamp", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Decorations", "sanctumWallWithLampSmoke", "torch_holder", "eob_sanctum_wall_lamp_smoke", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Wall Texts", "sanctumWallWithText", "temple_wall_text_long", "eob_sanctum_text", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Alcoves", "sanctumWallWithAlcove", "temple_alcove", "eob_sanctum_alcove", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Locks", "sanctumWallWithKeyHole", "lock", "eob_sanctum_skull_lock", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Buttons / Levers", "sanctumWallWithButton", "wall_button", "eob_sanctum_button", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Buttons / Levers", "sanctumWallWithButton2", "wall_button", "eob_sanctum_button2", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Buttons / Levers", "sanctumWallWithSecretButtonSmall", "temple_secret_button_small", "eob_sanctum_secret_button_small", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Buttons / Levers", "sanctumWallWithSecretButtonTiny", "temple_secret_button_small", "eob_sanctum_secret_button_tiny", "");
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Firing mechanism", "sanctumWallWithFiringMechanism", "daemon_head", "eob_sanctum_fireball_firing_pad", ""); // Level 12: Fireball
        GrimrockWall.addWall(grimrockWalls, "Sanctum", "Portals", "sanctumWallPortalLevelOrb", "temple_glass_wall_2", "eob_sanctum_portal_orb", ""); // Level 12 - missing Stone Orb
    }

    public void addLevel(LevelParser levelParser) {
        levels.put((long) levelParser.levelId, levelParser);
    }

    public void addLevelInfo(InfFile infFile) {
        levelsInfo.put((long) infFile.levelId, infFile);
    }

    public void exportIntoGrimrock(boolean createFilePerLevel) {
        unusedExternalChanges.clear();
        unusedExternalChanges.addAll(externalChanges.keySet());

        exportItems(eobGlobalData.itemParser, settings.dstPath + "/" + ITEM_FILE);
        exportMonsters(levelsInfo.values(), settings.dstPath + "/" + MONSTER_FILE);
        exportObjects(settings.dstPath + "/" + OBJECTS_FILE);

        if (createFilePerLevel) {
            for (Long levelId : levels.keySet()) {
                try {
                    FileWriter outFile = new FileWriter(String.format(settings.dstPath + "/" + LEVEL_FILE, levelId));
                    PrintWriter out = new PrintWriter(outFile);
                    out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));

                    exportGrimrockLevel(levels.get(levelId), levelsInfo.get(levelId), out);

                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                FileWriter outFile = new FileWriter(settings.dstPath + "/" + DUNGEON_FILE);
                PrintWriter out = new PrintWriter(outFile);
                out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));
                for (Long levelId : levels.keySet()) {
                    exportGrimrockLevel(levels.get(levelId), levelsInfo.get(levelId), out);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (unusedExternalChanges.size() > 0) {
            EobLogger.println("[WARNING] Next external changes was not used: ");
            for (String unusedExternalChange : unusedExternalChanges) {
                EobLogger.println(unusedExternalChange);
            }
        }
    }

    private int getFacing(InSquarePositionType inSquarePositionType) {
        switch (inSquarePositionType) {
            default:
                EobLogger.println("[Error]: Unsupported Grimrock position: " + inSquarePositionType.name());
            case North:
            case NW:
                return 0;
            case East:
            case NE:
                return 1;
            case South:
            case SE:
                return 2;
            case West:
            case SW:
                return 3;
        }
    }

    private void prepareExternalChanges(List<String> externalChangesList) {
        for (String change : externalChangesList) {
            String[] words = change.split(" ");
            externalChanges.put(words[1], words[0]);
            if (words.length > 2) {
                externalElementName.put(words[1], words[2]);
            }
        }
    }


    //--------------------
    //--- Items Export ---
    //--------------------

    private void exportItems(ItemParser itemParser, String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);

            out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));
            out.println();


            Map<Long, Set<Item>> itemsByLevel = new TreeMap<Long, Set<Item>>();

            for (int level = settings.from; level <= settings.to; level++) {
                Set<ItemObject> levelItemObjects = new HashSet<ItemObject>();
                Set<Item> levelItems = new LinkedHashSet<Item>();
                for (ItemObject item : itemParser.getItemSet()) {
                    if (item.level == level) {
                        levelItems.add(item.item);
                        levelItemObjects.add(item);
                    }
                }
                itemsByLevel.put((long) level, levelItems);
            }

            Set<Item> levelItems = new LinkedHashSet<Item>();
            for (ItemObject item : itemParser.getItemSet()) {
                if (item.level == 0 || item.level > 99) {
                    levelItems.add(item.item);
                }
            }
            itemsByLevel.put(0L, levelItems);

            // Export by levels
            for (Map.Entry<Long, Set<Item>> level : itemsByLevel.entrySet()) {
                out.println("---------------");
                out.println("--- Level " + level.getKey() + " ---");
                out.println("---------------");
                out.println("");
                for (Item item : level.getValue()) {
                    createItem(item, out, false);
                    createItem(item, out, true);
                }
            }

            out.close();
        } catch (java.io.IOException e) {
            EobLogger.println("Error while exporting:");
            e.printStackTrace();
        }
    }

    private void spawnItemOnFloor(ItemObject item, PrintWriter out) {
        String elementType = namePrefix + item.item.getElementType(item.identified);
        String elementName = getNextNameByIndex(itemNameNextIndex, elementType);

        String itemString = String.format("spawn(\"%s\", %d, %d, %d, \"%s\")", elementType, item.x, item.y, getFacing(item.inSquarePosition), elementName);
        if (settings.debug) {
            if (item.item.itemType.unknownType) {
                itemString += String.format(" -- %s type=%s subtype=%s", item.item.getDescription(item.identified), item.item.itemType.itemTypeId, item.item.itemSubType.id);
            }
        }

        out.println(itemString);
    }

    private void spawnItemInAlcove(ItemObject item, PrintWriter out) {
        String elementType = namePrefix + item.item.getElementType(item.identified);

        String itemString = String.format("\t:addItem(spawn(\"%s\"))", elementType);
        if (settings.debug) {
            itemString += String.format(" -- %s ", item.item.getDescription(item.identified));
            if (item.item.itemType.unknownType) {
                itemString += String.format(" type=%s subtype=%s", item.item.itemType.itemTypeId, item.item.itemSubType.id);

            }
        }

        out.println(itemString);
    }

    private void createItem(Item item, PrintWriter out, boolean identified) {
        out.println("cloneObject {");
        out.println(String.format("\tname = \"%s\",%s", namePrefix + item.getElementType(identified), identified ? "" : "  -- " + item.getDescription(identified)));
        String baseObject = defaultItems.get(item.itemType.elementType);
        out.println(String.format("\tbaseObject = \"%s\",", baseObject != null ? baseObject : DEFAULT_BASE_OBJECT));
        out.println(String.format("\tuiName = \"%s\",", item.getDescription(identified)));
        if (item.initialCountValue > 0) {
            out.println(String.format("\tcharges = %d,", item.initialCountValue));
        }

        String slot = "";
        if (item.itemType.itemSlotSupported.contains(ItemSlotType.Armour)) {
            slot = "\"Torso\"";
        } else if (item.itemType.itemSlotSupported.contains(ItemSlotType.Helmet)) {
            slot = "\"Head\"";
        } else if (item.itemType.itemSlotSupported.contains(ItemSlotType.Bracers)) {
            slot = "\"Bracers\"";
        } else if (item.itemType.itemSlotSupported.contains(ItemSlotType.Necklace)) {
            slot = "\"Neck\"";
        } else if (item.itemType.itemSlotSupported.contains(ItemSlotType.Boots)) {
            slot = "\"Feet\"";
        }
        if (slot.length() > 0) {
            out.println(String.format("\tslot = %s,", slot));
        }

        if (item.itemType.addArmorClass != 0) {
            out.println(String.format("\tprotection = %d,", Math.abs(item.itemType.addArmorClass) * 2));
        }

        out.println("}");
        out.println("");
    }

    private void spawnPossibleItemsIntoAlcove(PrintWriter out, Boolean containCompartment, int level, int x, int y) {
        if (!containCompartment) {
            return;
        }
        for (ItemObject itemObject : eobGlobalData.itemParser.getItemSet()) {
            if (itemObject.level == level && itemObject.x == x && itemObject.y == y && itemObject.inSquarePosition.equals(InSquarePositionType.Alcove)) {
                spawnItemInAlcove(itemObject, out);
            }
        }
    }

    //----------------------
    //--- Monster Export ---
    //----------------------

    private void exportMonsters(Collection<InfFile> values, String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);

            out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));
            out.println("");

            if (settings.generateDefaultStructures) {
                out.println("--======================--");
                out.println("--== Default monsters ==--");
                out.println("--======================--");
                out.println("");

                for (Map.Entry<String, String> monsterEntry : defaultMonsters.entrySet()) {

                    out.println("cloneObject {");
                    out.println(String.format("\tname = \"%s\",", monsterEntry.getKey()));
                    out.println(String.format("\tbaseObject = \"%s\",", monsterEntry.getValue()));
                    out.println("}");
                    out.println("");
                }
            }

            out.println("--========================--");
            out.println("--== Generated monsters ==--");
            out.println("--========================--");
            out.println("");

            Map<String, String> alreadyDefinedMonsterGroups = new HashMap<String, String>();

            for (InfFile infFile : values) {
                if (infFile.levelId < settings.from || infFile.levelId > settings.to) {
                    continue;
                }
                out.println("----------------");
                out.println(String.format("--- Level %02d ---", infFile.levelId));
                out.println("----------------");
                out.println("");

                // Sort the monsters by position
                Map<String, List<MonsterObject>> monstersInSquare = new HashMap<String, List<MonsterObject>>();
                for (MonsterObject monsterObject : infFile.monsterObjects) {
                    String square = monsterObject.x + "_" + monsterObject.y;
                    List<MonsterObject> monsters = monstersInSquare.get(square);
                    if (monsters == null) {
                        monsters = new ArrayList<MonsterObject>();
                        monstersInSquare.put(square, monsters);
                    }
                    monsters.add(monsterObject);
                }

                // Create monster groups if necessary
                for (List<MonsterObject> monsterObjects : monstersInSquare.values()) {
                    createMonsterGroup(monsterObjects, alreadyDefinedMonsterGroups, out);
                }
            }

            out.close();
        } catch (java.io.IOException e) {
            EobLogger.println("Error while exporting:");
            e.printStackTrace();
        }
    }

    private void spawnMonsters(List<MonsterObject> monsters, PrintWriter out) {
        MonsterObject monster = monsters.get(0);
        String monstersPos = monster.levelId + "_" + monster.x + "_" + monster.y;
        String monsterType = monstersByPosition.get(monstersPos);
        int facing = 0;

        if (monsters.size() != 1 && monsters.size() != 2 && monsters.size() != 4) {
            EobLogger.println(String.format("Unsupported number of monsters: %d [L: %d, X: %d, Y:%d]", monsters.size(), monster.levelId, monster.x, monster.y));
            return;
        }

        String monsterName = getNextNameByIndex(monsterNameNextIndex, monsterType);
        out.println(String.format("spawn(\"%s\", %d, %d, %d, \"%s\")", monsterType, monster.x, monster.y, facing, monsterName));
    }

    private void createMonsterGroup(List<MonsterObject> monsters, Map<String, String> alreadyDefinedMonsterGroups, PrintWriter out) {
        if (monsters.size() < 1) {
            return;
        }

        String monstersPos = monsters.get(0).levelId + "_" + monsters.get(0).x + "_" + monsters.get(0).y;

        List<MonsterGroup> sameMonsters = new ArrayList<MonsterGroup>();

        // Group monsters by the same type
        for (MonsterObject monster : monsters) {
            String pocketItem50Name = monster.pocketItem50 == null ? "" :
                    namePrefix + monster.pocketItem50.item.getElementType(monster.pocketItem50.identified);
            String pocketItemName = monster.pocketItem == null ? "" :
                    namePrefix + monster.pocketItem.item.getElementType(monster.pocketItem.identified);

            MonsterGroup findGroup = null;
            for (MonsterGroup sameMonster : sameMonsters) {
                if (sameMonster.pocketItem50Name.equals(pocketItem50Name) && sameMonster.pocketItemName.equals(pocketItemName)) {
                    findGroup = sameMonster;
                    break;
                }
            }
            if (findGroup == null) {
                findGroup = new MonsterGroup(monster, pocketItem50Name, pocketItemName);
                sameMonsters.add(findGroup);
            }

            findGroup.count++;
        }

        // Prepare monster group Id
        sortMonsterGroups(sameMonsters);
        String monsterGroupId = getMonsterGroupId(monsters.size(), sameMonsters);
        if (alreadyDefinedMonsterGroups.get(monsterGroupId) != null) {
            monstersByPosition.put(monstersPos, alreadyDefinedMonsterGroups.get(monsterGroupId) + (monsters.size() < 2 ? "" : "_group"));
            return;
        }

        // Prepare basic monster name
        final String monsterName;

        // Monster group with the same loot
        if (sameMonsters.size() == 1) {
            MonsterGroup monsterGroup = sameMonsters.get(0);
            if (monsterGroup.pocketItem50Name.length() != 0 || monsterGroup.pocketItemName.length() != 0) {
                monsterName = getNextNameByIndex(monsterGroupNextIndex, monsterGroup.monsterObject.monster.monsterName + monsters.size());

                out.println("cloneObject {");
                out.println(String.format("\tname = \"%s\",", monsterName));
                out.println(String.format("\tbaseObject = \"%s\",", monsterGroup.monsterObject.monster.monsterName));
                out.print("\tlootDrop = {");
                if (monsterGroup.pocketItem50Name.length() > 0) {
                    out.print(String.format("50, \"%s\"", monsterGroup.pocketItem50Name));
                }
                if (monsterGroup.pocketItemName.length() > 0) {
                    if (monsterGroup.pocketItem50Name.length() > 0) {
                        out.print(", ");
                    }
                    out.print(String.format("100, \"%s\"", monsterGroup.pocketItemName));
                }
                out.println("},");
                out.println("}");
                out.println("");

                alreadyDefinedMonsterGroups.put(monsterGroupId, monsterName);
            } else {
                monsterName = monsterGroup.monsterObject.monster.monsterName;
            }

        } else {
            // Group with the different loot
            monsterName = getNextNameByIndex(monsterGroupNextIndex, sameMonsters.get(0).monsterObject.monster.monsterName + monsters.size());
            MonsterGroup monsterGroup = sameMonsters.get(0);

            out.println("cloneObject {");
            out.println(String.format("\tname = \"%s\",", monsterName));
            out.println(String.format("\tbaseObject = \"%s\",", monsterGroup.monsterObject.monster.monsterName));
            out.println("\tlootDrop = {},");
            out.println("\tonDie = function(monster)");
            out.println(String.format("\t\tif findEntity(\"%s_c\") == nil then", monsterName));
            out.println(String.format("\t\t\tspawn(\"counter\", %d, 0, 0, 0, \"%s_c\")", monsterGroup.monsterObject.levelId, monsterName));
            out.println("\t\tend");

            int count = 0;
            for (MonsterGroup sameMonster : sameMonsters) {
                if (count == 0) {
                    out.print("\t\t");
                } else {
                    out.print("\t\telse");
                }
                out.println(String.format("if %s_c:getValue() %% %d < %d then", monsterName, monsters.size(), count + sameMonster.count));
                count += sameMonster.count;

                if (sameMonster.pocketItem50Name.length() > 0) {
                    String elementType = namePrefix + sameMonster.monsterObject.pocketItem50.item.getElementType(sameMonster.monsterObject.pocketItem50.identified);
                    String elementName = elementType + "_" + sameMonster.monsterObject.x + "_" + sameMonster.monsterObject.y + "_";

                    out.println("\t\t\tif math.random(0,99) < 50 then");
                    out.println(String.format("\t\t\t\tspawn(\"%s\", monster.level, monster.x, monster.y, monster.facing, \"%s\"..%s_c:getValue())", elementType, elementName, monsterName));
                    out.println("\t\t\tend");
                }
                if (sameMonster.pocketItemName.length() > 0) {
                    String elementType = namePrefix + sameMonster.monsterObject.pocketItem.item.getElementType(sameMonster.monsterObject.pocketItem.identified);
                    String elementName = getNextNameByIndex(itemNameNextIndex, elementType);

                    out.println(String.format("\t\t\tspawn(\"%s\", monster.level, monster.x, monster.y, monster.facing, \"%s\"..%s_c:getValue())", elementType, elementName, monsterName));
                }
            }
            out.println("\t\tend");

            out.println(String.format("\t\t%s_c:increment()", monsterName));
            out.println("\tend,");
            out.println("}");
            out.println("");
        }

        monstersByPosition.put(monstersPos, monsterName + (monsters.size() < 2 ? "" : "_group"));

        if (monsters.size() < 2) {
            return;
        }

        // Define group
        out.println("defineObject {");
        out.println(String.format("\tname = \"%s_group\",", monsterName));
        out.println("\tclass = \"MonsterGroup\",");
        out.println(String.format("\tmonsterType = \"%s\",", monsterName));
        out.println(String.format("\tcount = %d,", monsters.size()));
        out.println("}");
        out.println("");
    }

    private void sortMonsterGroups(List<MonsterGroup> monsterGroups) {
        Collections.sort(monsterGroups, new Comparator<MonsterGroup>() {
            @Override
            public int compare(MonsterGroup monsterGroup1, MonsterGroup monsterGroup2) {
                int result = monsterGroup1.monsterObject.monster.monsterName.compareTo(monsterGroup2.monsterObject.monster.monsterName);
                if (result != 0) {
                    return result;
                }
                result = monsterGroup1.pocketItem50Name.compareTo(monsterGroup2.pocketItem50Name);
                if (result != 0) {
                    return result;
                }
                return monsterGroup1.pocketItemName.compareTo(monsterGroup2.pocketItemName);
            }
        });
    }

    private String getMonsterGroupId(int groupSize, List<MonsterGroup> monsterGroups) {
        String monsterGroupId = "";

        for (MonsterGroup monsterGroup : monsterGroups) {
            monsterGroupId += (monsterGroupId.length() == 0 ? "" : "|") + monsterGroup.monsterObject.monster.monsterName + groupSize +
                    "|" + monsterGroup.pocketItem50Name + "|" + monsterGroup.pocketItemName;
        }

        return monsterGroupId;
    }

    //---------------------
    //--- Levels Export ---
    //---------------------

    private void exportObjects(String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);

            out.println(String.format("-- This file has been generated by EobConverter v%s", EobConverter.CONVERTER_VERSION));
            out.println("");

            if (settings.generateDefaultStructures) {
                Map<String, Map<String, List<GrimrockWall>>> objects = new LinkedHashMap<String, Map<String, List<GrimrockWall>>>();
                for (GrimrockWall grimrockWall : grimrockWalls.values()) {
                    Map<String, List<GrimrockWall>> wallGroupMap = objects.get(grimrockWall.wallGroup);
                    if (wallGroupMap == null) {
                        wallGroupMap = new LinkedHashMap<String, List<GrimrockWall>>();
                        objects.put(grimrockWall.wallGroup, wallGroupMap);
                    }

                    List<GrimrockWall> walls = wallGroupMap.get(grimrockWall.typeGroup);
                    if (walls == null) {
                        walls = new ArrayList<GrimrockWall>();
                        wallGroupMap.put(grimrockWall.typeGroup, walls);
                    }

                    walls.add(grimrockWall);
                }


                for (Map.Entry<String, Map<String, List<GrimrockWall>>> wallGroupEntry : objects.entrySet()) {
                    String row = "";
                    for (int i = 0; i < wallGroupEntry.getKey().length(); i++) {
                        row += "=";
                    }
                    out.println(String.format("--===%s===--", row));
                    out.println(String.format("--== %s ==--", wallGroupEntry.getKey()));
                    out.println(String.format("--===%s===--", row));
                    out.println("");

                    for (Map.Entry<String, List<GrimrockWall>> wallTypeEntry : wallGroupEntry.getValue().entrySet()) {
                        row = "";
                        for (int i = 0; i < wallTypeEntry.getKey().length(); i++) {
                            row += "-";
                        }
                        out.println(String.format("----%s----", row));
                        out.println(String.format("--- %s ---", wallTypeEntry.getKey()));
                        out.println(String.format("----%s----", row));
                        out.println("");

                        for (GrimrockWall grimrockWall : wallTypeEntry.getValue()) {
                            out.println("cloneObject {");
                            out.println(String.format("\tname = \"%s\",", grimrockWall.grimrockType));
                            out.println(String.format("\tbaseObject = \"%s\",", grimrockWall.originalGrimrockType));
                            out.println("}");
                            out.println("");

                        }
                    }
                }
            }

            out.close();
        } catch (java.io.IOException e) {
            EobLogger.println("Error while exporting:");
            e.printStackTrace();
        }
    }

    private void exportGrimrockLevel(LevelParser levelParser, InfFile infFile, PrintWriter out) {
        EobLogger.println("Writing level " + levelParser.levelId + "...");

        GrimrockLevelSettings levelSettings = null;
        for (GrimrockLevelSettings levelInfo : levelsSettings) {
            if (levelParser.levelId == levelInfo.levelId) {
                levelSettings = levelInfo;
            }
        }

        // Write dungeon
        out.println("");
        out.println(String.format("--- level %d ---", levelParser.levelId));
        out.println("");
        if (levelSettings != null) {
            out.println(String.format("mapName(\"%s\")", levelSettings.levelName));
            out.println(String.format("setWallSet(\"%s\")", levelSettings.wallSet));
            out.println(String.format("playStream(\"%s\")", levelSettings.playStream));
        }
        out.println("mapDesc([[");
        for (int y = 0; y < levelParser.height; y++) {
            for (int x = 0; x < levelParser.width; x++) {
                if (levelParser.level[x][y].isUnknownBlock()) {
                    out.print("?");
                } else if (levelParser.level[x][y].isSolidBlock()) {
                    out.print("#");
                } else {
                    out.print(".");
                }
            }
            out.println("");
        }
        out.println("]])");

        // Write walls
        for (int y = 0; y < levelParser.height; y++) {
            for (int x = 0; x < levelParser.width; x++) {
                Square square = levelParser.level[x][y];

                Set<Wall> usedWalls = new HashSet<Wall>();

                // Special walls
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_N"))) {
                    unusedExternalChanges.remove(levelParser.levelId + "_" + x + "_" + y + "_N");
                    spawnWall(levelParser, out, x, y, square.north, 0, "Wall", "N");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_E"))) {
                    unusedExternalChanges.remove(levelParser.levelId + "_" + x + "_" + y + "_E");
                    spawnWall(levelParser, out, x, y, square.east, 1, "Wall", "E");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_S"))) {
                    unusedExternalChanges.remove(levelParser.levelId + "_" + x + "_" + y + "_S");
                    spawnWall(levelParser, out, x, y, square.south, 2, "Wall", "S");
                }
                if ("F".equals(externalChanges.get(levelParser.levelId + "_" + x + "_" + y + "_W"))) {
                    unusedExternalChanges.remove(levelParser.levelId + "_" + x + "_" + y + "_W");
                    spawnWall(levelParser, out, x, y, square.west, 3, "Wall", "W");
                }

                // Doors
                Wall squareType = square.getDoor(usedWalls, settings.debugWalls);
                if (squareType != null) {
                    spawnWall(levelParser, out, x, y, squareType, square.getDoorFacing(), "Door", square.getDoorFacing() == 1 ? "EW" : "NS");
                }

                // In Square
                squareType = square.getInSquare(usedWalls);
                if (squareType != null) {
                    int facing = 0;
                    if ((x < levelParser.level.length - 1) && !levelParser.level[x + 1][y].isSolidBlock()) {
                        facing = 3;
                    }
                    if (x > 0 && !levelParser.level[x - 1][y].isSolidBlock()) {
                        facing = 1;
                    }
                    if (y > 0 && !levelParser.level[x][y - 1].isSolidBlock()) {
                        facing = 2;
                    }
                    spawnWall(levelParser, out, x, y, squareType, facing, "InSquare", "?");

                }

                if (square.north.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.north) &&
                        (!square.north.wallType.equals(WallType.Wall) || (square.north.wallType.equals(WallType.Wall) && !levelParser.level[x][y - 1].isSolidBlock()))) {
                    if (square.north.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.north, 2, "Wall", "N");
                    } else {
                        spawnWall(levelParser, out, x, y - 1, square.north, 2, "Wall", "S");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.north.containCompartment, levelParser.levelId, x, y);
                }
                if (square.east.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.east) &&
                        (!square.east.wallType.equals(WallType.Wall) || (square.east.wallType.equals(WallType.Wall) && !levelParser.level[x + 1][y].isSolidBlock()))) {
                    if (square.east.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.east, 3, "Wall", "E");
                    } else {
                        spawnWall(levelParser, out, x + 1, y, square.east, 3, "Wall", "W");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.east.containCompartment, levelParser.levelId, x, y);
                }
                if (square.south.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.south) &&
                        (!square.south.wallType.equals(WallType.Wall) || (square.south.wallType.equals(WallType.Wall) && !levelParser.level[x][y + 1].isSolidBlock()))) {
                    if (square.south.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.south, 0, "Wall", "S");
                    } else {
                        spawnWall(levelParser, out, x, y + 1, square.south, 0, "Wall", "N");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.south.containCompartment, levelParser.levelId, x, y);
                }
                if (square.west.gameSupportType.contains(GameSupportType.Grimrock) && !usedWalls.contains(square.west) &&
                        (!square.west.wallType.equals(WallType.Wall) || (square.west.wallType.equals(WallType.Wall) && !levelParser.level[x - 1][y].isSolidBlock()))) {
                    if (square.west.wallType.equals(WallType.SquarePart)) {
                        spawnWall(levelParser, out, x, y, square.west, 1, "Wall", "W");
                    } else {
                        spawnWall(levelParser, out, x - 1, y, square.west, 1, "Wall", "E");
                    }
                    spawnPossibleItemsIntoAlcove(out, square.west.containCompartment, levelParser.levelId, x, y);
                }
            }
        }

        // Write items
        for (ItemObject itemObject : eobGlobalData.itemParser.getItemSet()) {
            if (itemObject.level == levelParser.levelId && !itemObject.inSquarePosition.equals(InSquarePositionType.Alcove)) {
                spawnItemOnFloor(itemObject, out);
            }
        }

        // Write monsters
        Map<String, List<MonsterObject>> monstersInSquare = new HashMap<String, List<MonsterObject>>();
        for (MonsterObject monsterObject : infFile.monsterObjects) {
            String square = monsterObject.x + "_" + monsterObject.y;
            List<MonsterObject> monsters = monstersInSquare.get(square);
            if (monsters == null) {
                monsters = new ArrayList<MonsterObject>();
                monstersInSquare.put(square, monsters);
            }
            monsters.add(monsterObject);
        }
        for (List<MonsterObject> monsterObjects : monstersInSquare.values()) {
            spawnMonsters(monsterObjects, out);
        }

        // Prepare additive data
        int row = 1;
        Map<Integer, Integer> positionMap = new LinkedHashMap<Integer, Integer>();
        for (EobCommand command : infFile.script) {
            positionMap.put(command.originalPos, row);
            row++;
        }
        Map<Integer, EobScriptFunction> scriptFunctionMap = new LinkedHashMap<Integer, EobScriptFunction>();
        for (EobScriptFunction function : infFile.scriptFunctions) {
            scriptFunctionMap.put(function.addressStart, function);
        }

        // Export eob scripts
        VisitorGlobalData visitorGlobalData = new VisitorGlobalData(positionMap, scriptFunctionMap, eobGlobalData);
        if (settings.exportEobScripts) {
            PrintWriter output = null;
            try {
                String levelScriptFile = String.format(settings.dstPath + "/" + EobConverter.LEVEL_SCRIPT_FILE, levelParser.levelId);
                if (settings.debug) {
                    EobLogger.println("Writing text file with name: " + levelScriptFile + " ...");
                }
                FileWriter outFile = new FileWriter(levelScriptFile);
                output = new PrintWriter(outFile);

                CommandPrintVisitor visitor = new CommandPrintVisitor(output, visitorGlobalData, settings.scriptDebug, "\"", "\n");
                for (EobScriptFunction function : infFile.scriptFunctions) {
                    visitor.parseFunction(function, infFile.script);
                }
                for (EobTrigger trigger : infFile.triggers) {
                    visitor.parseTrigger(trigger, infFile.script);
                }
            } catch (FileNotFoundException ex) {
                EobLogger.println("File not found.");
            } catch (IOException ex) {
                EobLogger.println(ex.getMessage());
                ex.printStackTrace();
            } finally {
                if (output != null) {
                    output.close();
                }
            }
        }

        // Prepare lua scripts
        if (settings.addEobScriptIntoLua) {
            CommandPrintVisitor visitor = new CommandPrintVisitor(out, visitorGlobalData, settings.scriptDebug, "\\\"", "\\\n");
            EobTrigger trigger00 = null;
            for (EobTrigger trigger : infFile.triggers) {
                if (trigger.x == 0 && trigger.y == 0) {
                    trigger00 = trigger;
                }
            }
            if (trigger00 != null || infFile.scriptFunctions.size() > 0) {
                out.println(String.format("spawn(\"script_entity\", 0, 0, 1, \"script_entity_%s_0_0\")", levelParser.levelId));
                out.println("\t:setSource(\"--[[\\");
                for (EobScriptFunction function : infFile.scriptFunctions) {
                    visitor.parseFunction(function, infFile.script);
                }
                if (trigger00 != null) {
                    visitor.parseTrigger(trigger00, infFile.script);
                }
                out.println("--]]\")");
            }

            for (EobTrigger trigger : infFile.triggers) {
                if (trigger.x != 0 || trigger.y != 0) {
                    String levelText = levelParser.levelId + "_" + trigger.x + "_" + trigger.y;
                    out.println(String.format("spawn(\"script_entity\", %d, %d, 1, \"script_entity_%s\")", trigger.x, trigger.y, levelText));
                    out.println("\t:setSource(\"--[[\\");
                    visitor.parseTrigger(trigger, infFile.script);
                    out.println("--]]\")");
                }
            }
        }
    }

    private void spawnWall(LevelParser levelParser, PrintWriter out, int x, int y, Wall wall, int facing, String text, String direction) {
        if (!wall.internalName.equals("")) {
            GrimrockWall grimrockWall = grimrockWalls.get(wall.internalName);
            if (grimrockWall == null) {
                EobLogger.println("[ERROR] Wall " + wall.internalName + " haven't Grimrock wall equivalent!");
                return;
            }

            String positionName = levelParser.levelId + "_" + x + "_" + y;
            String name = grimrockWall.grimrockType + "_" + positionName;
            if (direction.equals("N") || direction.equals("E") || direction.equals("S") || direction.equals("W")) {
                name += "_" + direction;
                positionName += "_" + direction;
            }

            String externalFilledWall = externalChanges.get(positionName);
            if (externalFilledWall != null && externalFilledWall.equals("F")) {
                unusedExternalChanges.remove(positionName);
                if (wall.wallType.isSolid()) {
                    String elementName = externalElementName.get(positionName);
                    out.println("spawn(\"" + (elementName == null ? "???" : elementName) + "\", " + x + ", " + y + ", " + facing + ", \"wall_" + positionName + "\")");
                }
            }


            String externalChange = externalChanges.get(name);
            if (externalChange != null) {
                if (externalChange.equals("R")) {
                    unusedExternalChanges.remove(name);
                    return;
                }

                if (wall.wallType.equals(WallType.DoorPart)) {
                    unusedExternalChanges.remove(name);
                    facing = getFacing(InSquarePositionType.valueByString(externalChange));
                }
            }

            out.println("spawn(\"" + grimrockWall.grimrockType + "\", " + x + ", " + y + ", " + facing + ", \"" + name + "\")");
            if (!grimrockWall.grimrockProperties.equals("")) {
                out.println(grimrockWall.grimrockProperties);
            }
        } else {
            EobLogger.println(text + " type haven't defined elementType: " + wall.internalName + " [" + x + "," + y + "," + direction + "] ");
        }
    }

    //-------------------------
    //--- Grimrock Settings ---
    //-------------------------

    class GrimrockLevelSettings {
        private Long levelId;
        String levelName;
        String wallSet;
        String playStream;

        public GrimrockLevelSettings(Long levelId, String levelName, String wallSet, String playStream) {
            this.levelId = levelId;
            this.levelName = levelName;
            this.wallSet = wallSet;
            this.playStream = playStream;
        }
    }

    static class GrimrockWall {
        public final String wallGroup;
        public final String typeGroup;
        public final String internalName;
        public final String originalGrimrockType;
        public final String grimrockType;
        public final String grimrockProperties;

        private GrimrockWall(String wallGroup, String typeGroup, String internalName, String originalGrimrockType, String grimrockType, String grimrockProperties) {
            this.wallGroup = wallGroup;
            this.typeGroup = typeGroup;
            this.internalName = internalName;
            this.originalGrimrockType = originalGrimrockType;
            this.grimrockType = grimrockType;
            this.grimrockProperties = grimrockProperties;
        }

        public static void addWall(Map<String, GrimrockWall> walls, String wallGroup, String typeGroup, String internalName, String originalGrimrockType, String grimrockType, String grimrockProperties) {
            walls.put(internalName, new GrimrockWall(wallGroup, typeGroup, internalName, originalGrimrockType, grimrockType, grimrockProperties));
        }
    }

    //-----------------------
    //--- Index functions ---
    //-----------------------

    public String getNextNameByIndex(Map<String, Long> indexMap, String indexName) {
        Long index = indexMap.get(indexName);
        if (index == null) {
            index = 1L;
        }
        String result = indexName + "_" + index;
        index++;
        indexMap.put(indexName, index);

        return result;
    }
}
