package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DirectoryNode implements Element {

    private final String name;
    private final List<Element> children = new ArrayList<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Directory name cannot be empty");
        }
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
        Objects.requireNonNull(child, "Child cannot be null");

        if (child.getParent() != null) {
            throw new IllegalArgumentException("Element already has a parent");
        }

        for (Element existing : children) {
            if (existing.getName().equals(child.getName())) {
                throw new IllegalArgumentException("Duplicate child name: " + child.getName());
            }
        }

        DirectoryNode current = this;
        while (current != null) {
            if (current == child) {
                throw new IllegalArgumentException("Adding this element would create a cycle");
            }
            current = current.getParent();
        }

        child.accept(new ParentAssignmentVisitor(this));
        children.add(child);
    }

    void assignParent(DirectoryNode parent) {
        this.parent = parent;
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
