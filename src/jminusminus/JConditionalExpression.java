package jminusminus;

import static jminusminus.CLConstants.*;

public class JConditionalExpression extends JExpression {

    private JExpression condition;
    private JExpression then;
    private JExpression elsePart;

    public JConditionalExpression(int line, JExpression condition, JExpression then, JExpression elsePart) {
        super(line);
        this.condition = condition;
        this.then = then;
        this.elsePart = elsePart;
    }


    public JExpression analyze(Context context) {
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line, type.BOOLEAN); 
        then = (JExpression) then.analyze(context);
        elsePart = (JExpression) elsePart.analyze(context);
        elsePart.type().mustMatchExpected(line(), then.type());
        type = then.type();
        return this;
    }

    public void codegen(CLEmitter output) {
        String elseLabel = output.createLabel();
        String endLabel = output.createLabel();
        condition.codegen(output, elseLabel,false);
        then.codegen(output);
        output.addBranchInstruction(GOTO, endLabel); 
        output.addLabel(elseLabel);
        elsePart.codegen(output);
        output.addLabel(endLabel);
    }

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JconditionExpression line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("<ThenClause>\n");
        p.indentRight();
        then.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ThenClause>\n");
        p.printf("<ElseClause\n");
        p.indentRight();
        elsePart.writeToStdOut(p);
        p.indentLeft();
        p.printf("</ElseClause>\n");
        p.indentLeft();
        p.printf("</JIfStatement>\n");     
    }


}