package SlayByDay.relics;

import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import SlayByDay.powers.RefillPower;
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
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import SlayByDay.relics.Anima;

import java.util.ArrayList;

import static SlayByDay.SlayByDay.makeRelicOutlinePath;
import static SlayByDay.SlayByDay.makeRelicPath;

public class PortableCauldron extends CustomRelic {

    public static final String ID = "SlayByDay:PortableCauldron";

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PortableCauldron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PortableCauldron.png"));

    public PortableCauldron() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RefillPower(AbstractDungeon.player, AbstractDungeon.player, 1)));
    }
}


