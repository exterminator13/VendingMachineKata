package vendingmachinekata;

public class VendingMachineTransaction {
    public VendingMachineTransaction(){
        
    }
    public String transactionTotalCoins(){
        return "INSERT COIN";
    }
        public int coinRecognition(String weight, String diameter){
        if(weight.equals("5.670 g") && diameter.equals("0.955 in")){
            return 25;
        }
        if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
            return 10;
        }
        if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
            return 5;
        }
        return 0;
    } 
}