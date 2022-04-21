/**
 * 
 */
package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * @author KyleD
 *
 */
public class JForStatement extends JStatement {
	
	private JVariableDeclaration init;
	private JExpression condition;
	private ArrayList<JStatement> increments;
	private JStatement body;
	
	public JForStatement(int line, JVariableDeclaration init, 
			JExpression condition, ArrayList<JStatement> increments, 
			JStatement body) {
		super(line);
		this.init = init;
		this.condition = condition;
		this.increments = increments;
		this.body = body;
	}

	/* (non-Javadoc)
	 * @see jminusminus.JAST#analyze(jminusminus.Context)
	 */
	@Override
	public JAST analyze(Context context) {
		if (init != null) {
			init = (JVariableDeclaration) init.analyze(context);
		}
		if (condition != null) {
			condition = condition.analyze(context);
        	condition.type().mustMatchExpected(line(), Type.BOOLEAN);
		}
		if (increments != null) {
	        for (int i = 0; i < increments.size(); i++) {
	        	JStatement temp = increments.get(i);
	        	temp = (JStatement) temp.analyze(context);
	        	increments.set(i, temp);
	        }
		}
		body = (JStatement) body.analyze(context);
		return this;
	}

	/* (non-Javadoc)
	 * @see jminusminus.JAST#codegen(jminusminus.CLEmitter)
	 */
	@Override
	public void codegen(CLEmitter output) {
		if (init != null) {
			init.codegen(output);
		}
		String test = output.createLabel();
		String out = output.createLabel();

		output.addLabel(test);
		if (condition != null) {
			condition.codegen(output, out, false);
		}
		
		body.codegen(output);
		if (increments != null) {
			for (JStatement inc : increments) {
				inc.codegen(output);
			}
		}
		output.addBranchInstruction(GOTO, test);
		
		output.addLabel(out);
	}

	/* (non-Javadoc)
	 * @see jminusminus.JAST#writeToStdOut(jminusminus.PrettyPrinter)
	 */
	@Override
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JForStatement line=\"%d\">\n", line());
		p.indentRight();
		p.printf("<ForInitial>\n");
        p.indentRight();
        if (init != null) {
        	init.writeToStdOut(p);
        } else {
        	p.printf("Empty forInit\n");
        }
        p.indentLeft();
        p.printf("<Condition Test>\n");
        p.indentRight();
        if (condition != null) {
        	condition.writeToStdOut(p);
        } else {
        	p.printf("Empty condition\n");
        }
        p.indentLeft();
        p.printf("<ForUpdate>\n");
        p.indentRight();
        if (increments != null) {
        	for (JStatement inc : increments) {
        		inc.writeToStdOut(p);
        	}
        } else {
        	p.printf("Empty forUpdate\n");
        }
        p.indentLeft();
        p.printf("</ForExpression>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JForStatement>\n");
	}

}