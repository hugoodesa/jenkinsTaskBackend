pipeline {
    agent any
    tools {
      maven 'MAVEN_LOCAL'
      jdk 'JAVA_LOCAL'
    }
    stages{
        stage("#1 build") {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage("#2 unit tests") {
            steps {
                bat 'mvn test'
            }
        }
        stage("#3 deploy tomcat") {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'tomcat_user', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
    }
}