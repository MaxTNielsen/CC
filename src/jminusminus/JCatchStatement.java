package jminusminus;

import static jminusminus.CLConstants.*;
public class JCatchStatement extends JStatement {

    private JBlock catchBlock;
    private JFormalParameter exception;

    private LocalVariableDefn defn;
    protected LocalContext context;
    public JCatchStatement(int line, JFormalParameter exception, JBlock catchBlock){
        super(line);
        this.catchBlock = catchBlock;
        this.exception = exception;
    }

    public JAST analyze(Context context) {
        
        exception = (JFormalParameter) exception.analyze(context);
        exception.setType(exception.type().resolve(context));
        

        this.context = new LocalContext(context);
        defn = new LocalVariableDefn(exception.type(), 
        (this.context).nextOffset());
        defn.initialize();

        IDefn previousDefn = context.lookup(exception.name());
        if (previousDefn != null
                && previousDefn instanceof LocalVariableDefn) {
            JAST.compilationUnit.reportSemanticError(exception.line(),
                    "The name " + exception.name()
                            + " overshadows another local variable.");
        }
        this.context.addEntry(catchBlock.line(),  exception.name(), defn);

        catchBlock = (JBlock) catchBlock.analyze(this.context);
        
        return this;
    }
    public String getException(){
        return exception.type().jvmName();
    }
    public void codegen(CLEmitter output) {
        exception.codegen(output);
        //output.addLabel(getException());

        if (defn instanceof LocalVariableDefn) {
            int offset = ((LocalVariableDefn) defn).offset();
            if (exception.type().isReference()) {
                switch (offset) {
                case 0:
                    output.addNoArgInstruction(ASTORE_0);
                    break;
                case 1:
                    output.addNoArgInstruction(ASTORE_1);
                    break;
                case 2:
                    output.addNoArgInstruction(ASTORE_2);
                    break;
                case 3:
                    output.addNoArgInstruction(ASTORE_3);
                    break;
                default:
                    output.addOneArgInstruction(ASTORE, offset);
                    break;
                }
            }
        }


        catchBlock.codegen(output);

       
        

        
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
