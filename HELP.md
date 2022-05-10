# PAAS
## Pronounce As A service


EndPoint to get name pronouncation as base 64:

    localhost:8080/paas/base64/{name}


### Technology Stack
    Spring Boot
    JPA
    FreeTTS (added as external jar can be found in extLibs folder)


##############
UI SET UP
############
1) Navigate to wf-pronounce-me-ui folder
2) Run "npm install"
3) Run "npm run-script build"

Restart Springboot application, We can see the UI pages on http://localhost:8080/paas/views/index.html
or 
Run it as standalone app with command "npm run-script start" and listen in http://localhost:3000/