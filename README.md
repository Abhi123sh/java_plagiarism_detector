### Introduction: 

The aim of the project is to develop a plagiarism detector that compares two text as well as code files and checks if they are plagiarized or not. If plagiarized, it also detects the percentage of plagiarized data.

To achieve the goal, two algorithms have been used:

* The LevenshtTheien Distance - The minimum number of operations to convert one text into another is determined. This distance is used to calculate plagiarism between the files. 
Plagiarism = (1-(Distance/Max(File1.size , File2.size)))*100
If it is not within a threshold (22.5%), then it is concluded that the files are plagiarized. 

* Jaccard Similarity - The common words between two files are counted to determine the plagiarism. 
Plagiarism = (File1 Intersection File2)/(File1 Union File2)*100
If it is not within a threshold (34.5%), then it is concluded that the files are plagiarized. 

The path of the two files is taken as command-line arguments. The files are read using Buffered Reader after which the files are checked to determine if the files are text or code using some keywords that can help differentiating between both. 

In case of code, the data is pre-processed extra spaces and tabs. Then Levenshtheien Distance algorithm is applied. Similarly, for text data, all text is converted to lower case, punctuations are removed and then Jaccard Similarity algorithm is applied.
