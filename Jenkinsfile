pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        withDockerRegistry([credentialsId: 'aliyuncr', url: 'https://index.docker.io/v1/']) {
            sh '''cd containerization-spring-with-helm
docker build -t yunlzheng .'''
        sh '''docker push yunlzheng/spring-sample'''
        }
      }
    }
  }
}
