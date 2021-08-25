CREATE TABLE "Users" (
	"GUID" UUID PRIMARY KEY,
	"Version" BIGINT NOT NULL,
	
	"Display Name" VARCHAR(128) NOT NULL,
	"User Name" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(256) NOT NULL,
	"Mobile Phone" VARCHAR(16),
	
	"Password Date" DATE,
	"Password Salt" BYTEA,
	"Password Hash" BYTEA,
	"Password Iterations" INT,
	
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
	CURRENT_DATE, 10000,
	DECODE('640ef99aa80844eaaeeae7b35ead3d221b9c6e1eea0df7339739369b93450ddd', 'HEX'),
	DECODE('4cfbc5fb150865ee4a5a109ce29e868253d31aa6613cccc774bbc5ad1c0b3893b151a59eb0ea02890e59a2c73249b6d2a5c3197c8cdaf3244ee0d417e80c8a45', 'HEX'),
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
INSERT INTO "Actions" ("GUID", "Version", "Display Name", "Abbreviation")
VALUES 
	('b1f9efe539ec460e94d1880295799b3f', 1, 'Create', 'C'),
	('2681aeff2e4d4943a20748abd784a67e', 1, 'Read', 'R'),
	('4cfaa8968bb24248afada78368d96fc0', 1, 'Update', 'U'),
	('1027f346e1074d8fbc4174520c51b7b5', 1, 'Delete', 'D');

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


