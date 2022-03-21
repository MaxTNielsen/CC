package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;


public class JForStatement extends JStatement {
	
	private JVariableDeclaration init;

	private JExpression condition;

	private ArrayList<JStatement> incrementer;

	private JStatement body;
	
	public JForStatement(int line, JVariableDeclaration init, 
			JExpression condition, ArrayList<JStatement> incrementer, 
			JStatement body) {
		super(line);
		this.init = init;
		this.condition = condition;
		this.incrementer = incrementer;
		this.body = body;
	}

	@Override
	public JAST analyze(Context context) {
		if (init != null) {
			init = (JVariableDeclaration) init.analyze(context);
		}
		if (condition != null) {
			condition = condition.analyze(context);
        	condition.type().mustMatchExpected(line(), Type.BOOLEAN);
		}
		if (incrementer != null) {
	        for (int i = 0; i < incrementer.size(); i++) {
	        	JStatement temp = incrementer.get(i);
	        	temp = (JStatement) temp.analyze(context);
	        	incrementer.set(i, temp);
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
		String test = output.createLabel();
		String out = output.createLabel();

		output.addLabel(test);
		if (condition != null) {
			condition.codegen(output, out, false);
		}
		
		body.codegen(output);
		if (incrementer != null) {
			for (JStatement inc : incrementer) {
				inc.codegen(output);
			}
		}
		output.addBranchInstruction(GOTO, test);
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
        if (incrementer != null) {
        	for (JStatement inc : incrementer) {
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
