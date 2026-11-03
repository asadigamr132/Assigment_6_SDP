package com.aitu.visitor;

/**
 * Common interface for files and directories in the in-memory file system.
 */
public interface Element {
    String getName();

    void accept(Visitor visitor);

    DirectoryNode getParent();
}
