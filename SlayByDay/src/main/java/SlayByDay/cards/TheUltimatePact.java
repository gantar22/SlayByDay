package SlayByDay.cards;

import SlayByDay.actions.SwitchAction;
import SlayByDay.characters.TheModal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import basemod.abstracts.CustomCard;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class TheUltimatePact extends CustomCard {

    public TheUltimatePact() {
        super( "SlayByDay:TheUltimatePact", "The Ultimate Pact", null, 0,"None", CardType.POWER,
            TheModal.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Nex));

        /*switch (this.currentID) {
            case "Prediction":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
                break;
            case "FeatherShield":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                break;
        }*/
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheUltimatePact();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }
}
