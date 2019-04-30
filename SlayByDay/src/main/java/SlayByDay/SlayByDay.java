package SlayByDay;


import SlayByDay.relics.Anima;

import SlayByDay.powers.interfaces.OnPostPotionUsePower;
import SlayByDay.powers.interfaces.OnSwitchPower;
import SlayByDay.relics.*;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import SlayByDay.cards.*;
import SlayByDay.cards.switchCards.*;
import SlayByDay.characters.TheMedium;
import SlayByDay.events.IdentityCrisisEvent;
import SlayByDay.potions.PlaceholderPotion;

// Given custom relics
//import SlayByDay.relics.BottledPlaceholderRelic;
//import SlayByDay.relics.DefaultClickableRelic;
//import SlayByDay.relics.PlaceholderRelic2;

// Custom Relics (Joey)
//import SlayByDay.relics.MarkOfTheOther;
import SlayByDay.relics.SpiritualCharm;
import SlayByDay.relics.SpiritualCrystal;

import SlayByDay.util.TextureLoader;
import SlayByDay.variables.DefaultCustomVariable;
import SlayByDay.variables.DefaultSecondMagicNumber;

import java.nio.charset.StandardCharsets;


/*
 * Welcome to this mildly over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 3 types of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. Happy modding!
 */

@SpireInitializer
public class SlayByDay implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostPotionUseSubscriber,
        IOnSwitch {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(SlayByDay.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Medium";
    private static final String AUTHOR = "CMU Game Creation Society";
    private static final String DESCRIPTION = "A mod themed around human and spirit duality.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color MEDIUM_PURPLE = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_MEDIUM_PURPLE = "SlayByDayResources/images/512/cardBG_attack.png";
    private static final String SKILL_MEDIUM_PURPLE = "SlayByDayResources/images/512/cardBG_skill.png";
    private static final String POWER_MEDIUM_PURPLE = "SlayByDayResources/images/512/cardBG_power.png";

    private static final String ENERGY_ORB_MEDIUM_PURPLE = "SlayByDayResources/images/512/card_medium_purple_orb.png";
    private static final String CARD_ENERGY_ORB = "SlayByDayResources/images/512/card_small_purple_orb.png";

    private static final String ATTACK_MEDIUM_PURPLE_PORTRAIT = "SlayByDayResources/images/1024/cardBG_attack.png";
    private static final String SKILL_MEDIUM_PURPLE_PORTRAIT = "SlayByDayResources/images/1024/cardBG_skill.png";
    private static final String POWER_MEDIUM_PURPLE_PORTRAIT = "SlayByDayResources/images/1024/cardBG_power.png";
    private static final String ENERGY_ORB_MEDIUM_PURPLE_PORTRAIT = "SlayByDayResources/images/1024/card_medium_purple_orb.png";

    // Character assets
    private static final String THE_DEFAULT_BUTTON = "SlayByDayResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "SlayByDayResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_MEDIUM_SHOULDER_1 = "SlayByDayResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_MEDIUM_SHOULDER_2 = "SlayByDayResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_MEDIUM_CORPSE = "SlayByDayResources/images/char/defaultCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "SlayByDayResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String THE_MEDIUM_SKELETON_ATLAS = "SlayByDayResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_MEDIUM_SKELETON_JSON = "SlayByDayResources/images/char/defaultCharacter/skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_M_PURPLE, INITIALIZE =================

    public SlayByDay() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        setModID("SlayByDay");

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheMedium.Enums.COLOR_M_PURPLE.toString());

        BaseMod.addColor(TheMedium.Enums.COLOR_M_PURPLE, MEDIUM_PURPLE, MEDIUM_PURPLE, MEDIUM_PURPLE,
                MEDIUM_PURPLE, MEDIUM_PURPLE, MEDIUM_PURPLE, MEDIUM_PURPLE,
                ATTACK_MEDIUM_PURPLE, SKILL_MEDIUM_PURPLE, POWER_MEDIUM_PURPLE, ENERGY_ORB_MEDIUM_PURPLE,
                ATTACK_MEDIUM_PURPLE_PORTRAIT, SKILL_MEDIUM_PURPLE_PORTRAIT, POWER_MEDIUM_PURPLE_PORTRAIT,
                ENERGY_ORB_MEDIUM_PURPLE_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) {
        if (ID.equals("theDefault")) {
            throw new RuntimeException("Go to your constructor in your class with SpireInitializer and change your mod ID from \"theDefault\"");
        } else if (ID.equals("theDefaultDev")) {
            modID = "theDefault";
        } else {
            modID = ID;
        }
    }

    public static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        String packageName = SlayByDay.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals("theDefaultDev")) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException("Rename your theDefault folder to match your mod ID! " + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException("Rename your theDefaultResources folder to match your mod ID! " + getModID() + "Resources");
            }
        }
    }
    // ====== YOU CAN EDIT AGAIN ======


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing SlayByDay Mod. Hi. =========================");
        SlayByDay slaybyday = new SlayByDay();
        logger.info("========================= /SlayByDay Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_M_PURPLE, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheMedium.Enums.THE_MEDIUM.toString());

        BaseMod.addCharacter(new TheMedium("the Medium", TheMedium.Enums.THE_MEDIUM),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheMedium.Enums.THE_MEDIUM);

        receiveEditPotions();
        logger.info("Added " + TheMedium.Enums.THE_MEDIUM.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("SlayByDay doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");

    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================


    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_MEDIUM".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheMedium.Enums.THE_MEDIUM);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new Anima(), TheMedium.Enums.COLOR_M_PURPLE);
//        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheMedium.Enums.COLOR_M_PURPLE);
//        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheMedium.Enums.COLOR_M_PURPLE);

        /*
         * CUSTOM RELICS START HERE
         */
        // BaseMod.addRelicToCustomPool(new MarkOfTheOther(), TheModal.Enums.COLOR_M_PURPLE);
        BaseMod.addRelicToCustomPool(new SpiritualCharm(), TheMedium.Enums.COLOR_M_PURPLE);
        BaseMod.addRelicToCustomPool(new SpiritualCrystal(), TheMedium.Enums.COLOR_M_PURPLE);


        // This adds a relic to the Shared pool. Every character can find this relic.
//        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
//        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");

        // Subscribe to relic based listeners
        Anima.subscribe(this);
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultCommonSkill());
//        BaseMod.addCard(new OrbSkill());
//        BaseMod.addCard(new DefaultSecondMagicNumberSkill());
//        BaseMod.addCard(new DefaultAttackWithVariable());
//        BaseMod.addCard(new DefaultCommonPower());
//        BaseMod.addCard(new DefaultUncommonSkill());
//        BaseMod.addCard(new DefaultUncommonAttack());
//        BaseMod.addCard(new DefaultUncommonPower());
//        BaseMod.addCard(new DefaultRareAttack());
//        BaseMod.addCard(new DefaultRareSkill());
//        BaseMod.addCard(new DefaultRarePower());

        // Dom's cards
        BaseMod.addCard(new PredictionFeatherShieldSwitch());
        BaseMod.addCard(new DefensiveManeuversOffensiveRushSwitch());
        BaseMod.addCard(new EvilEyePiercingGazeSwitch());
        BaseMod.addCard(new MagicDefenseSharpenFeathersSwitch());
        BaseMod.addCard(new ClawSlashClawSmashSwitch());
        BaseMod.addCard(new RecollectForgetSwitch());
        BaseMod.addCard(new HoneLacerateSwitch());
        BaseMod.addCard(new PunishmentFurySwitch());
        BaseMod.addCard(new SplashDamagePowerUpSwitch());
        BaseMod.addCard(new Harmony());
        BaseMod.addCard(new TacticalRetreatBlitzSwitch());
        BaseMod.addCard(new BolsterGuardSwitch());
        BaseMod.addCard(new FlyRoostSwitch());
        BaseMod.addCard(new ResupplyResurgenceSwitch());
        BaseMod.addCard(new InfinityFinalitySwitch());
        BaseMod.addCard(new OwlsHowl());
        BaseMod.addCard(new TranquilizePreyOnTheWeakSwitch());
        BaseMod.addCard(new DailyCommuneBideSwitch());
        BaseMod.addCard(new ReinvestSuddenStrikeSwitch());
        BaseMod.addCard(new RenewFeatherBashSwitch());

        // Joey's cards
        BaseMod.addCard(new PossessionExpulsionSwitch());

        //Carter's card
        BaseMod.addCard(new PrecisionDevilishLuckSwitch());


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
        UnlockTracker.unlockCard(DefaultCommonSkill.ID);
//        UnlockTracker.unlockCard(OrbSkill.ID);
//        UnlockTracker.unlockCard(DefaultSecondMagicNumberSkill.ID);
//        UnlockTracker.unlockCard(DefaultAttackWithVariable.ID);
//        UnlockTracker.unlockCard(DefaultCommonPower.ID);
//        UnlockTracker.unlockCard(DefaultUncommonSkill.ID);
//        UnlockTracker.unlockCard(DefaultUncommonAttack.ID);
//        UnlockTracker.unlockCard(DefaultUncommonPower.ID);
//        UnlockTracker.unlockCard(DefaultRareAttack.ID);
//        UnlockTracker.unlockCard(DefaultRareSkill.ID);
//        UnlockTracker.unlockCard(DefaultRarePower.ID);

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-Orb-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/SlayByDay-UI-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/SlayByDay-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    


    // ================ EXTRANEOUS LISTENERS ===================

    @Override
    public void OnSwitch(boolean Reason_Mode) {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnSwitchPower) {
                ((OnSwitchPower) p).onSwitch(Reason_Mode);
            }
        }

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower p : monster.powers) {
                if (p instanceof OnSwitchPower) {
                    ((OnSwitchPower) p).onSwitch(Reason_Mode);
                }
            }
        }
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnPostPotionUsePower) {
                ((OnPostPotionUsePower) p).onPostPotionUse(abstractPotion);
            }
        }

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower p : monster.powers) {
                if (p instanceof OnPostPotionUsePower) {
                    ((OnPostPotionUsePower) p).onPostPotionUse(abstractPotion);
                }
            }
        }
    }

    // ================ /EXTRANEOUS LISTENERS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

}
