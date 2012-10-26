package org.eob;

import org.eob.enums.DescriptionMergeType;
import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.model.ItemType;
import org.eob.model.SubItemType;
import org.eob.model.Wall;

import java.util.Arrays;

/**
 * User: Bifrost
 * Date: 10/25/12
 * Time: 12:33 AM
 */
public class Eob1Settings {

    @SuppressWarnings("UnusedDeclaration")
    public static void init() {
        //--------------
        // --- Items ---
        //--------------
        ItemType Axe = new ItemType(0x00, "axe", "Axe", "hand_axe");
        ItemType Longsword = new ItemType(0x01, "long_sword", "Long Sword", "long_sword");
        ItemType Dart = new ItemType(0x04, "dart", "Dart", "shuriken");
        ItemType Dagger = new ItemType(0x05, "dagger", "Dagger", "dagger");
        ItemType DwarvenPotion = new ItemType(0x06, "dwarven_potion", "Dwarven potion", "potion_healing");
        ItemType Bow = new ItemType(0x07, "bow", "Bow", "short_bow");
        ItemType Spear = new ItemType(0x09, "spear", "Spear", "legionary_spear");
        ItemType Mace = new ItemType(0x0B, "mace", "Mace", "knoffer");
        ItemType Sling = new ItemType(0x0E, "sling", "Sling", "sling");
        ItemType Dart2 = new ItemType(0x0F, "dart", "Dart", "shuriken");
        ItemType Arrow = new ItemType(0x10, "arrow", "Arrow", "arrow");
        ItemType Rock = new ItemType(0x12, "rock", "Rock", "rock");
        ItemType Chainmail = new ItemType(0x14, "chainmail", "Chainmail", "ring_mail");
        ItemType DwarvenHelmet = new ItemType(0x15, "dwarven_helmet", "Dwarven Helmet", "full_helmet");
        ItemType LeatherArmor = new ItemType(0x16, "leather_armor", "Leather Armor", "leather_brigandine");
        ItemType Shield = new ItemType(0x1B, "shield", "Shield", "round_shield");
        ItemType LockPicks = new ItemType(0x1C, "lock_picks", "Lock picks", "machine_part_south");
        ItemType Rations = new ItemType(0x1F, "rations", "Rations", "pitroot_bread");
        ItemType LeatherBoots = new ItemType(0x20, "leather_boots", "Leather boots", "leather_boots");
        ItemType Bones = new ItemType(0x21, "bones", "Bones", "remains_of_toorum");
        ItemType MageScroll = new ItemType(0x22, "mage_scroll", "Mage scroll", "scroll");
        ItemType ClericScroll = new ItemType(0x23, "cleric_scroll", "Cleric scroll", "scroll");
        ItemType TextScroll = new ItemType(0x24, "scroll", "Text scroll", "scroll");
        ItemType Stone = new ItemType(0x25, "stone", "Stone", "rock");
        ItemType Key = new ItemType(0x26, "key", "Key", "brass_key"); // Different keys
        ItemType Potion = new ItemType(0x27, "potion", "Potion", "potion_healing");
        ItemType Gem = new ItemType(0x28, "gem", "Gem", "blue_gem"); // Gems of different colors (red and blue)
        ItemType Robe = new ItemType(0x29, "robe", "Robe", "peasant_tunic");
        ItemType MedallionOfAdornment = new ItemType(0x2c, "medallion_of_adornment", "Medallion of Adornment", "spirit_mirror_pendant");
        ItemType Ring = new ItemType(0x2a, "ring", "Ring", "hardstone_bracelet"); // There are no rings in Grimrock, we need to replace them with bracelets
        ItemType Ring2 = new ItemType(0x2f, "ring2", "Ring", "bracelet_tirin"); // This one has no power (found in lv 4, x=11,y=29)
        ItemType Wand = new ItemType(0x30, "wand", "Wand", "whitewood_wand");


        //-----------------
        // --- SubItems ---
        //-----------------
        // Text scroll
        SubItemType TextScroll1 = new SubItemType(47, TextScroll, "text1", "", "Unknown text");
        SubItemType TextScroll2 = new SubItemType(48, TextScroll, "text2", "", "Unknown text");
        SubItemType TextScroll3 = new SubItemType(49, TextScroll, "text3", "", "Unknown text");
        SubItemType TextScroll4 = new SubItemType(50, TextScroll, "text4", "", "Unknown text");

        // Cleric scroll
        SubItemType ClericScrollBless = new SubItemType(1, ClericScroll, "bless", "Bless", "1L");
        SubItemType ClericScrollCureLightWnds = new SubItemType(2, ClericScroll, "cure_light_wnds", "Cure Light Wounds", "1L");
        SubItemType ClericScrollCauseLightWnds = new SubItemType(3, ClericScroll, "cause_light_wnds", "Cause Light Wounds", "1L");
        SubItemType ClericScrollDetectMagic = new SubItemType(4, ClericScroll, "detect_magic", "Detect Magic", "1L");
        SubItemType ClericScrollProtectEvil = new SubItemType(5, ClericScroll, "protect_evil", "Protect-Evil", "1L");
        SubItemType ClericScrollAid = new SubItemType(6, ClericScroll, "aid", "Aid", "2L");
        SubItemType ClericScrollFlameBlade = new SubItemType(7, ClericScroll, "flame_blade", "Flame Blade", "2L");
        SubItemType ClericScrollHoldPerson = new SubItemType(8, ClericScroll, "hold_person", "Hold Person", "2L");
        SubItemType ClericScrollSlowPoison = new SubItemType(9, ClericScroll, "slow_poison", "Slow Poison", "2L");
        SubItemType ClericScrollCreateFood = new SubItemType(10, ClericScroll, "create_food", "Create Food", "3L");
        SubItemType ClericScrollDispelMagic = new SubItemType(11, ClericScroll, "dispel_magic", "Dispel Magic", "3L");
        SubItemType ClericScrollMagicalVestment = new SubItemType(12, ClericScroll, "magical_vestment", "Magical Vestment", "3L");
        SubItemType ClericScrollPrayer = new SubItemType(13, ClericScroll, "prayer", "Prayer", "3L");
        SubItemType ClericScrollRemoveParalysis = new SubItemType(14, ClericScroll, "remove_paralysis", "Remove Paralysis", "3L");
        SubItemType ClericScrollCureSeriousWnds = new SubItemType(15, ClericScroll, "cure_serious_wnds", "Cure Serious Wounds", "4L");
        SubItemType ClericScrollCauseSeriousWnds = new SubItemType(16, ClericScroll, "cause_serious_wnds", "Cause Serious Wounds", "4L");
        SubItemType ClericScrollNeutralPoison = new SubItemType(17, ClericScroll, "neutral_poison", "Neutral-Poison", "4L");
        SubItemType ClericScrollProtectEvil10 = new SubItemType(18, ClericScroll, "protect_evil10", "Protect-Evil 10'", "4L");
        SubItemType ClericScrollCureCriticalWnds = new SubItemType(20, ClericScroll, "cure_critical_wnds", "Cure Critical Wounds", "5L");
        SubItemType ClericScrollCauseCriticalWnds = new SubItemType(21, ClericScroll, "cause_critical_wnds", "Cause Critical Wounds", "5L");
        SubItemType ClericScrollFlameStrike = new SubItemType(22, ClericScroll, "flame_strike", "Flame Strike", "5L");
        SubItemType ClericScrollRaiseDead = new SubItemType(23, ClericScroll, "raise_dead", "Raise Dead", "5L");

        // Mage scroll
        SubItemType MageScrollArmor = new SubItemType(1, MageScroll, "armor", "Armor", "1L");
        SubItemType MageScrollBurningHands = new SubItemType(2, MageScroll, "burning_hands", "Burning Hands", "1L");
        SubItemType MageScrollDetectMagic = new SubItemType(3, MageScroll, "detect_magic", "Detect Magic", "1L");
        SubItemType MageScrollMagicMissile = new SubItemType(4, MageScroll, "magic_missile", "Magic Missile", "1L");
        SubItemType MageScrollReadMagic = new SubItemType(5, MageScroll, "read_magic", "Read Magic", "1L");
        SubItemType MageScrollShield = new SubItemType(6, MageScroll, "shield", "Shield", "1L");
        SubItemType MageScrollShockingGrasp = new SubItemType(7, MageScroll, "shocking_grasp", "Shocking Grasp", "1L");
        SubItemType MageScrollInvisibility = new SubItemType(8, MageScroll, "invisibility", "Invisibility", "2L");
        SubItemType MageScrollKnock = new SubItemType(9, MageScroll, "knock", "Knock", "2L");
        SubItemType MageScrollMsAcidArrow = new SubItemType(10, MageScroll, "ms_acid_arrow", "M's Acid Arrow", "2L");
        SubItemType MageScrollStinkingCloud = new SubItemType(11, MageScroll, "stinking_cloud", "Stinking Cloud", "2L");
        SubItemType MageScrollDispelMagic = new SubItemType(12, MageScroll, "dispel_magic", "Dispel Magic", "3L");
        SubItemType MageScrollFireball = new SubItemType(13, MageScroll, "fireball", "Fireball", "3L");
        SubItemType MageScrollFlameArrow = new SubItemType(14, MageScroll, "flame_arrow", "Flame Arrow", "3L");
        SubItemType MageScrollHaste = new SubItemType(15, MageScroll, "haste", "Haste", "3L");
        SubItemType MageScrollHoldPerson = new SubItemType(16, MageScroll, "hold_person", "Hold Person", "3L");
        SubItemType MageScrollInvisibility10 = new SubItemType(17, MageScroll, "invisibility 10'", "Invisibility 10'", "3L");
        SubItemType MageScrollLightningBolt = new SubItemType(18, MageScroll, "lightning_bolt", "Lightning Bolt", "3L");
        SubItemType MageScrollVampiricTouch = new SubItemType(19, MageScroll, "vampiric_touch", "Vampiric Touch", "3L");
        SubItemType MageScrollFear = new SubItemType(20, MageScroll, "fear", "Fear", "4L");
        SubItemType MageScrollIceStorm = new SubItemType(21, MageScroll, "ice_storm", "Ice Storm", "4L");
        SubItemType MageScrollStoneSkin = new SubItemType(22, MageScroll, "stone_skin", "Stone Skin", "4L");
        SubItemType MageScrollCloudKill = new SubItemType(23, MageScroll, "cloud_kill", "Cloud Kill", "5L");
        SubItemType MageScrollConeOfCold = new SubItemType(24, MageScroll, "cone_of_cold", "Cone of Cold", "5L");
        SubItemType MageScrollHoldMonster = new SubItemType(25, MageScroll, "hold_monster", "Hold Monster", "5L");

        // Potion
        SubItemType PotionGiantStrength = new SubItemType(1, Potion, "giant_strength", "Giant Strength", "");
        SubItemType PotionHealing = new SubItemType(2, Potion, "healing", "Healing", "");
        SubItemType PotionExtraHealing = new SubItemType(3, Potion, "extra_healing", "Extra Healing", "");
        SubItemType PotionPoison = new SubItemType(4, Potion, "poison", "Poison", "");
        SubItemType PotionVitality = new SubItemType(5, Potion, "vitality", "Vitality", "");
        SubItemType PotionSpeed = new SubItemType(6, Potion, "speed", "Speed", "");
        SubItemType PotionInvisibility = new SubItemType(7, Potion, "invisibility", "Invisibility", "");
        SubItemType PotionCurePoison = new SubItemType(8, Potion, "cure_poison", "Cure Poison", "");

        // Dwarven potion
        SubItemType DwarvenPotionHealing = new SubItemType(8, Potion, "healing", "Healing", "");

        // Key
        SubItemType KeySilver = new SubItemType(1, Key, "silver", "", "");
        SubItemType KeyGold = new SubItemType(2, Key, "gold", "", "");
        SubItemType KeyDwarven = new SubItemType(3, Key, "dwarven", "", "");
        SubItemType KeySimple = new SubItemType(4, Key, "", "", "");
        SubItemType KeySkull = new SubItemType(5, Key, "skull", "", "");
        SubItemType KeyDrow = new SubItemType(6, Key, "drow", "", "");
        SubItemType KeyJeweled = new SubItemType(7, Key, "jeweled", "", "");
        SubItemType KeyRuby = new SubItemType(8, Key, "ruby", "", "");

        // Wand
        SubItemType WandLightningBolt = new SubItemType(1, Wand, "lightning_bolt", "Lightning Bolt", "");
        SubItemType WandConeOfCold = new SubItemType(2, Wand, "cone_of_cold", "Cone of Cold", "");
        SubItemType WandCureSeriousWounds = new SubItemType(3, Wand, "cure_serious_wnds", "Cure Serious Wounds", "");
        SubItemType WandFireball = new SubItemType(4, Wand, "fireball", "Fireball", "");
        SubItemType WandSlivias = new SubItemType(5, Wand, "slivias", "Slivias", "Move 1 square away");
        SubItemType WandMagicMissile = new SubItemType(6, Wand, "magic_missile", "Magic Missile", "");
        SubItemType WandMagicalVestment = new SubItemType(7, Wand, "magical_vestment", "Magical Vestment", "");


        // Rations
        SubItemType RationsBasic = new SubItemType(25, Rations, "", "Rations", "");
        SubItemType RationsIron = new SubItemType(50, Rations, "iron", "Iron Rations", "");

        // Bones
        SubItemType HumanBones = new SubItemType(1, Bones, "human", "", "");
        SubItemType HalflingBones = new SubItemType(6, Bones, "halfling", "", "");

        // Dart
        SubItemType DartPlus1 = new SubItemType(1, Dart, "plus1", "+1", DescriptionMergeType.Suffix,  "");
        SubItemType DartPlus2 = new SubItemType(2, Dart, "plus2", "+2", DescriptionMergeType.Suffix, "");
        SubItemType DartPlus3 = new SubItemType(3, Dart, "plus3", "+3", DescriptionMergeType.Suffix, "");
        SubItemType DartPlus4 = new SubItemType(4, Dart, "plus4", "+4", DescriptionMergeType.Suffix, "");
        SubItemType DartPlus5 = new SubItemType(5, Dart, "plus5", "+5", DescriptionMergeType.Suffix, "");
        SubItemType Dart2Plus1 = new SubItemType(1, Dart2, "plus1", "+1", DescriptionMergeType.Suffix, "");
        SubItemType Dart2Plus2 = new SubItemType(2, Dart2, "plus2", "+2", DescriptionMergeType.Suffix, "");
        SubItemType Dart2Plus3 = new SubItemType(3, Dart2, "plus3", "+3", DescriptionMergeType.Suffix, "");
        SubItemType Dart2Plus4 = new SubItemType(4, Dart2, "plus4", "+4", DescriptionMergeType.Suffix, "");
        SubItemType Dart2Plus5 = new SubItemType(5, Dart2, "plus5", "+5", DescriptionMergeType.Suffix, "");

        // Stone
        SubItemType StoneHolySymbol = new SubItemType(1, Stone, "holy_symbol", "", "");
        SubItemType StoneNecklace = new SubItemType(2, Stone, "necklace", "", "");
        SubItemType StoneOrb = new SubItemType(3, Stone, "orb", "", "");
        SubItemType StoneDagger = new SubItemType(4, Stone, "dagger", "", "");
        SubItemType StoneMedallion = new SubItemType(5, Stone, "medallion", "", "");
        SubItemType StoneScepter = new SubItemType(6, Stone, "scepter", "", "");

        // Dagger
        SubItemType DaggerBackstabber = new SubItemType(3, Dagger, "backstabber", "+3", "");
        SubItemType DaggerFlica  = new SubItemType(5, Dagger, "flicka", "'Flicka'", "");

        // Gem
        SubItemType GemRed = new SubItemType(1, Gem, "red", "Red Gem", "");
        SubItemType GemBlue = new SubItemType(2, Gem, "blue", "Blue Gem", "");

        // Rings
        SubItemType RingProtection2 = new SubItemType(2, Ring, "protection2", "Protection +2", ""); // level 11, (x=27,y=16)
        SubItemType RingProtection3 = new SubItemType(3, Ring, "protection3", "Protection +3", ""); // level 4, (x=6, y=24)

        // Axe
        SubItemType AxeDrowCleaver = new SubItemType(3, Axe, "drow_cleaver", "'Drow Cleaver'", DescriptionMergeType.Replace, "");

        //-------------
        // --- Wall ---
        //-------------
        // All levels
        Wall empty = new Wall(0L, WallType.Empty, Arrays.asList(GameSupportType.Eob1));
        Wall wall1 = new Wall(1L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        Wall wall2 = new Wall(2L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        Wall emptyMonsterBlock = new Wall(25L, WallType.SquarePart, null, "eob_blocker", "eob_blocker"); // Block monsters to move trough
        Wall teleport = new Wall(52L, WallType.SquarePart, null, "eob_teleporter", "eob_teleporter");
        Wall wallWithDoor = new Wall(58L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look
        Wall wallWithNet = new Wall(59L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look

        // Sewers levels
        Wall sewersDoorWithButton = new Wall(3L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_metal", "eob_sewers_door_metal", "\t:addPullChain()");
        Wall sewersDoor = new Wall(8L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_metal", "eob_sewers_door_metal");
        Wall sewersDoorOpened = new Wall(12L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_metal", "eob_sewers_door_metal", "\t:setDoorState(\"open\")");
        Wall sewersDoorPortcullisWithButton = new Wall(13L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_portcullis", "eob_sewers_door_portcullis", "\t:addPullChain()");
        Wall sewersDoorPortcullisThrowableThrough = new Wall(18L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_portcullis_throwable", "eob_portcullis_throwable");
        Wall sewersLevelUp = new Wall(23L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_ladder_up", "eob_ladder_up"); // Ladder
        Wall sewersLevelDown = new Wall(24L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_ladder_down", "eob_ladder_down"); // Ladder
        Wall sewersEmptyCeilingShaft = new Wall(26L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_sewers_ceiling_shaft", "eob_sewers_ceiling_shaft");
        Wall sewersEmptyPit = new Wall(27L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_sewers_ceiling_shaft", "eob_sewers_ceiling_shaft");
        Wall sewersPressurePlate = new Wall(28L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_sewers_pressure_plate", "eob_sewers_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        Wall sewersWallWithAlcove = new Wall(29L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_alcove", "eob_sewers_alcove");
        Wall sewersDoorStacked = new Wall(30L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_metal_force", "eob_sewers_door_metal_stacked");
        Wall sewersDoorPortcullisStacked = new Wall(31L, WallType.DoorPart, Wall.levels(1, 2, 3), "eob_sewers_door_portcullis", "eob_sewers_door_portcullis_stacked");
        Wall sewersWallWithEyeKeyHole = new Wall(32L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_lock_eye", "eob_sewers_lock_eye");
        Wall sewersWallWithJewelKeyHole = new Wall(35L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_lock_gem", "eob_sewers_lock_gem");
        Wall sewersWallWithSecretButtonLarge = new Wall(39L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_secret_button", "eob_sewers_secret_button_large"); // Brick
        Wall sewersWallWithKeyHoleButton = new Wall(41L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_lock_iron", "eob_sewers_lock_iron"); // Key hole -> button
        Wall sewersWallWithDrainage = new Wall(43L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_drainage", "eob_sewers_wall_drainage");
        Wall sewersWallWithDrainageBent1 = new Wall(44L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_drainage", "eob_sewers_wall_drainage_bent"); // Is it the same as 62???
        Wall sewersWallWithButtonSmall = new Wall(50L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_secret_button_small", "eob_sewers_secret_button_small");
        Wall sewersWallWithText = new Wall(51L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_text_long", "eob_sewers_wall_text_long");
        Wall sewersWallWithKeyHole = new Wall(53L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_lock_silver", "eob_lock_silver");
        Wall sewersWallWithKeyHole2_ = new Wall(54L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_lock_golden", "eob_lock_golden");
        Wall sewersWallWithLever = new Wall(55L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_lever", "eob_lever");
        Wall sewersWallWithText2_ = new Wall(57L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_text_long", "eob_sewers_wall_text_rats"); // Read able text (Rats ->)
        Wall sewersWallWithButton = new Wall(60L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_wall_button", "eob_severs_wall_button");
        Wall sewersWallWithButtonPressed = new Wall(61L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_switch", "eob_sewers_switch", "\t:setLeverState(\"activated\")");
        Wall sewersWallWithDrainageBent2 = new Wall(62L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_drainage", "eob_sewers_wall_drainage_bent"); // Is it the same as 44???
        Wall sewersWallWithPipe = new Wall(63L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_pipe", "eob_sewers_wall_pipe");
        Wall sewersWallIllusion = new Wall(64L, WallType.Wall, Wall.levels(1, 2, 3), "eob_sewers_illusion_wall_rune", "eob_sewers_illusion_wall_rune");
        Wall sewersWallWithRune = new Wall(65L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_wall_text_rune1", "eob_sewers_wall_text_rune"); // Rune on the wall
        Wall sewersWallWithDaggerKeyHole = new Wall(66L, WallType.SolidPart, Wall.levels(1, 2, 3), "eob_sewers_alcove_dagger", "eob_sewers_alcove_dagger"); // Dagger is used as key
        Wall sewersWallIllusionWithRune = new Wall(67L, WallType.Wall, Wall.levels(1, 2, 3), "eob_sewers_illusion_wall_rune", "eob_sewers_illusion_wall_rune");
        Wall sewersCaveIn = new Wall(69L, WallType.SquarePart, Wall.levels(1, 2, 3), "eob_sewers_cave_in", "eob_sewers_cave_in");

        // Ruins levels
        Wall ruinsDoorWithButton = new Wall(3L, WallType.DoorPart, Wall.levels(4, 5, 6), "eob_ruins_door_stone", "eob_ruins_door_stone", "\t:addPullChain()");
        Wall ruinsDoor = new Wall(8L, WallType.DoorPart, Wall.levels(4, 5, 6), "eob_ruins_door_stone", "eob_ruins_door_stone");
        Wall ruinsLevelUp = new Wall(23L, WallType.SquarePart, Wall.levels(4, 5, 6), "eob_ruins_stairs_up", "eob_ruins_stairs_up");
        Wall ruinsLevelDown = new Wall(24L, WallType.SquarePart, Wall.levels(4, 5, 6), "eob_ruins_stairs_down", "eob_ruins_stairs_down");
        Wall ruinsEmptyCeilingShaft = new Wall(26L, WallType.SquarePart, Wall.levels(4, 5, 6), "eob_ruins_ceiling_shaft", "eob_ruins_ceiling_shaft");
        Wall ruinsEmptyPit = new Wall(27L, WallType.SquarePart, Wall.levels(4, 5, 6), "eob_ruins_pit", "eob_ruins_pit");
        Wall ruinsPressurePlate = new Wall(28L, WallType.SquarePart, Wall.levels(4, 5, 6), "eob_ruins_pressure_plate", "eob_ruins_pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        Wall ruinsWallWithAlcove = new Wall(29L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_alcove", "eob_ruins_alcove");
        Wall ruinsDoorStacked = new Wall(30L, WallType.DoorPart, Wall.levels(4, 5, 6), "eob_ruins_door_stone_stacked", "eob_ruins_door_stone_stacked");
        Wall ruinsWallWithStatueLever = new Wall(32L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_statue_lever", "eob_ruins_statue_lever");
        Wall ruinsWallWithSmallStatue = new Wall(34L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_small_statue", "eob_ruins_small_statue");
        Wall ruinsWallWithChain = new Wall(36L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_chain_lever", "eob_ruins_chain_lever"); // Chain on the wall -> Type of the lever
        Wall ruinsWallWithFiringMechanism = new Wall(38L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_dart_firing_pad", "eob_ruins_dart_firing_pad"); // Level 6: Darts
        Wall ruinsWallWithSecretButtonTiny = new Wall(39L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_secret_button_tiny", "eob_ruins_secret_button_tiny");
        Wall ruinsNet = new Wall(40L, WallType.DoorPart, Wall.levels(4, 5, 6), "eob_ruins_net", "eob_ruins_net");
        Wall ruinsNetTorn = new Wall(41L, WallType.DoorPart, Wall.levels(4, 5, 6), "eob_ruins_net", "eob_ruins_net_torn", "\t:setDoorState(\"open\")"); // NetTorn (4 level)
        Wall ruinsWallPortalLevelNecklace = new Wall(43L, WallType.SolidPart, Wall.levels(5), "eob_ruins_portal_necklace", "eob_ruins_portal_necklace"); // Level 5 - Stone Necklace
        Wall ruinsWallPortalLevelScepter = new Wall(45L, WallType.SolidPart, Wall.levels(6), "eob_ruins_portal_scepter", "eob_ruins_portal_scepter"); // Level 6 - Stone Scepter
        Wall ruinsWallPortalLevelAmulet = new Wall(46L, WallType.SolidPart, Wall.levels(4), "eob_ruins_portal_amulet", "eob_ruins_portal_amulet"); // Level 4 - Stone Amulet
        Wall ruinsWallWithText = new Wall(51L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_wall_text", "eob_ruins_wall_text");
        Wall ruinsWallWithKeyHole = new Wall(53L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_statue_lock", "eob_ruins_statue_lock");
        Wall ruinsWallWithKeyHole2 = new Wall(54L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_ornate_lock", "eob_ruins_ornate_lock");
        Wall ruinsWallWithLever = new Wall(55L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_lever", "eob_ruins_lever");
        Wall ruinsWallWithButton = new Wall(60L, WallType.SolidPart, Wall.levels(4, 5, 6), "eob_ruins_wall_button", "eob_ruins_wall_button");
        Wall ruinsWallIllusion = new Wall(64L, WallType.Wall, Wall.levels(4, 5, 6), "eob_ruins_illusion_wall", "eob_ruins_illusion_wall");
        Wall ruinsWallIllusionWithStatue = new Wall(66L, WallType.Wall, Wall.levels(4, 5, 6), "eob_ruins_illusion_wall_statue", "eob_ruins_illusion_wall_statue");
        Wall ruinsWallIllusionWithRune = new Wall(67L, WallType.Wall, Wall.levels(4, 5, 6), "eob_ruins_illusion_wall_rune", "eob_ruins_illusion_wall_rune");

        // Drow levels
        Wall drowDoorWithButton = new Wall(3L, WallType.DoorPart, Wall.levels(7, 8, 9), "prison_door_metal", "door", "\t:addPullChain()");
        Wall drowDoor = new Wall(8L, WallType.DoorPart, Wall.levels(7, 8, 9), "prison_door_metal", "door");
        Wall drowLevelUp = new Wall(23L, WallType.SquarePart, Wall.levels(7, 8, 9), "prison_stairs_up", "stairs_up");
        Wall drowLevelDown = new Wall(24L, WallType.SquarePart, Wall.levels(7, 8, 9), "prison_stairs_down", "stairs_down");
        Wall drowEmptyCeilingShaft = new Wall(26L, WallType.SquarePart, Wall.levels(7, 8, 9), "prison_ceiling_shaft", "ceiling_shaft");
        Wall drowEmptyPit = new Wall(27L, WallType.SquarePart, Wall.levels(7, 8, 9), "prison_pit", "pit");
        Wall drowPressurePlate = new Wall(28L, WallType.SquarePart, Wall.levels(7, 8, 9), "prison_pressure_plate", "pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        Wall drowWallWithAlcove = new Wall(29L, WallType.SolidPart, Wall.levels(7, 8, 9), "prison_alcove", "wall_alcove");
        Wall drowWallWithFiringMechanism2 = new Wall(32L, WallType.SolidPart, Wall.levels(7, 8, 9), "daemon_head", "firing_mechanism"); // Level 7: Fireball
        Wall drowWallWithText2 = new Wall(33L, WallType.SolidPart, Wall.levels(7, 8, 9), "dungeon_wall_text_long", "wall_text"); // It is written, the key lies on the other side.
        Wall drowWallWithGemKeyHole = new Wall(36L, WallType.SolidPart, Wall.levels(7, 8, 9), "lock_prison", "wall_gem_lock"); // Gem is used as key
        Wall drowWallWithSecretButtonTiny = new Wall(39L, WallType.SolidPart, Wall.levels(7, 8, 9), "prison_secret_button_small", "secret_button_tiny");
        Wall drowWallPortalLevelCross = new Wall(40L, WallType.SolidPart, Wall.levels(7), "temple_glass_wall_2", "portal"); // Level 7 - missing Stone Cross
        Wall drowWallPortalLevelNecklace = new Wall(41L, WallType.SolidPart, Wall.levels(7), "temple_glass_wall_2", "portal"); // Level 7 - missing Stone Necklace
        Wall drowWallPortalLevelDaggerL7 = new Wall(43L, WallType.SolidPart, Wall.levels(7), "temple_glass_wall_2", "portal"); // Level 7 - missing Stone Dagger
        Wall drowWallPortalLevelDaggerL9 = new Wall(43L, WallType.SolidPart, Wall.levels(9), "temple_glass_wall_2", "portal"); // Level 9 - missing Stone Dagger
        Wall drowWallPortalLevelAmulet = new Wall(44L, WallType.SolidPart, Wall.levels(7), "temple_glass_wall_2", "portal"); // Level 7 - missing Stone Amulet
        Wall drowWallPortalLevelScepter = new Wall(45L, WallType.SolidPart, Wall.levels(8), "temple_glass_wall_2", "portal"); // Level 8 - missing Stone Scepter
        Wall drowWallPortalLevelGem = new Wall(46L, WallType.SolidPart, Wall.levels(7), "temple_glass_wall_2", "portal"); // Level 7 - missing Gem
        Wall drowWallThrowable = new Wall(47L, WallType.Wall, Wall.levels(7, 8, 9), "prison_secret_door", "wall_throwable");
        Wall drowWallWithFiringMechanismFireball = new Wall(48L, WallType.SolidPart, Wall.levels(7), "daemon_head", "firing_mechanism"); // Level 7: Fireball
        Wall drowWallWithFiringMechanismDartsL9 = new Wall(48L, WallType.SolidPart, Wall.levels(9), "daemon_head", "firing_mechanism"); // Level 9: Darts
        Wall drowWallWithFiringMechanismDartsL8 = new Wall(49L, WallType.SolidPart, Wall.levels(8), "daemon_head", "firing_mechanism"); // Level 7: Darts
        Wall drowWallWithFiringMechanismMagicMissile = new Wall(49L, WallType.SolidPart, Wall.levels(9), "daemon_head", "firing_mechanism2"); // Level 9: MagicMissile
        Wall drowWallWithText = new Wall(51L, WallType.SolidPart, Wall.levels(7, 8, 9), "prison_wall_text_long", "wall_text");
        Wall drowWallWithKeyHole = new Wall(53L, WallType.SolidPart, Wall.levels(7, 8, 9), "lock", "wall_spider_lock");
        Wall drowWallWithKeyHole2 = new Wall(54L, WallType.SolidPart, Wall.levels(7, 8, 9), "lock_ornate", "wall_lock_ornate");
        Wall drowWallWithLever = new Wall(55L, WallType.SolidPart, Wall.levels(7, 8, 9), "lever", "wall_lever");
        Wall drowWallIllusionWithSpider = new Wall(57L, WallType.Wall, Wall.levels(7, 8, 9), "temple_secret_door", "wall_illusion_with_spider");
        Wall drowWallWithButton = new Wall(60L, WallType.SolidPart, Wall.levels(7, 8, 9), "wall_button", "wall_button");
        Wall drowWallIllusion = new Wall(64L, WallType.Wall, Wall.levels(7, 8, 9), "prison_secret_door", "wall_illusion");

        // Hive levels
        Wall hiveDoorWithButton = new Wall(3L, WallType.DoorPart, Wall.levels(10, 11), "dungeon_door_metal", "door", "\t:addPullChain()");
        Wall hiveDoor = new Wall(8L, WallType.DoorPart, Wall.levels(10, 11), "prison_door_metal", "door");
        Wall hiveLevelUp = new Wall(23L, WallType.SquarePart, Wall.levels(10, 11), "dungeon_stairs_up", "stairs_up");
        Wall hiveLevelDown = new Wall(24L, WallType.SquarePart, Wall.levels(10, 11), "dungeon_stairs_down", "stairs_down");
        Wall hiveEmptyCeilingShaft = new Wall(26L, WallType.SquarePart, Wall.levels(10, 11), "dungeon_ceiling_shaft", "ceiling_shaft");
        Wall hiveEmptyPit = new Wall(27L, WallType.SquarePart, Wall.levels(10, 11), "dungeon_pit", "pit");
        Wall hivePressurePlate = new Wall(28L, WallType.SquarePart, Wall.levels(10, 11), "dungeon_pressure_plate", "pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        Wall hiveWallWithAlcove = new Wall(29L, WallType.SolidPart, Wall.levels(10, 11), "dungeon_alcove", "wall_alcove");
        Wall hiveWallWithSecretButtonSmall = new Wall(31L, WallType.SolidPart, Wall.levels(10, 11), "dungeon_secret_button_small", "secret_button_small");
        Wall hiveWallWithSecretButtonTiny = new Wall(33L, WallType.SolidPart, Wall.levels(10, 11), "dungeon_secret_button_small", "secret_button_tiny");
        Wall hiveWallPortalLevelCross = new Wall(36L, WallType.SolidPart, Wall.levels(11), "temple_glass_wall_2", "portal"); // Level 11 - missing Stone Cross
        Wall hiveWallPortalLevelOrb = new Wall(37L, WallType.SolidPart, Wall.levels(11), "temple_glass_wall_2", "portal"); // Level 11 - missing Stone Orb
        Wall hiveWallPortalLevelScepter = new Wall(38L, WallType.SolidPart, Wall.levels(10), "temple_glass_wall_2", "portal"); // Level 10 - missing Stone Scepter
        Wall hiveWallPortalLevelRing = new Wall(39L, WallType.SolidPart, Wall.levels(10), "temple_glass_wall_2", "portal"); // Level 10 - missing Stone Ring
        Wall hiveWallWithText = new Wall(41L, WallType.SolidPart, Wall.levels(10, 11), "dungeon_wall_text_long", "wall_text");
        Wall hiveWallWithKeyHole = new Wall(42L, WallType.SolidPart, Wall.levels(10, 11), "lock", "wall_lock");
        Wall hiveWallWithLeverUp = new Wall(43L, WallType.SolidPart, Wall.levels(10, 11), "lever", "wall_lever");
        Wall hiveWallWithLeverDown = new Wall(44L, WallType.SolidPart, Wall.levels(10, 11), "lever", "wall_lever", "\t:setLeverState(\"activated\")");
        Wall hiveWallWithButton = new Wall(45L, WallType.SolidPart, Wall.levels(10, 11), "wall_button", "wall_button");
        Wall hiveWallWithSwitch = new Wall(46L, WallType.SolidPart, Wall.levels(10, 11), "wall_button", "wall_switch"); // Can be pressed or released
        Wall hiveWallWithStar = new Wall(48L, WallType.SolidPart, Wall.levels(10, 11), "daemon_head", "wall_star"); // Level 11: Celestial star
        Wall hiveWallWithRift = new Wall(49L, WallType.SolidPart, Wall.levels(10, 11), "dungeon_ivy_1", "hive_wall_rift");
        Wall hiveWallWithManacles = new Wall(50L, WallType.SolidPart, Wall.levels(10, 11), "prison_bench", "prison_manacles");
        Wall hiveWallWithButton2 = new Wall(60L, WallType.SolidPart, Wall.levels(10, 11), "wall_button", "wall_button");

        // Sanctum levels
        Wall sanctumDoorWithButton = new Wall(3L, WallType.DoorPart, Wall.levels(12), "temple_door_metal", "door", "\t:addPullChain()");
        Wall sanctumDoor = new Wall(8L, WallType.DoorPart, Wall.levels(12), "temple_door_metal", "door");
        Wall sanctumPedestal = new Wall(22L, WallType.SquarePart, Wall.levels(12), "altar", "pedestal");
        Wall sanctumPedestalWithEye = new Wall(26L, WallType.SquarePart, Wall.levels(12), "altar", "pedestal_eye");
        Wall sanctumPedestalWithEyeDetector = new Wall(27L, WallType.SquarePart, Wall.levels(12), "altar", "pedestal_eye");
        Wall sanctumPressurePlate = new Wall(28L, WallType.SquarePart, Wall.levels(12), "temple_pressure_plate", "pressure_plate", "\t:setTriggeredByParty(true)\n\t:setTriggeredByMonster(true)\n\t:setTriggeredByItem(true)");
        Wall sanctumWallWithAlcove = new Wall(29L, WallType.SolidPart, Wall.levels(12), "temple_alcove", "wall_alcove");
        Wall sanctumWallWithFiringMechanism = new Wall(30L, WallType.SolidPart, Wall.levels(12), "daemon_head", "firing_mechanism"); // Level 12: Fireball
        Wall sanctumWallWithSecretButtonSmall = new Wall(31L, WallType.SolidPart, Wall.levels(12), "temple_secret_button_small", "secret_button_small");
        Wall sanctumWallWithSecretButtonTiny = new Wall(33L, WallType.SolidPart, Wall.levels(12), "temple_secret_button_small", "secret_button_tiny");
        Wall sanctumWallPortalLevelOrb = new Wall(37L, WallType.SolidPart, Wall.levels(12), "temple_glass_wall_2", "portal"); // Level 12 - missing Stone Orb
        Wall sanctumWallWithLamp = new Wall(38L, WallType.SolidPart, Wall.levels(12), "torch_holder", "wall_lamp");
        Wall sanctumSpikeTrap = new Wall(39L, WallType.SquarePart, Wall.levels(12), "temple_floor_drainage", "spike_trap");
        Wall sanctumWallWithText = new Wall(41L, WallType.SolidPart, Wall.levels(12), "temple_wall_text_long", "wall_text");
        Wall sanctumWallWithKeyHole = new Wall(42L, WallType.SolidPart, Wall.levels(12), "lock", "wall_skull_lock");
        Wall sanctumWallWithLampSmoke = new Wall(43L, WallType.SolidPart, Wall.levels(12), "torch_holder", "wall_lamp_smoke");
        Wall sanctumWallWithButton = new Wall(45L, WallType.SolidPart, Wall.levels(12), "wall_button", "wall_button");
        Wall sanctumWallWithButton2 = new Wall(60L, WallType.SolidPart, Wall.levels(12), "wall_button", "wall_button");
    }
}
