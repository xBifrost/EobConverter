package org.eob;

import org.eob.enums.DescriptionMergeType;
import org.eob.enums.GameSupportType;
import org.eob.enums.WallType;
import org.eob.file.dat.ItemTypeDatFile;
import org.eob.model.ItemType;
import org.eob.model.Monster;
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
    public static void init(ItemTypeDatFile itemTypeDatFile) {
        //--------------
        // --- Items ---
        //--------------

        ItemType Axe = itemTypeDatFile.itemTypeList.get(0x00L).setExternalData("axe", "Axe");
        ItemType LongSword = itemTypeDatFile.itemTypeList.get(0x01L).setExternalData("long_sword", "Long Sword");
        ItemType ShortSword = itemTypeDatFile.itemTypeList.get(0x02L).setExternalData("short_sword", "Short Sword");
        ItemType OrbOfPower = itemTypeDatFile.itemTypeList.get(0x03L).setExternalData("orb_of_power", "Orb of Power");
        ItemType Dart = itemTypeDatFile.itemTypeList.get(0x04L).setExternalData("dart", "Dart");
        ItemType Dagger = itemTypeDatFile.itemTypeList.get(0x05L).setExternalData("dagger", "Dagger");
        ItemType DwarvenPotion = itemTypeDatFile.itemTypeList.get(0x06L).setExternalData("dwarven_potion", "Dwarven potion");
        ItemType Bow = itemTypeDatFile.itemTypeList.get(0x07L).setExternalData("bow", "Bow");
        ItemType Spear = itemTypeDatFile.itemTypeList.get(0x09L).setExternalData("spear", "Spear");
        ItemType Halberd = itemTypeDatFile.itemTypeList.get(0x0AL).setExternalData("halberd", "Halberd"); // there is no other long weapon in Grimrock, so spear will have to do
        ItemType Mace = itemTypeDatFile.itemTypeList.get(0x0BL).setExternalData("mace", "Mace");
        ItemType Flail = itemTypeDatFile.itemTypeList.get(0x0CL).setExternalData("flail", "Flail");
        ItemType Staff = itemTypeDatFile.itemTypeList.get(0x0DL).setExternalData("staff", "Staff"); // Not present in the dungeon, but defined as 4th item (offset=0x2c)
        ItemType Sling = itemTypeDatFile.itemTypeList.get(0x0EL).setExternalData("sling", "Sling");
        ItemType Dart2 = itemTypeDatFile.itemTypeList.get(0x0FL).setExternalData("dart", "Dart");
        ItemType Arrow = itemTypeDatFile.itemTypeList.get(0x10L).setExternalData("arrow", "Arrow");
        ItemType Rock = itemTypeDatFile.itemTypeList.get(0x12L).setExternalData("rock", "Rock");
        ItemType BandedArmor = itemTypeDatFile.itemTypeList.get(0x13L).setExternalData("banded_armor", "Banded Armor");
        ItemType ChainMail = itemTypeDatFile.itemTypeList.get(0x14L).setExternalData("chain_mail", "Chain Mail");
        ItemType DwarvenHelmet = itemTypeDatFile.itemTypeList.get(0x15L).setExternalData("dwarven_helmet", "Dwarven Helmet");
        ItemType LeatherArmor = itemTypeDatFile.itemTypeList.get(0x16L).setExternalData("leather_armor", "Leather Armor");
        ItemType PlateMail = itemTypeDatFile.itemTypeList.get(0x18L).setExternalData("plate_mail", "Plate Mail");
        ItemType ScaleMail = itemTypeDatFile.itemTypeList.get(0x19L).setExternalData("scale_mail", "Scale Mail");
        ItemType Shield = itemTypeDatFile.itemTypeList.get(0x1BL).setExternalData("shield", "Shield");
        ItemType LockPicks = itemTypeDatFile.itemTypeList.get(0x1CL).setExternalData("lock_picks", "Lock picks");
        ItemType SpellBook = itemTypeDatFile.itemTypeList.get(0x1dL).setExternalData("spell_book", "Spell Book");
        ItemType HolySymbol = itemTypeDatFile.itemTypeList.get(0x1EL).setExternalData("holy_symbol", "Holy Symbol"); // there's nothing that looks like ankh symbol
        ItemType Rations = itemTypeDatFile.itemTypeList.get(0x1FL).setExternalData("rations", "Rations");
        ItemType LeatherBoots = itemTypeDatFile.itemTypeList.get(0x20L).setExternalData("leather_boots", "Leather boots");
        ItemType Bones = itemTypeDatFile.itemTypeList.get(0x21L).setExternalData("bones", "Bones");
        ItemType MageScroll = itemTypeDatFile.itemTypeList.get(0x22L).setExternalData("mage_scroll", "Mage scroll");
        ItemType ClericScroll = itemTypeDatFile.itemTypeList.get(0x23L).setExternalData("cleric_scroll", "Cleric scroll");
        ItemType TextScroll = itemTypeDatFile.itemTypeList.get(0x24L).setExternalData("scroll", "Text scroll");
        ItemType Stone = itemTypeDatFile.itemTypeList.get(0x25L).setExternalData("stone", "Stone");
        ItemType Key = itemTypeDatFile.itemTypeList.get(0x26L).setExternalData("key", "Key"); // Different keys
        ItemType Potion = itemTypeDatFile.itemTypeList.get(0x27L).setExternalData("potion", "Potion");
        ItemType Gem = itemTypeDatFile.itemTypeList.get(0x28L).setExternalData("gem", "Gem"); // Gems of different colors (red and blue)
        ItemType Robe = itemTypeDatFile.itemTypeList.get(0x29L).setExternalData("robe", "Robe");
        ItemType Ring = itemTypeDatFile.itemTypeList.get(0x2AL).setExternalData("ring", "Ring"); // There are no rings in Grimrock, we need to replace them with bracelets
        ItemType MedallionOfAdornment = itemTypeDatFile.itemTypeList.get(0x2CL).setExternalData("medallion_of_adornment", "Medallion of Adornment");
        ItemType Bracers = itemTypeDatFile.itemTypeList.get(0x2BL).setExternalData("bracers", "Bracers");
        ItemType Medallion = itemTypeDatFile.itemTypeList.get(0x2EL).setExternalData("medallion", "Stone Medallion"); // Actual name Luck Stone Medallion
        ItemType Ring2 = itemTypeDatFile.itemTypeList.get(0x2FL).setExternalData("ring2", "Ring"); // This one has no power (found in lv 4, x=11,y=29)
        ItemType Wand = itemTypeDatFile.itemTypeList.get(0x30L).setExternalData("wand", "Wand");
        ItemType KenkuEgg = itemTypeDatFile.itemTypeList.get(0x31L).setExternalData("egg", "Kenku Egg"); // There is really nothing in Grimrock
        // that even vaguely resembles an egg. Slime bell is at least round :)

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
        SubItemType DwarvenPotionHealing = new SubItemType(8, DwarvenPotion, "healing", "Healing", "");

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

        // Bones (sorted by level they appear on)
        SubItemType HalflingBones = new SubItemType(6, Bones, "halfling_tod", "Halfling", DescriptionMergeType.Prefix, "Tod Uphill"); // resurrects Tod Uphill (from level 1)
        SubItemType HumanBones1 = new SubItemType(1, Bones, "human_anya", "Human", DescriptionMergeType.Prefix, "Anya"); // resurrects Anya (from level 3)
        SubItemType HumanBones4 = new SubItemType(4, Bones, "human_ileria", "Human", DescriptionMergeType.Prefix, "Ileria"); // resurrects Ileria (from level 7)
        SubItemType HumanBones2 = new SubItemType(2, Bones, "human_beohram", "Human", DescriptionMergeType.Prefix, "Beohram"); // resurrects Beohram (from level 9)
        SubItemType ElfBones = new SubItemType(5, Bones, "elf_tyrra", "Elf", DescriptionMergeType.Prefix, "Tyrra"); // resurrects Tyrra (from level 10)
        SubItemType HumanBones3 = new SubItemType(3, Bones, "human_kirath", "Human", DescriptionMergeType.Prefix, "Kirath"); // resurrects Kirath (from level 11)

        // Dart
        SubItemType DartPlus1 = new SubItemType(1, Dart, "plus1", "+1", DescriptionMergeType.Suffix, "");
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
        SubItemType StoneRing = new SubItemType(7, Stone, "ring", "", "");

        // Dagger
        SubItemType DaggerBackstabber = new SubItemType(3, Dagger, "backstabber", "+3", "");
        SubItemType DaggerGuinsoo = new SubItemType(4, Dagger, "guinsoo", "'Guinsoo'", "+4");
        SubItemType DaggerFlica = new SubItemType(5, Dagger, "flicka", "'Flicka'", "");

        // Gem
        SubItemType GemRed = new SubItemType(1, Gem, "red", "Red Gem", "");
        SubItemType GemBlue = new SubItemType(2, Gem, "blue", "Blue Gem", "");

        // Rings
        SubItemType RingProtection2 = new SubItemType(2, Ring, "protection2", "Protection +2", ""); // level 11, (x=27,y=16)
        SubItemType RingProtection3 = new SubItemType(3, Ring, "protection3", "Protection +3", ""); // level 4, (x=6, y=24)

        // Different base type for this ring
        SubItemType BlueRingOfFeatherFall = new SubItemType(3, Ring2, "feather_fall", "Feather Fall", "");
        SubItemType RingOfSustenance = new SubItemType(2, Ring2, "sustenance", "Sustenance", DescriptionMergeType.SuffixWithOf, "");
        SubItemType RingOfWizardry = new SubItemType(1, Ring2, "wizardry", "Wizardry", DescriptionMergeType.SuffixWithOf, "");

        // Medallion
        SubItemType LuckStoneMedallion = new SubItemType(1, Medallion, "luck_stone", "Luck Stone", DescriptionMergeType.Prefix, "");

        // Longsword
        SubItemType LongSwordNightStalker = new SubItemType(3, LongSword, "night_stalker", "'Night Stalker'", DescriptionMergeType.Replace, "+3");
        SubItemType LongSwordSlasher = new SubItemType(4, LongSword, "slasher", "'Slasher'", DescriptionMergeType.Replace, "+4");
        SubItemType LongSwordSeverious = new SubItemType(5, LongSword, "severious", "'Severious'", DescriptionMergeType.Replace, "+5");

        // Sword
        SubItemType ShortSwordSlicer = new SubItemType(3, ShortSword, "slicer", "'Slicer'", DescriptionMergeType.Replace, "+3");

        // Axe
        SubItemType AxeDrowCleaver = new SubItemType(3, Axe, "drow_cleaver", "'Drow Cleaver'", DescriptionMergeType.Replace, "");
        SubItemType AxeCursed3 = new SubItemType(-3, Axe, "cursed3", "Cursed -3", DescriptionMergeType.Suffix, "");

        // Mace
        SubItemType MacePlus3 = new SubItemType(3, Mace, "plus3", "+3", "");

        // Rock
        SubItemType GlowingRock = new SubItemType(1, Rock, "glowing", "", "glowing");
        SubItemType MossyRock = new SubItemType(2, Rock, "mossy", "Mossy", DescriptionMergeType.Prefix, "");

        // Halberd
        SubItemType HalberdChieftain = new SubItemType(5, Halberd, "chieftain", "Chieftain", DescriptionMergeType.Prefix, "");

        // Sling
        SubItemType SlingCursed3 = new SubItemType(-3, Sling, "cursed3", "Cursed -3", DescriptionMergeType.Suffix, "");

        // Shield
        SubItemType DwarvenShield = new SubItemType(1, Shield, "dwarven", "Dwarven", DescriptionMergeType.Prefix, "");
        SubItemType DrowShield = new SubItemType(3, Shield, "drow", "Drow", DescriptionMergeType.Prefix, "+3");

        // Plate mail
        SubItemType PlateMailCursed3 = new SubItemType(-3, PlateMail, "cursed3", "Great Beauty", DescriptionMergeType.SuffixWithOf, "-3"); // cursed -3

        // Banded Armor
        SubItemType BandedArmor3 = new SubItemType(3, BandedArmor, "plus3", "", "+3");

        // Robe
        SubItemType RobeOfProtection5 = new SubItemType(5, Robe, "protection5", "Protection", DescriptionMergeType.SuffixWithOf, "+5");

        // Bracers
        SubItemType BracersOfDefense2 = new SubItemType(2, Bracers, "defense2", "Defense", DescriptionMergeType.SuffixWithOf, "+2");
        SubItemType ElvenBracersOfDefense = new SubItemType(3, Bracers, "defense3", "Elven Bracers of Defense", DescriptionMergeType.Replace, "+3");

        // Kenku Eggs (I don't know how the egg types differ from each other)
        SubItemType KenkuEgg10 = new SubItemType(10, KenkuEgg, "10", "", "");
        SubItemType KenkuEgg20 = new SubItemType(20, KenkuEgg, "20", "", "");
        SubItemType KenkuEgg30 = new SubItemType(30, KenkuEgg, "30", "", "");
        SubItemType KenkuEgg40 = new SubItemType(40, KenkuEgg, "40", "", "");

        // Orb of Power
        // orb of power from level 11 (I think it looks like the regular one and the different subtype is used to just distinguish between them)
        SubItemType OrbOfPowerLevel11 = new SubItemType(1, OrbOfPower, "_from_level11", "", "");

        // The following items are not present in the dungeons. I do not know what they really are, so their descriptions and names
        // are just guesses
        SubItemType RationDoubleIron = new SubItemType(100, Rations, "iron_double", "", ""); // 100 type suggest that is is twice as good as iron (50)
        SubItemType LongSword1 = new SubItemType(1, LongSword, "sharp", "Sharp", DescriptionMergeType.Prefix, "+1");
        SubItemType LongSwordCursed2 = new SubItemType(-2, LongSword, "cursed2", "Unlucky", DescriptionMergeType.Prefix, "-2");
        SubItemType UnknownBones = new SubItemType(7, Bones, "not_used_in_dungeon", "Mysterious", DescriptionMergeType.Prefix, ""); // Not present in the dungeon (item offset=0x1D0 uses that subtype)
        SubItemType UnknownTextScroll = new SubItemType(46, TextScroll, "not_used_in_dungeon", "not found anywhere in the dungeon", DescriptionMergeType.Ignore, ""); // (item offset=0x1EC)

        //----------------
        // --- Monster ---
        //----------------

        // Level 1
        Monster.createMonster("kobold", 0, "eob_kobold");
        Monster.createMonster("leech", 1, "eob_leech");
        // Level 2
        Monster.createMonster("skeleton", 2, "eob_skeleton");
        Monster.createMonster("zombie", 3, "eob_zombie");
        // Level 3
        Monster.createMonster("kuotoa", 4, "eob_kuotoa");
        Monster.createMonster("flind", 5, "eob_flind");
        // Level 4
        Monster.createMonster("spider", 6, "eob_spider");
        // Level 5
        Monster.createMonster("dwarf", 7, "eob_dwarf");
        // Level 6
        Monster.createMonster("kenku", 16, "eob_kenku");
        Monster.createMonster("mage", 11, "eob_mage");
        // Level 7
        Monster.createMonster("skelwar", 8, "eob_skelwar");
        Monster.createMonster("drowelf", 12, "eob_drowelf");
        // Level 8
        Monster.createMonster("drider", 20, "eob_drider"); // 20
        Monster.createMonster("hellhnd", 15, "eob_hellhnd"); // 15
        // Level 9
        Monster.createMonster("rust", 9, "eob_rust"); // 9?
        Monster.createMonster("disbeast", 18, "eob_disbeast"); // 18?
        // Level 10
        Monster.createMonster("shindia", 14, "eob_shindia");
        Monster.createMonster("mantis", 10, "eob_mantis");
        // Level 11
        Monster.createMonster("xorn", 19, "eob_xorn"); // 19?
        Monster.createMonster("mflayer", 17, "eob_mflayer"); // 17?
        // Level 12
        Monster.createMonster("xanath", 21, "eob_xanath");
        Monster.createMonster("golem", 13, "eob_golem");


        //-------------
        // --- Wall ---
        //-------------
        // All levels
        Wall.createWall("empty", 0L, WallType.Empty, Arrays.asList(GameSupportType.Eob1));
        Wall.createWall("wall1", 1L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        Wall.createWall("wall2", 2L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1));
        Wall.createWall("emptyMonsterBlock", 25L, WallType.SquarePart, false, null); // Block monsters to move trough
        Wall.createWall("teleporter", 52L, WallType.SquarePart, false, null);
        Wall.createWall("wallWithDoor", 58L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look
        Wall.createWall("wallWithNet", 59L, WallType.SolidPart, Arrays.asList(GameSupportType.Eob1)); // Side look

        // Sewers levels
        Wall.createWall("sewersDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoor", 8L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoorOpened", 12L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoorPortcullisWithButton", 13L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoorPortcullisThrowableThrough", 18L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersLevelUp", 23L, WallType.SquarePart, false, Wall.levels(1, 2, 3)); // Ladder
        Wall.createWall("sewersLevelDown", 24L, WallType.SquarePart, false, Wall.levels(1, 2, 3)); // Ladder
        Wall.createWall("sewersEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoorStacked", 30L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersDoorPortcullisStacked", 31L, WallType.DoorPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithEyeKeyHole", 32L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithJewelKeyHole", 35L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithSecretButtonLarge", 39L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Brick
        Wall.createWall("sewersWallWithKeyHoleButton", 41L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Key hole -> button
        Wall.createWall("sewersWallWithDrainage", 43L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithDrainageBent1", 44L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Is it the same as 62???
        Wall.createWall("sewersWallWithButtonSmall", 50L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithText", 51L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithSilverKeyHole", 53L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithGoldKeyHole", 54L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithText2", 57L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Read able text (Rats ->)
        Wall.createWall("sewersWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithButtonPressed", 61L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithDrainageBent2", 62L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Is it the same as 44???
        Wall.createWall("sewersWallWithPipe", 63L, WallType.SolidPart, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallIllusion", 64L, WallType.Wall, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallWithRune", 65L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Rune on the wall
        Wall.createWall("sewersWallWithDaggerKeyHole", 66L, WallType.SolidPart, false, Wall.levels(1, 2, 3)); // Dagger is used as key
        Wall.createWall("sewersWallIllusionWithRune", 67L, WallType.Wall, false, Wall.levels(1, 2, 3));
        Wall.createWall("sewersWallPortalInactive", 68L, WallType.SolidPart, false, Wall.levels(2)); // Level 2 - debug area left by developers
        Wall.createWall("sewersCaveIn", 69L, WallType.SquarePart, false, Wall.levels(1, 2, 3));

        // Ruins levels
        Wall.createWall("ruinsDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsDoor", 8L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsLevelUp", 23L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsLevelDown", 24L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsDoorStacked", 30L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithStatueLever", 32L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithSmallStatue", 34L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithChain", 36L, WallType.SolidPart, false, Wall.levels(4, 5, 6)); // Chain on the wall -> Type of the lever
        Wall.createWall("ruinsWallWithFiringMechanism", 38L, WallType.SolidPart, false, Wall.levels(4, 5, 6)); // Level 6: Darts
        Wall.createWall("ruinsWallWithSecretButtonTiny", 39L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsNet", 40L, WallType.DoorPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsNetTorn", 41L, WallType.DoorPart, false, Wall.levels(4, 5, 6)); // NetTorn (4 level)
        Wall.createWall("ruinsWallPortalLevelNecklace", 43L, WallType.SolidPart, false, Wall.levels(5)); // Level 5 - Stone Necklace
        Wall.createWall("ruinsWallPortalLevelScepter", 45L, WallType.SolidPart, false, Wall.levels(6)); // Level 6 - Stone Scepter
        Wall.createWall("ruinsWallPortalLevelAmulet", 46L, WallType.SolidPart, false, Wall.levels(4)); // Level 4 - Stone Amulet
        Wall.createWall("ruinsWallWithText", 51L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithKeyHole", 53L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithKeyHole2", 54L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallIllusion", 64L, WallType.Wall, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallIllusionWithStatue", 66L, WallType.Wall, false, Wall.levels(4, 5, 6));
        Wall.createWall("ruinsWallIllusionWithRune", 67L, WallType.Wall, false, Wall.levels(4, 5, 6));

        // Drow levels
        Wall.createWall("drowDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowDoor", 8L, WallType.DoorPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowLevelUp", 23L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowLevelDown", 24L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithFiringMechanismFireball2", 32L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // Level 7: Fireball
        Wall.createWall("drowWallWithText2", 33L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // It is written, the key lies on the other side.
        Wall.createWall("drowWallWithGemKeyHole", 36L, WallType.SolidPart, false, Wall.levels(7, 8, 9)); // Gem is used as key
        Wall.createWall("drowWallWithSecretButtonTiny", 39L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallPortalLevelCross", 40L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Cross
        Wall.createWall("drowWallPortalLevelNecklace", 41L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Necklace
        Wall.createWall("drowWallPortalLevelDaggerL7", 43L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Dagger
        Wall.createWall("drowWallPortalLevelDaggerL9", 43L, WallType.SolidPart, false, Wall.levels(9)); // Level 9 - missing Stone Dagger
        Wall.createWall("drowWallPortalLevelAmulet", 44L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Stone Amulet
        Wall.createWall("drowWallPortalLevelScepter", 45L, WallType.SolidPart, false, Wall.levels(8)); // Level 8 - missing Stone Scepter
        Wall.createWall("drowWallPortalLevelGem", 46L, WallType.SolidPart, false, Wall.levels(7)); // Level 7 - missing Gem
        Wall.createWall("drowWallThrowable", 47L, WallType.Wall, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithFiringMechanismFireball", 48L, WallType.SolidPart, false, Wall.levels(7)); // Level 7: Fireball
        Wall.createWall("drowWallWithFiringMechanismDartsL9", 48L, WallType.SolidPart, false, Wall.levels(9)); // Level 9: Darts
        Wall.createWall("drowWallWithFiringMechanismDartsL8", 49L, WallType.SolidPart, false, Wall.levels(8)); // Level 7: Darts
        Wall.createWall("drowWallWithFiringMechanismMagicMissile", 49L, WallType.SolidPart, false, Wall.levels(9)); // Level 9: MagicMissile
        Wall.createWall("drowWallWithText", 51L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithKeyHole", 53L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithKeyHole2", 54L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithLever", 55L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallIllusionWithSpider", 57L, WallType.Wall, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallWithButton", 60L, WallType.SolidPart, false, Wall.levels(7, 8, 9));
        Wall.createWall("drowWallIllusion", 64L, WallType.Wall, false, Wall.levels(7, 8, 9));

        // Hive levels
        Wall.createWall("hiveDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveDoor", 8L, WallType.DoorPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveLevelUp", 23L, WallType.SquarePart, false, Wall.levels(10, 11));
        Wall.createWall("hiveLevelDown", 24L, WallType.SquarePart, false, Wall.levels(10, 11));
        Wall.createWall("hiveEmptyCeilingShaft", 26L, WallType.SquarePart, false, Wall.levels(10, 11));
        Wall.createWall("hiveEmptyPit", 27L, WallType.SquarePart, false, Wall.levels(10, 11));
        Wall.createWall("hivePressurePlate", 28L, WallType.SquarePart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithSecretButtonSmall", 31L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithSecretButtonTiny", 33L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallPortalLevelCross", 36L, WallType.SolidPart, false, Wall.levels(11)); // Level 11 - missing Stone Cross
        Wall.createWall("hiveWallPortalLevelOrb", 37L, WallType.SolidPart, false, Wall.levels(11)); // Level 11 - missing Stone Orb
        Wall.createWall("hiveWallPortalLevelScepter", 38L, WallType.SolidPart, false, Wall.levels(10)); // Level 10 - missing Stone Scepter
        Wall.createWall("hiveWallPortalLevelRing", 39L, WallType.SolidPart, false, Wall.levels(10)); // Level 10 - missing Stone Ring
        Wall.createWall("hiveWallWithText", 41L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithKeyHole", 42L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithLeverUp", 43L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithLeverDown", 44L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithButton", 45L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithSwitch", 46L, WallType.SolidPart, false, Wall.levels(10, 11)); // Can be pressed or released
        Wall.createWall("hiveWallWithStar", 48L, WallType.SolidPart, false, Wall.levels(10, 11)); // Level 11: Celestial star
        Wall.createWall("hiveWallWithRift", 49L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithManacles", 50L, WallType.SolidPart, false, Wall.levels(10, 11));
        Wall.createWall("hiveWallWithButton2", 60L, WallType.SolidPart, false, Wall.levels(10, 11));

        // Sanctum levels
        Wall.createWall("sanctumDoorWithButton", 3L, WallType.DoorPart, false, Wall.levels(12));
        Wall.createWall("sanctumDoor", 8L, WallType.DoorPart, false, Wall.levels(12));
        Wall.createWall("sanctumPedestal", 22L, WallType.SquarePart, false, Wall.levels(12));
        Wall.createWall("sanctumPedestalWithEye", 26L, WallType.SquarePart, false, Wall.levels(12));
        Wall.createWall("sanctumPedestalWithEyeDetector", 27L, WallType.SquarePart, false, Wall.levels(12));
        Wall.createWall("sanctumPressurePlate", 28L, WallType.SquarePart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithAlcove", 29L, WallType.SolidPart, true, Wall.levels(12));
        Wall.createWall("sanctumWallWithFiringMechanism", 30L, WallType.SolidPart, false, Wall.levels(12)); // Level 12: Fireball
        Wall.createWall("sanctumWallWithSecretButtonSmall", 31L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithSecretButtonTiny", 33L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallPortalLevelOrb", 37L, WallType.SolidPart, false, Wall.levels(12)); // Level 12 - missing Stone Orb
        Wall.createWall("sanctumWallWithLamp", 38L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumSpikeTrap", 39L, WallType.SquarePart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithText", 41L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithKeyHole", 42L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithLampSmoke", 43L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithButton", 45L, WallType.SolidPart, false, Wall.levels(12));
        Wall.createWall("sanctumWallWithButton2", 60L, WallType.SolidPart, false, Wall.levels(12));
    }
}
