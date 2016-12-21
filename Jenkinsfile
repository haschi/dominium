node {

    def nodeHome = tool name: 'node-6.9.2'
    sh "${nodeHome}/bin/node -v"

    stage('build') {
        checkout scm
        echo "Hello World"
        withMaven(maven: 'M3') {
            sh 'mvn clean install'
        }
    }

    stage('test') {

    }

    stage('deploy') {

    }
}