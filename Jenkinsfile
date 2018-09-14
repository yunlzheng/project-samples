pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        withDockerRegistry([credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/']) {
            sh '''cd containerization-spring-with-helm
docker build -t yunlzheng/spring-sample .'''
        sh '''docker push yunlzheng/spring-sample'''
        }
      }
    }
  }
}
