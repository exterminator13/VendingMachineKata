
package vendingmachinekata;

public class StringMaker {
    public static final String stringMaker(int quarterAmount, int dimeAmount, int nickelAmount){
    String quarter = " Quarters, ";
    String dime = " Dimes, ";
    String nickel = " Nickels";
    if(quarterAmount == 1){
        quarter = " Quarter, ";
    }
    if(dimeAmount == 1){
        dime = " Dime, ";
    }
    if(nickelAmount == 1){
        nickel = " Nickel";
    }
    return quarterAmount + quarter + dimeAmount + dime + nickelAmount + nickel;
}
}
