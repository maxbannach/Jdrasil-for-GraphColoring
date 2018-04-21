# Jdrasil-for-GraphColoring
Demonstrate the new interface of Jdrasil for dynamic programming on tree decompositions.

# Build and Run
Simply execute the following commands to build and run the tool. You may want to manually get a newer version of [Jdrasil](https://maxbannach.github.io/Jdrasil/), though.
```
mkdir bin
javac -cp libs/Jdrasil.jar -d bin src/*.java
java -cp libs/Jdrasil.jar:bin/ Main < example.gr
```
# The File Format
The tool works with both, the DIMACS graph coloring format (aka .col), as well as with the very similar format used by the PACE challenge (aka .gr), see [here](https://pacechallenge.wordpress.com/pace-2016/track-a-treewidth/) for a description.

An simple example may look as follows:
```
p edge 4 5
e 1 2
e 1 3
c we may add some comments here and there
e 2 3
e 2 4
e 3 4
```
