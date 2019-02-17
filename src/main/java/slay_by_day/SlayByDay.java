package slay_by_day;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.nio.charset.StandardCharsets;

import slay_by_day.cards.*;
import slay_by_day.relics.*;

import static basemod.BaseMod.addRelic;
import static basemod.BaseMod.addRelicToCustomPool;


@SpireInitializer
public class SlayByDay implements PostExhaustSubscriber,
        PostBattleSubscriber, PostDungeonInitializeSubscriber,
        EditStringsSubscriber, EditCardsSubscriber, EditRelicsSubscriber {

    private static final String MODNAME = "SlayByDay";
    private static final String AUTHOR = "CMU Game Creation Society";
    private static final String DESCRIPTION = "A complete expansion with a new unique character.";

    // Path variables
    public static final String IMG_PATH = "img/";
    public static final String LOCALIZATION_PATH = "localization/";

    public static final String RELIC_IMG_PATH = IMG_PATH + "relics/";

    public static final String CARD_STRINGS_PATH = LOCALIZATION_PATH + "cards.json";
    public static final String RELIC_STRINGS_PATH = LOCALIZATION_PATH + "relics.json";


    private int count, totalCount;

    public SlayByDay() {
        BaseMod.subscribe(this);
        resetCounts();
    }

    public static void initialize() {
        new SlayByDay();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new CartersModTheCard());
    }

    private void resetCounts() {
        totalCount = count = 0;
    }

    @Override
    public void receivePostExhaust(AbstractCard c) {
        count++;
        totalCount++;
    }

    @Override
    public void receivePostBattle(AbstractRoom r) {
        System.out.println(count + " cards were exhausted this battle, " +
                totalCount + " cards have been exhausted so far this act.");
        count = 0;
    }

    @Override
    public void receivePostDungeonInitialize() {
        resetCounts();
    }

    @Override
    public void receiveEditStrings() {
        System.out.println("Start receiveEditStrings");

        // RelicStrings

        BaseMod.loadCustomStringsFile(RelicStrings.class, "SlayByDay/localization/relics.json");

//        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/relics.json");

//        String relicStrings = Gdx.files.internal(RELIC_STRINGS_PATH).readString(
//                String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings

        BaseMod.loadCustomStringsFile(CardStrings.class, "SlayByDay/localization/cards.json");

//        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/cards.json");

//        String cardStrings = Gdx.files.internal(CARD_STRINGS_PATH).readString(
//                String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        System.out.println("Finish receiveEditStrings");
    }


    @Override
    public void receiveEditRelics() {
        // Universal relics
//        addRelic(AbstractRelic relic, RelicType.SHARED);
        // Relics custom to our character
        Pomelo thonk = new Pomelo();
        System.out.println(thonk);
        System.out.println(AbstractCard.CardColor.RED);
        addRelicToCustomPool(thonk, AbstractCard.CardColor.RED);
    }

}