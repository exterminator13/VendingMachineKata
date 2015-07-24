package vendingmachinekata;

public class VendingMachineTransaction {
    Money currentAmount;
    public VendingMachineTransaction(){
        this.currentAmount = new Money();
    }
    public String transactionTotalCoins(){
        if(this.currentAmount.toString().equals("$0.00")){
            return "INSERT COINS";
        }else{
            return this.currentAmount.toString();
        }
    }
        public int coinRecognition(String weight, String diameter){
            if(weight.equals("5.670 g") && diameter.equals("0.955 in")){
                this.currentAmount.addMoney(.25);
                return 25;
            }
            if(weight.equals("2.268 g") && diameter.equals("0.705 in")){
                this.currentAmount.addMoney(.10);
                return 10;
            }
            if(weight.equals("5.000 g") && diameter.equals("0.835 in")){
                this.currentAmount.addMoney(.05);
                return 5;
            }
            return 0;
        }
        public String selectCola(){
            if(this.currentAmount.toString().equals("$0.00")){
                return "$1.50";
            }else if(this.currentAmount.toString().equals("$1.00")){
                return "THANK YOU";
            }
            return "";
        }
        public String selectChips(){
            if(this.currentAmount.toString().equals("$0.00")){
                return "$0.50";
            }else if(this.currentAmount.toString().equals("$0.50")){
                this.currentAmount.removeMoney(.50);
                return "THANK YOU";
            }
            return "";
        }
        public String selectCandy(){
            if(this.currentAmount.toString().equals("$0.00")){
                return "$0.65";
            }else if(this.currentAmount.toString().equals("$0.65")){
                this.currentAmount.removeMoney(.65);
                return "THANK YOU";
            }
            return "";
        }
}