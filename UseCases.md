
**API Use Cases**

**Authenticate User API:** 
1. APIs are protected by OIDC Plugin on apache (RP layer).
2. User information is passed to the API via the AUTH_USER header.

**User Application List API:**
1. The API should retrieve a list of applications owned by a specific user.
2. The API should allow users to remove themselves as application owners.
3. The API should support pagination for easy navigation between lists.
4. The API should provide search and filtering capabilities for applications.

**Edit Application API:**
1. The API should allow users to edit fields of SAML and OIDC applications that they own.
2. The API should update the validation status based on specific conditions (e.g., "Decommission Now" status, ESP ID + Owner Group + Audience Confirmed).

**Admin Application Access API:**
1. The API should provide admin users access to all applications.
2. The API should allow admin users to edit application details.

**Support for different App Types**
1. The API should support different application types (e.g., SAML, OIDC, etc.).
