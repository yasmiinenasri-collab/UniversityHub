# UniversityHub

UniversityHub is a web application for managing universities, campuses, and related data. The project integrates modern DevOps tools like **Spring Boot**, **Jenkins**, **Docker**, **Maven**, **SonarQube**, **JaCoCo**, **Prometheus**, **Grafana**, **Nexus**, and **Vagrant** for continuous integration, monitoring, and environment provisioning.

## Technologies Used

- **Spring Boot**: Backend framework
- **Jenkins**: CI/CD automation
- **Docker**: Containerization
- **Maven**: Build automation
- **SonarQube**: Code quality analysis
- **JaCoCo**: Test coverage
- **Prometheus & Grafana**: Monitoring and metrics visualization
- **Nexus**: Artifact management
- **Vagrant**: Virtualized environment setup

## Getting Started

### Prerequisites

- Java 11+
- Maven
- Docker
- Jenkins
- SonarQube
- Prometheus & Grafana
- Nexus
- Vagrant

### Clone the Repo :
    ```bash
    git clone https://github.com/yasmiinenasri-collab/UniversityHub.git
    cd UniversityHub

###Build the Project

    ```bash
    mvn clean install
    Run Locally

    ```bash
    mvn spring-boot:run

Or with Docker:

    ```bash
    docker build -t universityhub .
    docker run -p 8080:8080 universityhub

### Points principaux :
- **Simplification** du processus pour cloner, construire, et exécuter l'application.
- **CI/CD** simplifié avec Jenkins, Maven, et Docker.
- **Surveillance** avec Prometheus et Grafana, ainsi que gestion d'artefacts avec Nexus.
