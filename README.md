elon.bendor
203016308
Nadav.meidan
200990240

===================================
= File Description =
===================================
filesprocessing.java
README

==================================
= Design =
==================================

we worked on this project thinking, first of all, how can we make this as simple and elegant as possible.
we realised that there are plenty of issues to attend and address. so we decided to separate the whole thing
into main classes that will hold nested classes, if needed to be.

=====================================
= Implementation Details =
=====================================

1. we have the main Directory Processor class that has the the Main method, and that uses the other classes
to do the procedure of reading the command file, filtering the list of files and ordering them.
the order and filter classes both use a bunch of nested classes to represent the given filters and orders
and both use the enum to get the right filtering or ordering method.
in the command file, block and exceptions classes we simply build an interface that works with the given
object or situation and then builds or prints the suitable thing.

2. ???

3. we simply used an array list of files, and we used the sorting methods that java has
for sorting an array.

