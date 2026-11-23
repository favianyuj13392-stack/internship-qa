pipeline {
    agent any

    environment {
        DEPLOY_DIR = '/tmp/qa-deployments'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== [FASE 0] Obteniendo código fuente desde Git ==='
                checkout scm
            }
        }

        stage('Unit Tests') {
            steps {
                echo '=== [FASE 1] Ejecutando 75 Pruebas Unitarias TDD ==='
                dir('backend') {
                    script {
                        if (isUnix()) {
                            sh 'chmod +x mvnw && ./mvnw test'
                        } else {
                            bat 'mvnw.cmd test'
                        }
                    }
                }
            }
            post {
                always {
                    junit testResults: 'backend/target/surefire-reports/*.xml', allowEmptyResults: false
                }
            }
        }

        stage('Build & Package') {
            steps {
                echo '=== [FASE 2] Construyendo Ejecutable .jar ==='
                dir('backend') {
                    script {
                        if (isUnix()) {
                            sh './mvnw package -DskipTests'
                        } else {
                            bat 'mvnw.cmd package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('Deploy DEV/QA') {
            steps {
                echo '=== [FASE 3] Instalando Ejecutable en Ambiente Destino DEV/QA ==='
                script {
                    if (isUnix()) {
                        sh '''
                            mkdir -p ${DEPLOY_DIR}
                            cp backend/target/internship-backend-0.0.1-SNAPSHOT.jar ${DEPLOY_DIR}/internship-backend-qa.jar
                            ls -lh ${DEPLOY_DIR}/internship-backend-qa.jar
                            echo "Artefacto instalado exitosamente en DEV/QA (${DEPLOY_DIR}/internship-backend-qa.jar)"
                        '''
                    } else {
                        bat '''
                            if not exist C:\\qa-deployments mkdir C:\\qa-deployments
                            copy backend\\target\\internship-backend-0.0.1-SNAPSHOT.jar C:\\qa-deployments\\internship-backend-qa.jar
                            echo Artefacto instalado exitosamente en DEV/QA (C:\\qa-deployments\\internship-backend-qa.jar)
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo '=== Pipeline finalizado con ÉXITO: 75/75 pruebas unitarias pasadas y ejecutable desplegado ==='
        }
        failure {
            echo '=== Pipeline FALLIDO: Verifique los reportes de pruebas en JUnit ==='
        }
    }
}
