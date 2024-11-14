pipeline {
    agent any

    environment {
        // SonarQube
        SONARQUBE_SERVER = 'http://192.168.1.66:9000/' // SonarQube server URL
        SONARQUBE_TOKEN = credentials('sonar') // SonarQube token
        SONAR_PROJECT_KEY = 'devops' // Project key in SonarQube

        // Nexus
        NEXUS_Cred = credentials('nexus') // Nexus credentials in Jenkins
        Nexus_Url = 'http://192.168.1.66:8081/repository/maven-releases/' // Nexus URL
    }

    stages {
        stage('Checkout Code from GitHub') {
            steps {
                git branch: 'yasmine', url: 'https://github.com/Louaysghaier/DEVOPS'
            }
        }

        stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Maven Test') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('JaCoCo Report') {
            steps {
                // Generate and publish the JaCoCo coverage report
                jacoco(execPattern: 'target/jacoco.exec')
            }
        }

        stage('Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Starting SonarQube analysis...'
                sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                    -Dsonar.host.url=${SONARQUBE_SERVER} \
                    -Dsonar.login=${SONARQUBE_TOKEN}
                """
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh """
                        mvn deploy -DskipTests \
                        -DaltDeploymentRepository=deploymentRepo::default::${Nexus_Url} \
                        -Dusername=${NEXUS_USER} -Dpassword=${NEXUS_PASSWORD}
                    """
                }
            }
        }

        stage("Build Docker image") {
            steps {
                script {
                    // Build Docker image with a specific tag
                    sh 'docker build -t yasmiinecode/devops-integration .'
                }
            }
        }

        stage('Push image to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerhubpwd')]) {
                        // Connexion à Docker Hub
                        sh "echo ${dockerhubpwd} | docker login -u yasmiinecode --password-stdin"

                        // Pousser l'image vers Docker Hub
                        sh "docker push yasmiinecode/devops-integration"
                    }
                }
            }
        }
        stage('Run Docker Compose') {
    steps {
        script {
            sh 'docker-compose down'  // Stop any existing services first
            sh 'docker-compose up -d' // Start services in detached mode
        }
    }
}
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs for more details.'
        }

    }

}
