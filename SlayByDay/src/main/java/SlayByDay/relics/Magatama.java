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
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class Magatama extends CustomRelic {

    public static final String ID = "SlayByDay:Magatama";

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Magatama.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Magatama.png"));

    private boolean switched;
    private boolean mode;

    public Magatama() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        mode = TheMedium.Reason_Mode;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void update() {
        super.update();
        if (mode != TheMedium.Reason_Mode) {
            mode = TheMedium.Reason_Mode;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        }
    }
}


