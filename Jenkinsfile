// Jenkinsfile
pipeline {
    agent any
    environment {
        SONAR_HOST = "http://localhost:9000"
        SONAR_TOKEN = credentials('sonarqube-token') // Store token in Jenkins credentials
        PROJECT_KEY = "vulnerable-app"
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-repo/vulnerable-java-app.git'
            }
        }
        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        sonar-scanner \
                          -Dsonar.projectKey=${PROJECT_KEY} \
                          -Dsonar.java.binaries=. \
                          -Dsonar.sources=src
                    """
                }
            }
        }
        stage('Check Quality Gate') {
            steps {
                script {
                    // Wait for analysis to complete
                    def analysisUrl = sh(
                        script: "curl -s ${SONAR_HOST}/api/ce/task?id=${SONAR_SCANNER_TASK_ID} | jq -r .task.analysisId",
                        returnStdout: true
                    ).trim()
                    
                    // Check Quality Gate status via API
                    def gateStatus = sh(
                        script: "curl -s -u ${SONAR_TOKEN}: ${SONAR_HOST}/api/qualitygates/project_status?analysisId=${analysisUrl} | jq -r .projectStatus.status",
                        returnStdout: true
                    ).trim()
                    
                    if (gateStatus != 'OK') {
                        error "Quality Gate failed: Status = ${gateStatus}"
                    }
                }
            }
        }
    }
}
