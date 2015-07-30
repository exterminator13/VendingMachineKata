
package vendingmachinekata;
import java.lang.Runnable;

public class VendingMachine implements Runnable{
    CoinDispenser coinDispenser;
    VendingMachineTransaction vendingMachineTransaction;
    public VendingMachine(){
        this.coinDispenser = new CoinDispenser();
        this.vendingMachineTransaction = new VendingMachineTransaction(this.coinDispenser);
    }
    @Override
    public void run(){
        System.out.print(this.vendingMachineTransaction.display()+"\n");
    }
}
