package pass;

public class ColonForLoop {
    public int test01() {
        int result = 0;
        int[] array = {1,2,3};
        // result = 3
        for(int i : array){
            result++;
        }
        return result;
    }
    public int test02(){
        int result = 0;
        int[] array = {1,2,3};
        //Result = 6;
        for(int i : array){
            result += i;
        }
        return result;
    }
    public int test03(){
        int result = 0;
        String[] array = {"a","b","c"};
        // Result = 3
        for(String x : array){
            result++;
        }
        return result;

    }
}
