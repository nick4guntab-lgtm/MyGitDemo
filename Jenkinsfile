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
            choices: ['chrome', 'headless-chrome', 'edge'],
            description: 'Select the required browser execution mode'
        )
    }

    stages {
        stage('Environment Cleanup') {
            steps {
                echo "Wiping legacy container instances for clean run in ${params.ENVIRONMENT}..."
                bat 'docker compose down --volumes --remove-orphans'
            }
        }

        stage('Build Container Network') {
            steps {
                echo "Compiling framework code and packaging the Test Runner image..."
                bat 'docker compose build --no-cache'
            }
        }

        stage('Parallel Test Execution') {
            parallel {
                stage('UI Automation Suite') {
                    steps {
                        echo "Launching Web UI ${params.TEST_SUITE} Tests on ${params.BROWSER} inside Docker..."
                        
                        bat """
                            set BROWSER=${params.BROWSER.toLowerCase()}
                            set TEST_SUITE=${params.TEST_SUITE}
                            set ENVIRONMENT=${params.ENVIRONMENT}
                            docker compose up --exit-code-from test-runner
                        """
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
            echo 'Archiving container-mapped test reports...'
            junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: '**/reports/**/*', allowEmptyArchive: true
            
            echo 'Tearing down active running grid infrastructure nodes...'
            bat 'docker compose down'
        }
        
        success {
            emailext (
                subject: "SUCCESS: Job '${env.JOB_NAME}' [Build #${env.BUILD_NUMBER}]",
                body: """<h3>Build Execution Passed Successfully</h3>
                         <p>The automation suite ran clean without regression drops inside Docker.</p>
                         <p><b>Job Profile:</b> ${env.JOB_NAME}<br>
                         <b>Build Number:</b> #${env.BUILD_NUMBER}<br>
                         <b>Console Log URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                         <p><i>This is an automated pipeline status notification.</i></p>""",
                to: 'nick4guntab@gmail.com',
                mimeType: 'text/html'
            )
        }

        failure {
            emailext (
                subject: "CRITICAL FAILURE: Job '${env.JOB_NAME}' [Build #${env.BUILD_NUMBER}]",
                body: """<h3>Build Execution Failed</h3>
                         <p>Errors were intercepted during compilation or containerized test assertion loops.</p>
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