PAAS
Pronounce as a Service

Important Links
Read before u see video recording - https://paasstorage.blob.core.windows.net/newcontainer/Readtoknow.txt
Video recording - https://paasstorage.blob.core.windows.net/newcontainer/PAAS_FinalRecording.mp4
war file - https://paasstorage.blob.core.windows.net/newcontainer/paas.war

To start the App, there are several ways

IMP Note : Our clould deploy to azure failed as we did not have neccesary permissions to attach acr to aks cluster. 
Also we faced other issues, so keeping it in local for now.

Option1: Run it as a Spring boot app in any preferred IDE
Then once the app is up, enter http://localhost:<port number>/paas/views/index.html
It will redirect to login page.
Now for existing users below use credentials, automatically it will let you run all features.
  {
  "username": "2002443",
  "password": pass31
}
  If new users needs to be created. Meta data needs to be added, otherwise it will fail
  Once meta data in T_USER table is entered, Open swagger page to get the jwt token
  http://localhost:8080/paas/swagger-ui/index.html#/
  Invoke user api, with below body.
  {
  "username": "XXXXX",
  "password": "*****"
}
  Now use the jwt token from the response and you should be able to access all features.
  
Option2: Run maven clean install
  Generate war file, deploy to locally installed tomcat.

