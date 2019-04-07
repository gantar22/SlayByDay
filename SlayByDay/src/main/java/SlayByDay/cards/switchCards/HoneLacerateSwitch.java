package SlayByDay.cards.switchCards;

import SlayByDay.actions.HoneAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HoneLacerateSwitch extends AbstractSwitchByModeCard {

    // WARNING - When tweaking these values, make sure equivalent changes are made in constructor as well
    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Hone", "Lacerate", 1, 0, 0, 0, 0, 0, 3, 1,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new switchCard("Lacerate", "Hone", 1, 0, 6, 3, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, false, false) );

    public String reasonCardID() {
        return "Hone";
    }
    public String passionCardID() {
      return "Lacerate";
    }

    public int damage_counter;

    public HoneLacerateSwitch(String switchID) {
        super("SlayByDay:HoneLacerate", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.COMMON, CardTarget.NONE, HoneLacerateSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }

        this.damage_counter = 6;
        this.baseDamage = this.damage_counter;
    }

    public HoneLacerateSwitch() { this(null); }

    @Override
    public void switchTo(String id) {
        super.switchTo(id);
        this.baseDamage = damage_counter;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        HoneLacerateSwitch card = (HoneLacerateSwitch)super.makeStatEquivalentCopy();
        card.damage_counter = this.damage_counter;
        this.baseDamage = damage_counter;
        return card;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Hone":
                AbstractDungeon.actionManager.addToBottom(new HoneAction(this.uuid, this.magicNumber));
                break;
            case "Lacerate":
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
        }
    }
}
