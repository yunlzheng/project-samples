pipeline {
  agent any

  environment {
        HELM_USERNAME = credentials('HELM_USERNAME')
        HELM_PASSWORD = credentials('HELM_PASSWORD')
  }

  stages {
    stage('build') {
      steps {

        dir('containerization-spring-with-helm') {
          sh 'docker build -t yunlzheng/spring-sample:$GIT_COMMIT .'
        }

      }
    }

    stage('push image') {
      steps {

        withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
          sh 'docker push yunlzheng/spring-sample:$GIT_COMMIT'
        }

        script {
          def filename = 'containerization-spring-with-helm/chart/values.yaml'
          def data = readYaml file: filename
          data.image.tag = env.GIT_COMMIT
          sh "rm $filename"
          writeYaml file: filename, data: data
        }

      }
    }

    stage('deploy/update helm') {
      steps {
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm lint'
            sh 'helm upgrade --install --name=spring-app .'
          }
        }
      }
    }

    stage('public helm') {
      steps {
        dir('containerization-spring-with-helm') {
          sh 'helm push chart https://repomanage.rdc.aliyun.com/helm_repositories/26125-play-helm --username=$HELM_USERNAME --password=$HELM_PASSWORD  --version=$GIT_COMMIT'
        }
      }
    }

  }
}
