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
      steps {
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm upgrade spring-app-dev --install --namespace=dev --set ingress.host=dev.spring-example.local .'
          }
        }
      }
    }

    stage('Deploy To Stageing') {
      steps {
        input 'Do you approve staging?'
        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm upgrade spring-app-staging --install --namespace=staging --set ingress.host=staging.spring-example.local .'
          }
        }
      }
    }

    stage('Deploy To Production') {
      steps {
        input 'Do you approve production?'

        script {                
            env.RELEASE = input message: 'Please input the release version',
            ok: 'Deploy',
            parameters: [
              [$class: 'TextParameterDefinition', defaultValue: '0.0.1', description: 'Cureent release version', name: 'release']
            ]
        }

        echo 'Deploy and release: $RELEASE'

        script {
          def filename = 'containerization-spring-with-helm/chart/Chart.yaml'
          def data = readYaml file: filename
          data.version = env.RELEASE
          sh "rm $filename"
          writeYaml file: filename, data: data
        }

        dir('containerization-spring-with-helm') {
          dir('chart') {
            sh 'helm lint'
            sh 'helm upgrade spring-app-prod --install --namespace=production --set ingress.host=production.spring-example.local .'
          }
          sh 'helm push chart https://repomanage.rdc.aliyun.com/helm_repositories/26125-play-helm --username=$HELM_USERNAME --password=$HELM_PASSWORD  --version=$RELEASE'
        }

      }
    }

  }
}
