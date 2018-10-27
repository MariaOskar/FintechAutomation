# FintechAutomation

**JDK 1.8.0_162**  <br/>
This project contains tests of form on moscow-job.tinkoff page. <br/>
Package `ru.oskerko.imported` contains tests, which were imported from katalon recorder and customized.<br/>
Package `ru.oskerko.handwritten` contains tests written without using any recorders.<br/>
Tests optimized for multithreading.

To run all tests with concrete driver:
````bash
mvn test -Ddriver=firefox 
````
To run concrete test/s with concrete driver:
````bash
mvn test -Ddriver=firefox -Dtest=ru.oskerko.imported.JobFormTests,ru.oskerko.handwritten.JobFormTests
````


**Available tests:**
- ru.oskerko.imported.JobFormTests
  - testRequiredFieldsMessages
  - testInvalidDataMessages
- ru.oskerko.handwritten.JobFormTests
  - testRequiredFieldsMessages
  - testInvalidDataMessages

**Available drivers:**
- chrome
- chrome_headless
- firefox
- opera

> To change path to firefox/opera binary set right properties in the `src\test\resources\application.properties`. <br/>
> **For example:**  
````bash
driver.firefox.bin = D:\\MozillaFirefox\\firefox.exe
driver.opera.bin = C:\\Program Files\\Opera\\56.0.3051.52\\opera.exe
````