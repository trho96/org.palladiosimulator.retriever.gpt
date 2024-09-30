package org.palladiosimulator.retriever.gpt.FilePreprocessing;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.palladiosimulator.retriever.gpt.Helper.FileTypes;
import org.palladiosimulator.retriever.gpt.Visitors.ClassDeclarationVisitor;

public class JavaExtractor {

    private final FileDiscoverer fileDiscoverer;

    public JavaExtractor(FileDiscoverer fileDiscoverer) {
        this.fileDiscoverer = fileDiscoverer;
    }

    public String extractDescription() {
        StringBuilder descriptionBuilder = new StringBuilder();

        Map<Path, Object> files = fileDiscoverer.getFile(FileTypes.JAVA);
        ClassDeclarationVisitor visitor = new ClassDeclarationVisitor();
        files.values().forEach(file -> ((CompilationUnit) file).accept(visitor));

        for (TypeDeclaration node : visitor.getVisitedNodes()) {
            if (isTestClass(node)) {
                continue;
            }
            appendClassDetails(descriptionBuilder, node);
            appendImports(descriptionBuilder, node);
            appendBodyDeclarations(descriptionBuilder, node);
            descriptionBuilder.append("\n");
        }

        return cleanUpDescription(descriptionBuilder.toString());
    }

    private boolean isTestClass(TypeDeclaration node) {
        return node.getName().toString().contains("Test");
    }

    private void appendClassDetails(StringBuilder str, TypeDeclaration node) {
        str.append("Class:").append(node.getName()).append("\n");
        if (node.getSuperclassType() != null) {
            str.append("extends:").append(node.getSuperclassType().toString()).append("\n");
        }
        if (!node.superInterfaceTypes().isEmpty()) {
            str.append("implements:").append(node.superInterfaceTypes().toString()).append("\n");
        }
        str.append("Package:")
            .append(getPackageName(node))
            .append("\n");
        str.append("Modifiers:").append(node.modifiers().toString()).append("\n");
    }

    private String getPackageName(TypeDeclaration node) {
        Object packageProperty = node.getParent()
                                     .getStructuralProperty((StructuralPropertyDescriptor) node.getParent().structuralPropertiesForType().get(0));

        return packageProperty != null ? packageProperty.toString() : "Unknown Package";
    }


    private void appendImports(StringBuilder str, TypeDeclaration node) {
        List<Object> imports = getImports(node);
        List<String> filteredImports = imports.stream()
                                              .map(Object::toString)
                                              .filter(importStr -> importStr.contains("acme"))
                                              .collect(Collectors.toList());
        str.append("imports:").append(filteredImports.toString()).append("\n");
    }

    @SuppressWarnings("unchecked")
    private List<Object> getImports(TypeDeclaration node) {
        return (List<Object>) node.getParent()
                                  .getStructuralProperty((StructuralPropertyDescriptor) node.getParent().structuralPropertiesForType().get(1));
    }

    private void appendBodyDeclarations(StringBuilder str, TypeDeclaration node) {
        for (Object bodyDeclaration : node.bodyDeclarations()) {
            if (bodyDeclaration instanceof FieldDeclaration) {
                appendFieldDetails(str, (FieldDeclaration) bodyDeclaration);
            } else if (bodyDeclaration instanceof MethodDeclaration) {
                appendMethodDetails(str, (MethodDeclaration) bodyDeclaration);
            }
        }
    }

    private void appendFieldDetails(StringBuilder str, FieldDeclaration field) {
        String fieldName = field.fragments().toString();
        String fieldType = field.getType().toString();
        String modifiers = field.modifiers().toString();
        str.append("Field:").append(fieldName).append(": ").append(fieldType).append(modifiers).append("\n");
    }

    private void appendMethodDetails(StringBuilder str, MethodDeclaration method) {
        String methodName = method.getName().toString();
        String returnType = method.getReturnType2() != null ? method.getReturnType2().toString() : "void";
        String modifiers = method.modifiers().toString();

        if (!isPrivateOrProtected(modifiers)) {
            List<String> parameters = filterParameters(method.parameters());
            str.append("Method:").append(methodName)
               .append("(").append(parameters).append("): ")
               .append(returnType).append("\n");
        }
    }

    private boolean isPrivateOrProtected(String modifiers) {
        return modifiers.contains("private") || modifiers.contains("protected");
    }

    private List<String> filterParameters(List<Object> parameters) {
        return parameters.stream()
                         .map(Object::toString)
                         .filter(param -> !isCommonType(param))
                         .collect(Collectors.toList());
    }

    private boolean isCommonType(String parameter) {
        return parameter.contains("String") || parameter.contains("int") || parameter.contains("ArrayList") ||
               parameter.contains("Date") || parameter.contains("BigDecimal") || parameter.contains("double") ||
               parameter.contains("boolean");
    }

    private String cleanUpDescription(String description) {
        return description.replace("[", "").replace("]", "");
    }
}
