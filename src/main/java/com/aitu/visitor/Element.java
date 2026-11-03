package com.aitu.visitor;

public interface Element {
    String getName();

    void accept(Visitor visitor);

    DirectoryNode getParent();
}
