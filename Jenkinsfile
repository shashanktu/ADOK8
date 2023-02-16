node {

 def IMAGE_NAME = params.IMAGE_NAME
 def TAG_NAME = BUILD_NUMBER
 //def Jfog_Ip = params.Jfog_Ip
 //def Jfog_Port = params.Jfog_Port
 def Docker_URL = params.Docker_URL  // this is ECR URL

stage('Checkout') {
  git branch: 'master', credentialsId:'githubdemo', url: 'https://github.com/dpanigrahy2020/ADOK8.git'  
 }

stage('Build') {
    withMaven(jdk: 'java', maven: 'maven') {        
        println "build is running"
        sh 'mvn -f pom.xml clean package -Dmaven.test.skip=true'
    }
}

stage('Build Image'){
    sh """
        echo TAG_NAME is ${TAG_NAME}
        docker build -t ${IMAGE_NAME}:${TAG_NAME} .
        docker tag ${IMAGE_NAME}:${TAG_NAME} ${Docker_URL}:${TAG_NAME}
      """
} 
        
 /*stage('Test'){
            steps {
                 echo 'Empty'
            }
 }*/
 
 stage('predeploy'){
              withDockerRegistry(credentialsId: 'ecr:us-east-1:awsECRForeksdemo', url: 'https://670166063118.dkr.ecr.us-east-1.amazonaws.com') {
               def dockerimage="${Docker_URL}:${TAG_NAME}"
               sh "docker push ${Docker_URL}:${TAG_NAME}"
               sh "docker pull ${Docker_URL}:${TAG_NAME}"
               sh "sed -i 's|dockerimage|${dockerimage}|g' deployment.yml"
              } 
 }
 
 stage('Deploy'){
              withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: 'kubeAWS', namespace: '', serverUrl: '') {
               sh """aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID --profile default && aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY --profile default && aws configure set region "us-east-1" --profile default && aws configure set output "" --profile default
                     /usr/local/bin/aws eks update-kubeconfig --name eksdemo1 --region us-east-1  
                     kubectl config view --minify
                     echo check kubectl access
                     kubectl apply -f deployment.yml
                     kubectl apply -f service.yaml
                     kubectl rollout restart -f deployment.yml
                     """              
               }
           
 }
 }
