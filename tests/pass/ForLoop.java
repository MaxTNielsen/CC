package pass;

public class ForLoop {
    public int testForLoop() {
        int[] array = {1,2,3};
        int i = 0;
        int result = 0;
        int j,l;

        // Result = 10
        for (int k = 0 ; k < 10; k++) {
             result = k;
        }
        // Result =  15
        // i = 5
        for(int k = 1; i < 5;i++) {
            result += k;
        }

        // i = 2
        // Result = 12
        for( i ; i >= array.length; i--) {
            result--;
        }

        // i = 0
        // l = 2
        // j = 2
        // Result = 14
        for(j = 0, l = 0;j<3 && l<3 && i > 0; i--,l++,j++){
            result++;
        }

        return result;
    }
}
