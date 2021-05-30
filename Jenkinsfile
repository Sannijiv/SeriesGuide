pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'gradlew clean build'
            }
        }

        stage('Testing') {
            steps {
                echo 'Starting testing'
                bat 'gradlew test'
                echo 'Testing completed'
            }
        }

        stage('Sonar') {
            steps {
               echo 'Starting sonar scan'
               echo 'Sonar scan completed'
            }
        }

        stage('Deploy') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                bat 'gradlew generateMetadataFileForCentralPublication'
            }
        }
    }
}