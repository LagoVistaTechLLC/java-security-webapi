CREATE TABLE "Users" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Display Name" VARCHAR(128) NOT NULL,
	"User Name" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(256) NOT NULL,
	"Mobile Phone" VARCHAR(16),
	
	"Password Date" DATE NOT NULL,
	"Password Salt" BYTEA NOT NULL,
	"Password Hash" BYTEA NOT NULL,
	"Password Iterations" INT NOT NULL,
	
	"Disabled" BOOLEAN NOT NULL
);

INSERT INTO "Users" (
	"GUID", "Version",
	"Display Name", "User Name", "Email Address", "Mobile Phone",
	"Password Date", "Password Iterations",
	"Password Salt", 
	"Password Hash",
	"Disabled"
) VALUES (
	'07e938003af84c6a99015528dc8ecdf0', 1,
	'System Administrator', 'administrator', 'administrator@localhost', NULL,
	CURRENT_DATE, 1000000,
	DECODE('15870178a49ec45b42a46f5c94154d8ccad4810ae0c7a1565e848f2a07c4004e', 'HEX'),
	DECODE('df3ef5f3c7807080857ebe1ee1aa45bffaaf39bd32c4c0b6c9314555c4d5bca898e7b2c1f70c3211351a0729021f35b3b9c26444ce1bd9d192b5cdf664fda4e0', 'HEX'),
	FALSE
);

CREATE TABLE "Groups" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,

	"Display Name" VARCHAR(128) NOT NULL UNIQUE,
	"Email Address" VARCHAR(256),
	
	"Disabled" BOOLEAN NOT NULL
);

INSERT INTO "Groups" (
	"GUID", "Version",
	"Display Name", "Email Address",
	"Disabled"
) VALUES (
	'38500a92c26f4491844ddbc84a485fe9', 1,
	'Administrators', 'administrators@localhost',
	FALSE
);
INSERT INTO "Groups" (
	"GUID", "Version",
	"Display Name", "Email Address",
	"Disabled"
) VALUES (
	'977f148f173f4b97aaf86268f052b984', 1,
	'Everyone', 'everyone@localhost',
	FALSE
);

CREATE TABLE "Memberships" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,

	"Users GUID" UUID NOT NULL,
	"Groups GUID" UUID NOT NULL,
	
	"Included" BOOLEAN
);

ALTER TABLE "Memberships" ADD CONSTRAINT "Memberships > User" FOREIGN KEY ("Users GUID") REFERENCES "Users" ("GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "Memberships > Groups" FOREIGN KEY ("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "Membership Unique User and Group" UNIQUE ("Users GUID", "Groups GUID");

INSERT INTO "Memberships" ("GUID", "Version", "Users GUID", "Groups GUID")
VALUES('4ee775078b5b4b14aa9315a28f2d29f7', 1, '07e938003af84c6a99015528dc8ecdf0', '38500a92c26f4491844ddbc84a485fe9');

CREATE TABLE "Securables" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Display Name" VARCHAR(255) UNIQUE,
	
	"Disabled" BOOLEAN NOT NULL
);

CREATE TABLE "Actions" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Display Name" VARCHAR(255) UNIQUE NOT NULL,
	"Abbreviation" VARCHAR(3) UNIQUE NOT NULL
);

CREATE TABLE "Securable Actions" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Securables GUID" UUID NOT NULL,
	"Actions GUID" UUID NOT NULL
);
ALTER TABLE "Securable Actions" ADD CONSTRAINT "Allowed Actions > Securable" FOREIGN KEY ("Securables GUID") REFERENCES "Securables" ("GUID");
ALTER TABLE "Securable Actions" ADD CONSTRAINT "Allowed Securables > Action" FOREIGN KEY ("Actions GUID") REFERENCES "Actions" ("GUID");
ALTER TABLE "Securable Actions" ADD CONSTRAINT "Unique Securable Action" UNIQUE ("Securables GUID", "Actions GUID");

CREATE TABLE "Permissions" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Groups GUID" UUID NOT NULL,
	"Securable Actions GUID" UUID NOT NULL
);
ALTER TABLE "Permissions" ADD CONSTRAINT "Permissions > Securable Action" FOREIGN KEY ("Securable Actions GUID") REFERENCES "Securable Actions" ("GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Permissions > Group" FOREIGN KEY ("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Unique Permissions" UNIQUE ("Groups GUID", "Securable Actions GUID");

CREATE TABLE "Settings" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Users GUID" UUID,
	
	"Key" VARCHAR(128) NOT NULL UNIQUE,
	"Value" VARCHAR(1024) NOT NULL,
	
	"Server Side Only" BOOLEAN NOT NULL
);
INSERT INTO "Settings" ("GUID", "Version", "Users GUID", "Key", "Value", "Server Side Only")
VALUES ('617f24ef2e504836b2c249a90001d17c', 1, NULL, 'Minimum Password Length', '8', FALSE);
INSERT INTO "Settings" ("GUID", "Version", "Users GUID", "Key", "Value", "Server Side Only")
VALUES ('7f9c42c4b2264892887b4b31e944b568', 1, NULL, 'Maximum Password Age in Days', '45', FALSE);
INSERT INTO "Settings" ("GUID", "Version", "Users GUID", "Key", "Value", "Server Side Only")
VALUES ('80b3c9a9a561416d89bc2718931557c8', 1, NULL, 'Minimum Password Complexity', '3', FALSE);


