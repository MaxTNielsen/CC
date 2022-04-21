package pass;

public class ForLoop {
    public int additionForLoop() {
        int[] cars = {1,2,3};
        int result = 0;
        for ( int car : cars) {
             result = result+1;
        }
        return result;
    }
}
