# SauceDemo Automation Framework

Java automation framework covering:

- Selenium WebDriver
- Cucumber / Gherkin
- TestNG
- Chrome and Firefox
- Thread-safe parallel execution
- Page Object Model
- External CSV test data
- Cucumber HTML/JSON reports
- Failure screenshots attached to Cucumber scenarios
- REST Assured API tests
- Appium Android positive/negative login tests
- Maven build

## Prerequisites

- JDK 17+
- Maven 3.9+
- Chrome and/or Firefox
- Android SDK + emulator for mobile tests
- Appium 2.x + UiAutomator2 driver for mobile tests
- ADB (Android Debug Bridge)
- Git
- Internet connection for downloading dependencies and test applications

For UI Tests:
- Chrome and/or Firefox installed
- Java and Maven configured in PATH

For Mobile Tests:
- Android SDK installed and configured
- Android emulator created and running
- adb devices should show the emulator as device
- Appium server running on http://127.0.0.1:4723
- UiAutomator2 driver installed

For API Tests:
- No additional application is required.
- Internet access is required to reach the Simple Books API.
    
## Configure the browser and URL

Edit:

`src/test/resources/config/config.properties`

Example:

```properties
browser=chrome
baseUrl=https://www.saucedemo.com/
headless=false
```

Use:

```properties
browser=firefox
```

to run the same UI tests in Firefox.

You can also override a property from Maven:

```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

## Run only Cucumber UI tests

```bash
mvn -Dtest=runner.UiTestRunner test
```

The Cucumber runner uses:

```
@DataProvider(parallel = true)
```

so Cucumber scenarios can run simultaneously.

## Run only Cucumber Mobile tests

```bash
mvn -Dtest=runner.MobileTestRunner test
```

The Mobile Cucumber runner executes scenarios tagged with:

```
@mobile
```
The mobile tests require a running Appium server and connected Android emulator.

## Run only Cucumber API tests

```bash
mvn -Dtest=runner.ApiTestRunner test
```

The API Cucumber runner executes scenarios tagged with:

```
@api
```
The API tests use REST Assured to send requests to the Simple Books API.


## Invalid login test data

The four requested data sets are stored in:

`src/test/resources/testdata/invalid-login.csv`

They cover:

1. Missing username
2. Missing password
3. Invalid username
4. Invalid password

The Gherkin scenario outline executes all four cases.

## Reports when execute UI only

After execution:

- Cucumber HTML: `target/cucumber-reports/ui-report.html`
- Cucumber JSON: `target/cucumber-reports/ui-report.json`
- Maven/TestNG results: `target/surefire-reports/`

Failed UI scenarios receive a screenshot attachment in the Cucumber report.
## UI Test Coverage

### Login
The tests cover ( saucedemo.com ) Login functionality and cover:

- Valid login
- Invalid login with 4 external data sets
- Login error message validation
- Products page validation

### Checkout
The tests cover ( saucedemo.com ) E2E Scenario and cover:

- Add the two most expensive products
- Verify products in the cart
- Verify checkout page
- Fill checkout information
- Verify Overview page
- Verify items total
- Verify checkout URL
- Complete the order
- Verify order confirmation and dispatch messages
## API Test Coverage

The API tests use the Simple Books API and cover:

- GET books
- GET a specific book
- POST API client / token
- POST order
- GET order
- PATCH order
- DELETE order

## Mobile Test Coverage
The tests cover SauceLabs App Login functionality and cover:
- Valid login
- Invalid login
- Successful login validation
- Login error validation

Download the APK from the official Sauce Labs sample-app-mobile release and place it at:

`src/test/resources/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk`

The APK is intentionally ignored by Git because binary application files should not normally be committed to this repository.

Start Appium:

```bash
appium
```

Start an Android emulator and verify:

```bash
adb devices
```

Then run:

```bash
mvn -Dtest=mobile.MobileTest test
```

If the sample application's resource IDs differ from the release being used, update the selectors only in `MobileLoginPage.java`.

## Parallel/thread safety

`DriverFactory` uses:

```
ThreadLocal<WebDriver>
```

and the mobile factory `MobileDriverFactory` uses:

```
ThreadLocal<AndroidDriver>
```

Therefore,parallel tests do not share a WebDriver/Page session.

Each test thread owns its own browser / emulator session.

## Suggested Git workflow

```bash
git init
git add .
git commit -m "Initial automation framework"
git branch -M main
git remote add origin <YOUR_GITHUB_REPOSITORY_URL>
git push -u origin main
```

## Project structure

```
saucedemo-automation-framework/
│
├── pom.xml
├── README.md
├── testng.xml
│
└── src/
    └── test/
        │
        ├── java/
        │   │
        │   ├──  api
        │   │   ├── SimpleBooksApi.java
        │   │   └── SimpleBooksApiTest.java
        │   │
        │   ├── framework/
        │   │   ├── config/
        │   │   │   └── ConfigReader.java
        │   │   │
        │   │   ├── data/
        │   │   │   └── LoginCsvReader.java
        │   │   │
        │   │   ├── driver/
        │   │   │   └── DriverFactory.java
        │   │   │
        │   │   └── hooks/
        │   │       ├── CucumberHooks.java
        │   │       ├── MobileHooks.java
        │   │       └── WebHooks.java
        │   │
        │   ├── mobile/
        │   │   ├── MobileDriverFactory.java
        │   │   ├── MobileLoginPage.java
        │   │   └── MobileTest.java
        │   │
        │   ├── pages/
        │   │   ├── CartPage.java
        │   │   ├── CheckoutPage.java
        │   │   ├── LoginPage.java
        │   │   ├── OverviewPage.java
        │   │   └── ProductsPage.java
        │   │
        │   ├── runner/
        │   │   ├── UiTestRunner.java
        │   │   ├── MobileTestRunner.java
        │   │   └── ApiTestRunner.java
        │   │
        │   └── steps/
        │       ├── CheckoutSteps.java
        │       ├── LoginSteps.java
        │       ├── MobileLoginStepDefs.java
        │       └── SimpleBooksApiSteps.java
        │
        └── resources/
            │
            ├── app/
            │   └── Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
            │
            ├── config/
            │   └── config.properties
            │
            ├── features/
            │   ├── ui_login.feature
            │   ├── mobile_login.feature
            │   └── simple_books_api.feature
            │
            └── testdata/
                └── invalid-login.csv
```
