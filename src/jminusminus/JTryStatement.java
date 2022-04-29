package jminusminus;

import static jminusminus.CLConstants.*;

import java.util.ArrayList;
import java.lang.Exception;
public class JTryStatement extends JStatement{

    private JBlock tryBlock;
    private ArrayList<JCatchStatement> catches;
    private JBlock finallyBlock;
    public JTryStatement(int line, JBlock tryBlock, ArrayList<JCatchStatement> catches, JBlock finallyPart) {
        super(line);
        this.tryBlock = tryBlock;
        this.catches = catches;
        this.finallyBlock = finallyPart;
    }


    public JStatement analyze(Context context) {
        tryBlock = (JBlock) tryBlock.analyze(context);
        for (int i = 0; i < catches.size(); i++) {
            catches.set(i, (JCatchStatement) catches.get(i).analyze(context));
        }
        if (finallyBlock != null){
            finallyBlock = (JBlock) finallyBlock.analyze(context);
        }
        
        return this;
    }


    public void codegen(CLEmitter output) {
        //4 labels
        String startLabel = output.createLabel();
        String tryEndLabel = output.createLabel();
        String finallyLabel = output.createLabel();           
        String endLabel = output.createLabel();   
        output.addLabel(startLabel);
        tryBlock.codegen(output);
        output.addLabel(tryEndLabel);
        
        if (finallyBlock != null){
            output.addBranchInstruction(JSR, finallyLabel);
        }
        output.addNoArgInstruction(RETURN);

        for(JCatchStatement catchSt : catches){
            String handlerLabel = output.createLabel();
            

            output.addLabel(handlerLabel);
            catchSt.codegen(output);
            output.addExceptionHandler(startLabel, tryEndLabel, handlerLabel, catchSt.getException()); 
            if (finallyBlock != null){
                output.addBranchInstruction(JSR, finallyLabel);
            }
            
            output.addNoArgInstruction(RETURN);
        }

        if (finallyBlock != null){
        String handlerLabel = output.createLabel();
        output.addLabel(handlerLabel);
        output.addExceptionHandler(startLabel, tryEndLabel, handlerLabel, null);

        output.addNoArgInstruction(ASTORE_1);
        output.addBranchInstruction(JSR, finallyLabel);
        output.addNoArgInstruction(ALOAD_1);
        output.addNoArgInstruction(ATHROW);
        }
        if (finallyBlock != null){output.addLabel(finallyLabel);
            output.addNoArgInstruction(ASTORE_2);
            finallyBlock.codegen(output);  
            output.addOneArgInstruction(RET, 2);
        }
       
        
    }



    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTryStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Try>\n");
        p.indentRight();
        tryBlock.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TryClause>\n");
        for (int i = 0; i < catches.size(); i++) {
            catches.get(i).writeToStdOut(p);
        }
        if (finallyBlock != null) {
            p.printf("<FinallyClause>\n");
            p.indentRight();
            finallyBlock.writeToStdOut(p);
            p.indentLeft();
            p.printf("</FinallyClause>\n");
        }
        p.indentLeft();
        p.printf("</JTryStatement>\n");
    }

}
