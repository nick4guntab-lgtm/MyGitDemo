# Step 1: Fetch an official lightweight Linux image pre-loaded with Maven and OpenJDK 21
FROM maven:3.9-eclipse-temurin-21

# Step 2: Create a workspace folder inside that Linux container
WORKDIR /home/automation

# Step 3: Copy your pom.xml into the folder to prepare for download
COPY pom.xml .

# Step 4: Tell Maven to pre-download all your dependencies (Selenium, TestNG, Jackson, etc.)
RUN mvn dependency:go-offline

# Step 5: Copy all your main and test source architecture blueprints
COPY src ./src
COPY *.xml ./

# Step 6: Execute using a shell to interpolate dynamic parameters
ENTRYPOINT ["sh", "-c", "mvn test -DsuiteXmlFile=${TEST_SUITE}.xml -Dbrowser=${BROWSER}"]