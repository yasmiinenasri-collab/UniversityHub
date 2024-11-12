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

        // DockerHub Credentials
        DOCKERHUB_CREDENTIALS = credentials('dockerhub') // DockerHub credentials
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

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t yasmiinecode/devops-integration .'
                }
            }
        }

        stage('Push Image to DockerHub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'dockerhubpwd')]) {
                        sh "echo ${dockerhubpwd} | docker login -u yasmiinecode --password-stdin"
                        sh "docker push yasmiinecode/devops-integration"
                    }
                }
            }
        }

        // New Docker Compose Stage
        stage('Start Docker Compose') {
            steps {
                script {
                    // Run docker-compose to start the application and database containers
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }

        stage('Run Tests against Docker Container') {
            steps {
                script {
                    // Run tests or checks against the running containers
                    // Here we're assuming an HTTP test, change this based on your needs
                    sh 'curl http://localhost:8082/health'
                }
            }
        }

        stage('Stop Docker Containers') {
            steps {
                script {
                    // Stop and remove containers
                    sh 'docker-compose down --remove-orphans -v'
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
