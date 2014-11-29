package dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jun on 11/27/2014.
 * O(N*S) time dynamic programming
 * Given a list of N coins, their values (V1, V2, ... , VN), and the total sum S.
 * Find the minimum number of coins the sum of which is S (we can use as many coins of one type as we want),
 * or report that it's not possible to select coins in such a way that they sum up to S.
 * http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
 */
public class MinimumCoins {
    private static final int MAX_INT = 2147483647;
    private ArrayList<Integer> availableCoins;

    public static void main(String[] args) {
        MinimumCoins mc = new MinimumCoins();
        System.out.println("for 100 " + mc.findCoins( 100));

        MinimumCoins mc2  = new MinimumCoins(defaultCoins2());
        System.out.println("for 11 " + mc2.findCoins(11));
    }

    public MinimumCoins (){
        availableCoins = new ArrayList<Integer>();
        availableCoins.add(1);
        availableCoins.add(5);
        availableCoins.add(10);
        availableCoins.add(25);
    }

    public MinimumCoins(ArrayList<Integer> coins){
        availableCoins = new ArrayList<Integer>();
        availableCoins.addAll(coins);
    }

    public CoinList findCoins(int amount){
        HashMap<Integer, CoinList> coinSets = new HashMap<Integer, CoinList>();
        //put all coins as shortest combo in the map
        for(int coin: availableCoins){
            if(coin < amount){
                coinSets.put(coin, new CoinList(coin));
            }else if( coin == amount){
                return new CoinList(coin);
            }
        }
        //calculate shortest combo based on bases
        for(int i = 1; i <= amount; i++){
            CoinList newCombo = new CoinList();
            if(coinSets.containsKey(i)){
                continue; // already in hashmap -- is the amount of some coin, no need to check
            }
            for(int coin : availableCoins){
                int base = i - coin;
                if(base > 0 && coinSets.get(base) != null && (coinSets.get(base).length < (newCombo.length -1) )){
                    newCombo = new CoinList(coin, coinSets.get(base));
                }
            }
            if(newCombo.length < MAX_INT){
                coinSets.put(i, newCombo);
            }
        }

        return coinSets.get(amount);
    }

    private static ArrayList<Integer> defaultCoins(){
        ArrayList<Integer> def = new ArrayList<Integer>();
        def.add(1);
        def.add(5);
        def.add(10);
        def.add(25);
        return def;
    }

    private static ArrayList<Integer> defaultCoins2(){
        ArrayList<Integer> def = new ArrayList<Integer>();
        def.add(2);
        def.add(5);
        def.add(10);
        def.add(25);
        return def;
    }


    private class CoinList{
        int length;
        int thisCoin;
        int amount;
        CoinList next;
        public  CoinList(int coin, CoinList next){
            this.amount = next.amount + coin;
            this.thisCoin = coin;
            this.next = next;
            this.length = next.length+1;
        }

        public CoinList(int amount){
            this.thisCoin = amount;
            this.amount = amount;
            this.length = 1;
            this.next = null;
        }

        public CoinList(){this.length = MAX_INT;} //set to maximum int, as a initial condition, so every real list would be shorter

        public String toString(){
            StringBuffer sb = new StringBuffer();
            CoinList runner = this;
            if(amount == 0){//empty CoinList -- just default value by VM
                return null;
            }
            sb.append("Minimum number of " + length+ " for amount of " + amount);
            sb.append(" Coins: ");
            while (runner != null){
                sb.append(runner.thisCoin + ", ");
                runner = runner.next;
            }
            return sb.toString();
        }
    }


    //NOTE: this clss is not used
    private class CoinSets{
        private final ArrayList<Integer> availableCoins;
        private final int ammount;
        private ArrayList<ArrayList<Integer>> sets;

        public CoinSets(int ammount){
            this.ammount = ammount;
            availableCoins = new ArrayList<Integer>();
            availableCoins.addAll(defaultCoins());
        }

        public CoinSets(int ammount, ArrayList<Integer> availableCoins){
            this.ammount = ammount;
//            this.sets.add(set);
            this.availableCoins = new ArrayList<Integer>();
            this.availableCoins.addAll(availableCoins);
        }



        public boolean addSet(ArrayList<Integer> set) throws Exception{
            int sumCheck = 0;
            for (Integer coin : set) {
                if(!availableCoins.contains(coin)){
                    throw new Exception("Using non-available coin " + coin);
                }
                sumCheck += coin;
            }
            if(sumCheck != ammount){
                throw new Exception("Coins do not sum up to the correct ammount");
            }
            sets.add(set);
            return true;
        }

        public ArrayList<ArrayList<Integer>> getSets(){
            return this.sets;
        }
    }
}
