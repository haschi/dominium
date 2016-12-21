node {
    def nodeHome = tool name: 'node-6.9.2', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
    env.PATH = "${nodeHome}/bin:${env.PATH}"

    stage 'check environment'
    sh "node -v"
    sh "npm -v"

    stage 'checkout'
    checkout scm

    stage('build') {
        withMaven(maven: 'M3') {
            sh 'mvn clean install'
        }
    }

    stage('test') {

    }

    stage('deploy') {

    }
}