package SlayByDay.powers;


import SlayByDay.SlayByDay;
import SlayByDay.characters.TheMedium;
import SlayByDay.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import SlayByDay.relics.Anima;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.lwjgl.Sys;

import static SlayByDay.SlayByDay.makePowerPath;

public class LosePassionOnDamage extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = SlayByDay.makeID("LosePassionOnDamage");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public LosePassionOnDamage(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

        updateDescription();
        System.out.println("can we even print from here???");
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }


    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        System.out.println("Delt damage: "+ damageAmount + " against block: " + target.currentBlock);
    }

    public void atStartOfTurn() {
        System.out.println("This is the start of the turn");
    }


}
