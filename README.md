#Purpose
The API gateway has multiple roles: routing, identity check and invoke plugins, such as policy enforcement and auditing.

#Overall System Architecture Diagram
![System](./shared/docs/components.jpg)

#System Port/Proxy Usage
| Components | Description                         | Port                                  |
| ---------- | ----------------------------------- | ------------------------------------- |
| IDP        | Identity Decision Point             | proxy /idp (debug 8180)               |
| OAuth2     | Authentication/Authorization Server | 8081 and proxy /oauth (debug 8181)    |
| Registry   | Service Registration                | 8000 and proxy /registry (debug 8189) |
| Gateway    | API Gateway                         | proxy /gateway (debug 8190)           |
| IAMSVC     | IAM REST Service                    | proxy /iamsvc (debug 8191)            |
| TOTP       | MFA                                 | proxy /totp (debug 8192)              |

#Key Technologies of Gateway
- Spring Cloud Zuul
- Spring Cloud Eureka
- Spring Security OAuth2

#Prerequisites
- Install JDK 1.8
- Install Intellij IDEA
- Install docker/docker-compose

#Configuration
| Key                        | Default Value           | Description                          |
| -------------------------- | ----------------------- | ------------------------------------ |
| EUREKA_DEFAULT_SERVICE_URL | http://registry/eureka/ | The url of service registry endpoint |
| GATEWAY_ENABLE_AUTHORIZE   | true                    | Enable authorization or not          |
| GATEWAY_ENABLE_CORS        | false                   | Enable CORS or not                   |

#Build
- Import gradle project
- Run in terminal

```
./gradlew clean build -x test
```

#Run
- Open another terminal and run 

```
docker-compose up -d
```
Or run below in China

```
docker-compose -f docker-compose.cn.yml up -d
```

#Troubleshooting
- In another terminal, use any of the following to troubleshoot containers

```
docker ps
docker logs -f <container>
docker exec -it <container> bash
```

#Testing
- itest folder under each module contains integration tests, and test folder unit tests respectively.
- Use hosts entry to bind when manually testing your changes:

```
127.0.0.1 www.brozton.com brozton.com
127.0.0.1 dev.penroz.com penroz.com
```
- Access UI in web browser (preferably Chrome) at above address

#Reset
- Following will help reset dev environment

```
docker-compose down -v
docker rmi $(docker images --format "{{.Repository}} {{.ID}}" | grep brozton_ | awk '{ print $2 }')
```

#Best practice
- Use your own branch. We follow naming after our first name
- Merge to dev branch via bitbucket web using pull request
- Merge to images branch to kick-start CI process
- Skip pipeline run for trivial non-build changes by adding following to commit message

```
[skip ci]
```

- Run itest and unit tests on modified code before check-in. You can use launcher configs of IDEA or run via:

```
./gradlew itest
```

```
./gradlew test
```

- Make sure to use mocking and isolation of module when writing unit tests. If testing multiple module orchestration then it is not "unit test" and rather it is an "integration test" which belongs in itest

#TODO
- Add an identifier of a micro service in GatewayPermission
- Change URL pattern to 

```
http://api.<main-domain>/servcie-id/**
```

- Gateway cluster
- API Routing for third party micro services