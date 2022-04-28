package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;


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

	@Override
	public JAST analyze(Context context) {
		if (init != null) {
			init = (JVariableDeclaration) init.analyze(context);
		}
		// Condition must be boolean (duh)
		if (condition != null) {
			condition = condition.analyze(context);
        	condition.type().mustMatchExpected(line(), Type.BOOLEAN);
		}
		//Analyze each loop through temp.
		if (increments != null) {
	        for (int i = 0; i < increments.size(); i++) {
	        	JStatement temp = increments.get(i);
	        	temp = (JStatement) temp.analyze(context);
				// Replace i with temp.
	        	increments.set(i, temp);
	        }
		}
		body = (JStatement) body.analyze(context);
		return this;
	}


	@Override
	public void codegen(CLEmitter output) {
		if (init != null) {
			init.codegen(output);
		}
		String loop = output.createLabel();
		String out = output.createLabel();

		output.addLabel(loop);
		// Check the condition. branch out of loop if false
		if (condition != null) {
			condition.codegen(output, out, false);
		}
		// Increment the incrementer and generate code.
		body.codegen(output);
		if (increments != null) {
			for (JStatement x : increments) {
				x.codegen(output);
			}
		}
		// Loop while condition is true.
		output.addBranchInstruction(GOTO, loop);
		
		output.addLabel(out);
	}

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
