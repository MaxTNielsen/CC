// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an if-statement.
 */

class JThrowStatement extends JStatement {

    /** throw expression. */
    private JExpression expression;


    public JThrowStatement(int line, JExpression expression) {
        super(line);
        this.expression = expression;
    }


    public JStatement analyze(Context context) {
        expression = (JExpression) expression.analyze(context);
        return this;
    }



    public void codegen(CLEmitter output) {
        expression.codegen(output);
        output.addNoArgInstruction(ATHROW);

    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowStatement line=\"%d\">\n", line());
        p.indentRight();
        expression.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JThrowStatement>\n");
    }

}
