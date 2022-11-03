node {

def IMAGE_NAME = params.IMAGE_NAME
def TAG_NAME = params.TAG_NAME
 def TAG_NAME_Latest = params.TAG_NAME_Latest
 def Jfog_Ip = params.Jfog_Ip
 def Jfog_Port = params.Jfog_Port
 def Repository_Key = params.Repository_Key
 
// def docker_login = params.docker_login
// def Dockerhub_URL = params.Dockerhub_URL

stage('Checkout') {
  git credentialsId:'Gitlab_Login', url: 'https://github.com/leenatejababu/ADOK8'   
 }


stage('Build') {
    withMaven(jdk: 'java', maven: 'maven') {
        
        println "build is running"
        sh 'mvn -f pom.xml clean package -Dmaven.test.skip=true'
    }
}


stage('Build Image'){
    sh """        
        docker build -t ${IMAGE_NAME}:${TAG_NAME} .
        docker save -o maven.tar ${IMAGE_NAME}:${TAG_NAME}
      """
}

 

 stage("Deploy to VM"){
  def dockerRun = "docker run -d -p 8085:8085 docker:v3"
    sshagent(['SSH-JENKINS']){
     sh "rsync -avz /var/lib/jenkins/workspace/Test/maven.tar root@20.115.5.151:/root"
     sh "ssh -o StrictHostKeyChecking=no root@20.115.5.151 'docker load -i maven.tar'"
     sh "ssh -o StrictHostKeyChecking=no root@20.115.5.151 'docker images'"
     sh "ssh -o StrictHostKeyChecking=no root@20.115.5.151 ${dockerRun}"
    }
 }
 
}
