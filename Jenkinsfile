node {
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