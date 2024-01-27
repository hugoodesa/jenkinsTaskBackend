pipeline {
    agent any
    tools {
      maven 'MAVEN_LOCAL'
      jdk 'JAVA_LOCAL'
    }
    stages{
        stage("#1 build") {
            steps {
                bat 'mvn clean package'
            }
        }
        stage("#2 unit tests") {
            steps {
                bat 'mvn test'
            }
        }
    }
}