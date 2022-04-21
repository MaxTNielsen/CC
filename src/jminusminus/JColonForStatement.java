package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

public class JColonForStatement extends JStatement {

	/** Initialize a variable. */
	private JVariableDeclarator init;

	/** Statement that holds the array */
	private JExpression array;

	/** Statement that occurs on every loop */
	private JBlock consequent;
	
	public JColonForStatement(int line, JVariableDeclarator init, JExpression array, JBlock consequent) {
		super(line);
		this.init = init;
		this.array = array;
		this.consequent = consequent;
	}

	public JAST analyze(Context context) {
        if (init != null) {
			init = (JVariableDeclarator) init.analyze(context);
		}
        if (array != null) {
			array = array.analyze(context);
		}
        if (consequent != null) {
            consequent.analyze(context);
        }
        return this;
	}

	public void codegen(CLEmitter output) {

        if (init != null) {
			init.codegen(output);
		}
		String test = output.createLabel();
		String out = output.createLabel();

		output.addLabel(test);
		if (array != null) {
			array.codegen(output, out, false);
		}
		
		consequent.codegen(output);
		if (consequent != null) {
            
		}
		output.addBranchInstruction(GOTO, test);
		output.addLabel(out);

	}

	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JColonForStatement line=\"%d\">\n", line());
		p.indentRight();
		p.printf("<Init>\n");
		p.indentRight();
		init.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Init>\n");
		p.printf("<Array>\n");
		p.indentRight();
		array.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Array>\n");
		p.indentRight();
		p.printf("<Consequent>\n");
		p.indentRight();
		consequent.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Consequent>\n");
		p.indentLeft();
		p.printf("</JColonForStatement>\n");
	}

}