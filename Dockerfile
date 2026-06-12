# Step 1: Fetch an official lightweight Linux image pre-loaded with Maven and OpenJDK 17
FROM maven:3.9-eclipse-temurin-21

# Step 2: Create a workspace folder inside that Linux container
WORKDIR /home/automation

# Step 3: Copy your pom.xml into the folder to prepare for download
COPY pom.xml .

# Step 4: Tell Maven to pre-download all your dependencies (Selenium, TestNG, Jackson, etc.)
RUN mvn dependency:go-offline

# Explicitly copy all your main and test source architecture blueprints
COPY src ./src
COPY *.xml ./

# FORCE COPY fallback checks for properties and JSON resource files just in case
COPY src/main/resources ./src/main/resources
COPY src/test/resources ./src/test/resources

# --- CHANGE HERE: EXECUTE USING A SHELL TO INTERPOLATE DYNAMIC PARAMETERS ---
# We use "sh -c" so the container environment can read $TEST_SUITE and $BROWSER variables at runtime.
ENTRYPOINT ["sh", "-c", "mvn test -DsuiteXmlFile=${TEST_SUITE}.xml -Dbrowser=${BROWSER}"]