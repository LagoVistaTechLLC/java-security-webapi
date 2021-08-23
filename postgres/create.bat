rmdir /q /s %cd%\data

docker create ^
	--name lvt-pg-security ^
	-e POSTGRES_USER=postgres ^
	-e POSTGRES_DB=postgres ^
	-e POSTGRES_PASSWORD=postgres ^
	-v "%cd%/data":"/var/lib/postgresql/data" ^
	-p 54321:5432 ^
	postgres