package SlayByDay.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

@SpirePatch(clz = VulnerablePower.class,method = "atDamageReceive")
public class PrecisionPowerEffect {
    public static boolean in_progress = false;
    public static float Postfix(float __result, VulnerablePower __instance, float damage, DamageInfo.DamageType type) {
        float precision = 1;
        for (int i = 0; i < AbstractDungeon.player.powers.size(); i++) {
            if (AbstractDungeon.player.powers.get(i).ID.equals("SlayByDay:Precision")) {
                precision += AbstractDungeon.player.powers.get(i).amount * .01f;
            }
        }
        return __result * precision;

    }

}