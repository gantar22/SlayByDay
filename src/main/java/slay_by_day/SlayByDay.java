package slay_by_day;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import slay_by_day.cards.*;

@SpireInitializer
public class SlayByDay implements PostExhaustSubscriber,
        PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCardsSubscriber {

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
}