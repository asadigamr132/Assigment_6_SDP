package com.aitu.visitor;

public class Main {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode projects = new DirectoryNode("projects");
        root.addChild(documents);
        root.addChild(projects);

        documents.addChild(new FileNode("notes.txt", 120));
        documents.addChild(new FileNode("report.pdf", 850));
        projects.addChild(new FileNode("Main.java", 240));
        projects.addChild(new FileNode("README.md", 80));

        long totalSize = new TotalSizeVisitor().calculate(root);
        SearchVisitor search = new SearchVisitor(
                name -> name.endsWith(".pdf") || name.endsWith(".java"), 100);
        StatisticsVisitor stats = new StatisticsVisitor().calculate(root);

        System.out.println("Total size: " + totalSize + " bytes");
        System.out.println("Search results: " + search.search(root));
        System.out.println("Files: " + stats.getFileCount());
        System.out.println("Directories: " + stats.getDirectoryCount());
        System.out.println("Maximum depth: " + stats.getMaxDepth());
    }
}
