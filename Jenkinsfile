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
        
        success {
            // Fires automatically only if all stages pass successfully
            emailext (
                subject: "SUCCESS: Job '${env.JOB_NAME}' [Build #${env.BUILD_NUMBER}]",
                body: """<h3>Build Execution Passed Successfully</h3>
                         <p>The automation suite ran clean without regression drops.</p>
                         <p><b>Job Profile:</b> ${env.JOB_NAME}<br>
                         <b>Build Number:</b> #${env.BUILD_NUMBER}<br>
                         <b>Console Log URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                         <p><i>This is an automated pipeline status notification.</i></p>""",
                to: 'nick4guntab@gmail.com',
                mimeType: 'text/html'
            )
        }

        failure {
            // Fires automatically if ANY stage compiles with an error or tests drop
            emailext (
                subject: "CRITICAL FAILURE: Job '${env.JOB_NAME}' [Build #${env.BUILD_NUMBER}]",
                body: """<h3>Build Execution Failed</h3>
                         <p>Errors were intercepted during compilation or test assertion loops.</p>
                         <p><b>Job Profile:</b> ${env.JOB_NAME}<br>
                         <b>Build Number:</b> #${env.BUILD_NUMBER}<br>
                         <b>Target Test Environment:</b> ${params.ENVIRONMENT}<br>
                         <b>Review Test Failure Log Here:</b> <a href="${env.BUILD_URL}testngreports/">TestNG Execution Dashboard</a></p>
                         <p>Please inspect the attached build logs or console logs to identify the locator mismatch or system crash.</p>""",
                to: 'nick4guntab@gmail.com',
                mimeType: 'text/html'
            )
        }
    }
}