package SlayByDay.powers;

import SlayByDay.SlayByDay;
import SlayByDay.relics.IOnSwitch;
import SlayByDay.relics.PlaceholderRelic;
import SlayByDay.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static SlayByDay.SlayByDay.makePowerPath;

// Whenever a potion is consumed, deal (amount) damage to all enemies

public class SynchronizedPower extends AbstractPower
implements IOnSwitch {
    public AbstractCreature source;

    public static final String POWER_ID = SlayByDay.makeID("Synchronized");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public SynchronizedPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        PlaceholderRelic.subscribe(this);
        updateDescription();
    }

    @Override
    public void OnSwitch(boolean Reason_Mode) {
        if (!this.owner.hasPower(this.ID)) {
            PlaceholderRelic.unsubscribe(this);
            return;
        }

        if (Reason_Mode) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, 1));
            this.flash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, 1)));
            this.flash();
        }
    }

    @Override
    public void updateDescription() {
        if (DESCRIPTIONS == null) {
            System.out.println("DESCRIPTIONS is null in SynchronizedPower");
            return;
        } else if (DESCRIPTIONS[0] == null) {
            System.out.println("DESCRIPTIONS[0] is null in SynchronizedPower");
            return;
        }
        description = DESCRIPTIONS[0];
    }
}
