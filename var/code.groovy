def call(String url, String branch){
  echo "This is clonig the code"
  sh " git clone --branch ${branch} ${url}" 
}
