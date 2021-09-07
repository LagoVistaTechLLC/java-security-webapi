SELECT DISTINCT * 
FROM "Securable Actions"
WHERE 
	"GUID" IN (
		SELECT "Securable Actions GUID" FROM "Permissions" 
		WHERE 
			"Groups GUID" = @EveryonesGuid
			OR "Groups GUID" IN (
				SELECT "Groups GUID" FROM "Memberships" 
				WHERE "Users GUID" = @UsersGuid
			)	
	)