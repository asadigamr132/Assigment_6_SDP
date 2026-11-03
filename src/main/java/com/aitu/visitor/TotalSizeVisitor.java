package com.aitu.visitor;

import java.util.Objects;

/**
 * Calculates the sum of all file sizes in an element's subtree.
 */
public class TotalSizeVisitor implements Visitor {

    private long totalSize;

    public long calculate(Element root) {
        Objects.requireNonNull(root, "Root cannot be null");
        totalSize = 0;
        root.accept(this);
        return totalSize;
    }

    @Override
    public void visit(FileNode file) {
        totalSize += file.getSize();
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
    }

    public long getTotalSize() {
        return totalSize;
    }
}
