package pass;

public class PostfixIncrement {
    
    int k = 5;
    static int p = 5;

    public int doPostfixInc(){
        int x = 5;
        int y = x++;
        return y;
    }

    public void doPostfixInc2(){
        int z = k++;
    }

    public static void doPostfixInc3(){
        int q = p++;
    }

    public void doPostfixInc4(){
        p++;
    }
}