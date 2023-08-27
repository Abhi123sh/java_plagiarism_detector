build: 
	javac s40185375_detector.java

run: build
	java s40185375_detector "$(FILE1)" "$(FILE2)"
