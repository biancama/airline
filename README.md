## Run
To run the application loading the env file

```shell
set -o allexport && source ./.env && set +o allexport && sbt core/run
```
## Tests

To run Unit Tests:

```
sbt test
```

To run Integration Tests we need to run both `PostgreSQL` and `Redis`:

```
docker-compose up
sbt it:test
docker-compose down
```