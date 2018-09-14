pipeline {
  agent any
  stages {
    stage('build') {
      steps {
            sh '''cd containerization-spring-with-helm
                docker build -t yunlzheng/spring-sample:$GIT_COMMIT .'''
      }
    }

    stage('public') {
      steps {
        withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
          sh 'docker push yunlzheng/spring-sample:$GIT_COMMIT'
        }
      }
    }
  }
}
