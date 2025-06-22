pipeline {
  agent any

  environment {
    MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
  }

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/marius-kengne/playwright-java-cucumber-api.git', branch: 'main'
      }
    }

    stage('Build & Test') {
      steps {
        sh 'mvn clean test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Upload to Xray') {
      steps {
        withCredentials([string(credentialsId: 'xray-api-token', variable: 'XRAY_TOKEN')]) {
          sh """
            curl -H "Content-Type: multipart/form-data" \
                 -H "Authorization: Bearer $XRAY_TOKEN" \
                 -F "file=@target/surefire-reports/TEST-com.example.MyTest.xml" \
                 https://xray.cloud.getxray.app/api/v2/import/execution/junit
          """
        }
      }
    }
  }
}
