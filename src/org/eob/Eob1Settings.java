package org.eob;

import org.eob.enums.DescriptionMergeType;
import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.model.ItemType;
import org.eob.model.SubItemType;
import org.eob.model.Wall;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Bifrost
 * Date: 10/25/12
 * Time: 12:33 AM
 */
public class Eob1Settings {

    @SuppressWarnings("UnusedDeclaration")
    public static void init(EobGlobalData eobGlobalData) {
        //--------------
        // --- Items ---
        //--------------
        // Add any item type
        ItemType anyItem = new ItemType(0xFF, "any", "AnyItem");
        eobGlobalData.itemTypeDatFile.itemTypeList.put(0xFFL, anyItem);

        ItemType Axe = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x00L).setExternalData("axe", "Axe");
        ItemType LongSword = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x01L).setExternalData("long_sword", "Long Sword");
        ItemType ShortSword = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x02L).setExternalData("short_sword", "Short Sword");
        ItemType OrbOfPower = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x03L).setExternalData("orb_of_power", "Orb of Power");
        ItemType Dart = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x04L).setExternalData("dart", "Dart");
        ItemType Dagger = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x05L).setExternalData("dagger", "Dagger");
        ItemType DwarvenPotion = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x06L).setExternalData("dwarven_potion", "Dwarven potion");
        ItemType Bow = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x07L).setExternalData("bow", "Bow");
        ItemType Spear = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x09L).setExternalData("spear", "Spear");
        ItemType Halberd = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0AL).setExternalData("halberd", "Halberd"); // there is no other long weapon in Grimrock, so spear will have to do
        ItemType Mace = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0BL).setExternalData("mace", "Mace");
        ItemType Flail = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0CL).setExternalData("flail", "Flail");
        ItemType Staff = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0DL).setExternalData("staff", "Staff"); // Not present in the dungeon, but defined as 4th item (offset=0x2c)
        ItemType Sling = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0EL).setExternalData("sling", "Sling");
        ItemType Dart2 = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x0FL).setExternalData("dart", "Dart");
        ItemType Arrow = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x10L).setExternalData("arrow", "Arrow");
        ItemType Rock = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x12L).setExternalData("rock", "Rock");
        ItemType BandedArmor = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x13L).setExternalData("banded_armor", "Banded Armor");
        ItemType ChainMail = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x14L).setExternalData("chain_mail", "Chain Mail");
        ItemType DwarvenHelmet = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x15L).setExternalData("dwarven_helmet", "Dwarven Helmet");
        ItemType LeatherArmor = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x16L).setExternalData("leather_armor", "Leather Armor");
        ItemType PlateMail = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x18L).setExternalData("plate_mail", "Plate Mail");
        ItemType ScaleMail = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x19L).setExternalData("scale_mail", "Scale Mail");
        ItemType Shield = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x1BL).setExternalData("shield", "Shield");
        ItemType LockPicks = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x1CL).setExternalData("lock_picks", "Lock picks");
        ItemType SpellBook = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x1dL).setExternalData("spell_book", "Spell Book");
        ItemType HolySymbol = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x1EL).setExternalData("holy_symbol", "Holy Symbol"); // there's nothing that looks like ankh symbol
        ItemType Rations = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x1FL).setExternalData("rations", "Rations");
        ItemType LeatherBoots = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x20L).setExternalData("leather_boots", "Leather boots");
        ItemType Bones = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x21L).setExternalData("bones", "Bones");
        ItemType MageScroll = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x22L).setExternalData("mage_scroll", "Mage scroll");
        ItemType ClericScroll = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x23L).setExternalData("cleric_scroll", "Cleric scroll");
        ItemType TextScroll = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x24L).setExternalData("scroll", "Text scroll");
        ItemType Stone = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x25L).setExternalData("stone", "Stone");
        ItemType Key = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x26L).setExternalData("key", "Key"); // Different keys
        ItemType Potion = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x27L).setExternalData("potion", "Potion");
        ItemType Gem = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x28L).setExternalData("gem", "Gem"); // Gems of different colors (red and blue)
        ItemType Robe = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x29L).setExternalData("robe", "Robe");
        ItemType Ring = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x2AL).setExternalData("ring", "Ring"); // There are no rings in Grimrock, we need to replace them with bracelets
        ItemType MedallionOfAdornment = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x2CL).setExternalData("medallion_of_adornment", "Medallion of Adornment");
        ItemType Bracers = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x2BL).setExternalData("bracers", "Bracers");
        ItemType Medallion = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x2EL).setExternalData("medallion", "Stone Medallion"); // Actual name Luck Stone Medallion
        ItemType Ring2 = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x2FL).setExternalData("ring2", "Ring"); // This one has no power (found in lv 4, x=11,y=29)
        ItemType Wand = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x30L).setExternalData("wand", "Wand");
        ItemType KenkuEgg = eobGlobalData.itemTypeDatFile.itemTypeList.get(0x31L).setExternalData("egg", "Kenku Egg"); // There is really nothing in Grimrock
        // that even vaguely resembles an egg. Slime bell is at least round :)

        //-----------------
        // --- SubItems ---
        //-----------------
        // Text scroll
        eobGlobalData.addSubItem(new SubItemType(47, TextScroll, "text1", "", "Unknown text"));
        eobGlobalData.addSubItem(new SubItemType(48, TextScroll, "text2", "", "Unknown text"));
        eobGlobalData.addSubItem(new SubItemType(49, TextScroll, "text3", "", "Unknown text"));
        eobGlobalData.addSubItem(new SubItemType(50, TextScroll, "text4", "", "Unknown text"));

        // Cleric scroll
        eobGlobalData.addSubItem(new SubItemType(1, ClericScroll, "bless", "Bless", "1L"));
        eobGlobalData.addSubItem(new SubItemType(2, ClericScroll, "cure_light_wnds", "Cure Light Wounds", "1L"));
        eobGlobalData.addSubItem(new SubItemType(3, ClericScroll, "cause_light_wnds", "Cause Light Wounds", "1L"));
        eobGlobalData.addSubItem(new SubItemType(4, ClericScroll, "detect_magic", "Detect Magic", "1L"));
        eobGlobalData.addSubItem(new SubItemType(5, ClericScroll, "protect_evil", "Protect-Evil", "1L"));
        eobGlobalData.addSubItem(new SubItemType(6, ClericScroll, "aid", "Aid", "2L"));
        eobGlobalData.addSubItem(new SubItemType(7, ClericScroll, "flame_blade", "Flame Blade", "2L"));
        eobGlobalData.addSubItem(new SubItemType(8, ClericScroll, "hold_person", "Hold Person", "2L"));
        eobGlobalData.addSubItem(new SubItemType(9, ClericScroll, "slow_poison", "Slow Poison", "2L"));
        eobGlobalData.addSubItem(new SubItemType(10, ClericScroll, "create_food", "Create Food", "3L"));
        eobGlobalData.addSubItem(new SubItemType(11, ClericScroll, "dispel_magic", "Dispel Magic", "3L"));
        eobGlobalData.addSubItem(new SubItemType(12, ClericScroll, "magical_vestment", "Magical Vestment", "3L"));
        eobGlobalData.addSubItem(new SubItemType(13, ClericScroll, "prayer", "Prayer", "3L"));
        eobGlobalData.addSubItem(new SubItemType(14, ClericScroll, "remove_paralysis", "Remove Paralysis", "3L"));
        eobGlobalData.addSubItem(new SubItemType(15, ClericScroll, "cure_serious_wnds", "Cure Serious Wounds", "4L"));
        eobGlobalData.addSubItem(new SubItemType(16, ClericScroll, "cause_serious_wnds", "Cause Serious Wounds", "4L"));
        eobGlobalData.addSubItem(new SubItemType(17, ClericScroll, "neutral_poison", "Neutral-Poison", "4L"));
        eobGlobalData.addSubItem(new SubItemType(18, ClericScroll, "protect_evil10", "Protect-Evil 10'", "4L"));
        eobGlobalData.addSubItem(new SubItemType(20, ClericScroll, "cure_critical_wnds", "Cure Critical Wounds", "5L"));
        eobGlobalData.addSubItem(new SubItemType(21, ClericScroll, "cause_critical_wnds", "Cause Critical Wounds", "5L"));
        eobGlobalData.addSubItem(new SubItemType(22, ClericScroll, "flame_strike", "Flame Strike", "5L"));
        eobGlobalData.addSubItem(new SubItemType(23, ClericScroll, "raise_dead", "Raise Dead", "5L"));

        // Mage scroll
        eobGlobalData.addSubItem(new SubItemType(1, MageScroll, "armor", "Armor", "1L"));
        eobGlobalData.addSubItem(new SubItemType(2, MageScroll, "burning_hands", "Burning Hands", "1L"));
        eobGlobalData.addSubItem(new SubItemType(3, MageScroll, "detect_magic", "Detect Magic", "1L"));
        eobGlobalData.addSubItem(new SubItemType(4, MageScroll, "magic_missile", "Magic Missile", "1L"));
        eobGlobalData.addSubItem(new SubItemType(5, MageScroll, "read_magic", "Read Magic", "1L"));
        eobGlobalData.addSubItem(new SubItemType(6, MageScroll, "shield", "Shield", "1L"));
        eobGlobalData.addSubItem(new SubItemType(7, MageScroll, "shocking_grasp", "Shocking Grasp", "1L"));
        eobGlobalData.addSubItem(new SubItemType(8, MageScroll, "invisibility", "Invisibility", "2L"));
        eobGlobalData.addSubItem(new SubItemType(9, MageScroll, "knock", "Knock", "2L"));
        eobGlobalData.addSubItem(new SubItemType(10, MageScroll, "ms_acid_arrow", "M's Acid Arrow", "2L"));
        eobGlobalData.addSubItem(new SubItemType(11, MageScroll, "stinking_cloud", "Stinking Cloud", "2L"));
        eobGlobalData.addSubItem(new SubItemType(12, MageScroll, "dispel_magic", "Dispel Magic", "3L"));
        eobGlobalData.addSubItem(new SubItemType(13, MageScroll, "fireball", "Fireball", "3L"));
        eobGlobalData.addSubItem(new SubItemType(14, MageScroll, "flame_arrow", "Flame Arrow", "3L"));
        eobGlobalData.addSubItem(new SubItemType(15, MageScroll, "haste", "Haste", "3L"));
        eobGlobalData.addSubItem(new SubItemType(16, MageScroll, "hold_person", "Hold Person", "3L"));
        eobGlobalData.addSubItem(new SubItemType(17, MageScroll, "invisibility 10'", "Invisibility 10'", "3L"));
        eobGlobalData.addSubItem(new SubItemType(18, MageScroll, "lightning_bolt", "Lightning Bolt", "3L"));
        eobGlobalData.addSubItem(new SubItemType(19, MageScroll, "vampiric_touch", "Vampiric Touch", "3L"));
        eobGlobalData.addSubItem(new SubItemType(20, MageScroll, "fear", "Fear", "4L"));
        eobGlobalData.addSubItem(new SubItemType(21, MageScroll, "ice_storm", "Ice Storm", "4L"));
        eobGlobalData.addSubItem(new SubItemType(22, MageScroll, "stone_skin", "Stone Skin", "4L"));
        eobGlobalData.addSubItem(new SubItemType(23, MageScroll, "cloud_kill", "Cloud Kill", "5L"));
        eobGlobalData.addSubItem(new SubItemType(24, MageScroll, "cone_of_cold", "Cone of Cold", "5L"));
        eobGlobalData.addSubItem(new SubItemType(25, MageScroll, "hold_monster", "Hold Monster", "5L"));

        // Potion
        eobGlobalData.addSubItem(new SubItemType(1, Potion, "giant_strength", "Giant Strength", ""));
        eobGlobalData.addSubItem(new SubItemType(2, Potion, "healing", "Healing", ""));
        eobGlobalData.addSubItem(new SubItemType(3, Potion, "extra_healing", "Extra Healing", ""));
        eobGlobalData.addSubItem(new SubItemType(4, Potion, "poison", "Poison", ""));
        eobGlobalData.addSubItem(new SubItemType(5, Potion, "vitality", "Vitality", ""));
        eobGlobalData.addSubItem(new SubItemType(6, Potion, "speed", "Speed", ""));
        eobGlobalData.addSubItem(new SubItemType(7, Potion, "invisibility", "Invisibility", ""));
        eobGlobalData.addSubItem(new SubItemType(8, Potion, "cure_poison", "", "")); // Cure Poison

        // Dwarven potion
        eobGlobalData.addSubItem(new SubItemType(8, DwarvenPotion, "healing", "Healing", ""));

        // Key
        eobGlobalData.addSubItem(new SubItemType(1, Key, "silver", "", ""));
        eobGlobalData.addSubItem(new SubItemType(2, Key, "gold", "", ""));
        eobGlobalData.addSubItem(new SubItemType(3, Key, "dwarven", "", ""));
        eobGlobalData.addSubItem(new SubItemType(4, Key, "", "", ""));
        eobGlobalData.addSubItem(new SubItemType(5, Key, "skull", "", ""));
        eobGlobalData.addSubItem(new SubItemType(6, Key, "drow", "", ""));
        eobGlobalData.addSubItem(new SubItemType(7, Key, "jeweled", "", ""));
        eobGlobalData.addSubItem(new SubItemType(8, Key, "ruby", "", ""));

        // Wand
        eobGlobalData.addSubItem(new SubItemType(1, Wand, "lightning_bolt", "Lightning Bolt", ""));
        eobGlobalData.addSubItem(new SubItemType(2, Wand, "cone_of_cold", "Cone of Cold", ""));
        eobGlobalData.addSubItem(new SubItemType(3, Wand, "cure_serious_wnds", "Cure Serious Wounds", ""));
        eobGlobalData.addSubItem(new SubItemType(4, Wand, "fireball", "Fireball", ""));
        eobGlobalData.addSubItem(new SubItemType(5, Wand, "slivias", "", "Move 1 square away"));
        eobGlobalData.addSubItem(new SubItemType(6, Wand, "magic_missile", "Magic Missile", ""));
        eobGlobalData.addSubItem(new SubItemType(7, Wand, "magical_vestment", "Magical Vestment", ""));

        // Rations
        eobGlobalData.addSubItem(new SubItemType(25, Rations, "", "Rations", DescriptionMergeType.Ignore, ""));
        eobGlobalData.addSubItem(new SubItemType(50, Rations, "iron", "Iron Rations", DescriptionMergeType.Replace,  ""));

        // Bones (sorted by level they appear on)
        eobGlobalData.addSubItem(new SubItemType(6, Bones, "halfling_tod", "Halfling", DescriptionMergeType.Ignore, "Tod Uphill")); // resurrects Tod Uphill (from level 1)
        eobGlobalData.addSubItem(new SubItemType(1, Bones, "human_anya", "Human", DescriptionMergeType.Ignore, "Anya")); // resurrects Anya (from level 3)
        eobGlobalData.addSubItem(new SubItemType(4, Bones, "human_ileria", "Human", DescriptionMergeType.Ignore, "Ileria")); // resurrects Ileria (from level 7)
        eobGlobalData.addSubItem(new SubItemType(2, Bones, "human_beohram", "Human", DescriptionMergeType.Ignore, "Beohram")); // resurrects Beohram (from level 9)
        eobGlobalData.addSubItem(new SubItemType(5, Bones, "elf_tyrra", "Elf", DescriptionMergeType.Ignore, "Tyrra")); // resurrects Tyrra (from level 10)
        eobGlobalData.addSubItem(new SubItemType(3, Bones, "human_kirath", "Human", DescriptionMergeType.Ignore, "Kirath")); // resurrects Kirath (from level 11)

        // Dart
        eobGlobalData.addSubItem(new SubItemType(1, Dart, "plus1", "+1", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(2, Dart, "plus2", "+2", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(3, Dart, "plus3", "+3", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(4, Dart, "plus4", "+4", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(5, Dart, "plus5", "+5", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(1, Dart2, "plus1", "+1", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(2, Dart2, "plus2", "+2", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(3, Dart2, "plus3", "+3", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(4, Dart2, "plus4", "+4", DescriptionMergeType.Suffix, ""));
        eobGlobalData.addSubItem(new SubItemType(5, Dart2, "plus5", "+5", DescriptionMergeType.Suffix, ""));

        // Stone
        eobGlobalData.addSubItem(new SubItemType(1, Stone, "holy_symbol", "", ""));
        eobGlobalData.addSubItem(new SubItemType(2, Stone, "necklace", "", ""));
        eobGlobalData.addSubItem(new SubItemType(3, Stone, "orb", "", ""));
        eobGlobalData.addSubItem(new SubItemType(4, Stone, "dagger", "", ""));
        eobGlobalData.addSubItem(new SubItemType(5, Stone, "medallion", "", ""));
        eobGlobalData.addSubItem(new SubItemType(6, Stone, "scepter", "", ""));
        eobGlobalData.addSubItem(new SubItemType(7, Stone, "ring", "", ""));
        eobGlobalData.addSubItem(new SubItemType(8, Stone, "gem", "", ""));

        // Dagger
        eobGlobalData.addSubItem(new SubItemType(3, Dagger, "backstabber", "+3", ""));
        eobGlobalData.addSubItem(new SubItemType(4, Dagger, "guinsoo", "'Guinsoo'", DescriptionMergeType.Replace, "+4"));
        eobGlobalData.addSubItem(new SubItemType(5, Dagger, "flicka", "'Flicka'", ""));

        // Gem
        eobGlobalData.addSubItem(new SubItemType(1, Gem, "red", "Red Gem", DescriptionMergeType.Ignore, "Red"));
        eobGlobalData.addSubItem(new SubItemType(2, Gem, "blue", "Blue Gem", DescriptionMergeType.Ignore, "Blue"));

        // Rings
        eobGlobalData.addSubItem(new SubItemType(2, Ring, "protection2", "Protection +2", "")); // level 11, (x=27,y=16)
        eobGlobalData.addSubItem(new SubItemType(3, Ring, "protection3", "Protection +3", "")); // level 4, (x=6, y=24)

        // Different base type for this ring
        eobGlobalData.addSubItem(new SubItemType(3, Ring2, "feather_fall", "Feather Fall", ""));
        eobGlobalData.addSubItem(new SubItemType(2, Ring2, "sustenance", "Sustenance", DescriptionMergeType.SuffixWithOf, ""));
        eobGlobalData.addSubItem(new SubItemType(1, Ring2, "wizardry", "Wizardry", DescriptionMergeType.SuffixWithOf, ""));

        // Medallion
        eobGlobalData.addSubItem(new SubItemType(1, Medallion, "luck_stone", "Luck Stone", DescriptionMergeType.Prefix, ""));

        // Longsword
        eobGlobalData.addSubItem(new SubItemType(3, LongSword, "night_stalker", "'Night Stalker'", DescriptionMergeType.Replace, "+3"));
        eobGlobalData.addSubItem(new SubItemType(4, LongSword, "slasher", "'Slasher'", DescriptionMergeType.Replace, "+4"));
        eobGlobalData.addSubItem(new SubItemType(5, LongSword, "severious", "'Severious'", DescriptionMergeType.Replace, "+5"));

        // Sword
        eobGlobalData.addSubItem(new SubItemType(3, ShortSword, "slicer", "'Slicer'", DescriptionMergeType.Replace, "+3"));

        // Axe
        eobGlobalData.addSubItem(new SubItemType(3, Axe, "drow_cleaver", "'Drow Cleaver'", DescriptionMergeType.Replace, ""));
        eobGlobalData.addSubItem(new SubItemType(-3, Axe, "cursed3", "Cursed -3", DescriptionMergeType.Suffix, ""));

        // Mace
        eobGlobalData.addSubItem(new SubItemType(3, Mace, "plus3", "+3", ""));

        // Rock
        eobGlobalData.addSubItem(new SubItemType(1, Rock, "glowing", "", "glowing"));
        eobGlobalData.addSubItem(new SubItemType(2, Rock, "mossy", "Mossy", DescriptionMergeType.Prefix, ""));

        // Halberd
        eobGlobalData.addSubItem(new SubItemType(5, Halberd, "chieftain", "Chieftain", DescriptionMergeType.Prefix, ""));

        // Sling
        eobGlobalData.addSubItem(new SubItemType(-3, Sling, "cursed3", "Cursed -3", DescriptionMergeType.Suffix, ""));

        // Shield
        eobGlobalData.addSubItem(new SubItemType(1, Shield, "dwarven", "Dwarven", DescriptionMergeType.Prefix, ""));
        eobGlobalData.addSubItem(new SubItemType(3, Shield, "drow", "Drow", DescriptionMergeType.Prefix, "+3"));

        // Plate mail
        eobGlobalData.addSubItem(new SubItemType(-3, PlateMail, "cursed3", "", DescriptionMergeType.Ignore, "-3")); // Plate Mail of Great Beauty, cursed -3

        // Banded Armor
        eobGlobalData.addSubItem(new SubItemType(3, BandedArmor, "plus3", "", "+3"));

        // Robe
        eobGlobalData.addSubItem(new SubItemType(5, Robe, "protection5", "Protection", DescriptionMergeType.SuffixWithOf, "+5"));

        // Bracers
        eobGlobalData.addSubItem(new SubItemType(2, Bracers, "defense2", "Defense", DescriptionMergeType.SuffixWithOf, "+2"));
        eobGlobalData.addSubItem(new SubItemType(3, Bracers, "defense3", "Elven Bracers of Defense", DescriptionMergeType.Replace, "+3"));

        // Kenku Eggs (I don't know how the egg types differ from each other)
        eobGlobalData.addSubItem(new SubItemType(10, KenkuEgg, "10", "", ""));
        eobGlobalData.addSubItem(new SubItemType(20, KenkuEgg, "20", "", ""));
        eobGlobalData.addSubItem(new SubItemType(30, KenkuEgg, "30", "", ""));
        eobGlobalData.addSubItem(new SubItemType(40, KenkuEgg, "40", "", ""));

        // Orb of Power
        // orb of power from level 11 (I think it looks like the regular one and the different subtype is used to just distinguish between them)
        eobGlobalData.addSubItem(new SubItemType(1, OrbOfPower, "_from_level11", "", ""));

        // The following items are not present in the dungeons. I do not know what they really are, so their descriptions and names
        // are just guesses
        eobGlobalData.addSubItem(new SubItemType(100, Rations, "iron_double", "", "")); // 100 type suggest that is is twice as good as iron (50)
        eobGlobalData.addSubItem(new SubItemType(1, LongSword, "sharp", "Sharp", DescriptionMergeType.Prefix, "+1"));
        eobGlobalData.addSubItem(new SubItemType(-2, LongSword, "cursed2", "Unlucky", DescriptionMergeType.Prefix, "-2"));
        eobGlobalData.addSubItem(new SubItemType(7, Bones, "not_used_in_dungeon", "", DescriptionMergeType.Prefix, "")); // Not present in the dungeon (item offset=0x1D0 uses that subtype)
        eobGlobalData.addSubItem(new SubItemType(46, TextScroll, "not_used_in_dungeon", "not found anywhere in the dungeon", DescriptionMergeType.Ignore, "")); // (item offset=0x1EC)

        //----------------
        // --- Monster ---
        //----------------

        eobGlobalData.createMonster("any", 0xFF, "any");

        // Level 1
        eobGlobalData.createMonster("kobold", 0, "eob_kobold");
        eobGlobalData.createMonster("leech", 1, "eob_leech");
        // Level 2
        eobGlobalData.createMonster("skeleton", 2, "eob_skeleton");
        eobGlobalData.createMonster("zombie", 3, "eob_zombie");
        // Level 3
        eobGlobalData.createMonster("kuotoa", 4, "eob_kuotoa");
        eobGlobalData.createMonster("flind", 5, "eob_flind");
        // Level 4
        eobGlobalData.createMonster("spider", 6, "eob_spider");
        // Level 5
        eobGlobalData.createMonster("dwarf", 7, "eob_dwarf");
        // Level 6
        eobGlobalData.createMonster("kenku", 16, "eob_kenku");
        eobGlobalData.createMonster("mage", 11, "eob_mage");
        // Level 7
        eobGlobalData.createMonster("skelwar", 8, "eob_skelwar");
        eobGlobalData.createMonster("drowelf", 12, "eob_drowelf");
        // Level 8
        eobGlobalData.createMonster("drider", 15, "eob_drider");
        eobGlobalData.createMonster("hellhnd", 20, "eob_hellhnd");
        // Level 9
        eobGlobalData.createMonster("rust", 18, "eob_rust");
        eobGlobalData.createMonster("disbeast", 9, "eob_disbeast");
        // Level 10
        eobGlobalData.createMonster("shindia", 14, "eob_shindia");
        eobGlobalData.createMonster("mantis", 10, "eob_mantis");
        // Level 11
        eobGlobalData.createMonster("xorn", 19, "eob_xorn");
        eobGlobalData.createMonster("mflayer", 17, "eob_mflayer");
        // Level 12
        eobGlobalData.createMonster("xanath", 21, "eob_xanath");
        eobGlobalData.createMonster("golem", 13, "eob_golem");


        //-------------
        // --- Wall ---
        //-------------

        // Wall conversion
        eobGlobalData.wallConversion.put(Arrays.asList("sewersDoorWithButton", "sewersDoor"), "sewersDoorWithOneButton");
        eobGlobalData.wallConversion.put(Arrays.asList("sewersDoorPortcullisWithButton", "sewersDoorPortcullisThrowableThrough"), "sewersDoorPortcullisWithOneButton");
        eobGlobalData.wallConversion.put(Arrays.asList("ruinsDoorWithButton", "ruinsDoor"), "ruinsDoorWithOneButton");
        eobGlobalData.wallConversion.put(Arrays.asList("drowDoorWithButton", "drowDoor"), "drowDoorWithOneButton");
        eobGlobalData.wallConversion.put(Arrays.asList("hiveDoorWithButton", "hiveDoor"), "hiveDoorWithOneButton");
        eobGlobalData.wallConversion.put(Arrays.asList("sanctumDoorWithButton", "sanctumDoor"), "sanctumDoorWithOneButton");


        // All levels
        eobGlobalData.createWall("empty", 0L, WallType.Empty, Arrays.asList(GameSupportType.Eob1));
        eobGlobalData.createWall("wall1", 1L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        eobGlobalData.createWall("wall2", 2L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        eobGlobalData.createWall("emptyMonsterBlock", 25L, WallType.SquarePart, false, null); // Block monsters to move trough
        eobGlobalData.createWall("teleporter", 52L, WallType.SquarePart, false, null);
        eobGlobalData.createWall("wallWithDoor", 58L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look
        eobGlobalData.createWall("wallWithNet", 59L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look

        // Sewers levels
        eobGlobalData.createWall("sewersDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoor", 8L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoorOpened", 12L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoorPortcullisWithButton", 13L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoorPortcullisThrowableThrough", 18L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersLevelUp", 23L, WallType.SquarePart, false, Wall.levels(1, 2, 3)); // Ladder
        eobGlobalData.createWall("sewersLevelDown", 24L, WallType.SquarePart, false, Wall.levels(1, 2, 3)); // Ladder
        eobGlobalData.createWall("sewersEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoorStacked", 30L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersDoorPortcullisStacked", 31L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithEyeKeyHole", 32L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithEyeKeyHoleWithGem", 33L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithPurpleEyeKeyHoleWithGem", 34L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithJewelKeyHole", 35L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithJewelKeyHoleWithGem", 36L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithSecretButtonLarge", 39L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Brick
        eobGlobalData.createWall("sewersWallWithKeyHoleButton", 41L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Key hole -> button
        eobGlobalData.createWall("sewersWallWithDrainage", 43L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithDrainageBent1", 44L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Is it the same as 62???
        eobGlobalData.createWall("sewersWallWithBlueButton", 45L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithRedButton", 48L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersCaveIn", 49L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithButtonSmall", 50L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithText", 51L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithSilverKeyHole", 53L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithGoldKeyHole", 54L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithText2", 57L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Read able text (Rats ->)
        eobGlobalData.createWall("sewersWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithButtonPressed", 61L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithDrainageBent2", 62L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Is it the same as 44???
        eobGlobalData.createWall("sewersWallWithPipe", 63L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallIllusion", 64L, WallType.Wall, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallWithRune", 65L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Rune on the wall
        eobGlobalData.createWall("sewersWallWithDaggerKeyHole", 66L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Dagger is used as key
        eobGlobalData.createWall("sewersWallIllusionWithRune", 67L, WallType.Wall, false, Wall.levels(1, 2, 3));
        eobGlobalData.createWall("sewersWallPortalInactive", 68L, WallType.SolidPart, false, Wall.levels(2)); // Level 2 - debug area left by developers
        eobGlobalData.createWall("sewersCaveIn", 69L, WallType.SquarePart, false, Wall.levels(1, 2, 3));

        // Ruins levels
        eobGlobalData.createWall("ruinsDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsDoor", 8L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsLevelUp", 23L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsLevelDown", 24L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsDoorStacked", 30L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithStatueLever", 32L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithSmallStatue", 34L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithChain", 36L, WallType.SolidPart, false, Wall.levels(4, 5, 6)); // Chain on the wall -> Type of the lever
        eobGlobalData.createWall("ruinsWallWithFiringMechanism", 38L, WallType.SolidPart, false, Wall.levels(4, 5, 6)); // Level 6: Darts
        eobGlobalData.createWall("ruinsWallWithSecretButtonTiny", 39L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsNet", 40L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsNetTorn", 41L, WallType.DoorPart, false, Wall.levels(4, 5, 6)); // NetTorn (4 level)
        eobGlobalData.createWall("ruinsWallPortalLevelNecklace", 43L, WallType.SolidPart, false, Wall.levels(5)); // Level 5 - Stone Necklace
        eobGlobalData.createWall("ruinsWallPortalLevelScepter", 45L, WallType.SolidPart, false, Wall.levels(6)); // Level 6 - Stone Scepter
        eobGlobalData.createWall("ruinsWallPortalLevelAmulet", 46L, WallType.SolidPart, false, Wall.levels(4)); // Level 4 - Stone Amulet
        eobGlobalData.createWall("ruinsWallWithText", 51L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithKeyHole", 53L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithKeyHole2", 54L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallIllusion", 64L, WallType.Wall, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallIllusionWithStatue", 66L, WallType.Wall, false, Wall.levels(4, 5, 6));
        eobGlobalData.createWall("ruinsWallIllusionWithRune", 67L, WallType.Wall, false, Wall.levels(4, 5, 6));

        // Drow levels
        eobGlobalData.createWall("drowDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowDoor", 8L, WallType.DoorPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowDoorOpened", 12L, WallType.DoorPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowLevelUp", 23L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowLevelDown", 24L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithFiringMechanismFireball2", 32L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // Level 7: Fireball
        eobGlobalData.createWall("drowWallWithText2", 33L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // It is written, the key lies on the other side.
        eobGlobalData.createWall("drowWallWithGemKeyHole", 36L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // Gem is used as key
        eobGlobalData.createWall("drowWallWithGemKeyHoleWithGem", 37L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // Gem is used as key
        eobGlobalData.createWall("drowWallWithSecretButtonTiny", 39L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallPortalLevelCross", 40L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Cross
        eobGlobalData.createWall("drowWallPortalLevelNecklace", 41L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Necklace
        eobGlobalData.createWall("drowWallPortalLevelDaggerL7", 43L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Dagger
        eobGlobalData.createWall("drowWallPortalLevelDaggerL9", 43L, WallType.SolidPart, false, Wall.levels(9)); // Level 9 - missing Stone Dagger
        eobGlobalData.createWall("drowWallPortalLevelAmulet", 44L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Amulet
        eobGlobalData.createWall("drowWallPortalLevelScepter", 45L, WallType.SolidPart, false, Wall.levels(8)); // Level 8 - missing Stone Scepter
        eobGlobalData.createWall("drowWallPortalLevelGem", 46L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Gem
        eobGlobalData.createWall("drowWallThrowable", 47L, WallType.Wall, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithFiringMechanismFireball", 48L, WallType.SolidPart, false, Wall.levels(7)); // Level 7: Fireball
        eobGlobalData.createWall("drowWallWithFiringMechanismDartsL9", 48L, WallType.SolidPart, false, Wall.levels(9)); // Level 9: Darts
        eobGlobalData.createWall("drowWallWithFiringMechanismDartsL8", 49L, WallType.SolidPart, false, Wall.levels(8)); // Level 7: Darts
        eobGlobalData.createWall("drowWallWithFiringMechanismMagicMissile", 49L, WallType.SolidPart, false, Wall.levels(9)); // Level 9: MagicMissile
        eobGlobalData.createWall("drowWallWithText", 51L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithKeyHole", 53L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithKeyHole2", 54L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallIllusionWithSpider", 57L, WallType.Wall, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        eobGlobalData.createWall("drowWallIllusion", 64L, WallType.Wall, false, Wall.levels(7, 8, 9));

        // Hive levels
        eobGlobalData.createWall("hiveDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveDoor", 8L, WallType.DoorPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveLevelUp", 23L, WallType.SquarePart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveLevelDown", 24L, WallType.SquarePart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hivePressurePlate", 28L, WallType.SquarePart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithSecretButtonSmall", 31L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithSecretButtonTiny", 33L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallPortalLevelCross", 36L, WallType.SolidPart, false, Wall.levels(11)); // Level 11 - missing Stone Cross
        eobGlobalData.createWall("hiveWallPortalLevelOrb", 37L, WallType.SolidPart, false, Wall.levels(11)); // Level 11 - missing Stone Orb
        eobGlobalData.createWall("hiveWallPortalLevelScepter", 38L, WallType.SolidPart, false, Wall.levels(10)); // Level 10 - missing Stone Scepter
        eobGlobalData.createWall("hiveWallPortalLevelRing", 39L, WallType.SolidPart, false, Wall.levels(10)); // Level 10 - missing Stone Ring
        eobGlobalData.createWall("hiveWallWithText", 41L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithKeyHole", 42L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithLeverUp", 43L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithLeverDown", 44L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithButton", 45L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithSwitch", 46L, WallType.SolidPart, false, Wall.levels(10, 11)); // Can be pressed or released
        eobGlobalData.createWall("hiveWallWithStar", 48L, WallType.SolidPart, false, Wall.levels(10, 11)); // Level 11: Celestial star
        eobGlobalData.createWall("hiveWallWithRift", 49L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithManacles", 50L, WallType.SolidPart, false, Wall.levels(10, 11));
        eobGlobalData.createWall("hiveWallWithButton2", 60L, WallType.SolidPart, false, Wall.levels(10, 11));

        // Sanctum levels
        eobGlobalData.createWall("sanctumDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumDoor", 8L, WallType.DoorPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumPedestal", 22L, WallType.SquarePart, true, Wall.levels(12));
        eobGlobalData.createWall("sanctumPedestalWithEye", 26L, WallType.SquarePart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumPedestalWithEyeDetector", 27L, WallType.SquarePart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithFiringMechanism", 30L, WallType.SolidPart, false, Wall.levels(12)); // Level 12: Fireball
        eobGlobalData.createWall("sanctumWallWithSecretButtonSmall", 31L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithSecretButtonTiny", 33L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallPortalLevelOrb", 37L, WallType.SolidPart, false, Wall.levels(12)); // Level 12 - missing Stone Orb
        eobGlobalData.createWall("sanctumWallWithLamp", 38L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumSpikeTrap", 39L, WallType.SquarePart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithText", 41L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithKeyHole", 42L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithLampSmoke", 43L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithButton", 45L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithButtonPressed", 46L, WallType.SolidPart, false, Wall.levels(12));
        eobGlobalData.createWall("sanctumWallWithButton2", 60L, WallType.SolidPart, false, Wall.levels(12));
    }
}
