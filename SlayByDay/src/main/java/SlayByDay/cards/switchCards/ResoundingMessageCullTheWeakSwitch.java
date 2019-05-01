package SlayByDay.cards.switchCards;

import SlayByDay.actions.DefeatEnemyAction;
import SlayByDay.actions.ResoundingMessageAction;
import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ResoundingMessageCullTheWeakSwitch extends AbstractSwitchByModeCard {

    // WARNING - When tweaking these values, make sure equivalent changes are made in constructor as well
    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("ResoundingMessage", "CullTheWeak", 1, 0, 4, 0, 0, 0, 4, 0,
                    CardType.ATTACK, CardTarget.ALL_ENEMY, true, false, false, false),

            new switchCard("CullTheWeak", "ResoundingMessage", 1, 0, 5, 0, 0, 0, 0, 0,
                    CardType.ATTACK, CardTarget.ENEMY, false, false, true, false) );

    public String reasonCardID() {
        return "ResoundingMessage";
    }
    public String passionCardID() {
      return "CullTheWeak";
    }

    public int damage_counter;

    public ResoundingMessageCullTheWeakSwitch(String switchID) {
        super("SlayByDay:ResoundingMessageCullTheWeak", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, ResoundingMessageCullTheWeakSwitch.class);

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

        this.damage_counter = 6;
        this.baseDamage = this.damage_counter;
    }

    public ResoundingMessageCullTheWeakSwitch() { this(null); }

    @Override
    public void switchTo(String id) {
        super.switchTo(id);
        this.baseDamage = damage_counter;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        ResoundingMessageCullTheWeakSwitch card = (ResoundingMessageCullTheWeakSwitch)super.makeStatEquivalentCopy();
        card.damage_counter = this.damage_counter;
        this.baseDamage = damage_counter;
        return card;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            // TODO - make sure this works even if it's not in the right form
            findSwitch(passionCardID()).exhaust = false;
            this.exhaust = false;
        }
        super.upgrade();
    }

    @Override
    public void triggerWhenDrawn() {
        if (TheMedium.Reason_Mode) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "ResoundingMessage":
                AbstractDungeon.actionManager.addToTop(new ResoundingMessageAction(this.uuid, this.magicNumber));
                break;
            case "CullTheWeak":
                Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                while (var1.hasNext()) {
                    AbstractMonster mo = (AbstractMonster) var1.next();
                    if (mo.hasPower(MinionPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new DefeatEnemyAction(mo));
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
        }
    }
}
