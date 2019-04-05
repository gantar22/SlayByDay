package SlayByDay.powers;

import SlayByDay.SlayByDay;
import SlayByDay.util.TextureLoader;
import basemod.BaseMod;
import basemod.interfaces.PostPotionUseSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SlayByDay.SlayByDay.makePowerPath;

// Whenever a potion is consumed, deal (amount) damage to all enemies

public class SplashDamagePower extends AbstractPower
implements PostPotionUseSubscriber {
    public AbstractCreature source;

    public static final String POWER_ID = SlayByDay.makeID("SplashDamage");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public SplashDamagePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        BaseMod.subscribe(this);
        updateDescription();
    }

    @Override
    public void receivePostPotionUse(AbstractPotion abstractPotion) {
        if (this.owner != null) {
            AbstractPower splash_power = this.owner.getPower(SplashDamagePower.POWER_ID);
            if (splash_power != null) {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(splash_power.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
                this.flash();
            } else {
                BaseMod.unsubscribe(this);
            }
        }
    }

    @Override
    public void onRemove() {
        BaseMod.unsubscribe(this);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
