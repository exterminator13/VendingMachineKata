package vendingmachinekata;

public class VendingMachineTransaction {
    public VendingMachineTransaction(){
        
    }
    public String transactionTotalCoins(){
        return "INSERT COIN";
    }
        public int coinRecognition(String weight, String diameter){
        if("5.670 g".equals(weight) && "0.955 in".equals(diameter)){
            return 25;
        }
        return 0;
    } 
}