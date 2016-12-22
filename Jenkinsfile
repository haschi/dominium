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

        stage('abnahme') {
            input message: 'PO Abnahme durchführen', parameters: [booleanParam(defaultValue: false, description: 'Durch die Abnahme bestätigt der PO, dass das gebaute Artefakt ausgeliefert werden kann.', name: 'Abgenommen')]
        }

        stage('deploy') {
        }
    }
}