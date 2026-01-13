pipeline {
    agent any

    stages {
        stage("CI/CD start") {
            steps {
                script {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    
                    withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_WEBHOOK')]){
                        sh """
                        curl -X POST \
                            -H "Content-Type: application/json" \
                            -d '{
                                    "username": "Jenkins",
                                    "content": "🚀 **배포 시작입니다**\\n프로젝트: Board-Server\\n브랜치: release\\n요청자: ${Author_ID} (${Author_Name})\\n빌드 번호: #${BUILD_NUMBER}\\n---"
                                }' \
                            ${DISCORD_WEBHOOK}
                        """
                    }
                }
            }
        }

        stage("Clone Repository") {
            steps {
                echo '클론 시작'
                git branch: 'release', credentialsId: 'git-user', url: 'https://github.com/ajeng518/Board-Server.git'
                echo '클론 끝'
            }
        }
        

        stage("Build BE JAR to Docker Image") {
            steps {
                echo '백엔드 도커 이미지 빌드 시작!'
                dir("./BE") {
                    // 빌드된 JAR 파일을 Docker 이미지로 빌드
                    sh "docker build -t ajeng518/board-gcp-be:latest ."
                }
                echo '백엔드 도커 이미지 빌드 완료!'
            }
        }

        stage("Push to Docker Hub-BE") {
            steps {
                echo '백엔드 도커 이미지를 Docker Hub에 푸시 시작!'
                withCredentials([usernamePassword(credentialsId: 'board-docker', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                }
                dir("./BE") {
                    sh "docker push ajeng518/board-gcp-be:latest"
                }
                echo '백엔드 도커 이미지를 Docker Hub에 푸시 완료!'
            }
        }

        stage("Deploy to E2-BE") {
            steps {
                echo '백엔드 EC2에 배포 시작!'
                // 여기에서는 SSH 플러그인이나 SSH 스크립트를 사용하여 EC2로 연결하고 Docker 컨테이너 실행
                
                sh "docker rm -f backend"
                sh "docker rmi ajeng518/board-gcp-be:latest"
                sh "docker image prune -f"
                sh "docker pull ajeng518/board-gcp-be:latest && docker run -d -p 8080:8080 --name backend ajeng518/board-gcp-be:latest"
                
                echo '백엔드 EC2에 배포 완료!'
            }
        }
    }

    post{
        success{
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_WEBHOOK')]){
                sh """
                curl -X POST \
                    -H "Content-Type: application/json" \
                    -d '{
                            "username": "Jenkins",
                            "content": "✅ **🎉 배포 성공 🎉**\\n프로젝트: Board-Server\\n빌드 번호: #${BUILD_NUMBER}\\n---"
                        }' \
                    ${DISCORD_WEBHOOK}
                """
            }
        }
        failure{
            withCredentials([string(credentialsId: 'discord-webhook', variable: 'DISCORD_WEBHOOK')]){
                sh """
                curl -X POST \
                    -H "Content-Type: application/json" \
                    -d '{
                            "username": "Jenkins",
                            "content": "❌ ** 배포 실패 ㅜ^ㅜㅜ**\\n프로젝트: Board-Server\\n빌드 번호: #${BUILD_NUMBER}\\n---"
                        }' \
                    ${DISCORD_WEBHOOK}
                """
            }
        }
    }
}
