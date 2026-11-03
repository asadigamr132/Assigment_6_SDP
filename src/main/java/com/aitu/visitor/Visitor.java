package com.aitu.visitor;

/**
 * Visitor operations for the two element types in the file system.
 */
public interface Visitor {
    void visit(FileNode file);

    void visit(DirectoryNode directory);
}
