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


stage('Build Image') {
    sh """
        
        docker build -t ${IMAGE_NAME}:${TAG_NAME} .
        docker save -o ado.tar ${IMAGE_NAME}:${TAG_NAME}
       scp -r /var/lib/jenkins/workspace/JOB/ado.tar ubuntu@35.74.67.102:/root/Leena
    """
}


stage('Publish Image') {
// withCredentials([usernamePassword(credentialsId: 'docker_login', passwordVariable: 'password', usernameVariable: 'username')]) {
  withCredentials([usernamePassword(credentialsId: 'Jfrog_login', passwordVariable: 'password', usernameVariable: 'username')]) {
   sh """
        docker login -u ${username} -p ${password} ${Jfog_Ip}:${Jfog_Port}/${Repository_Key}
        docker tag ${IMAGE_NAME}:${TAG_NAME} ${Jfog_Ip}:${Jfog_Port}/${Repository_Key}/${IMAGE_NAME}:${TAG_NAME_Latest}
        docker push ${Jfog_Ip}:${Jfog_Port}/${Repository_Key}/${IMAGE_NAME}:${TAG_NAME_Latest}
        docker pull ${Jfog_Ip}:${Jfog_Port}/${Repository_Key}/${IMAGE_NAME}:${TAG_NAME_Latest}
    """
   
 /*   sh """
        docker login -u ${username} -p ${password} 
        docker tag ${IMAGE_NAME}:${TAG_NAME} ${IMAGE_NAME}:${TAG_NAME_Latest}
        docker push ${IMAGE_NAME}:${TAG_NAME_Latest}
        docker pull ${IMAGE_NAME}:${TAG_NAME_Latest}
        """ */

    }
}   

stage("Deploy to master") {
       withCredentials([kubeconfigFile(credentialsId: 'Kconfig', variable: 'KUBECONFIG')]) {
    script{
         def docker_image = "${IMAGE_NAME}:${TAG_NAME_Latest}"
             try{   sh 'kubectl get deploy peram-v1'
             
            sh """
            kubectl set image deployment/peram-v1 peram='${docker_image}'
            """
            }
            catch(error){
              sh 'kubectl apply -f adok8.yaml' }
    }
        }
     }
}
