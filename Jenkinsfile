pipeline {
  agent any
  stages {
    stage('build') {
      steps {
            sh '''cd containerization-spring-with-helm
                docker build -t yunlzheng/spring-sample:$GIT_COMMIT .'''
      }
    }

    stage('push image') {
      steps {
        withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
          sh 'docker push yunlzheng/spring-sample:$GIT_COMMIT'
        }
      }
    }

    stage('public helm') {
      steps {
        def filename = 'containerization-spring-with-helm/charts/values.yaml'
        def data = readYaml file: filename
        data.image.tag = '0.0.1-$GIT_COMMIT'

        sh "rm $filename"
        writeYaml file: filename, data: data
      }
    }

  }
}
