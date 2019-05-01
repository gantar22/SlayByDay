package SlayByDay.patches.cards;

import SlayByDay.cards.switchCards.EntrapSkillfulDodgeSwitch;
import SlayByDay.characters.TheMedium;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;


@SpirePatch(clz = RewardItem.class,method = "claimReward")
public class RewardItemPatch {

    public static void  Prefix(RewardItem __Instance)
    {
        if(__Instance.type == RewardItem.RewardType.POTION && __Instance.relicLink != null)
        {
            __Instance.relicLink.isDone = true;
            __Instance.relicLink.ignoreReward = true;
            System.out.println("patched reqard with potion links");
            if(__Instance.relicLink.relicLink != null)
            {
                __Instance.relicLink.relicLink.isDone = true;
                __Instance.relicLink.relicLink.ignoreReward = true;
            }

        }
    }
}
