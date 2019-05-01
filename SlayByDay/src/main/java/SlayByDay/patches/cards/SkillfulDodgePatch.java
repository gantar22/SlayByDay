package SlayByDay.patches.cards;

import SlayByDay.cards.switchCards.EntrapSkillfulDodgeSwitch;
import SlayByDay.characters.TheMedium;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

// Source: https://github.com/kiooeht/ModTheSpire/wiki/SpirePatch#insert
@SpirePatch(clz= AbstractCard.class, method="applyPowersToBlock")
public class SkillfulDodgePatch {

    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"tmp", "p"}
    )
    public static void Insert(AbstractCard __instance, @ByRef float[] tmp, AbstractPower p) {
        if (__instance instanceof EntrapSkillfulDodgeSwitch && p instanceof DexterityPower && !TheMedium.Reason_Mode) {
            for (int i=0; i < __instance.magicNumber - 1; i++) {
                tmp[0] = p.modifyBlock(tmp[0]);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.powers.AbstractPower", "modifyBlock");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
