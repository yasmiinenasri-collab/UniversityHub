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

### Clone the Repo 


        git clone https://github.com/yasmiinenasri-collab/UniversityHub.git
        cd UniversityHub

### Build the Project

    mvn clean install
    
Run Locally

    mvn spring-boot:run

Or with Docker:

    docker build -t universityhub .
    docker run -p 8080:8080 universityhub

## Key Points:
- **Simplified** process for cloning, building, and running the application.
- **Simplified CI/CD** with Jenkins, Maven, and Docker.
- **Monitoring** with Prometheus and Grafana, along with artifact management using Nexus.

