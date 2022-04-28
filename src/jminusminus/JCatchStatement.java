package jminusminus;

import static jminusminus.CLConstants.*;
public class JCatchStatement extends JStatement {

    private JBlock catchBlock;
    private JFormalParameter exception;

    private LocalVariableDefn defn;

    public JCatchStatement(int line, JFormalParameter exception, JBlock catchBlock){
        super(line);
        this.catchBlock = catchBlock;
        this.exception = exception;
    }

    public JAST analyze(Context context) {
        context = new LocalContext(context);
        exception = (JFormalParameter) exception.analyze(context);
        exception.setType(exception.type().resolve(context));
        catchBlock = (JBlock) catchBlock.analyze(context);
        
        defn = new LocalVariableDefn( exception.type(), 
        ((LocalContext) context).nextOffset());
        defn.initialize();
        context.addEntry(catchBlock.line(),  exception.name(), defn);

        //---
        
        return this;
    }
    public String getException(){
        return exception.type().jvmName();
    }
    public void codegen(CLEmitter output) {
        exception.codegen(output);
        //output.addLabel(getException());
        catchBlock.codegen(output);

        if (defn instanceof LocalVariableDefn) {
            int offset = ((LocalVariableDefn) defn).offset();
            if (exception.type().isReference()) {
                output.addOneArgInstruction(ASTORE, offset);
            }
        }
        

        
    }

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JCatchStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Exception>\n");
        p.indentRight();
        exception.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Exception>\n");
        p.printf("<CatchBlock>\n");
        p.indentRight();
        catchBlock.writeToStdOut(p);
        p.indentLeft();
        p.printf("</CatchBlock>\n");
        p.indentLeft();
        p.printf("</JCatchStatement>\n");
        
    }

    
    
}
