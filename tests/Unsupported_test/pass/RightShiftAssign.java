package pass;

public class RightShiftAssign {
    public boolean doRightShiftAssign(int x , int y) {
        x >>= y;
        return x == x>>y;
    } 
}
