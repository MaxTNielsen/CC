package jminusminus;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static jminusminus.CLConstants.*;

class JInterfaceDeclaration extends JAST implements JTypeDecl{

     public JInterfaceDeclaration(int line, ArrayList<String> mods, String name, ArrayList<Type> interfaces, ArrayList<JMember> interfaceBlock) {
        super(line);
        mods.add("interface");
        mods.add("abstract");
        this.interfaceBlock = interfaceBlock;
        this.interfaceSuperTypes = superTypes;
        this.mods = mods;
        //hasExplicitConstructor = false;
        this.name = name;
        instanceFieldInitializations = new ArrayList<JFieldDeclaration>();
        staticFieldDeclaration = new ArrayList<JFieldDeclaration>();
    }

    /** Class modifiers. */
     private ArrayList<String> mods;

     /** Class name. */
     private String name;
 
     /** Class block. */
     private ArrayList<JMember> interfaceBlock;
 
     /** Super class type. */
     private Type superType;

     private ArrayList<Type> superTypes;
     private ArrayList<Type> interfaceSuperTypes;
 
     /** This class type. */
     private Type thisType;
 
     /** Context for this class. */
     private ClassContext context;

     private ArrayList<JFieldDeclaration> staticFieldDeclaration;
 
     /** Whether this class has an explicit constructor. */
     private boolean hasExplicitConstructor;
 
     /** Instance fields of this class. */
     private ArrayList<JFieldDeclaration> instanceFieldInitializations;
 

 
     /**
      * Constructs an AST node for a class declaration given the line number, list
      * of class modifiers, name of the class, its super class type, and the
      * class block.
      * 
      * @param line
      *            line in which the class declaration occurs in the source file.
      * @param mods
      *            class modifiers.
      * @param name
      *            class name.
      * @param superType
      *            super class type.
      * @param classBlock
      *            class block.
      */
 

public void declareThisType(Context context) {
    String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
            : JAST.compilationUnit.packageName() + "/" + name;
    CLEmitter partial = new CLEmitter(false);
    partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null,
            false); // Object for superClass, just for now
    thisType = Type.typeFor(partial.toClass());
    context.addType(line, thisType);
}

public String name() {
    return name;
}

/**
 * Returns the class' super class type.
 * 
 * @return the super class type.
 */

public Type superType() {
    return superType;
}

/**
 * Returns the type that this class declaration defines.
 * 
 * @return the defined type.
 */

public Type thisType() {
    return thisType;
}

/**
 * Returns the initializations for instance fields (now expressed as 
 * assignment statements).
 * 
 * @return the field declarations having initializations.
 */

public ArrayList<JFieldDeclaration> instanceFieldInitializations() {
    return instanceFieldInitializations;
}

@Override
public void preAnalyze(Context context) {
    this.context = new ClassContext(this, context);

    superType = superType.resolve(this.context);

    thisType.checkAccess(line, superType);
    interfaceSuperTypes = interfaceSuperTypes.stream().map(x -> x.resolve(this.context))
    .collect(Collectors.toCollection(ArrayList::new));
    
    ArrayList<String> interfaceJVMNames = this.interfaceSuperTypes.stream().map(x -> x.jvmName())
                .collect(Collectors.toCollection(ArrayList::new));
        
    CLEmitter partial = new CLEmitter(false);

    String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
    : JAST.compilationUnit.packageName() + "/" + name;
    partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), interfaceJVMNames, false);


    for (JMember member : interfaceBlock) {
        if(!(member instanceof JMethodDeclaration || member instanceof JFieldDeclaration)){
            JAST.compilationUnit.reportSemanticError(line, "Member is not valid for the interface", member.toString());
        }
        if(member instanceof JMethodDeclaration){
            JMethodDeclaration m = (JMethodDeclaration) member;
            if(!m.isStatic){
                m.setAbstract();
            }
            member = m;
        }
        if (member instanceof JFieldDeclaration) {
            JFieldDeclaration field = (JFieldDeclaration) member;
            field.setStatic();
            field.setFinal();
            member = field;
        }   
        member.preAnalyze(this.context, partial);
    }
    
    

        }

@Override
public JAST analyze(Context context) {
    for (JMember member : interfaceBlock){
        ((JAST) member).analyze(this.context);
    }

    for (JMember member : interfaceBlock){
        if (member instanceof JFieldDeclaration){
            JFieldDeclaration FD = (JFieldDeclaration) member;
            if (FD.mods().contains("static")){
                staticFieldDeclaration.add(FD);
            }
            else{
                JAST.compilationUnit.reportSemanticError(line, "Field declaration MUST be static", member.toString());
            }

        }
    }
    return this;
}

@Override
public void codegen(CLEmitter output) {

    String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
    : JAST.compilationUnit.packageName() + "/" + name;
    output.addClass(mods, qualifiedName, superType.jvmName(), null, false);

// The implicit empty constructor?
if (!hasExplicitConstructor) {
codegenImplicitConstructor(output);
}

// The members
for (JMember member : interfaceBlock) {
((JAST) member).codegen(output);
}

// Generate a class initialization method?
if (staticFieldDeclaration.size() > 0) {
codegenClassInit(output);
}

    for (JMember member : interfaceBlock){
            ((JAST) member).codegen(output);
        }

    for (JFieldDeclaration staticField : staticFieldDeclaration) {
            staticField.codegenInitializations(output); }
}

@Override
public void writeToStdOut(PrettyPrinter p) {
    p.printf("<JInterfaceDeclaration line=\"%d\" name=\"%s\">\n", line(), name);
    p.indentRight();
    if (context != null) {
        context.writeToStdOut(p);
    }
    if (mods != null) {
        p.println("<Modifiers>");
        p.indentRight();
        for (String mod : mods) {
            p.printf("<Modifier name=\"%s\"/>\n", mod);
        }
        p.indentLeft();
        p.println("</Modifiers>");
    }
    if (interfaceSuperTypes != null) {
        p.println("<Extends>");
        p.indentRight();
        for (Type extended : interfaceSuperTypes) {
            p.printf("<Extends name=\"%s\"/>\n", extended.toString());
        }
        p.indentLeft();
        p.println("</Extends>");
    }
    if (interfaceBlock != null) {
        p.println("<InterfaceBlock>");
        for (JMember member : interfaceBlock) {
            ((JAST) member).writeToStdOut(p);
        }
        p.println("</InterfaceBlock>");
    }
    p.indentLeft();
    p.println("</JInterfaceDeclaration>");
}

private void codegenImplicitConstructor(CLEmitter output) {
    // Invoke super constructor
    ArrayList<String> mods = new ArrayList<String>();
    mods.add("public");
    output.addMethod(mods, "<init>", "()V", null, false);
    output.addNoArgInstruction(ALOAD_0);
    output.addMemberAccessInstruction(INVOKESPECIAL, superType.jvmName(),
            "<init>", "()V");

    // If there are instance field initializations, generate
    // code for them
    for (JFieldDeclaration instanceField : instanceFieldInitializations) {
        instanceField.codegenInitializations(output);
    }

    // Return
    output.addNoArgInstruction(RETURN);
}

private void codegenClassInit(CLEmitter output) {
    ArrayList<String> mods = new ArrayList<String>();
    mods.add("public");
    mods.add("static");
    output.addMethod(mods, "<clinit>", "()V", null, false);

    // If there are instance initializations, generate code
    // for them
    for (JFieldDeclaration staticField : staticFieldDeclaration) {
        staticField.codegenInitializations(output);
    }

    // Return
    output.addNoArgInstruction(RETURN);
}

//private void codegenPartialImplicitConstructor(CLEmitter partial) {
    // Invoke super constructor
  //  ArrayList<String> mods = new ArrayList<String>();
   // mods.add("public");
    //partial.addMethod(mods, "<init>", "()V", null, false);
    //partial.addNoArgInstruction(ALOAD_0);
    //partial.addMemberAccessInstruction(INVOKESPECIAL, superType.jvmName(),
      //      "<init>", "()V");

    // Return
    //partial.addNoArgInstruction(RETURN);
//}
    
}


  

