### Introduction: 

The aim of the project is to develop a Plagiarism Detection Program designed to compare two sets of source code files or text files and determine whether they exhibit plagiarism. This program assists in identifying potential instances of content reuse and calculates the plagiarism percentage to provide a clear indication of similarity between the files.

To achieve this goal, two algorithms have been employed:

1. **Levenshtein Distance:** This algorithm calculates the minimum number of operations required to transform one text into another. It's used to assess plagiarism between the files. Plagiarism is calculated as follows: Plagiarism = (1 - (Distance / Max(File1.size, File2.size))) * 100. If the calculated plagiarism is not within the threshold (22.5%), then it is concluded that the files are plagiarized.

2. **Jaccard Similarity:** This algorithm counts the common words between two files to determine plagiarism. The plagiarism is calculated as follows: Plagiarism = (File1 Intersection File2) / (File1 Union File2) * 100. If the calculated plagiarism is not within the threshold (34.5%), then it is concluded that the files are plagiarized.

The paths of the two files are taken as command-line arguments. The files are read using a Buffered Reader. Afterward, the program checks whether the files are text or code by utilizing certain keywords that aid in distinguishing between the two.

In the case of code, the data is pre-processed by removing extra spaces and tabs. Then, the Levenshtein Distance algorithm is applied. Similarly, for text data, all text is converted to lowercase, punctuations are removed, and then the Jaccard Similarity algorithm is applied.
