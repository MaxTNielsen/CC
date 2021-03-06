// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * This abstract base class is the AST node for an assignment statement.
 */

abstract class JAssignment extends JBinaryExpression {

    /**
     * Constructs an AST node for an assignment operation.
     * 
     * @param line
     *                 line in which the assignment operation occurs in the source
     *                 file.
     * @param operator
     *                 the actual assignment operator.
     * @param lhs
     *                 the lhs operand.
     * @param rhs
     *                 the rhs operand.
     */

    public JAssignment(int line, String operator, JExpression lhs,
            JExpression rhs) {
        super(line, operator, lhs, rhs);
    }

}

/**
 * The AST node for an assignment (=) expression. The = operator has two
 * operands: a lhs and a rhs.
 */

class JAssignOp extends JAssignment {

    /**
     * Constructs the AST node for an assignment (=) expression given the lhs and
     * rhs operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             lhs operand.
     * @param rhs
     *             rhs operand.
     */

    public JAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs, checking that types match, and sets the result
     * type.
     * 
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = rhs.type();
        if (lhs instanceof JVariable) {
            IDefn defn = ((JVariable) lhs).iDefn();
            if (defn != null) {
                // Local variable; consider it to be initialized now.
                ((LocalVariableDefn) defn).initialize();
            }
        }
        return this;
    }

    /**
     * Code generation for an assignment involves, generating code for loading
     * any necessary Lvalue onto the stack, for loading the Rvalue, for (unless
     * a statement) copying the Rvalue to its proper place on the stack, and for
     * doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        rhs.codegen(output);
        if (!isStatementExpression) {
            // Generate code to leave the Rvalue atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }

}

/**
 * The AST node for a += expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JPlusAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a += expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JPlusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs, rewrites rhs as lhs + rhs (string concatenation)
     * if lhs is of type {@code String}, and sets the result type.
     * 
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for += involves, generating code for loading any
     * necessary l-value onto the stack, for (unless a string concatenation)
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if (lhs.type().equals(Type.INT)){
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        } else {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(DADD);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }

}

/**
 * The AST node for a -= expression. A -= expression has two operands: a lhs and
 * a rhs.
 */

class JMinusAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a -= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JMinusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs
     * 
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for -=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for -= involves, generating code for loading any
     * necessary l-value onto the stack, for loading the r-value,
     * for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        if (type == Type.DOUBLE){
            output.addNoArgInstruction(DSUB);
        } else {
        output.addNoArgInstruction(ISUB);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a -= expression. A -= expression has two operands: a lhs and
 * a rhs.
 */

class JDivAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a /= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JDivAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs and sets the result type.
     * 
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for /=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        if (type == Type.DOUBLE){
            output.addNoArgInstruction(DDIV);
        } else {
        output.addNoArgInstruction(IDIV);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a *= expression. A *= expression has two operands: a lhs and
 * a rhs.
 */

class JStarAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a *= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JStarAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for *=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for *= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        if (type == Type.DOUBLE){
            output.addNoArgInstruction(DMUL);
        } else {
        output.addNoArgInstruction(IMUL);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for a %= expression. A %= expression has two operands: a lhs and
 * a rhs.
 */

class JRemAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JRemAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%=", lhs, rhs);
    }



    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else if (lhs.type().equals(Type.DOUBLE)) {
            rhs.type().mustMatchExpected(line(), Type.DOUBLE);
            type = Type.DOUBLE;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for %=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        if (type == Type.DOUBLE){
            output.addNoArgInstruction(DREM);
        } else {
        output.addNoArgInstruction(IREM);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

/**
 * The AST node for an OR assignment (|=) expression. The |= operator has two
 * operands: a lhs and a rhs.
 */

class JOrAssignOp extends JAssignment {

    /**
     * Constructs the AST node for an OR assignment (|=) expression given the lhs and
     * rhs operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             lhs operand.
     * @param rhs
     *             rhs operand.
     */

    public JOrAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs, checking that types match, and sets the result
     * type.
     * 
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for |=: " + lhs.type());
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IOR);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

class JAndAssignOp extends JAssignment {

    public JAndAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&=", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            type = Type.ANY;
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { 
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }  else if (lhs.type().equals(Type.BOOLEAN)) {
            rhs.type().mustMatchExpected(line(),Type.BOOLEAN);
            type = Type.BOOLEAN;
        }  else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for %=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IREM);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

class JXORAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JXORAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^=", lhs, rhs);
    }



    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { // tilf??j support for double her
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else{
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for %=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IXOR);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

class JLNOTAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JLNOTAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "!=", lhs, rhs);
    }



    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { // tilf??j support for double her
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else{
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for !=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(INEG);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

class JSHLAssignOp extends JAssignment {

    /**
     * Constructs the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

    public JSHLAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<=", lhs, rhs);
    }



    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { // tilf??j support for double her
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else{
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for !=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(ISHL);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

    /**
     * Constructs the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *             line in which the assignment expression occurs in the source
     *             file.
     * @param lhs
     *             the lhs operand.
     * @param rhs
     *             the rhs operand.
     */

class JSHRAssignOp extends JAssignment {

    public JSHRAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>=", lhs, rhs);
    }

    /**
     * Analyzes the lhs and rhs and sets the result type.
     * @param context
     *                context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { // tilf??j support for double her
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }
        else{
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for !=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for %= involves, generating code for loading any
     * necessary l-value onto the stack, for
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *               the code emitter (basically an abstraction for producing the
     *               .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(ISHR);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
}

class JUshiftRightAssignOp extends JAssignment {

    public JUshiftRightAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>=", lhs, rhs);
    }

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            type = Type.ANY;
            return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) { 
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        }   else {
            type = type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for %=: " + lhs.type());
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        ((JLhs) lhs).codegenLoadLhsRvalue(output);
        rhs.codegen(output);
        output.addNoArgInstruction(IREM);
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    
    }
}

