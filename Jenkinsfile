pipeline {
  agent any

  environment {
        HELM_USERNAME = credentials('HELM_USERNAME')
        HELM_PASSWORD = credentials('HELM_PASSWORD')
  }

  stages {

    stage('Build And Test') {
      steps {

        dir('containerization-spring-with-helm') {
          sh 'docker build -t yunlzheng/spring-sample:$GIT_COMMIT .'
        }

      }
    }

    stage('Publish Docker And Helm') {
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

        script {
          def filename = 'containerization-spring-with-helm/chart/Chart.yaml'
          def data = readYaml file: filename
          data.version = env.GIT_COMMIT
          sh "rm $filename"
          writeYaml file: filename, data: data
        }

        dir('containerization-spring-with-helm') {
          sh 'helm push chart https://repomanage.rdc.aliyun.com/helm_repositories/26125-play-helm --username=$HELM_USERNAME --password=$HELM_PASSWORD  --version=$GIT_COMMIT'
        }

      }
    }

    stage('Deploy To Dev') {
      input 'Do you approve deployment dev?'
      steps {
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm upgrade spring-app --install --namespace dev .'
          }
        }
      }
    }

    stage('Deploy To Stageing') {
      input 'Do you approve staging?'
      steps {
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm upgrade spring-app --install --namespace staging .'
          }
        }
      }
    }

    stage('Deploy To Production') {
      input 'Do you approve production?'
      steps {
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm upgrade spring-app --install --namespace production .'
          }
        }
      }
    }

  }
}
