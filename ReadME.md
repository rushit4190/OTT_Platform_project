
## Backend Application for OTT_Platform project - (with SpringBoot)

### Functional requirements -

* Application to have capability to add/store desired data through set of APIs.
* Data to include - Movies, TvShows and their respective Seasons and Episodes, along with their ratings, genres, language, poster, trailer, cast_crew, etc.
* Working set of APIs to fetch/view detail movie and tvshow data, curated lists of movies and tvshows based on different categories.


### OTT_Platform database design -
![OTT_Platform_project database.png](src%2Fmain%2Fresources%2Fstatic%2FOTT_Platform_project%20database.png)


- API definition can be found in [Postman_files](src/main/resources/static/Postman_files) folder.
- MySQL is used as database.
- SpringBoot framework is used for writing the code.

### AWS Deployment

- Amazon RDS instance of MySQL created. MySQL workbench used to view/handle data.
- [Application properties](src/main/resources/application.properties) was dully updated with required configurations for MySQL database.
- Docker image built using [Docker file](Dockerfile) and pushed into a repository on dockerhub. Check [deploy_docker.sh](scripts/deploy_docker.sh).
- Amazon BeanStalk environment with appropriate service role and instance profile created.
- [Docker run](Dockerrun.aws.json) file was created and uploaded onto the above created beanstalk environment.
- Currently, application environment is running at - http://ott-platform-project-env.eba-duv2drzm.us-east-1.elasticbeanstalk.com/ottplatform/v1/