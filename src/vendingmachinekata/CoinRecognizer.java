
package vendingmachinekata;

public class CoinRecognizer {
    VendingMachineTransaction vendingMachineTransaction;
    MachineCoinTracker coinTracker;
    public CoinRecognizer(VendingMachineTransaction vendingMachineTransaction){
        this.vendingMachineTransaction = vendingMachineTransaction;
        this.coinTracker = vendingMachineTransaction.getCoinTracker();
    }
    public boolean coinRecognition(String weight, String diameter){
        if(weight.equals("5.670 g") && diameter.equals("0.955 in")){
            this.vendingMachineTransaction.currentAmount.addMoney(CoinValues.QUARTER_VALUE);
            this.coinTracker.addCoins(CoinValues.QUARTER_VALUE, 1);
            return true;
        }
        if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
            this.vendingMachineTransaction.currentAmount.addMoney(CoinValues.DIME_VALUE);
            this.coinTracker.addCoins(CoinValues.DIME_VALUE, 1);
            return true;
        }
        if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
            this.vendingMachineTransaction.currentAmount.addMoney(CoinValues.NICKEL_VALUE);
            this.coinTracker.addCoins(CoinValues.NICKEL_VALUE, 1);
            return true;
        }
        return false;
    }
}
