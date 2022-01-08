package test.testedMethods;

public class NumericRangeTools {
    public static boolean isValidPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            return true;
        }
        return false;
    }
}
