SELECT * FROM "Groups"
WHERE 
	"GUID" IN ( SELECT "Groups GUID" FROM "Memberships" WHERE "Users GUID" = @UsersGuid )
	OR "GUID" = '977f148f173f4b97aaf86268f052b984'