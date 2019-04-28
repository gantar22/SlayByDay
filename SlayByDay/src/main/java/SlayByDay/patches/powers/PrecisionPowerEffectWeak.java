package SlayByDay.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

@SpirePatch(clz = WeakPower.class,method = "atDamageGive")
public class PrecisionPowerEffectWeak {
    public static boolean in_progress = false;
    public static float Postfix(float __result, WeakPower __instance, float damage, DamageInfo.DamageType type) {
        if(type != DamageInfo.DamageType.NORMAL || __instance.owner.isPlayer) return damage;

        float precision = __result;
        for (int i = 0; i < AbstractDungeon.player.powers.size(); i++) {
            if (AbstractDungeon.player.powers.get(i).ID.equals("SlayByDay:Precision")) {
                precision -= (AbstractDungeon.player.powers.get(i).amount * .01f) * __result;
            }
        }
    if(precision < 0) precision = 0;


    return precision;

    }

}