pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh '''cd containerization-spring-with-helm
docker build -t registry.cn-hangzhou.aliyuncs.com/k8s-mirrors/spring-sample .'''
        sh '''docker push registry.cn-hangzhou.aliyuncs.com/k8s-mirrors/spring-sample'''
      }
    }
  }
}
