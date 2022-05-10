package jminusminus;

import java.util.ArrayList;
import java.util.Random;
import java.beans.Statement;

import static jminusminus.CLConstants.*;

public class JColonForStatement extends JStatement {

	// Initialize a variable. 
	private JFormalParameter init;

	private JFormalParameter param;

	// Statement that holds the array 
	private JExpression array;

	// Statement that occurs on every loop 
	protected JStatement consequent;

	// Expression for arrayLength
	protected JExpression arrayLength;

	// Statement for incrementing
	protected JStatement impUp;

	protected JVariableDeclaration forInit;
	
	public JColonForStatement(int line, JFormalParameter init, JExpression array, JStatement consequent) {
		super(line);
		this.param = init;
		this.array = array;
		this.consequent = consequent;

		// No need to set variables, since I use blocks, primary expressions and JVariableDeclarator

		ArrayList<JVariableDeclarator> forParams = new ArrayList<>();
        String randomName = "r" + (new Random().nextInt(10000));
        JVariable indexVar = new JVariable(line, randomName);
        forParams.add(new JVariableDeclarator(line, indexVar.name(), Type.INT, new JLiteralInt(line, "0")));
        this.forInit = new JVariableDeclaration(line, null, forParams);

		ArrayList<JVariableDeclarator> blockParams = new ArrayList<>();
		blockParams.add(new JVariableDeclarator(line, param.name(), param.type(), new JArrayExpression(line, array, indexVar)));

		if (consequent instanceof JBlock == false) {
			ArrayList<JStatement> statements = new ArrayList<>();
			statements.add(consequent);
			this.consequent = new JBlock(line, statements);
		}
		((JBlock) this.consequent).statements.add(0, new JVariableDeclaration(line, null, blockParams));

		// Have to create array.length by using JLessOP, check microsoft teams help channel
		// For more insight should i need it
		// String randomizer = "r"+(new Random().nextInt(100000));
		//First create index variable
		// JVariable i = new JVariable(line, randomizer);
		//Use condition to make array length, by making sure variable is less than array
		this.arrayLength = new JLessThanOp(line, indexVar, new JFieldSelection(line, array, "length"));
		//Most now create expression to increment #i (see javase/specs chapter 14.14.2)
		JExpression incrementer = new JPostIncrementOp(line, indexVar);
		this.impUp = new JStatementExpression(line, incrementer);
	
	}

	public JAST analyze(Context context) {
		// None of this is optional, therefore no Null checks.

		LocalContext localContext = new LocalContext(context);
		param.analyze(localContext);
		array.analyze(localContext);
		forInit.analyze(localContext);
		arrayLength.analyze(localContext);
		arrayLength.type().mustMatchExpected(line, Type.BOOLEAN);
		impUp.analyze(localContext);
        consequent.analyze(localContext);
        
        return this;
	}

	public void codegen(CLEmitter output) {

		// None of this is optional, therefore no Null checks.
		//init.codegen(output);
		
		String loop = output.createLabel();
		String out = output.createLabel();
		// Start test loop 
		output.addLabel(loop);
		// Branch if #i = array.length
		arrayLength.codegen(output, out, false);
		//Incrementer is called
		impUp.codegen(output);
		// Body is called after incrementer, like TA pointed out. Could lead to problems
		// otherwise.
        consequent.codegen(output);
		// GO back to test label to start loop again
		output.addBranchInstruction(GOTO, loop);
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