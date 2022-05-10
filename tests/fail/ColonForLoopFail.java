package fail;

public class ColonForLoopFail {
    public void ColonFail() {

        int i = 2;

        int result = 0;

        // Create tests where we should fail. These are typically type errors.
    
        for(int j:i){
            result +=j;
        }
    
    }
}
