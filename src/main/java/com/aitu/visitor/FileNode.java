package com.aitu.visitor;

import java.util.Objects;

public class FileNode implements Element {

    private final String name;
    private final long size;
    private DirectoryNode parent;

    public FileNode(String name, long size) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        this.name = name;
        this.size = size;
    }

    void assignParent(DirectoryNode parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        Objects.requireNonNull(visitor, "Visitor cannot be null").visit(this);
    }

    @Override
    public DirectoryNode getParent() {
        return parent;
    }
}
