package com.aitu.visitor;

/**
 * Internal visitor used by DirectoryNode to set parent links without casts
 * or exposing a public parent setter on Element.
 */
final class ParentAssignmentVisitor implements Visitor {

    private final DirectoryNode parent;

    ParentAssignmentVisitor(DirectoryNode parent) {
        this.parent = parent;
    }

    @Override
    public void visit(FileNode file) {
        file.assignParent(parent);
    }

    @Override
    public void visit(DirectoryNode directory) {
        directory.assignParent(parent);
    }
}
