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
        choice(
            name: 'BROWSER',
            choices: ['Chrome', 'Firefox', 'Edge', 'Chrome-Headless'],
            description: 'Select the required browser or execution type'
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

        stage('Parallel Test Execution') {
            parallel {
                stage('UI Automation Suite') {
                    steps {
                        echo "Launching Web UI ${params.TEST_SUITE} Tests on ${params.BROWSER}..."
                        
                        bat "mvn test -DsuiteXmlFile=${params.TEST_SUITE}.xml -Dbrowser=${params.BROWSER.toLowerCase()}"
                    }
                }
            }
        }

        stage('Deploy Test Results to Dashboard') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Publishing latest framework test reports to your server..."
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