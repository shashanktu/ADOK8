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
 git branch: 'master', credentialsId: 'github_login', url: 'https://github.com/Pocaccount1/ADOK8.git'   
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
      // scp -r /var/lib/jenkins/workspace/JOB/ado.tar ubuntu@35.78.214.230:/home/ubuntu
    """
}

 stage("Deploy to VM"){
    sshagent(['SSH-key']){
         sh 'sudo scp  /var/lib/jenkins/workspace/JOB/ado.tar ubuntu@35.78.214.230:/home/ubuntu'
    }
 }
 
}


