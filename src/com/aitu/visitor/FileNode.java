package com.aitu.visitor;

public class FileNode implements Element{

    private final String name;
    private final long size;
    private DirectoryNode parent;
    public FileNode(String name, long size) {
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }

        this.name = name;
        this.size = size;
    }

    void assignParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public long getSize(){
        return size;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DirectoryNode getParent(){
        return parent;
    }
}
