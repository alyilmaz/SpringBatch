pipeline {
    agent any
      environment {
        // Specify environment variables here if needed
        JAVA_HOME = tool(name: 'JDK 11', type: 'jdk') // Adjust according to your Java version
        GRADLE_HOME = tool(name: 'Gradle', type: 'gradle') // Make sure Gradle is installed on Jenkins
        PATH = "${env.PATH}:${GRADLE_HOME}/bin"
    }
    stages {
       stage('Checkout') {
            steps {
                // Checkout the source code from your repository
                git branch: 'master', url: 'https://github.com/alyilmaz/SpringBatch.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Clean and build the project
                    sh './gradlew clean build'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests
                    sh './gradlew test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Package the application (create a JAR file)
                    sh './gradlew bootJar'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                script {
                    // Deployment logic
                    // This might involve copying the JAR file to a server, or deploying to a cloud provider
                    echo 'Deploying the application...'
                }
            }
        }
    }

    post {
        always {
            // Cleanup after the build
            cleanWs()
        }

        success {
            echo 'Build and deployment successful!'
        }

        failure {
            echo 'Build or deployment failed.'
        }
    }
}
