package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RepurposeAdaptSwitch extends AbstractSwitchByModeCard {

    public List<switchCard> switchListInherit = Arrays.asList(
            new switchCard("Repurpose", "Adapt", 1, 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.NONE, false, false, false, false),

            new switchCard("Adapt", "Repurpose", 1, 1, 0, 0, 0, 0, 0, 0,
                    CardType.SKILL, CardTarget.SELF, false, false, false, false) );

    public String reasonCardID() {
        return "Repurpose";
    }
    public String passionCardID() {
      return "Adapt";
    }

    public RepurposeAdaptSwitch(String switchID) {
        super("SlayByDay:RepurposeAdapt", "None", null, 0, "None", CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE, RepurposeAdaptSwitch.class);

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

    public RepurposeAdaptSwitch() { this(null); }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "Repurpose":
                int slot = 0;
                for(Iterator var3 = AbstractDungeon.player.potions.iterator(); var3.hasNext(); slot++) {
                    AbstractPotion oldPotion = (AbstractPotion)var3.next();
                    if (!(oldPotion instanceof PotionSlot)) {
                        AbstractPotion potionToObtain = AbstractDungeon.returnRandomPotion(true);
                        AbstractDungeon.player.obtainPotion(slot, potionToObtain);
                        potionToObtain.flash();
                    }
                }
                break;
            case "Adapt":
                int strength = AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) ? AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount : 0;
                int dexterity = AbstractDungeon.player.hasPower(DexterityPower.POWER_ID) ? AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount : 0;
                if (strength != dexterity) {
                    int new_strength = dexterity - strength;
                    int new_dexterity = strength - dexterity;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, new_strength), new_strength));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, new_dexterity), new_dexterity));
                }
                break;
        }
    }
}
