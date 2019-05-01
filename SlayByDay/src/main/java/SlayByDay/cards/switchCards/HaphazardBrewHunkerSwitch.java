package SlayByDay.cards.switchCards;

import SlayByDay.characters.TheMedium;
import SlayByDay.powers.FlightPlayerPower;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.ModalChoiceCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static SlayByDay.SlayByDay.makeCardPath;

public class HaphazardBrewHunkerSwitch extends AbstractSwitchByModeCard implements ModalChoice.Callback{


    public List<AbstractSwitchByModeCard.switchCard> switchListInherit = Arrays.asList(
            new AbstractSwitchByModeCard.switchCard("HaphazardBrew", "Hunker", 2, 0, 0, 0, 0, 0, 1, 1,
                    AbstractCard.CardType.SKILL, AbstractCard.CardTarget.NONE, false, false, false, false),

            new AbstractSwitchByModeCard.switchCard("Hunker", "HaphazardBrew", 2, 0, 0, 0, 4, 0, 2, 1,
                    CardType.SKILL, AbstractCard.CardTarget.NONE, false, false, false, false) );

    public String reasonCardID() {
        return "HaphazardBrew";
    }
    public String passionCardID() {
        return "Hunker";
    }

    private int uses;

    private ModalChoice mc;

    public static ArrayList<AbstractPotion> potions;

    public HaphazardBrewHunkerSwitch(String switchID) {
        super("SlayByDay:HapazardBrewHunker", "None", null, 0, "None", AbstractCard.CardType.SKILL,
                TheMedium.Enums.COLOR_M_PURPLE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, HaphazardBrewHunkerSwitch.class);

        if (switchID == null) {
            switchID = switchListInherit.get(new Random().nextInt(switchListInherit.size())).cardID;
        }

        this.switchList = switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        } else {
            this.switchTo(switchID);
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            this.validateSwitchCardMode(true);
        }


    }

    public HaphazardBrewHunkerSwitch() { this(null); }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.currentID) {
            case "HaphazardBrew":
                if(potions == null)
                {
                    potions = new ArrayList<>(3);
                    potions.add(0,AbstractDungeon.returnRandomPotion());
                    potions.add(1,AbstractDungeon.returnRandomPotion());
                    potions.add(2,AbstractDungeon.returnRandomPotion());
                }
                create_modal();
                break;
            case "Hunker":
                for(int i = 0; i < this.magicNumber; i++)
                {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(p,-1)));
                break;
        }
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {

        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(potions.get(i)));

        potions.set(0,AbstractDungeon.returnRandomPotion());
        potions.set(1,AbstractDungeon.returnRandomPotion());
        potions.set(2,AbstractDungeon.returnRandomPotion());
        ModalChoice.Callback c = this;
        uses--;
        if(uses > 0) {
            create_modal();
        }


    }

    void create_modal()
    {
        ModalChoice.Callback c = this;
        uses = magicNumber;
        ModalChoiceBuilder mb =  new ModalChoiceBuilder().setCallback(c);


        for(int i = 0; i < potions.size(); i++)
        {
            String descscr = potions.get(i).description;
            descscr = descscr.replace("#b","");
            String desc = descscr.replace("#y","");

            mb = mb.addOption(
                    new CustomCard("choice" + i, potions.get(i).name, makeCardPath("choice.png"), 0, desc, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE) {
                        int i = Character.getNumericValue(this.cardID.charAt(this.cardID.length() - 1));

                        @Override
                        public void upgrade() {

                        }

                        @Override
                        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                            optionSelected(AbstractDungeon.player,null,i);
                        }

                        @Override
                        public CustomCard makeCopy()
                        {
                            return new CustomCard("choice", potions.get(i).name, makeCardPath("choice.png"), 0, desc, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE) {
                                @Override
                                public void upgrade() {

                                }

                                @Override
                                public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                                    optionSelected(AbstractDungeon.player,null,i);
                                }
                            };
                        }
                    }
            );


            //potions.get(i).name,potions.get(i).description,CardTarget.NONE);
        }
        mc = mb.create();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if(isDone) return;
                isDone = true;

                mc.open();
            }
        });
    }
}
