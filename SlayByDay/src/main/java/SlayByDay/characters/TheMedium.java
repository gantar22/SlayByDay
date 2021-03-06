package SlayByDay.characters;

import SlayByDay.relics.Anima;
import SlayByDay.cards.switchCards.DefensiveManeuversOffensiveRushSwitch;
import SlayByDay.relics.IOnSwitch;
import basemod.BaseMod;
import SlayByDay.cards.switchCards.PossessionExpulsionSwitch;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import SlayByDay.SlayByDay;
import SlayByDay.cards.*;

//import SlayByDay.relics.MarkOfTheOther;
import SlayByDay.relics.SpiritualCharm;
// import SlayByDay.relics.SpiritualCrystal;
import basemod.interfaces.PostInitializeSubscriber;

import java.util.ArrayList;

import static SlayByDay.SlayByDay.*;
import static SlayByDay.characters.TheMedium.Enums.COLOR_M_PURPLE;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in SlayByDay-character-Strings.json in the resources

public class TheMedium extends CustomPlayer implements PostInitializeSubscriber  {
    public static final Logger logger = LogManager.getLogger(SlayByDay.class.getName());

    @Override
    public void receivePostInitialize() {
        System.out.println("received post initialize");

    }

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_MEDIUM;
        @SpireEnum(name = "MEDIUM_PURPLE_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_M_PURPLE;
        @SpireEnum(name = "MEDIUM_PURPLE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        @SpireEnum
        public static AbstractCard.CardTags MODE_SWITCH_CARD;
        @SpireEnum
        public static AbstractPower.PowerType PACT;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("MediumCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static SkeletonData skdata;
    private static TextureAtlas atlas;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "SlayByDayResources/images/char/defaultCharacter/orb/layer1.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer2.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer3.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer4.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer5.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer6.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer1d.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer2d.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer3d.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer4d.png",
            "SlayByDayResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public static boolean Reason_Mode = true;

    public static SpineAnimation anim = new SpineAnimation("SlayByDayResources/images/char/defaultCharacter/CombinedAnim/skeleton.atlas","SlayByDayResources/images/char/defaultCharacter/CombinedAnim/skeleton.json",.9f);



    public TheMedium(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "SlayByDayResources/images/char/defaultCharacter/orb/vfx.png", null,
               anim);

        BaseMod.subscribe(this);

        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in SlayByDay.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_MEDIUM_SHOULDER_1, // campfire pose
                THE_MEDIUM_SHOULDER_2, // another campfire pose
                THE_MEDIUM_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  

        loadAnimation(
                anim.atlasUrl,
                anim.skeletonUrl,
                anim.scale);
        atlas = new TextureAtlas(Gdx.files.internal(anim.atlasUrl));
        skdata = new SkeletonJson(this.atlas).readSkeletonData(Gdx.files.internal(anim.skeletonUrl));

        AnimationState.TrackEntry e = state.setAnimation(0, "IdleR", true);
        e.setTime(e.getEndTime() * MathUtils.random());


        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        for(int i = 0; i < 5; i++)
            retVal.add(DefaultCommonAttack.ID);
        for(int i = 0; i < 5; i++)
            retVal.add(DefaultCommonSkill.ID);
        retVal.add(new PossessionExpulsionSwitch().cardID);
        retVal.add(new DefensiveManeuversOffensiveRushSwitch().cardID);

        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();



        retVal.add(SpiritualCharm.ID);
        retVal.add(Anima.ID);
        //retVal.add(PlaceholderRelic2.ID);
        //retVal.add(DefaultClickableRelic.ID);
        // retVal.add(MarkOfTheOther.ID);

        UnlockTracker.markRelicAsSeen(Anima.ID);
        //UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID);
        //UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID);
        // UnlockTracker.markRelicAsSeen(MarkOfTheOther.ID);
        UnlockTracker.markRelicAsSeen(SpiritualCharm.ID);

        Anima.subscribe(new IOnSwitch() {
            @Override
            public void OnSwitch(boolean Reason_Mode) {
                switch_mode(Reason_Mode);
            }
        });

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    public void switch_mode(boolean reason_Mode)
    {
        if(reason_Mode)
        {
            reset_anim();
            AnimationState.TrackEntry e = state.setAnimation(0, "IdleR", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        } else {

            reset_anim();
            AnimationState.TrackEntry e = state.setAnimation(0, "IdleP", true);
            e.setTime(e.getEndTime() * MathUtils.random());

        }
    }

    void reset_anim()
    {
        this.skeleton = new Skeleton(skdata);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skdata);
        this.state = new AnimationState(this.stateData);
    }


    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_M_PURPLE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return SlayByDay.MEDIUM_PURPLE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new DefaultCommonAttack();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheMedium(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return SlayByDay.MEDIUM_PURPLE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return SlayByDay.MEDIUM_PURPLE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
