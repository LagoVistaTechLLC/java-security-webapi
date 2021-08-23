#! /bin/sh

rm -R $PWD/data

docker create \
	--name lvt-pg-security \
	-e POSTGRES_USER=postgres \
	-e POSTGRES_DB=postgres \
	-e POSTGRES_PASSWORD=postgres \
	-v $PWD/data:/var/lib/postgresql/data \
	-p 54321:5432 \
	postgres