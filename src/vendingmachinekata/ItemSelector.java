
package vendingmachinekata;

public class ItemSelector {
    VendingMachineTransaction vendingMachineTransaction;
    public ItemSelector(VendingMachineTransaction vendingMachineTransaction){
        this.vendingMachineTransaction = vendingMachineTransaction;
    }
        public String selectItem(Item item){
        if(item.getStock() <= 0){
            return "SOLD OUT";
        }else{
            if(this.vendingMachineTransaction.currentAmount.getAmount() == 0.00){
                return item.getMoney().toString();
            }else if (this.vendingMachineTransaction.currentAmount.getAmount() < item.getPrice()){
                return "PRICE " + item.getMoney().toString();
            }else if(this.vendingMachineTransaction.currentAmount.getAmount() == item.getPrice()){
                item.itemSold();
                this.vendingMachineTransaction.currentAmount.removeMoney(item.getPrice());
                return "THANK YOU";
            }else if(this.vendingMachineTransaction.currentAmount.getAmount() > 0.00){
                this.vendingMachineTransaction.currentAmount.removeMoney(item.getPrice());
                double amountToReturn = RoundDoubles.round(this.vendingMachineTransaction.getCurrentAmount());
                ChangeMaker changeMaker = new ChangeMaker(amountToReturn, this.vendingMachineTransaction.coinTracker);
                String changeMade = changeMaker.returnChange();
                if(changeMade.equals("Can't make change")){
                    this.vendingMachineTransaction.currentAmount.addMoney(item.getPrice());
                    return changeMade;
                }else{ 
                    item.itemSold();
                    return "THANK YOU\n" + changeMade + " returned";
                }
            }
            return "";
        }
    }
}
