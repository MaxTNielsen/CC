package pass;

public class MinusAssign {

    public boolean doMinusAssign(int x, int y){
        x -= y;
        return x == x - y;
    }
}
