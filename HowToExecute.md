# How to execute parallel test and view reports & screen shots?
1- First step will be installation of the tools to run the test script.
  *** As scripting language is Java 8, you need to have Java installed 
https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
  *** IntelliJ IDEA as the integrated development environment.
https://www.jetbrains.com/idea/download/
2- Second step will be cloning the project from Github repository.
With a click on "File" option in top menu bar, you will be able to see "New" and then "Project from Version Control".
You just need to provide the Url of the repository: https://github.com/SumeyyeC/VossAssessment
3- Third step is to execute the written test scripts.
"parallelTest.xml" file is an xml file which enables parallel testing of our test suite and covers both Firefox and Chrome browsers.
Just with right click and selecting TestNG Run, it will execute the suite first with chrome and then firefox browser.
4- After execution of this xml file, the screenshots and test reports will have been generated under "test-output" folder.
You can view these reports with a right click and by selecting one of "Open in Browser" options.


