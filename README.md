# GitHub CI configuration file

## GitHub CI configuration file for android Continuous Integration

Here's an example of a GitHub CI configuration file (`.github/workflows/android-ci.yml`) for an Android library project that runs tests and generates a coverage report with the specified conditions:

```yaml
name: Android CI

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '11'

      - name: Set up Android SDK
        uses: actions/setup-java@v2
        with:
          java-version: '11'
          android-version: '30'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build and test
        run: |
          ./gradlew clean build connectedAndroidTest
          # Replace the above command with your build and test commands

      - name: Generate coverage report
        run: |
          ./gradlew jacocoTestReport
          # Replace the above command with your coverage report generation command

      - name: Check coverage and set status
        run: |
          COVERAGE=$(cat app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml | grep 'counter type="INSTRUCTION"' | awk '{ print $3 }' | awk -F"[=\" ]" '{ print $2 }')
          if [ "$(echo "$COVERAGE >= 90" | bc -l)" -eq 1 ]; then
            echo "Code coverage is $COVERAGE%. Coverage meets the required threshold."
            echo "::set-output name=status::success"
          else
            echo "Code coverage is $COVERAGE%. Please improve coverage to at least 90%."
            echo "::set-output name=status::failure"
          fi

      - name: Publish coverage report
        uses: actions/upload-artifact@v2
        with:
          name: coverage-report
          path: app/build/reports/jacoco/jacocoTestReport/index.html
```

In this configuration file, the `build` job performs the following steps:

1. Checks out the code from the repository.
2. Sets up JDK version 11.
3. Sets up Android SDK version 30.
4. Grants execute permission to the Gradle wrapper script.
5. Builds and runs tests using `./gradlew clean build connectedAndroidTest`. Modify this command to match your project's build and test commands.
6. Generates the coverage report using `./gradlew jacocoTestReport`. Adjust this command according to your project's coverage reporting tool or configuration.
7. Checks the coverage percentage by parsing the coverage report XML file. It extracts the coverage percentage for instructions and compares it to the desired threshold of 90%.
8. Sets the status of the job as either "success" or "failure" based on the coverage threshold.
9. Publishes the coverage report artifact to the GitHub Actions workflow artifacts.

Note: This configuration assumes you are using Gradle as your build tool and JaCoCo for code coverage. Adjust the commands and paths accordingly if you are using a different build tool or coverage reporting tool. Additionally, make sure to update the paths in the `Publish coverage report` step to match your project's coverage report location.