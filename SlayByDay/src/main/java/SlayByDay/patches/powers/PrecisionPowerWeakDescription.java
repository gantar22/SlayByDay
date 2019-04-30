package SlayByDay.patches.powers;

import SlayByDay.cards.switchCards.AbstractSwitchByModeCard;
import SlayByDay.characters.TheMedium;
import SlayByDay.powers.PrecisionPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.lwjgl.Sys;

@SpirePatch(clz = WeakPower.class,method = "updateDescription")
public class PrecisionPowerWeakDescription {
    public static boolean in_progress = false;
    public static void Postfix(WeakPower __Instance)
    {
        int precision = 0;
        for(int i = 0; i < AbstractDungeon.player.powers.size();i++)
        {
            if(AbstractDungeon.player.powers.get(i).ID.equals("SlayByDay:Precision"))
            {
                precision += AbstractDungeon.player.powers.get(i).amount;
            }
        }


        if (__Instance.amount == 1) {
            if (__Instance.owner != null && !__Instance.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
                __Instance.description = __Instance.DESCRIPTIONS[0] + (40 + precision) + __Instance.DESCRIPTIONS[1] + __Instance.amount + __Instance.DESCRIPTIONS[2];
            } else {
                __Instance.description = __Instance.DESCRIPTIONS[0] + (25 + precision) + __Instance.DESCRIPTIONS[1] + __Instance.amount + __Instance.DESCRIPTIONS[2];
            }
        } else if (__Instance.owner != null && !__Instance.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
            __Instance.description = __Instance.DESCRIPTIONS[0] + (40 + precision) + __Instance.DESCRIPTIONS[1] + __Instance.amount + __Instance.DESCRIPTIONS[3];
        } else {
            __Instance.description = __Instance.DESCRIPTIONS[0] + (25 + precision) + __Instance.DESCRIPTIONS[1] + __Instance.amount + __Instance.DESCRIPTIONS[3];
        }

    }
}