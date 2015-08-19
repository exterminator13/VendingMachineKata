
package vendingmachinekata;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundDoubles {
    public static double round(double number){
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
