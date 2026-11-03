package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode implements Element {

    private final String name;
    private final List<Element> children = new ArrayList<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(Element child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }
        if (child.getParent() != null) {
            throw new IllegalArgumentException("Element already has a parent");
        }
        for (Element existing : children) {
            if (existing.getName().equals(child.getName())) {
                throw new IllegalArgumentException("Duplicate child name");
            }
        }
        children.add(child);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DirectoryNode getParent() {
        return parent;
    }
}
