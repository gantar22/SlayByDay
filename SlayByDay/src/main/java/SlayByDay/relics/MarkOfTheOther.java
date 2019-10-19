package SlayByDay.relics;

import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import SlayByDay.util.TextureLoader;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.OnCardUseSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

public class MarkOfTheOther extends CustomRelic {

    // public static final String ID = SlayByDay.makeID("MarkOfTheOther");
    public static final String ID = "SlayByDay:MarkOfTheOther";
    public static MarkOfTheOther instance;
    private boolean mode;

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public MarkOfTheOther() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        instance = this;
        mode = TheMedium.Reason_Mode;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        if (mode == TheMedium.Reason_Mode) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1)));
        }
    }
}


