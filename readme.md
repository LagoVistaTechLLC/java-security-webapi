# Security Web API
This serve as a light weight security framework for developing applications. This system provides general a role based permission system: user, groups, group memberships, securables, actions, the actions allowed on the securables, authentication, verifiable user sessions, and settings.

## License

Copyright © 2021 Lago Vista Technologies LLC

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see https://www.gnu.org/licenses/.

## Lightweight and Complete
One principal of this system versus others, is it's lightweight nature and independence.  If this system were to use oAuth, we would either be dependent on a third party to provide authentication, or required to implement the root certificate servers.

### Certificate Validation
Rather than using the SSL certificate servers, this system will implement public key cryptography to authenticate the session came from the authentication server(s) we have elected to trust.  The security of this model is dependent on keeping the cryptography key of the authentication server(s) secure and secret.

## Role Based Security
This is a role based security system - no permissions are assigned directly to the user but to the groups.  This promotes role based security, and groups should be created for business domain roles - accounting, sales, marketing, etcetera.

### Administrators

There is a special user (the administrator) and a special group (administrators) identified internally by the entities GUID/UUID.  When security checks are executed, if the sessions user is either the administrator user or in the administrators group, the action will be allowed.

### Permissions

The permission system is made up of securables and actions.  Securables are items to be secured - screen, records, reports, API calls, anything can be assigned a securable.  Securables should be "noun" as they are objects we want to secure.  For each securable, we can do different things with - these are called actions.  The system allows us to define ad associate actions with securables.  Actions should be "verbs".  The securable, actions, and association of securable and action are not user editable - they are defined by the application developers as they see fit.  The "permissions" are user editable - they define the allowed actions on a securable for a group.

## Authentication

The system provides authentication using an iterative password hash.

### Sessions

Once authenticated the session will load in memory the data required to validate security requests for the client side, and an encrypted version for use in all subsequent server side requests.

### Session Validation

The server side version will be encrypted using a public/private key combination to validate the authenticity of the session.  This authenticity validation is based on the premise that the private key is not know by anyone but the authentication server - do not communicate the private key to the public or via insecure communication methods.

## Settings

The security application has a settings engine for use by the security system, and can also be used by other associated systems.  Initial settings relate to password policies and email setup.

### Server Side Only

Some settings are considered sensitive - such as the email server password used for unauthenticated users (for password resets).  These "server side only" settings should never be sent to the client side.

### User Defined

The application allows the user to override some settings with values specific to that user.  Those settings will be sent to the client side.
