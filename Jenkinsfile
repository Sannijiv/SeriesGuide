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
                bat 'gradlew test'
                echo 'Stopped testing'
            }
        }
    }
}