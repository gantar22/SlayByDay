package SlayByDay.powers.PactPowers;

import SlayByDay.characters.TheMedium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class PactPower extends AbstractPower {

    public PactPower() {
        this.type = TheMedium.Enums.PACT;
    }

    @Override
    public void playApplyPowerSfx() {
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("BUFF_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("BUFF_2");
        } else {
            CardCrawlGame.sound.play("BUFF_3");
        }
    }
}
