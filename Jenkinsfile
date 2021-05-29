pipeline {
    agent any
    stages {
        stage('Print') {
            steps {
                echo 'Hello world!'
            }
        }

        stage('Testing') {
            steps {
                echo 'Starting testing'
                sh 'gradlew test'
                echo 'Stopped testing'
            }
        }
    }
}