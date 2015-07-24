package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    public VendingMachineTransaction(){
        this.currentAmount = new Money();
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
        public String selectCola(){
            if(this.currentAmount.toString().equals("$0.00")){
                return "$1.50";
            }
            return "";
        }
        public String selectChips(){
            if(this.currentAmount.toString().equals("$0.00")){
                return "$0.50";
            }
            return "";
        }
}