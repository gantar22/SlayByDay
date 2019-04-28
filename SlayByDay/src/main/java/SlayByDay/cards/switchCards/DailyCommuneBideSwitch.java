package SlayByDay.cards.switchCards;

import SlayByDay.actions.DailyCommuneAction;
import SlayByDay.characters.TheMedium;
import SlayByDay.relics.Anima;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DailyCommuneBideSwitch extends AbstractSwitchByModeCard {

    // WARNING - When tweaking these values, make sure equivalent changes are made in constructor as well
    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("DailyCommune", "Bide", 1, 0, 0, 0, 4, 2, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false),

            new switchCard("Bide", "DailyCommune", 1, 0, 0, 0, 2, 2, 1, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false) );

    public String reasonCardID() {
        return "DailyCommune";
    }
    public String passionCardID() {
      return "Bide";
    }

    public boolean played = false;

    public DailyCommuneBideSwitch(String switchID) {
        super("SlayByDay:DailyCommuneBide", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, DailyCommuneBideSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            this.validateSwitchCardMode(true);
        }
    }

    public DailyCommuneBideSwitch() { this(null); }


    @Override
    public void onMoveToDiscard() {
        if (TheMedium.Reason_Mode && this.currentID == "DailyCommune" && played) {
            played = false;
            AbstractDungeon.player.discardPile.moveToDeck(this, false);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (TheMedium.Reason_Mode) {
            this.flash();
            this.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "DailyCommune":
                played = true;
                AbstractDungeon.actionManager.addToBottom(new DailyCommuneAction());
                break;
            case "Bide":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
                break;
        }
    }
}
