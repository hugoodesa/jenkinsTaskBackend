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
        stage("#3 docker generate image") {
            steps {
                bat 'docker build --no-cache -t back  .'
            }
        }
        stage("#4 docker run image") {
            steps {
                bat 'docker run --name back -e PROFILE=prod --rm -p 9000:9000 back'
            }
        }
    }
}