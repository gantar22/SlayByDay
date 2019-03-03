package SlayByDay.cards.switchCards;

import SlayByDay.actions.SwitchAction;
import SlayByDay.characters.TheDefault;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class PredictionFeatherShieldSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("Prediction", "FeatherShield", 1, 9, 3, 0, 0, 0, 0,
                    AbstractCard.CardType.ATTACK, AbstractCard.CardTarget.ALL_ENEMY, true, false, false, false),

            new AbstractSwitchByModeCard.switchCard("FeatherShield", "Prediction", 1, 12, 5, 0, 0, 0, 0,
                    AbstractCard.CardType.ATTACK, AbstractCard.CardTarget.ENEMY, false, false, false, false) );


    public PredictionFeatherShieldSwitch(String switchID) {
        super("SlayByDay:PredictionFeatherShield", "None", null, 0, "None", AbstractCard.CardType.ATTACK,
                TheDefault.Enums.COLOR_GRAY, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.NONE, PredictionFeatherShieldSwitch.class);
        // TODO - change this ^^ card color to porple

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }
    }

    public PredictionFeatherShieldSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Prediction":
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
                break;
            case "FeatherShield":
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
    }
}
