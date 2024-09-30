package org.palladiosimulator.retriever.gpt.Visitors;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationVisitor extends AstVisitor<MethodDeclaration> {

    public MethodDeclarationVisitor() {
        super();
    }

    public MethodDeclarationVisitor(final boolean visitDocTags, final boolean visitChildren) {
        super(visitDocTags, visitChildren);
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
        if (containsVisitedNode(node)) {
            return false;
        }
        addVisitedNode(node);
        System.out.println(node.getName());
        return visitChildren;
    }

}