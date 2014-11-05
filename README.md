Search Engine Project Version 1
-------------------------------

Team 2: Tom, Zack, Austin, Brandon

This iteration of the SearchEngine is designed to support
storing data on file, rather than in memory. The implementation
of this functionality will be described below.

Broad Concept
-------------

Our "database" is structured using two different files.
The first file contains field objects, and the second the identifiers.

The "IntegratedFileDatabase" class handles both databases and managing
the relationships and data of the two sub-databases.

The "FieldDatabase" class defines a set of operations that any implementation
for the database containing field objects must adhere to. 
The best implementation of it is currently the "AVLFieldDatabase" class.
It uses an AVL Tree to store the field objects on file. Each node in the 
tree contains information needed for the tree, the field it contains, and
a pointer to an identifier list.

The "IdentifierDatabase" class manages the storing and retrieval of identifiers.
They are stored as linked lists, with one node per identifer. Each node contains
a pointer to the next node. The address of the head of each linked list is 
stored in the corresponding field object.

Both databases are implemented using chunked access, so the file is divided up
into "chunks" of equal size. Each chunk contains one node. This is true
for both databases.

Example Diagrams
----------------

See "Example Diagrams.txt"

Existing class descriptions
---------------------------

Indexer.java

> The Indexer is simple: it stores data from a Field object 
> into the Database, calling the database "store" method. The 
> idea of an Indexer is that is may get called many times on
> behalf of one file, or even one line in a file.  It's the 
> file name, or the line number, that needs to be remembered
> later so that a query tells the user the location of the 
> content. Therefore, the identifier (file name, line number) 
> is a string that is saved by Indexer's constructor.

FieldSeach.java

> The FieldSearch class is very simple; in fact, it is far 
> too simple when thinking about future enhancements to the 
> project.  Currently there is only one kind of search, find
> all the locations where a specified Field object is found.

Database.java

> Unlike the other files, this one's not a class. Database is 
> an interface, which just has the methods for working with 
> the persistent storage.  What's perhaps interesting to learn
> is that an interface type can be used to type a variable, 
> and that is what Indexer and FieldSearch do: they have a variable
> of type Database that is set in the constructor. Conceptually, 
> there is only one Database, so it could be a class (static) 
> implementation rather than instance-based; we're using an 
> instance-based implementation because it offers a bit more 
> flexibility for unit testing. 

LinearMemoryDatabase.java

> This class implements the Database interface in memory, 
> with a simple ArrayList of Nodes. Search and update is 
> by brute-force iteration to find a Node with the desired
> key.  

FieldTest.java

> The integration test for all classes above. 

Limitation
----------

A major limitation of this design is that the entire Field 
object itself is a key for lookup into the Database. It's done
by converting Field into a byte array (the "toBytes" method 
does this).  In future, we will have to think how to do things
differently, so that users can query by Field name, or by 
Field value, or by a range: show me anything with a field name
of "quantity" and an associated value in the range 100-200.
