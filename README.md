### Task
- Two (2) servers shall read and expose one text file each. (frankenstein.txt and dracula.txt)
- One (1) client shall read and count the data from the two servers in parallel. As a
  suggestion the communication between server and client use sockets.
- The code should work just as well for very large files, thus do not keep entire files in
  memory at any time.
- The result shall be one (1) print out of the 5 most common words in the two texts with the
  total number of occurrences of the word.