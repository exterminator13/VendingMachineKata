
package vendingmachinekata;


public class VendingMachineDisplay {
    VendingMachineTransaction vendingMachineTransaction;
    MachineCoinTracker coinTracker;
    Money currentAmount;
    public VendingMachineDisplay(VendingMachineTransaction vendingMachineTransaction){
        this.coinTracker = vendingMachineTransaction.coinTracker;
        this.currentAmount = vendingMachineTransaction.currentAmount;
    }
    public String display(){
        if(this.coinTracker.getAmountInMachine().getAmount() == 0.00){
            return "EXACT CHANGE ONLY";
        }
        if(this.currentAmount.getAmount() == 0.00){
            return "INSERT COINS";
        }else{
            return this.currentAmount.toString();
        }
    }
}