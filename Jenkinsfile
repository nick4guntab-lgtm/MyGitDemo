pipeline {
    agent any 

    parameters {
        choice(
            name: 'ENVIRONMENT', 
            choices: ['QA', 'Staging', 'Dev'], 
            description: 'Select the target environment for testing and deployment'
        )
        choice(
            name: 'TEST_SUITE', 
            choices: ['Regression', 'Smoke', 'Sanity'], 
            description: 'Select the TestNG suite execution profile'
        )
    }

    tools {
       maven '3.9.9'
    }

    stages {
        stage('Clean & Compile') {
            steps {
                echo "Cleaning workspace and compiling framework for ${params.ENVIRONMENT}..."
                bat 'mvn clean compile'
            }
        }

//        stage('Code Quality (Checkstyle/Sonar)') {
  //          steps {
    //            echo 'Running static code analysis to check automation framework quality...'
                // Optional: You can use 'mvn checkstyle:check' or 'mvn sonar:sonar'
      //          bat 'mvn checkstyle:check'
        //    }
        //}

        // Parallel stage lets you split tasks to speed up the pipeline execution time
        stage('Parallel Test Execution') {
            parallel {
                stage('UI Automation Suite') {
                    steps {
                        echo "Launching Web UI ${params.TEST_SUITE} Tests..."
                        bat "mvn test -DsuiteXmlFile=${params.TEST_SUITE}-ui.xml -Denv=${params.ENVIRONMENT}"
                    }
                }
//                stage('API Automation Suite') {
  //                  steps {
    //                    echo "Launching Backend API ${params.TEST_SUITE} Tests..."
      //                  bat "mvn test -DsuiteXmlFile=${params.TEST_SUITE}-api.xml -Denv=${params.ENVIRONMENT}"
        //            }
          //      }
            }
        }

        stage('Deploy Test Results to Dashboard') {
            // This stage only executes if all previous testing stages pass
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Publishing latest framework test reports to your server..."
                // Example deployment step (e.g., uploading html reports to an S3 bucket or internal server)
                // bat 'aws s3 cp target/ExtentReports/ s3://my-test-reports-bucket/ --recursive'
            }
        }
    }

    post {
        always {
            echo 'Archiving test reports...'
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/ExtentReports/**', allowEmptyArchive: true
        }
    }
}