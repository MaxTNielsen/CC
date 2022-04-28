package jminusminus;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static jminusminus.CLConstants.*;

class JInterfaceDeclaration extends JAST implements JTypeDecl{

     public JInterfaceDeclaration(int line, ArrayList<String> mods, String name, ArrayList<Type> extend, ArrayList<JMember> interfaceBlock) {
        super(line);
        mods.add("interface");
        mods.add("abstract");
        this.interfaceBlock = interfaceBlock;
        this.interfaceSuperType = extend;
        this.mods = mods;
        this.name = name;
        this.staticFieldDeclaration = this.interfaceBlock.stream()
            .filter(x -> x instanceof JFieldDeclaration)
            .map(x -> (JFieldDeclaration) x)
            .collect(Collectors.toCollection(ArrayList::new));
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

     private ArrayList<Type> interfaceSuperType;
 
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

    interfaceSuperType = interfaceSuperType.stream().map(x -> x.resolve(this.context)).collect(Collectors.toCollection(ArrayList::new));
    
    ArrayList<String> interfaceJVMNames = this.interfaceSuperType.stream().map(x -> x.jvmName())
                .collect(Collectors.toCollection(ArrayList::new));
        
    CLEmitter partial = new CLEmitter(false);

    String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
    : JAST.compilationUnit.packageName() + "/" + name;
    partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), interfaceJVMNames, false);



    

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
     ArrayList<String> ifaceJVMNames = interfaceSuperType.stream().map(x -> x.jvmName()).collect(Collectors.toCollection(ArrayList::new));

     String qualifiedname = JAST.compilationUnit.packageName() == "" ? name : JAST.compilationUnit.packageName() + "/" + name;
     output.addClass(mods, qualifiedname, Type.OBJECT.jvmName(), ifaceJVMNames, false);

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
    if (interfaceSuperType != null) {
        p.println("<Extends>");
        p.indentRight();
        for (Type extended : interfaceSuperType) {
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
    
}


  

