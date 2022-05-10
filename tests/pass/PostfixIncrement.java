package pass;

public class PostfixIncrement {

    public int doPostfixInc(int x, int y){
        x = ++y;
        return x;
    }
}