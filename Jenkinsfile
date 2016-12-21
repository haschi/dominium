node {
    node {
        def nodeHome = tool name: 'node-v6.9.2'
        env.PATH = "${nodeHome}/bin:${env.PATH}"

        stage('check environment') {
            env.PATH = "${nodeHome}/bin:${env.PATH}"
            sh """
            which node
            node -v
            npm -v
        """
        }

        stage('checkout') {
            checkout scm
        }

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
}