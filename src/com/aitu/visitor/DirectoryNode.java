package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode implements Element {
    private final String name;
    private final List<Element> children;
    private DirectoryNode parent;

    public DirectoryNode(String name){
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<Element> getChildren(){
        return Collections.unmodifiableList(children);
    }

    public void addChild(Element child){
    if(child.getParent() != null){
        throw new IllegalArgumentException("Element already has a parent");
    }
    if(child == null){
        throw new IllegalArgumentException("Child can`t be null");
    }
    for(Element existing : children){
        if(existing.getName().equals(child.getName())){
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
