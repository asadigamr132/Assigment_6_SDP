package com.aitu.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Finds files by name predicate and inclusive minimum size.
 * Returned paths are relative to the element passed to search().
 */
public class SearchVisitor implements Visitor {

    private final Predicate<String> nameCriterion;
    private final long minimumSize;
    private final List<String> matches = new ArrayList<>();
    private String currentPath = "";

    public SearchVisitor(Predicate<String> nameCriterion, long minimumSize) {
        this.nameCriterion = Objects.requireNonNull(nameCriterion, "Name criterion cannot be null");
        if (minimumSize < 0) {
            throw new IllegalArgumentException("Minimum size cannot be negative");
        }
        this.minimumSize = minimumSize;
    }

    public List<String> search(Element root) {
        Objects.requireNonNull(root, "Root cannot be null");
        matches.clear();
        currentPath = "";
        root.accept(this);
        Collections.sort(matches);
        return Collections.unmodifiableList(new ArrayList<>(matches));
    }

    @Override
    public void visit(FileNode file) {
        if (nameCriterion.test(file.getName()) && file.getSize() >= minimumSize) {
            String path = currentPath.isEmpty()
                    ? file.getName()
                    : currentPath + "/" + file.getName();
            matches.add(path);
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        String previousPath = currentPath;
        currentPath = currentPath.isEmpty()
                ? directory.getName()
                : currentPath + "/" + directory.getName();

        for (Element child : directory.getChildren()) {
            child.accept(this);
        }

        currentPath = previousPath;
    }
}
