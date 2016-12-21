node {
    node {
        stage('build') {
            checkout scm

            withMaven(maven: 'M3') {
                sh 'mvn clean install'
            }
        }

        stage('test') {

        }

        stage('deploy') {

        }
    }
}