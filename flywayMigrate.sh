source .env

echo "Running flyway migration script"

echo "Database url: $URL"
echo "Database locations of migration scripts: $LOCATIONS"
echo "Database schema to be migrated: $SCHEMA"

$FLYWAY_PATH/flyway.cmd -url=$URL -user=$USER -password=$PASSWORD -locations=$LOCATIONS -defaultSchema=$SCHEMA migrate
