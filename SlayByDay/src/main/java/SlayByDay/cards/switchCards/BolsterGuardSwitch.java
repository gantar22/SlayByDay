package SlayByDay.cards.switchCards;

import SlayByDay.actions.BolsterAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BolsterGuardSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Bolster", "Guard", 1, 0, 0, 0, 0, 0, 2, 1,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new switchCard("Guard", "Bolster", 1, 0, 0, 0, 5, 3, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, true, false) );

    public String reasonCardID() {
        return "Bolster";
    }
    public String passionCardID() {
      return "Guard";
    }

    public int block_counter;

    public BolsterGuardSwitch(String switchID) {
        super("SlayByDay:BolsterGuard", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, BolsterGuardSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }

        this.block_counter = 6;
        this.baseBlock = this.block_counter;
    }

    public BolsterGuardSwitch() { this(null); }

    @Override
    public void switchTo(String id) {
        super.switchTo(id);
        this.baseBlock = block_counter;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        BolsterGuardSwitch card = (BolsterGuardSwitch)super.makeStatEquivalentCopy();
        card.block_counter = this.block_counter;
        this.baseBlock = block_counter;
        return card;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToTop(new BolsterAction(this.uuid, this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Bolster":
                AbstractDungeon.actionManager.addToBottom(new BolsterAction(this.uuid, this.magicNumber));
                break;
            case "Guard":
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                break;
        }
    }
}
