package pass;

public class PostfixIncrement {
    public int doPostfixInc(int x, int y){
        y = x++;
        return y;
    }
}