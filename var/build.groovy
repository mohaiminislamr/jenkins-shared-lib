def call(String tag){
  echo "this is building the code"
  sh "docker build -t ${tag} ."
}
