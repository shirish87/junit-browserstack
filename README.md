JUnit-BrowserStack
=========

Sample for running [JUnit] tests with BrowserStack Automate.

### Configuring the capabilities
- Open `src/main/java/com/browserstack/JUnitTest.java` or `src/main/java/com/browserstack/JUnitParallelSample.java`.
- Add `username` and `accessKey` with your BrowserStack credentials. Don't have one? Get one on BrowserStack [dashboard].
- Add / customise more [capabilities].
- Optionally, you can add your BrowserStack credentials to the environment variables `BROWSERSTACK_USER` and `BROWSERSTACK_ACCESSKEY`.

### Running the tests
- Run `mvn compile`
- To start sample test, run: `mvn exec:java -Dexec.mainClass="org.junit.runner.JUnitCore" -Dexec.args="com.browserstack.JUnitTest"`
- To start local tests, run: `mvn exec:java -Dexec.mainClass="org.junit.runner.JUnitCore" -Dexec.args="com.browserstack.JUnitLocalTest"`
- To start parallel tests, run: `mvn exec:java -Dexec.mainClass="org.junit.runner.JUnitCore" -Dexec.args="com.browserstack.JUnitParallelTest"`

[JUnit]:http://junit.org
[capabilities]:http://www.browserstack.com/automate/capabilities
[dashboard]:https://www.browserstack.com/automate
