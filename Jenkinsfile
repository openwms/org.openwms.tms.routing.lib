#!groovy

node {
   def mvnHome
   stage('\u27A1 Preparation') {
      git 'git@github.com:openwms/org.openwms.tms.routing.git'
      mvnHome = tool 'M3'
   }
   parallel (
     "default-build": {
       stage('\u27A1 Build') {
          sh "'${mvnHome}/bin/mvn' clean install -Ddocumentation.dir=${WORKSPACE} -Psordocs,sonatype -U"
       }
     },
     "sonar-build": {
       stage('\u27A1 Sonar') {
          sh "'${mvnHome}/bin/mvn' clean org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar -Djacoco.propertyName=jacocoArgLine -Denforcer.skip -Dbuild.number=${BUILD_NUMBER} -Dbuild.date=${BUILD_ID} -Ddocumentation.dir=${WORKSPACE} -Pjenkins"
       }
     }
   )
   stage('\u27A1 Results') {
      archive '**/target/*.jar'
   }
   stage('\u27A1 Heroku Staging') {
      sh "git remote remove heroku"
      sh "git remote add heroku https://:${HEROKU_API_KEY}@git.heroku.com/openwms-routing-service.git"
      sh "git push heroku master -f"
   }
}

