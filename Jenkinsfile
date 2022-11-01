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
  git credentialsId:'Github_Login', url: 'https://github.com/leenatejababu/ADOK8.git'   
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
       docker save -o ado.tar ${IMAGE_NAME}:${TAG_NAME}
      """
}

 stage("Deploy to VM"){
  def dockerRun = 'docker run -p 8080:8080 -d ${IMAGE_NAME}:${TAG_NAME}'
    sshagent(['SSH-key']){
     sh "ssh -o StrictHostKeyChecking=no ubuntu@3.112.124.119 ${dockerRun}"
    }
 }
 
}


