node {

def IMAGE_NAME = params.IMAGE_NAME
def TAG_NAME = params.TAG_NAME
 def TAG_NAME_Latest = params.TAG_NAME_Latest
 def Jfog_Ip = params.Jfog_Ip
 def Jfog_Port = params.Jfog_Port
 def Repository_Key = params.Repository_Key
 def Docker_URL = params.Docker_URL
 
// def docker_login = params.docker_login
// def Dockerhub_URL = params.Dockerhub_URL

stage('Checkout') {
  git branch: 'master', credentialsId:'github', url: 'https://github.com/dpanigrahy2020/ADOK8.git'  
 }


stage('Build') {
    withMaven(jdk: 'java', maven: 'maven') {
        
        println "build is running"
        sh 'mvn -f pom.xml clean package -Dmaven.test.skip=true'
    }
}
//docker build -t eksdemo .
stage('Build Image'){
    sh """        
        docker build -t ${IMAGE_NAME}:${TAG_NAME} .
        docker save -o maven.tar ${IMAGE_NAME}:${TAG_NAME}
        docker tag ${IMAGE_NAME}:${TAG_NAME} ${Docker_URL}:${TAG_NAME}
      """
} 
        
 /*stage('Test'){
            steps {
                 echo 'Empty'
            }
 }*/
 
 /*stage('Push') {
           // steps {
                script{
                        docker.withRegistry('https://670166063118.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:awsECRForeksdemo') {
                        }
                         
            }
        }*/
 
 stage('predeploy'){
              withDockerRegistry(credentialsId: 'ecr:us-east-1:awsECRForeksdemo', url: 'https://670166063118.dkr.ecr.us-east-1.amazonaws.com') {
              docker push ${Docker_URL}:${TAG_NAME}
              }
   
 }
 
 stage('Deploy'){
            steps {
                        sh """aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 670166063118.dkr.ecr.us-east-1.amazonaws.com
                              docker push ${Docker_URL}:latest 
                              kubectl apply -f deployment.yml
                            """
                
            }
        }

/* stage("Deploy to EKS"){
  def dockerRun = "docker run -d -p 9999:9999 --name myapp ${IMAGE_NAME}:${TAG_NAME}"
    sshagent(['SSH-JENKINS']){
        sh "ssh -o StrictHostKeyChecking=no webapps@20.25.118.165 'docker rm -f `docker ps -a -q`'"
        sh "ssh -o StrictHostKeyChecking=no webapps@20.25.118.165 'docker rmi -f `docker images  -q`'"
         sh "rsync -avz /var/lib/jenkins/workspace/JOB/maven.tar webapps@20.25.118.165:/home/webapps"
         sh "ssh -o StrictHostKeyChecking=no webapps@20.25.118.165 'docker load -i maven.tar'"
         sh "ssh -o StrictHostKeyChecking=no webapps@20.25.118.165 'docker images'"
         sh "ssh -o StrictHostKeyChecking=no webapps@20.25.118.165 ${dockerRun}"
    }
 }*/
 }
