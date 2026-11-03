# Assignment Report — Visitor Pattern

**Project:** In-Memory File System using Visitor  
**Language:** Java 17  
**Build:** Maven  
**Repository:** https://github.com/asadigamr132/Assigment_6_SDP

## Purpose and design

The project models a hierarchical in-memory file system. A file stores a name and a non-negative byte size. A directory stores named child elements and may contain other directories. The Visitor pattern adds operations over this structure without placing operation-specific code inside the node classes.

`Element` is the common abstraction. `FileNode` and `DirectoryNode` implement it and provide `accept(Visitor)`. The Visitor interface declares overloaded methods for both concrete element types. This is double dispatch: the element selects the visitor method matching its concrete type.

Three visitors are implemented:

- `TotalSizeVisitor` recursively sums file sizes.
- `SearchVisitor` matches filenames using a configurable predicate and includes files whose size is greater than or equal to the minimum threshold. It returns lexicographically sorted root-relative paths.
- `StatisticsVisitor` counts files and directories and calculates maximum depth, with the visited root at depth zero.

Traversal is visitor-owned. Each visitor recursively visits a directory's children. The elements remain independent of the operations performed on them.

## UML

The UML class diagram is included in the project README and shows Element, FileNode, DirectoryNode, Visitor and all three concrete visitors.

## Clean Code justifications

1. **Naming:** class and method names communicate intent, such as `calculate`, `search`, and `getMaxDepth`.
2. **Single Responsibility:** file-system nodes represent data and relationships; each visitor performs one operation.
3. **Small methods:** each overloaded `visit` method is focused on a single element type.
4. **Encapsulation:** fields are private, children are returned as an unmodifiable list, and parent assignment is not exposed as a public setter.
5. **Polymorphism:** overloaded visitor methods replace `instanceof`, casts and type-check chains.

## Validation and tests

The JUnit 5 test class `FileSystemTest` verifies invalid sizes and names, null children, duplicate sibling names, multiple parent ownership, cycle prevention, child-list encapsulation, recursive size totals, inclusive search thresholds, lexicographically sorted paths, statistics and visitor reuse.

Run tests:

```bash
mvn test
```

Run the demo:

```bash
mvn exec:java -Dexec.mainClass="com.aitu.visitor.Main"
```

## Sample output

```text
Total size: 1290 bytes
Search results: [root/documents/report.pdf, root/projects/Main.java]
Files: 4
Directories: 3
Maximum depth: 2
```

## Trade-offs

Visitor makes it straightforward to add new operations while keeping element classes stable. Its trade-off is that adding a new element type requires another method in the Visitor interface and corresponding updates to concrete visitors. For a fixed set of element types and a growing set of operations, this is a reasonable design.
