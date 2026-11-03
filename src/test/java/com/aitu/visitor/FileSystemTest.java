package com.aitu.visitor;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {
    @Test
    void fileRejectsNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad.txt", -1));
    }

    @Test
    void nodesRejectBlankNames() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new DirectoryNode(""));
    }

    @Test
    void directoryRejectsNullChild() {
        DirectoryNode root = new DirectoryNode("root");
        assertThrows(NullPointerException.class, () -> root.addChild(null));
    }

    @Test
    void directoryRejectsDuplicateSiblingNames() {
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("same.txt", 10));
        assertThrows(IllegalArgumentException.class,
                () -> root.addChild(new FileNode("same.txt", 20)));
    }

    @Test
    void elementCannotHaveTwoParents() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("data.bin", 12);
        first.addChild(file);
        assertThrows(IllegalArgumentException.class, () -> second.addChild(file));
        assertSame(first, file.getParent());
    }

    @Test
    void directoryRejectsCycle() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode child = new DirectoryNode("child");
        root.addChild(child);
        assertThrows(IllegalArgumentException.class, () -> child.addChild(root));
        assertThrows(IllegalArgumentException.class, () -> root.addChild(root));
    }

    @Test
    void childrenListCannotBeModifiedExternally() {
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("a.txt", 1));
        assertThrows(UnsupportedOperationException.class, () -> root.getChildren().clear());
    }

    @Test
    void totalSizeIncludesNestedFiles() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("nested");
        root.addChild(new FileNode("one.txt", 10));
        root.addChild(nested);
        nested.addChild(new FileNode("two.txt", 25));
        assertEquals(35, new TotalSizeVisitor().calculate(root));
    }

    @Test
    void searchUsesInclusiveMinimumAndReturnsSortedPaths() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode zFolder = new DirectoryNode("z-folder");
        DirectoryNode aFolder = new DirectoryNode("a-folder");
        root.addChild(zFolder);
        root.addChild(aFolder);
        zFolder.addChild(new FileNode("match.txt", 10));
        aFolder.addChild(new FileNode("match.txt", 20));
        aFolder.addChild(new FileNode("small.txt", 9));

        SearchVisitor search = new SearchVisitor(name -> name.endsWith(".txt"), 10);
        assertEquals(List.of("root/a-folder/match.txt", "root/z-folder/match.txt"), search.search(root));
    }

    @Test
    void statisticsCountRootAndNestedElements() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("nested");
        root.addChild(nested);
        nested.addChild(new FileNode("deep.txt", 5));
        root.addChild(new FileNode("top.txt", 2));
        StatisticsVisitor stats = new StatisticsVisitor().calculate(root);
        assertEquals(2, stats.getFileCount());
        assertEquals(2, stats.getDirectoryCount());
        assertEquals(2, stats.getMaxDepth());
    }

    @Test
    void visitorsCanBeReusedWithoutKeepingPreviousResults() {
        DirectoryNode first = new DirectoryNode("first");
        first.addChild(new FileNode("one.txt", 10));
        DirectoryNode second = new DirectoryNode("second");
        second.addChild(new FileNode("two.txt", 3));

        TotalSizeVisitor size = new TotalSizeVisitor();
        assertEquals(10, size.calculate(first));
        assertEquals(3, size.calculate(second));

        StatisticsVisitor stats = new StatisticsVisitor();
        stats.calculate(first);
        stats.calculate(second);
        assertEquals(1, stats.getFileCount());
        assertEquals(1, stats.getDirectoryCount());
    }
}
