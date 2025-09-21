def call(String iamge, String tag){
  echo "this is building the code"
  sh "docker build -t ${image}:${tag} . --network=host"
}
