package SlayByDay.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ResurgenceAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;

    public ResurgenceAction(final AbstractPlayer p, final int magicNumber,
                            final boolean upgraded, final boolean freeToPlayOnce,
                            final int energyOnUse)

    {
        this.freeToPlayOnce = false;
        this.p = p;
        this.magicNumber = magicNumber;
        this.freeToPlayOnce = freeToPlayOnce;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (upgraded) {
            ++effect;
        }

        int buffIndex = MathUtils.random(0, 3);

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                if (buffIndex == 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
                } else if (buffIndex == 1) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
                }
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
