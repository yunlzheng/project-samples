pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
            sh '''cd containerization-spring-with-helm
docker build -t yunlzheng/spring-sample .'''
        sh '''docker push yunlzheng/spring-sample'''
        }
      }
    }
  }
}
