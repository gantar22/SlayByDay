package SlayByDay.cards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class TheUltimatePact extends CustomCard {

    public TheUltimatePact() {
        super( "SlayByDay:TheUltimatePact", "The Ultimate Pact", null, 0,"None", CardType.POWER,
            TheMedium.Enums.COLOR_M_PURPLE, CardRarity.RARE, CardTarget.SELF);
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
