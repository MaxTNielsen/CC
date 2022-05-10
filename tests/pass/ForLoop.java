package pass;

public class ForLoop {
    public int testForLoop() {
        int[] array = {1,2,3};
        int i = 0;
        int result = 0;

        for(; i < array.length; i++) {
            result++;
        }

        return result;
    }
}
