docker-compose up -d
docker-compose exec broker kafka-topics --create --topic OPERATION --bootstrap-server broker:9092 --replication-factor 1 --partitions 1
docker-compose exec broker kafka-console-consumer --topic OPERATION --bootstrap-server broker:9092 --property print.key=true --property key.separator="-"


