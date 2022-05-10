package jminusminus;
import static jminusminus.CLConstants.*;

public class JLiteralDouble extends JExpression {
    /** String representation of the int. */
    private String text;

    /**
     * Constructs an AST node for an {@code int} literal given its line number 
     * and string representation.
     * 
     * @param line
     *            line in which the literal occurs in the source file.
     * @param text
     *            string representation of the literal.
     */

    public JLiteralDouble(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Analyzing the null literal is trivial.
     * 
     * @param context
     *            context in which names are resolved (ignored here).
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        type = Type.DOUBLE;
        return this;
    }

    /**
     * Generating code for a null literal means generating code to push it onto
     * the stack.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        double d = Double.parseDouble(text);
        switch (d) {
        case (0.0):
            output.addNoArgInstruction(DCONST_0);
            break;
        case (1.0):
            output.addNoArgInstruction(DCONST_1);
            break;
        default:
                output.addLDCInstruction(d);
        }
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JLiteralInt line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n",
                line(), ((type == null) ? "" : type.toString()), text);
    }

}