package dominionAgents;

import java.util.ArrayList;
import java.util.Random;

import dominionAgents.CardData.CardType;


// The Bot is similar to the rush bot but more laid back, the goal of the ai is to keep buying treasure cards until they are able to buy point cards
// priortizing higher point cards over lower value ones: in pratice the AI would buy try to get cards that allow it to buy more then once if able it buys those cards 
// then when it can and has the buys it first buys more action cards then a tresure card. The idea is to maximize the amount of gold it can play a turn then buy points
// unlike the rush bot it should play it slower focusing more on deck build along side just buying money making cards

// V0: very similar to attackBot when we get a chance we can flush this out more



/*TO DO
    Need to fix the ratio value to give different 
*/
public class MoneyMakingBotV1_0_2 extends BasicBotV1_0{
    
    protected String firstCard;
	protected boolean firstCardBuy;  
    boolean treasureInSupply = false;
    int actionNum = 0;   
    ArrayList<Float> ratioList = CardData.preferedCardM();
    ArrayList<Integer> cardsBought = new ArrayList<Integer>();
    

    public MoneyMakingBotV1_0_2(Kingdom k, PlayerCommunication pc) {
		super(k, pc);
        this.firstCard = "NA";
        this.firstCardBuy = false;
        for(int i = 0;i < 17;i++){ 
            cardsBought.add(0);
        }
    }

    public MoneyMakingBotV1_0_2(Kingdom k, PlayerCommunication pc, String c) {
		super(k, pc);
        this.firstCard = c;
        this.firstCardBuy = true;
        for(int i = 0;i < 17;i++){ 
            cardsBought.add(0);
        }
    }

    protected void resolveBuyPhase() {
        
        if (turnNumber <= 1 && !firstCard.equalsIgnoreCase("NA") && coins >= CardData.getCardCost(firstCard)){
            buyCard(firstCard);
        }else if(kingdom.canBuy("Province") && (coins >= 8) && buys > 0){// buy as many provinces as you can
	        buyCard("Province");
	    }else {
	
	        String cardToBuy = bestBuy();
	                          
	        if(coins < 8 && cardToBuy != null && actionNum < 1 && buys > 0){
	            int index = kingdom.kingdomIndex(cardToBuy);
	            //System.out.print("buying "+cardToBuy+ " with " + coins + " coins left\n");
	            if(coins >= CardData.getCardCost(cardToBuy) && kingdom.canBuy(cardToBuy)) {
	            	buyCard(cardToBuy);
	            	Integer addHere = cardsBought.get(index);
	                addHere ++;
	                actionNum += 1;
	            }
	        }else if(kingdom.canBuy("Gold") && coins >= 6 && buys > 0) { // while you can buy golds
	            buyCard("Gold");
	        }else if(kingdom.canBuy("Silver") && coins >= 3 && buys > 0){ // if you can't buy golds buy silvers
	        	buyCard("Silver");
	        }else {
	        	super.resolveBuyPhase();
	        }

        }
        
        if(buys > 0 && coins > 1) {
        	resolveBuyPhase();
        }
 
	}

    protected String bestBuy(){

    	String s = null;
        ArrayList<SupplyPile> supply = kingdom.getSupplyPiles();
        Float max = (float)0;
        
        int i = 0;
        

        for(SupplyPile sp: supply){
            
            Card currCard = sp.getCard();
            i = kingdom.kingdomIndex(currCard.getName());
            if(currCard.isCardType(CardData.CardType.ACTION) && currCard.getCost() <= coins){
                
                //System.out.print(currCard.getName() + " : " + ratioList.get(i) + "\n"); 
                
                int index = kingdom.kingdomIndex(currCard.getName());
              
                if(max < ratioList.get(i)){
                    if(cardsBought.get(index) < 3 && index != 0){
                        max = ratioList.get(i);
                        s = currCard.getName();

                    }
                    
                        
                }
                
               
            
            
            }
            
        }



        return s;
    }
    




}