
# Running the Program

- Compile the files using the command:

```
javac FlowLogAnalyzer.java ProtocolName.java
```

- Run the files using the command:

```
java FlowLogAnalyzer  <flowLogFilePath> <lookupFilePath>
```

- For the submitted files, this will work:

```
java FlowLogAnalyzer ./files/flowlogs.txt ./files/lookup.csv
```

- Changing DEBUG value to true in FlowLogAnalyzer.java will help if file locations turn out to be tricky.

# Running the Tests

- Compile the files using the command:

```
javac FlowLogAnalyzer.java ProtocolName.java FlowLogAnalyzerTest.java
```

- Run the tests using the command:

```
java FlowLogAnalyzerTest
```

- If the tests are successful, you should see 'All tests passed!' in the console.

- The current test methods use assertions that halt execution on failure. A testing framework will collect all test results and report failures without stopping execution.

- The test harness writes temporary files to disk, which can be slow and may leave residual files if tests fail. Should improve by using in-memory streams (StringReader, StringWriter) to simulate file I/O in tests.

# Thoughts

- Console outputs should ideally be replaced with a logging framework.
- File readers and writers may not be closed properly in case of exceptions. In production, we should probably use try-with-resources statements to ensure that resources are closed automatically.

- We can use multi-threading to process large flow log files in parallel, splitting the file into chunks and processing each independently. Just need to be mindful of thread safety when updating shared data structures.

- It would be a great idea to externalize configurations like file paths, delimiter characters, and protocol mappings in a configuration file.
